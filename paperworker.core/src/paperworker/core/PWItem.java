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

package paperworker.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import paperworker.core.annotation.PWItemBasicInfo;

public abstract class PWItem {
	
	public abstract Object getValue(String fieldName) throws PWError;
	
	public abstract void setValue(String fieldName, Object value) throws PWError;
	
	protected static Object getValue(PWItem item, String fieldName) throws PWError {
		PWField fieldInfo = PWField.getField(item.getClass(), fieldName);
		return fieldInfo.getValue(item);
	}
	
	public static List<PWField> getFields(Class<? extends PWItem> class_) {
		Field[] fields = class_.getDeclaredFields();
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
			list.add(PWField.getField(field));
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
	
	protected static void setValue(PWItem item, String fieldName, Object value) throws PWError {
		PWField fieldInfo = PWField.getField(item.getClass(), fieldName);
		fieldInfo.setValue(item, value);
	}

	public static Object parse(PWItem object, String fieldName, String input) throws PWError {
		PWField fieldInfo = PWField.getField(object.getClass(), fieldName);
		return fieldInfo.parse(input);
	}

	public static String getCaption(Class<? extends PWItem> type) throws PWError {
		PWItemBasicInfo info = type.getAnnotation(PWItemBasicInfo.class);
		return info == null ? null : info.caption();
	}

	public static String getTableName(Class<? extends PWItem> type) throws PWError {
		PWItemBasicInfo info = type.getAnnotation(PWItemBasicInfo.class);
		return info == null ? null : info.tableName();
	}
}
