package paperworker.master.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import paperworker.core.AfterQuery;
import paperworker.core.PWError;
import paperworker.core.PWField;
import paperworker.core.PWItem;
import paperworker.core.PWUtilities;
import paperworker.core.PWWarning;

public class AfterMasterQuery<T extends MasterItem> implements AfterQuery<ResultSet>{

	private List<T> masterItemList = new ArrayList<T>();
	private Class<T> masterItemClass;
	
	public AfterMasterQuery(Class<T> masterItemClass) {
		this.masterItemClass = masterItemClass;
	}
	
	@Override
	public void run(ResultSet resultSet) throws PWWarning, PWError {
        try {
			while (resultSet.next()) {
				T masterItem = PWUtilities.createInstance(masterItemClass);
	        	List<PWField> fields = PWItem.getFields(masterItemClass);
		    	for (PWField field : fields) {
		    		field.setValue(masterItem, resultSet.getObject(field.getName()));
		    	}
		    	masterItemList.add(masterItem);
			}
		} catch (SQLException e) {
			throw new PWWarning("Getting member list is failed.");
		}
	}

	public List<T> getItemList() {
		return masterItemList;
	}

}
