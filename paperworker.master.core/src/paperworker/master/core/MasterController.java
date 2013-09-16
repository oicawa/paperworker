package paperworker.master.core;

import java.util.List;

import paperworker.core.PWController;
import paperworker.core.PWFilter;
import paperworker.core.PWError;
import paperworker.core.PWQuery;
import paperworker.core.PWWarning;

public class MasterController<T extends MasterItem> extends PWController {
	
	private Class<T> type;
	
	@SuppressWarnings("unchecked")
	public MasterController(T... types) throws PWError, PWWarning {
		super();
		this.type = (Class<T>)types.getClass().getComponentType();
		String table = PWQuery.getTableName(type);
		if (!accesser.existTable(table)) {
        	create();
		}
	}
	
	private void create() throws PWError, PWWarning {
    	PWQuery query = PWMasterQuery.getCreateQuery(type);
		accesser.execute(query);
	}

	public void add(String itemId) throws PWError, PWWarning {
		PWQuery query = PWMasterQuery.getInsertQuery(type, itemId);
        accesser.execute(query);
	}

	public T get(String itemId) throws PWError, PWWarning {
    	PWQuery query = PWMasterQuery.getSelectQuery(type, itemId);
    	AfterMasterQuery<T> afterQuery = new AfterMasterQuery<T>(type);
        accesser.select(query, afterQuery);
        return afterQuery.getItemList().size() == 0 ? null : afterQuery.getItemList().get(0);
	}

	public List<T> get() throws PWError, PWWarning {
    	PWQuery query = PWMasterQuery.getSelectQuery(type);
    	AfterMasterQuery<T> afterQuery = new AfterMasterQuery<T>(type);
        accesser.select(query, afterQuery);
        return afterQuery.getItemList();
	}
	
	public T[] get(PWFilter<T> filter) throws PWError, PWWarning {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update(T item) throws PWError, PWWarning {
		PWQuery query = PWMasterQuery.getUpdateQuery(type, item);
		accesser.execute(query);
	}
	
	public void delete(String itemId) throws PWError, PWWarning {
		PWQuery query = PWMasterQuery.getDeleteQuery(type, itemId);
        accesser.execute(query);
	}

}
