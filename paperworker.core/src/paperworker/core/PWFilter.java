package paperworker.core;

public interface PWFilter<T> {
	boolean isMatch(T item);
}
