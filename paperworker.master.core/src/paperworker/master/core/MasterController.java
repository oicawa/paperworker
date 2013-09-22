/*
 *  $Id: MasterController.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

package paperworker.master.core;

import java.util.List;

import paperworker.core.PWController;
import paperworker.core.PWField;
import paperworker.core.PWFilter;
import paperworker.core.PWError;
import paperworker.core.PWQuery;
import paperworker.core.PWWarning;

public class MasterController<T extends MasterItem> extends PWController {
	
	private Class<T> type;
	
	@SuppressWarnings("unchecked")
	public MasterController(T... types) throws PWError, PWWarning {
		super();
		this.type = (Class<T>)types.getClass().getComponentType();
		String table = PWQuery.getTableName(type);
		if (!accesser.existTable(table)) {
        	create();
		}
	}
	
	private void create() throws PWError, PWWarning {
    	PWQuery query = PWQuery.getCreateTableQuery(type);
		accesser.execute(query);
	}

	public void add(String itemId) throws PWError, PWWarning {
		PWQuery query = PWQuery.getInsertQuery(type, itemId);
        accesser.execute(query);
	}

	public T get(Object... keyValues) throws PWError, PWWarning {
    	PWQuery query = PWQuery.getSelectQueryByKeys(type, PWField.KeyType.Primary, keyValues);
    	MasterAfterQuery<T> afterQuery = new MasterAfterQuery<T>(type);
        accesser.select(query, afterQuery);
        return afterQuery.getItemList().size() == 0 ? null : afterQuery.getItemList().get(0);
	}

	public List<T> get() throws PWError, PWWarning {
    	PWQuery query = PWQuery.getSelectQueryByKeys(type, PWField.KeyType.Primary);
    	MasterAfterQuery<T> afterQuery = new MasterAfterQuery<T>(type);
        accesser.select(query, afterQuery);
        return afterQuery.getItemList();
	}
	
	public T[] get(PWFilter<T> filter) throws PWError, PWWarning {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update(T item) throws PWError, PWWarning {
		PWQuery query = PWQuery.getUpdateQueryByKey(item, PWField.KeyType.Primary);
		accesser.execute(query);
	}
	
	public void delete(String itemId) throws PWError, PWWarning {
		PWQuery query = PWQuery.getDeleteQueryByKey(type, PWField.KeyType.Primary, itemId);
        accesser.execute(query);
	}

}
