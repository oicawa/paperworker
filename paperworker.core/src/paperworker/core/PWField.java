package paperworker.core;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import paperworker.core.annotation.PWFieldBasicInfo;
import paperworker.core.annotation.DateTimeInfo;

public class PWField {
	private Field field;
	
	private PWField(Field field) {
		this.field = field;
	}
	
	public static PWField getField(Class<? extends PWItem> type, String fieldName) throws PWError {
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

	public Object parse(String input) throws PWError {
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
	
	private Object parseAsNumber(String input) throws PWError {
		try {
			return Long.parseLong(input);
		} catch (NumberFormatException e) {
			throw new PWError(e, "Input date value is not number format.");
		}
	}
	
	private Object parseAsDate(String input) throws PWError {
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
		if (input == null) {
			return null;
		} else {
			return Enum.valueOf((Class)field.getType(), input);
		}
	}

	public void setValue(Object object, Object value) throws PWError {
		try {
			if (isEnum()) {
				String input = value == null ? null : value.toString();
				Object creanValue = parseAsEnum(input);
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
	public Object getValue(Object object) throws PWError {
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

	public boolean isPrimary() {
		PWFieldBasicInfo info = field.getAnnotation(PWFieldBasicInfo.class);
		return info.primary();
	}

	@SuppressWarnings("rawtypes")
	public Class getFieldType() {
		return field.getType();
	}
}
