/*
 *  $Id: PWAfterSqlQuery.java 2013/09/23 8:55:58 masamitsu $
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

package paperworker.core;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author masamitsu
 *
 */
public class PWAfterSqlQuery<TItem extends PWItem> implements PWAfterQuery<ResultSet> {

	private List<TItem> items = new ArrayList<TItem>();
	private Class<TItem> itemType;
	
	public PWAfterSqlQuery(Class<TItem> itemType) {
		this.itemType = itemType;
	}
	
	@Override
	public void run(ResultSet resultSet) throws PWWarning, PWError {
        try {
			while (resultSet.next()) {
				TItem item = PWUtilities.createInstance(itemType);
	        	List<PWField> fields = PWItem.getFields(itemType);
		    	for (PWField field : fields) {
		    		if (field.getType().equals("text")) {
		    			Clob clob = resultSet.getClob(field.getName());
		    			String value = clob == null ? null : clob.getSubString(1, (int) clob.length());
			    		field.setValue(item, value);
		    		} else {
			    		field.setValue(item, resultSet.getObject(field.getName()));
		    		}
		    	}
		    	items.add(item);
			}
		} catch (SQLException e) {
			throw new PWWarning(e, e.getMessage());
		}
	}

	public List<TItem> getItemList() {
		return items;
	}

}
