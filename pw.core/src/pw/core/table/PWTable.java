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
import java.util.List;

/**
 * @author masamitsu
 *
 */
public class PWTable {

	private List<PWTableColumn> columns;
	private List<PWTableRow> rows = new ArrayList<PWTableRow>();
	
	public PWTable(int columnCount) {
		columns = new ArrayList<PWTableColumn>();
		//columnMap = new HashMap<String, Integer>();
		for (int i = 0; i < columnCount; i++) {
			columns.add(new PWTableColumn());
			//columnMap.
		}
	}

	public String getColumnLabel(int index) {
		return columns.get(index).getLabel();
	}

	public void setColumnLabel(int index, String label) {
		columns.get(index).setLabel(label);
	}

	public String getColumnName(int index) {
		return columns.get(index).getName();
	}

	public void setColumnName(int index, String name) {
		columns.get(index).setName(name);
	}

	public String getColumnDbType(int index) {
		return columns.get(index).getDbType();
	}

	public void setColumnDbType(int index, String dbType) {
		columns.get(index).setDbType(dbType);
	}

	public int getColumnCount() {
		return columns.size();
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
		return columns;
	}

	/**
	 * @param label
	 * @return
	 */
	public int getColumnIndex(String label) {
		for (int i = 0; i < columns.size(); i++) {
			if (columns.get(i).getLabel().equals(label)) {
				return i;
			}
		}
		return -1;
	}
}
