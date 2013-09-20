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

	public static String getCommandName(String packageName) {
		final String prefix = "paperworker.";
		final String suffix = ".ui.command";
		
		// Suffix
		if (!packageName.endsWith(suffix)) {
			return null;
		}
		String commandName = packageName.substring(0, packageName.length() - suffix.length());
		
		// Prefix
		if (commandName.startsWith(prefix)) {
			commandName = commandName.substring(prefix.length());
		}
		
		return commandName;
	}
}
