/*
 *  $Id: BasicUpdateAction.java 2013/09/28 1:38:39 masamitsu $
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
import pw.core.PWUtilities;
import pw.core.accesser.PWQuery;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
public class BasicUpdateAction extends AbstractBasicAction {
	
	public BasicUpdateAction() {
		super();
	}

	/* (non-Javadoc)
	 * @see pw.core.PWAction#run(pw.core.PWItem)
	 */
	@Override
	public Object run(Object... objects) {
		assert(session != null);
		assert(2 <= objects.length);
		assert(objects[0] != null);
		assert(objects[1] != null);
		
		List<PWField> keyFields = PWItem.getFields(itemType, keyType);
		
		PWItem item = (PWItem)objects[0];
		
		Object[] keyObjects = (Object[])objects[1];
		String[] keyStrings = new String[keyObjects.length];
		for (int i = 0; i < keyObjects.length; i++) {
			keyStrings[i] = keyFields.get(i).toString(keyObjects[i]);
		}
		Object[] keyValues = PWUtilities.getKeyValuesFromArgumants(keyFields, 0, keyStrings);
		
		
		PWQuery query = getQuery(item, keyType, keyValues);
		if (query == null) {
			return null;
		}
		session.getAccesser().execute(query);
        return null;
	}
	
	public static PWQuery getQuery(PWItem item, PWField.KeyType keyType, Object... keyValues) {
    	List<PWField> fields = PWItem.getFields(item.getClass());
    	
    	// Create the fields part of query
    	StringBuffer fieldsBuffer = new StringBuffer();
    	for (PWField field : fields) {
     		if (field.isKey(PWField.KeyType.Primary)) {
     			continue;
     		}
     		Object value = field.getValue(item);
     		if (value == null) {
     			continue;
     		}
 			fieldsBuffer.append(PWQuery.COMMA);
 			fieldsBuffer.append(field.getName());
 			fieldsBuffer.append(" = ?");
    	}
    	if (fieldsBuffer.length() == 0) {
    		// No update fields.
    		return null;
    	}
    	String fieldsQuery = fieldsBuffer.substring(PWQuery.COMMA.length());
    	
    	
    	// Create all query
    	String allQuery = String.format("update %s set %s where %s;",
    			PWQuery.getTableName(item.getClass()),
    			fieldsQuery,
    			getWhereQueryByKeys(item.getClass(), keyType));
    	
    	// Create PWQuery & add parameters
    	// for fields
    	PWQuery query = new PWQuery(allQuery);
    	for (PWField field : fields) {
     		if (field.isKey(PWField.KeyType.Primary)) {
    			continue;
     		}
     		Object value = field.getValue(item);
     		if (value == null) {
     			continue;
     		}
     		query.addValue(value);
    	}
    	// for where
    	for (Object keyValue : keyValues) {
     		query.addValue(keyValue);
    	}
    	
    	return query;
	}
}
