/*
 *  $Id: ListAction.java 2013/09/28 12:32:12 masamitsu $
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

package pw.standard.action.basic;

import pw.core.PWError;
import pw.core.PWField;
import pw.core.accesser.PWAfterSqlQuery;
import pw.core.accesser.PWQuery;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
public class PWListAction extends AbstractBasicAction {
	
	public PWListAction() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see pw.core.PWAction#run(java.lang.Object[])
	 */
	@Override
	public Object run(Object... objects) {
		PWQuery query;
		switch (objects.length) {
		case 0:
			query = getQuery(itemType);
			break;
		case 1:
			PWItem item = (PWItem)objects[0];
			query = getQuery(itemType, item);
			break;
		default:
			throw new PWError("Illegal argument counts");
		}
		
		PWAfterSqlQuery afterQuery = new PWAfterSqlQuery(itemType);
		session.getAccesser().select(query, afterQuery);
		return afterQuery.getItemList();
	}
	
	public static PWQuery getQuery(Class<? extends PWItem> itemType) {
		// Create all query
		String allQuery = String.format("select * from %s;",
				PWQuery.getTableName(itemType));
		
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
		return query;
	}
	
	public static PWQuery getQuery(Class<? extends PWItem> itemType, PWItem item) {
		// Create all query
		String allQuery = String.format("select * from %s where %s;",
				PWQuery.getTableName(itemType),
				getWhereQuery(itemType, item));
		
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
    	for (PWField field : PWItem.getFields(itemType)) {
    		Object value = field.getValue(item);
    		if (value == null) {
    			continue;
    		}
    		query.addValue(value);
    	}
		return query;
	}
}
