/*
 *  $Id: PWViewRecord.java 2013/10/26 14:10:38 masamitsu $
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

/**
 * @author masamitsu
 *
 */
public class PWTableRow {
	private PWTable table;
	private Object[] values;

	PWTableRow(PWTable table) {
		this.table = table;
		values = new Object[table.getColumnCount()];
	}
	
	public void setValue(int index, Object value) {
		values[index] = value;
	}
	
	public Object getValue(int index) {
		return values[index];
	}

	/**
	 * @return
	 */
	public Object[] getValues() {
		return values;
	}

	/**
	 * @param string
	 * @return
	 */
	public Object getValue(String label) {
		return getValue(table.getColumnIndex(label));
	}
}
