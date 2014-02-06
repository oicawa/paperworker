/*
 *  $Id: PWTableModel.java 2014/01/27 22:49:22 masamitsu $
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

package pw.ui.swing.table;

import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

/**
 * @author masamitsu
 *
 */
class PWTableModel extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8124884810159811905L;
	
	protected HashMap<Integer, Boolean> columnEditableMap;
	
	PWTableModel() {
		columnEditableMap = new HashMap<Integer, Boolean>();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (!columnEditableMap.containsKey(column)) {
			return false;
		}
		
		return columnEditableMap.get(column);
	}
	
	void setColumnEditable(int columnIndex, boolean editable) {
		columnEditableMap.put(columnIndex, editable);
	}
}
