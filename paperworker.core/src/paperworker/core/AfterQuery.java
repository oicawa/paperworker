package paperworker.core;

public interface AfterQuery<T> {
	void run(T result) throws PWError, PWWarning;
}
