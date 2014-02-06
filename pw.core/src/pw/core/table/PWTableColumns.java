/*
 *  $Id: PWTableColumns.java 2014/02/01 10:20:58 masamitsu $
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

package pw.core.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author masamitsu
 *
 */
public class PWTableColumns {
	private List<PWTableColumn> columns;
	private HashMap<String, PWTableColumn> nameMap;

	public PWTableColumns() {
		columns = new ArrayList<PWTableColumn>();
		nameMap = new HashMap<String, PWTableColumn>();
	}
	
	public void add(String name, String dbType) {
		int index = columns.size();
		PWTableColumn column = new PWTableColumn(index, name, dbType);
		columns.add(column);
		nameMap.put(name, column);
	}

	public String getName(int index) {
		return columns.get(index).getName();
	}

	public String getDbType(int index) {
		return columns.get(index).getDbType();
	}

	public int getCount() {
		return columns.size();
	}

	/**
	 * @param label
	 * @return
	 */
	public int getIndex(String name) {
		if (!nameMap.containsKey(name)) {
			return -1;
		}
		return nameMap.get(name).getIndex();
	}
}
