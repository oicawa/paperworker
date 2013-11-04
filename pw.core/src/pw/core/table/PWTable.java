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
import java.util.Iterator;
import java.util.List;

/**
 * @author masamitsu
 *
 */
public class PWTable implements Iterable<PWTableRow> {

	private PWTableColumn[] columns;
	private List<PWTableRow> rows = new ArrayList<PWTableRow>();
	
	public PWTable(int columnCount) {
		columns = new PWTableColumn[columnCount];
	}

	public PWTableColumn getColumn(int index) {
		if (columns[index] == null) {
			columns[index] = new PWTableColumn();
		}
		return columns[index];
	}

	public int getColumnCount() {
		return columns.length;
	}
	
	public PWTableRow addRow() {
		PWTableRow row = new PWTableRow(columns.length);
		rows.add(row);
		return row;
	}
	
	public PWTableRow getRow(int index) {
		return rows.get(index);
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<PWTableRow> iterator() {
		return rows.iterator();
	}
}
