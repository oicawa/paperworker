package paperworker.core;

public class PWError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -685110906985740396L;
	protected Exception innerException;
	
	public PWError(Exception e, String format, Object... args) {
		super(String.format(format, args));
		innerException = e;
	}

	public PWError(String format, Object... args) {
		super(String.format(format, args));
	}
}
