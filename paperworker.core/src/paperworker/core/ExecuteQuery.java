package paperworker.core;

import java.util.ArrayList;
import java.util.Iterator;

public class ExecuteQuery implements Iterable<String>, Iterator<String> {
	private ArrayList<String> queries = new ArrayList<String>();
	private int index = 0;
	public void add(String format, Object... parameters) {
		String query = String.format(format, parameters);
		queries.add(query);
	}
	
	@Override
	public Iterator<String> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		if (index == queries.size()) {
			return false;
		}
		return true;
	}

	@Override
	public String next() {
		if (index == queries.size()) {
			index = 0;
			return null;
		}
		
		index++;
		return queries.get(index - 1);
	}

	@Override
	public void remove() {
	}
}
