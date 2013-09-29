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

package pw.core.action;

import java.util.List;

import pw.core.PWField;
import pw.core.PWItem;
import pw.core.PWQuery;

/**
 * @author masamitsu
 *
 */
public class BasicDeleteAction extends AbstractBasicAction {
	
	public BasicDeleteAction(String... arguments) {
		super(arguments);
	}
	
	/* (non-Javadoc)
	 * @see pw.core.PWAction#run(java.lang.Object[])
	 */
	@Override
	public Object run(Object... objects) {
		assert(session != null);
		assert(objects.length == 1);
		assert(objects[0] != null);
		PWItem item = (PWItem)objects[0];
		PWQuery query = getQuery(item, keyType);
		session.getAccesser().execute(query);
		return null;
	}

	public static PWQuery getQuery(PWItem item, PWField.KeyType keyType) {
    	// Create all query
		String allQuery = String.format("delete from %s where %s;",
				PWQuery.getTableName(item.getClass()),
				getWhereQueryByKeys(item.getClass(), keyType));
		
		// Get key fields
		List<PWField> keyFields = PWItem.getFields(item.getClass(), keyType);
		
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
    	for (PWField keyField : keyFields) {
    		Object keyValue = keyField.getValue(item);
        	query.addValue(keyValue);
    	}
    	return query;
	}
}
