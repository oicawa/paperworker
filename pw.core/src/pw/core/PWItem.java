/*
 *  $Id: PWItem.java 2013/09/21 3:03:36 Masamitsu Oikawa $
 *
 *  ===============================================================================
 *
 *   Copyright (C) 2013  Masamitsu Oikawa  <oicawa@gmail.com>
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 *  ===============================================================================
 */

package pw.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pw.core.annotation.PWItemBasicInfo;

public abstract class PWItem {
	
	public Object[] getKeyValues(Class<? extends PWItem> itemType, PWField.KeyType keyType) {
		List<PWField> keyFields = PWItem.getFields(itemType, keyType);
		Object[] keyValues = new Object[keyFields.size()];
		for (int i = 0; i < keyFields.size(); i++) {
			PWField keyField = keyFields.get(i);
			keyValues[i] = keyField.getValue(this);
		}
		return keyValues;
	}
	
	public static Object getValue(PWItem item, String fieldName) {
		if (item == null) {
			return null;
		}
		PWField field = PWField.getField(item.getClass(), fieldName);
		return field.getValue(item);
	}
	
	public static String getValueAsString(PWItem item, String fieldName) {
		if (item == null) {
			return null;
		}
		
		PWField field = PWField.getField(item.getClass(), fieldName);
		Object value = field.getValue(item);
		if (value == null) {
			return "";
		} else if (field.isDate()) {
			SimpleDateFormat formatter = new SimpleDateFormat(field.getDateTimeFormat());
			return formatter.format((Date)value);
		} else if (field.isInteger()){
			return String.format("%d", value);
		} else if (field.isReal()){
			return String.format("%f", value);
		} else if (field.isEnum()){
			return value.toString();
		} else {
			return (String)value;
		}
	}
	
	public static List<PWField> getFields(Class<? extends PWItem> itemType) {
		return getFields(itemType, PWField.KeyType.None);
	}
	
	public static List<PWField> getFields(Class<? extends PWItem> itemType, PWField.KeyType keyType) {
		Field[] fields = itemType.getDeclaredFields();
		List<PWField> list = new ArrayList<PWField>();
		for (Field field : fields) {
			int modifiers = field.getModifiers();
			if (Modifier.isFinal(modifiers))
				continue;
			if (Modifier.isStatic(modifiers))
				continue;
			if (!Modifier.isPrivate(modifiers))
				continue;
			field.setAccessible(true);
			
			PWField pwField = PWField.getField(field);
			
			if (keyType == PWField.KeyType.None) {
				list.add(pwField);
			}
			
			if (keyType == PWField.KeyType.Primary && pwField.isPrimary()) {
				list.add(pwField);
			}
			
			if (keyType == PWField.KeyType.Unique && pwField.isUnique()) {
				list.add(pwField);
			}
		}
		return list;
	}
	
	public static List<String> getCaptions(Class<? extends PWItem> type) {
		List<String> captions = new ArrayList<String>();
		for (PWField fieldInfo : getFields(type)) {
			captions.add(fieldInfo.getCaption());
		}
		return captions;
	}
	
	public static void setValue(PWItem item, String fieldName, Object value) {
		PWField fieldInfo = PWField.getField(item.getClass(), fieldName);
		fieldInfo.setValue(item, value);
	}

	public static Object parse(PWItem object, String fieldName, String input) {
		PWField fieldInfo = PWField.getField(object.getClass(), fieldName);
		return fieldInfo.parse(input);
	}

	public static String getCaption(Class<? extends PWItem> type) {
		PWItemBasicInfo info = type.getAnnotation(PWItemBasicInfo.class);
		return info == null ? null : info.caption();
	}

	public static String getTableName(Class<? extends PWItem> type) {
		PWItemBasicInfo info = type.getAnnotation(PWItemBasicInfo.class);
		return info == null ? null : info.tableName();
	}
	
	public static List<PWField> getPrimaryFields(Class<? extends PWItem> itemType) {
		return getFields(itemType, PWField.KeyType.Primary);
	}
	
	public static List<PWField> getUniqueFields(Class<? extends PWItem> itemType) {
		return getFields(itemType, PWField.KeyType.Unique);
	}
}
