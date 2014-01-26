/*
 *  $Id: PWView.java 2013/10/26 7:28:03 masamitsu $
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author masamitsu
 *
 */
public class PWTable {

	private List<PWTableColumn> columnList;
	private HashMap<String, Integer> indexMap;
	private List<PWTableRow> rows = new ArrayList<PWTableRow>();
	
	public PWTable(int columnCount) {
		columnList = new ArrayList<PWTableColumn>();
		indexMap = new HashMap<String, Integer>();
		for (int i = 0; i < columnCount; i++) {
			columnList.add(new PWTableColumn());
		}
	}

	public String getColumnLabel(int index) {
		return columnList.get(index).getLabel();
	}

	public void setColumnLabel(int index, String label) {
		columnList.get(index).setLabel(label);
	}

	public String getColumnName(int index) {
		return columnList.get(index).getName();
	}

	public void setColumnName(int index, String name) {
		columnList.get(index).setName(name);
	}

	public String getColumnDbType(int index) {
		return columnList.get(index).getDbType();
	}

	public void setColumnDbType(int index, String dbType) {
		columnList.get(index).setDbType(dbType);
	}

	public int getColumnCount() {
		return columnList.size();
	}
	
	public PWTableRow addRow() {
		PWTableRow row = new PWTableRow(this);
		rows.add(row);
		return row;
	}
	
	public PWTableRow getRow(int index) {
		return rows.get(index);
	}

	public Collection<PWTableRow> getRows() {
		return rows;
	}

	public Collection<PWTableColumn> getColumns() {
		return columnList;
	}

	/**
	 * @param label
	 * @return
	 */
	public int getColumnIndex(String label) {
		if (indexMap.containsKey(label)) {
			return indexMap.get(label);
		}
		
		for (int i = 0; i < columnList.size(); i++) {
			String headerValue = columnList.get(i).getLabel();
			if (headerValue.equals(label)) {
				indexMap.put(label, i);
				return i;
			}
		}
		return -1;
	}
}
