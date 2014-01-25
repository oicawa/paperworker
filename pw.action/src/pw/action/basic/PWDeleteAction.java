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
		PWQuery query = getQuery(itemType, keyType, objects);
		PWAccesser.getDefaultAccesser().execute(query);
		return null;
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
