package paperworker.core;

import java.util.ArrayList;
import java.util.List;

public class PWQuery {

	private String query;
	
	private List<Object> values = new ArrayList<Object>();
	
	public PWQuery(String query) {
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	
	public List<Object> getValues() {
		return values;
	}

	public void addValue(Object value) {
		values.add(value);
	}
	
	public static <T extends PWItem> String getTableName(Class<T> masterItemClass) {
		String className = masterItemClass.getSimpleName();
		return className;
	}
}
