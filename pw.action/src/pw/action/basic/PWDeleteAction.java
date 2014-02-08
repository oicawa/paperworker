/*
 *  $Id: DeleteAction.java 2013/09/28 11:45:50 masamitsu $
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

package pw.action.basic;

import java.util.ArrayList;
import java.util.List;

import pw.core.PWError;
import pw.core.PWField;
import pw.core.accesser.PWAccesser;
import pw.core.accesser.PWQuery;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
public class PWDeleteAction extends AbstractBasicAction {
	
	public PWDeleteAction() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see pw.core.PWAction#run(java.lang.Object[])
	 */
	@Override
	public Object run(Object... objects) {
		// Check argument counts
		if (objects.length == 0) {
			throw new PWError("Delete Action Failed.(Too few arguments.)");
		}
		
		// Check argument types
		if (!(objects[0] instanceof PWItem)) {
			throw new PWError("Delete Action Failed.(Illegal data type of 1st argument.)");
		}
		
		if (objects.length == 1) {
			deleteMultiItems(objects);
			return null;
		}
		
		if (objects[1] instanceof PWItem) {
			deleteMultiItems(objects);
		} else {
			deleteSingleItem(objects);
		}
		return null;
	}
	
	private void deleteSingleItem(Object... objects) {
		PWQuery query = getQuery(itemType, keyType, objects);
		PWAccesser.getDefaultAccesser().execute(query);
		return;
	}
	
	private void deleteMultiItems(Object... objects) {
		List<PWField> keyFields = PWItem.getFields(itemType, keyType);
		List<PWQuery> queries = new ArrayList<PWQuery>();
		for (int i = 0; i < objects.length; i++) {
			PWItem item = (PWItem)objects[i];
			
			Object[] keyValues = new Object[keyFields.size()];
			for (int j = 0; j < keyFields.size(); j++) {
				PWField keyField = keyFields.get(j);
				keyValues[j] = PWItem.getValue(item, keyField.getName());
			}
			
			PWQuery query = getQuery(itemType, keyType, keyValues);
			if (query == null) {
				continue;
			}
			
			queries.add(query);
		}
		
		PWQuery[] queryArray = queries.toArray(new PWQuery[0]);
		PWAccesser.getDefaultAccesser().execute(queryArray);
	}

	public static PWQuery getQuery(Class<? extends PWItem> itemType, PWField.KeyType keyType, Object... keyValues) {
		// Check
		List<PWField> keyFields = PWItem.getFields(itemType, keyType);
		if (keyFields.size() == 0) {
			throw new PWError("No '%s' key fields.", keyType.toString());
		}
		
		if (keyFields.size() != keyValues.length) {
			throw new PWError("The number of key values is different from the number of '%s' key fields.", keyType.toString());
		}
		
    	// Create all query
		String allQuery = String.format("delete from %s where %s;",
				PWQuery.getTableName(itemType),
				getWhereQueryByKeys(itemType, keyType));
		
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
    	for (Object keyValue : keyValues) {
        	query.addValue(keyValue);
    	}
    	return query;
	}
}
