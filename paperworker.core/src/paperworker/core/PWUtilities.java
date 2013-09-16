package paperworker.core;

public class PWUtilities {
	public static <T> T createInstance(Class<T> type) throws PWError {
		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			throw new PWError(e, "The class could not be instantiate. [%s]", type.getName());
		} catch (IllegalAccessException e) {
			throw new PWError(e, "The class was accessed illegally. [%s]", type.getName());
		}
	}
}
