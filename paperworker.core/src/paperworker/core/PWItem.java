package paperworker.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class PWItem {
	
	public abstract Object getValue(String fieldName) throws PWError;
	
	public abstract void setValue(String fieldName, Object value) throws PWError;
	
	protected static Object getValue(Object object, String fieldName) throws PWError {
		PWField fieldInfo = PWField.getField(object.getClass(), fieldName);
		return fieldInfo.getValue(object);
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
	
	public static List<String> getCaptions(Class<? extends PWItem> class_) {
		List<String> captions = new ArrayList<String>();
		for (PWField fieldInfo : getFields(class_)) {
			captions.add(fieldInfo.getCaption());
		}
		return captions;
	}
	
	protected static void setValue(Object object, String fieldName, Object value) throws PWError {
		PWField fieldInfo = PWField.getField(object.getClass(), fieldName);
		fieldInfo.setValue(object, value);
	}

	public static Object parse(Object object, String fieldName, String input) throws PWError {
		PWField fieldInfo = PWField.getField(object.getClass(), fieldName);
		return fieldInfo.parse(input);
	}
}
