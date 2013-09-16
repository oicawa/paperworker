package paperworker.master.core;

import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWField;
import paperworker.core.PWItem;
import paperworker.core.PWQuery;

public class PWMasterQuery {
	
	public static <T extends MasterItem> PWQuery getCreateQuery(Class<T> masterItemClass) {
    	List<PWField> fields = PWItem.getFields(masterItemClass);
    	
    	// Create the fields part of query
    	StringBuffer fieldBuffer = new StringBuffer();
    	for (PWField field : fields) {
    		fieldBuffer.append(",");
    		fieldBuffer.append(field.getName());
    		fieldBuffer.append(" ");
    		fieldBuffer.append(field.getType());
    		if (field.isPrimary()) {
        		fieldBuffer.append(" primary key");
    		}
    	}
    	String fieldQuery = fieldBuffer.substring(1);
    	
    	// Create all query
    	String allQuery = String.format("create table %s (%s);", PWQuery.getTableName(masterItemClass), fieldQuery);
    	
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
    	return query;
	}
	
	public static <T extends MasterItem> PWQuery getSelectQuery(Class<T> masterItemClass) {
		PWQuery query = new PWQuery(String.format("select * from %s;", PWQuery.getTableName(masterItemClass)));
		return query;
	}
	
	public static <T extends MasterItem> PWQuery getSelectQuery(Class<T> masterItemClass, Object primaryKey) throws PWError {
		PWField primaryKeyField = MasterItem.getPrimaryField(masterItemClass);
		PWQuery query = new PWQuery(String.format("select * from %s where %s = ?;", PWQuery.getTableName(masterItemClass), primaryKeyField.getName()));
		query.addValue(primaryKey);
		return query;
	}
	
	public static <T extends MasterItem> PWQuery getUpdateQuery(Class<T> masterItemClass, T masterItem) throws PWError {
    	List<PWField> fieldInfos = PWItem.getFields(masterItemClass);
    	PWField primaryKeyField = MasterItem.getPrimaryField(masterItemClass);
    	
    	// Create the fields part of query
    	StringBuffer fieldsBuffer = new StringBuffer();
    	for (PWField field : fieldInfos) {
     		if (field.isPrimary()) {
    			continue;
     		}
     		fieldsBuffer.append(String.format(",%s = ?", field.getName()));
    	}
    	String fieldsQuery = fieldsBuffer.substring(1);
    	
    	// Create all query
    	String allQuery = String.format("update %s set %s where %s = ?;",
    			PWQuery.getTableName(masterItemClass),
    			fieldsQuery,
    			primaryKeyField.getName());
    	
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
    	for (PWField field : fieldInfos) {
     		if (field.isPrimary()) {
    			continue;
     		}
     		query.addValue(field.getValue(masterItem));
    	}
    	query.addValue(primaryKeyField.getValue(masterItem));
    	return query;
	}
	
	public static <T extends MasterItem> PWQuery getInsertQuery(Class<T> masterItemClass, Object primaryKeyValue) throws PWError {
    	List<PWField> fields = PWItem.getFields(masterItemClass);
    	
    	// Create the fields part of query
    	StringBuffer fieldsBuffer = new StringBuffer();
    	for (PWField field : fields) {
    		if (field.isPrimary()) {
        		fieldsBuffer.append(",?");
    		} else {
    			fieldsBuffer.append(",NULL");
    		}
    	}
    	String fieldsQuery = fieldsBuffer.substring(1);
    	
    	// Create all query
    	String allQuery = String.format("insert into %s values (%s);",
    			PWQuery.getTableName(masterItemClass),
    			fieldsQuery);
    	
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
    	query.addValue(primaryKeyValue);
    	return query;
	}

	
	public static <T extends MasterItem> PWQuery getDeleteQuery(Class<T> masterItemClass, Object primaryKeyValue) throws PWError {
    	PWField primaryKeyField = MasterItem.getPrimaryField(masterItemClass);
    	
    	// Create all query
		String allQuery = String.format("delete from %s where %s = ?;",
				PWQuery.getTableName(masterItemClass),
				primaryKeyField.getName());
		
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
    	query.addValue(primaryKeyValue);
    	return query;
	}
}
