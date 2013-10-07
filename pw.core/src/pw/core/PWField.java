/*
 *  $Id: PWField.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import pw.core.annotation.DateTimeInfo;
import pw.core.annotation.PWFieldBasicInfo;

public class PWField {
	
	public enum KeyType {
		None,
		Primary,
		Unique,
	}
	
	private Field field;
	
	private PWField(Field field) {
		this.field = field;
	}
	
	public static PWField getField(Class<? extends PWItem> type, String fieldName) {
		Field field;
		try {
			field = type.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			throw new PWError(e, "Failed to access the field. [field name: '%s']", fieldName);
		} catch (NoSuchFieldException e) {
			throw new PWError(e, "The field not found. [field name: '%s']", fieldName);
		}
		field.setAccessible(true);
		return getField(field);
	}
	
	public static PWField getField(Field field) {
		return new PWField(field);
	}
	
	public String getName() {
		return field.getName();
	}

	public boolean isInteger() {
		return (field.getType() == int.class || field.getType() == long.class);
	}
	
	public boolean isReal() {
		return (field.getType() == float.class || field.getType() == double.class);
	}
	
	public boolean isNumber() {
		return (isInteger() || isReal());
	}
	
	public boolean isDate() {
		return (field.getType() == Date.class);
	}
	
	public boolean isString() {
		return (field.getType() == String.class);
	}
	
	public boolean isEnum() {
		return field.getType().isEnum();
	}

	public boolean isUuid() {
		return field.getType() == UUID.class;
	}

	public String getType() {
		PWFieldBasicInfo info = field.getAnnotation(PWFieldBasicInfo.class);
		return info.type();
	}

	public String getCaption() {
		PWFieldBasicInfo info = field.getAnnotation(PWFieldBasicInfo.class);
		return info.caption();
	}

	public String getDateTimeFormat() {
		DateTimeInfo datetimeInfo = field.getAnnotation(DateTimeInfo.class);
		return datetimeInfo == null ? null : datetimeInfo.format();
	}

	public Object parse(String input) {
		if (isString()) {
			// TODO: Should be the value checked by 'length' of annotation?
			return input;
		} else if (isNumber()) {
			return parseAsNumber(input);
		} else if (isDate()) {
			return parseAsDate(input);
		} else if (isEnum()) {
			return parseAsEnum(input);
		} else {
			return input;
		}
	}
	
	private Object parseAsNumber(String input) {
		try {
			return Long.parseLong(input);
		} catch (NumberFormatException e) {
			throw new PWError(e, "Input date value is not number format.");
		}
	}
	
	private Object parseAsDate(String input) {
		// TODO: should this field be checked 'Date' field?
		DateTimeInfo dateTimeInfo = field.getAnnotation(DateTimeInfo.class);
		String format = dateTimeInfo.format();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.parse(input);
		} catch (ParseException e) {
			throw new PWError(e, "Input date value like next format. [format: '%s']", format);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object parseAsEnum(String input) {
		return input == null ? null : Enum.valueOf((Class)field.getType(), input);
	}
	
	private Object parseAsUuid(String input) {
		return input == null ? null : UUID.fromString(input);
	}

	public void setValue(Object object, Object value) {
		try {
			if (isEnum()) {
				String input = value == null ? null : value.toString();
				Object creanValue = parseAsEnum(input);
				field.set(object, creanValue);
			} else if (isUuid()) {
				String input = value == null ? null : value.toString();
				Object creanValue = parseAsUuid(input);
				field.set(object, creanValue);
			} else {
				field.set(object, value);
			}
		} catch (IllegalArgumentException e) {
			final String format = 
					"Incorrect combination.\n" +
					"  object type: '%s'\n" +
					"  field type : '%s'\n" +
					"  field name : '%s'\n" +
					"  value type : '%s'\n";
			throw new PWError(e, format, object.getClass().getName(), field.getType().getName(), field.getName(), value.getClass().getName());
		} catch (IllegalAccessException e) {
			throw new PWError(e, "The field couldn't be access. [field name: '%s']", field.getName());
		}
	}
	
	public Object getValue(Object object) {
		try {
			return field.get(object);
		} catch (IllegalArgumentException e) {
			final String format = 
					"Incorrect combination.\n" +
					"  object type: '%s'\n" +
					"  field type : '%s'\n" +
					"  field name : '%s'\n";
			throw new PWError(e, format, object.getClass().getName(), field.getType().getName(), field.getName());
		} catch (IllegalAccessException e) {
			throw new PWError(e, "The field couldn't be access. [field name: '%s']", field.getName());
		}
	}
	
	public String toString(Object value) {
		if (value == null) {
			return "";
		} else if (isDate()) {
			SimpleDateFormat formatter = new SimpleDateFormat(getDateTimeFormat());
			return formatter.format((Date)value);
		} else if (isInteger()){
			return String.format("%d", value);
		} else if (isReal()){
			return String.format("%f", value);
		} else if (isEnum()){
			return value.toString();
		} else if (isUuid()){
			return value.toString();
		} else {
			return (String)value;
		}
	}
	
	public boolean isKey(KeyType keyType)  {
		assert(keyType != KeyType.None);
		return keyType == KeyType.Primary ? isPrimary() : isUnique();
	}

	public boolean isPrimary() {
		PWFieldBasicInfo info = field.getAnnotation(PWFieldBasicInfo.class);
		return info.primary();
	}

	public boolean isUnique() {
		PWFieldBasicInfo info = field.getAnnotation(PWFieldBasicInfo.class);
		return info.unique();
	}

	public Class<?> getFieldType() {
		return field.getType();
	}
}
