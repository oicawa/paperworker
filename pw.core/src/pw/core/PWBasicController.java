/*
 *  $Id: PWBasicController.java 2013/09/23 10:32:32 masamitsu $
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

package pw.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author masamitsu
 *
 */
public abstract class PWBasicController<TItem extends PWItem> extends PWController {
	
	private Class<TItem> itemType;

	/**
	 * 
	 */
	public PWBasicController() {
		super();
		
		this.itemType = getItemType();
		String table = PWQuery.getTableName(itemType);
		if (!accesser.existTable(table)) {
        	create();
		}
	}
	
	protected abstract Class<TItem> getItemType();
	
	private void create() {
    	PWQuery query = PWQuery.getCreateTableQuery(itemType);
		accesser.execute(query);
	}

	public void add(Object... keyValues) {
		PWQuery query = PWQuery.getInsertQueryOnlyKeyValues(itemType, keyValues);
        accesser.execute(query);
	}

	public TItem get(PWField.KeyType keyType, Object... keyValues) {
    	PWQuery query = PWQuery.getSelectQueryByKeys(itemType, keyType, keyValues);
    	PWAfterSqlQuery afterQuery = new PWAfterSqlQuery(itemType);
        accesser.select(query, afterQuery);
    	@SuppressWarnings("unchecked")
		TItem item = (TItem)(afterQuery.getItemList().size() == 0 ? null : (TItem)afterQuery.getItemList().get(0));
        return item;
	}

	public List<TItem> get() {
    	PWQuery query = PWQuery.getSelectQueryByKeys(itemType, PWField.KeyType.Primary);
    	PWAfterSqlQuery afterQuery = new PWAfterSqlQuery(itemType);
        accesser.select(query, afterQuery);
        List<TItem> items = new ArrayList<TItem>();
        for (Object object : afterQuery.getItemList()) {
        	@SuppressWarnings("unchecked")
			TItem item = (TItem)object;
			items.add(item);
        }
        return items;
	}
	
	public TItem[] get(PWFilter<TItem> filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update(PWField.KeyType keyType, TItem item) {
		PWQuery query = PWQuery.getUpdateQueryByKey(item, keyType);
		accesser.execute(query);
	}
	
	public void delete(PWField.KeyType keyType, Object... keyValues) {
		PWQuery query = PWQuery.getDeleteQueryByKey(itemType, keyType, keyValues);
        accesser.execute(query);
	}

}
