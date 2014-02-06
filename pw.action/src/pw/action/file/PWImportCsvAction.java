/*
 *  $Id: PWImportCsvAction.java 2013/10/09 6:11:11 masamitsu $
 *
 *  ===============================================================================
 *
 *   Copyright (C) 2013  Masamitsu Oikawa  <oicawa@gmail.com>
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 *  ===============================================================================
 */

package pw.action.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pw.core.PWAction;
import pw.core.PWError;
import pw.core.PWField;
import pw.core.PWField.KeyType;
import pw.core.PWUtilities;
import pw.core.accesser.PWAccesser;
import pw.core.accesser.PWQuery;
import pw.core.csv.PWCsvFile;
import pw.core.item.PWItem;
import pw.core.table.PWTable;
import pw.core.table.PWTableColumns;
import pw.core.table.PWTableRow;

/**
 * @author masamitsu
 *
 */
public class PWImportCsvAction extends PWAction {

	private Class<? extends PWItem> type;
	
	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#parseArguments(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void parseSettingParameters(String[] settingParameters) {
		if (settingParameters.length != 1) {
			throw new PWError("Required 1 parameter as the target item.");
		}
		
		type = (Class<? extends PWItem>)PWUtilities.getClass(settingParameters[0]);
	}

	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#run(java.lang.Object[])
	 */
	@Override
	public Object run(Object... objects) {
		if (objects.length != 1) {
			throw new PWError("Required the 3rd argument as an import file path.");
		}
		
		// Create file
		String importFilePath = (String)objects[0];
		PWCsvFile csvFile = new PWCsvFile();
		csvFile.read(importFilePath);
		PWTable table = csvFile.getTable();
		
		// TODO: Compare the table columns which match to the target item fields.
		
		// Get primary keys and indexes
		List<PWField> keyFields = PWItem.getFields(type, KeyType.Primary);
		int[] keyIndexes = new int[keyFields.size()];
		Arrays.fill(keyIndexes, -1);
		PWTableColumns columns = table.getColumns();
		for (int i = 0; i < keyFields.size(); i++) {
			PWField keyField = keyFields.get(i);
			for (int j = 0; j < columns.getCount(); j++) {
				if (columns.getName(j).toUpperCase().equals(keyField.getName().toUpperCase())) {
					keyIndexes[i] = j;
					break;
				}
			}
		}
		
		// Update rows
		List<PWQuery> queries = new ArrayList<PWQuery>();
		for (PWTableRow row : table.getRows()) {
			// Get key values
			Object[] keyValues = new Object[keyFields.size()];
			for (int keyIndex : keyIndexes) {
				keyValues[keyIndex] = row.getValue(keyIndex);
			}
			
			// Create item
			PWItem item = (PWItem)PWUtilities.createInstance(type);
			List<PWField> fields = PWItem.getFields(type);
			for (int i = 0; i < fields.size(); i++) {
				PWField field = fields.get(i);
				PWItem.setValue(item, field.getName(), row.getValue(i));
			}
			
			// Create query
			queries.add(getQuery(item));
		}
		
		PWAccesser.getDefaultAccesser().execute(queries.toArray(new PWQuery[0]));
		
		return null;
	}

	public static PWQuery getQuery(PWItem item) {
    	List<PWField> fields = PWItem.getFields(item.getClass());
    	
    	// Create the fields part of query
    	StringBuffer fieldsBuffer = new StringBuffer();
    	for (int i = 0; i < fields.size(); i++) {
    		fieldsBuffer.append(PWQuery.COMMA);
    		fieldsBuffer.append("?");
    	}
    	String fieldsQuery = fieldsBuffer.substring(PWQuery.COMMA.length());
    	
    	// Create all query
    	String allQuery = String.format("merge into %s values (%s);", PWQuery.getTableName(item.getClass()), fieldsQuery);
    	
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
    	for (PWField field : fields) {
    		Object keyValue = field.getValue(item);
        	query.addValue(keyValue);
    	}
    	
    	return query;
	}
}
