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
