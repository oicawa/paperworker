/*
 *  $Id: AddAction.java 2013/09/28 1:05:04 masamitsu $
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

import pw.core.PWField;
import pw.core.accesser.PWAccesser;
import pw.core.accesser.PWQuery;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
public class PWAddAction extends AbstractBasicAction {
	
	public PWAddAction() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see pw.core.PWAction#run()
	 */
	@Override
	public Object run(Object... objects) {
		PWQuery[] queries = new PWQuery[objects.length];
		for (int i = 0; i < objects.length; i++) {
			PWItem item = (PWItem)objects[i];
			PWQuery query = getQuery(item);
			queries[i] = query;
		}
		PWAccesser.getDefaultAccesser().execute(queries);
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
    	String allQuery = String.format("insert into %s values (%s);", PWQuery.getTableName(item.getClass()), fieldsQuery);
    	
    	// Create PWQuery
    	PWQuery query = new PWQuery(allQuery);
    	for (PWField field : fields) {
    		Object keyValue = field.getValue(item);
        	query.addValue(keyValue);
    	}
    	
    	return query;
	}
}
