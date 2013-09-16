package paperworker.core;

public class PWWarning extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5853067659752473942L;
	protected Exception innerException;

	public PWWarning(Exception e, String format, Object... args) {
		super(String.format(format, args));
		innerException = e;
	}

	public PWWarning(String format, Object... args) {
		super(String.format(format, args));
	}
}
