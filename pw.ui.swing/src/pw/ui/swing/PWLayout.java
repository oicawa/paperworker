/*
 *  $Id: PWLayout.java 2013/11/09 22:11:26 masamitsu $
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

package pw.ui.swing;

import java.io.Serializable;

import pw.core.PWError;

/**
 * @author masamitsu
 *
 */
public class PWLayout implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6816992708867105646L;
	private static final int DEFAULT_MAX_LENGTH = 5;
	private PWLayoutUnit[][] wholeTable = new PWLayoutUnit[DEFAULT_MAX_LENGTH][DEFAULT_MAX_LENGTH];
	private int columnLength;
	private int rowLength;
	
	public PWLayout() {
		for (int i = 0; i < DEFAULT_MAX_LENGTH; i++) {
			for (int j = 0; j < DEFAULT_MAX_LENGTH; j++) {
				wholeTable[i][j] = new PWLayoutUnit();
			}
		}
	}
	
	public PWLayoutUnit[][] getWholeTable() {
		return wholeTable;
	}
	
	public void setWholeTable(PWLayoutUnit[][] wholeTable) {
		this.wholeTable = wholeTable;
	}
	
	public int getColumnLength() {
		if (columnLength <= 0) {
			columnLength = 1;
		}
		return columnLength;
	}
	
	public int getRowLength() {
		if (rowLength <= 0) {
			rowLength = 1;
		}
		return rowLength;
	}
	
	public void setColumnLength(int length) {
		if (length <= 0 || DEFAULT_MAX_LENGTH <= length) {
			throw new PWError("Invalid value was specified. (The column length of layout table shoud be set from 0 to %d.)", DEFAULT_MAX_LENGTH);
		}
		this.columnLength = length;
	}
	
	public void setRowLength(int length) {
		if (length <= 0 || DEFAULT_MAX_LENGTH <= length) {
			throw new PWError("Invalid value was specified. (The row length of layout table shoud be set from 0 to %d.)", DEFAULT_MAX_LENGTH);
		}
		this.rowLength = length;
	}
	
	public PWLayoutUnit[][] getTable() {
		PWLayoutUnit[][] table = new PWLayoutUnit[getColumnLength()][getRowLength()];
		for (int columnIndex = 0; columnIndex < getColumnLength(); columnIndex++) {
			for (int rowIndex = 0; rowIndex < getRowLength(); rowIndex++) {
				table[columnIndex][rowIndex] = wholeTable[columnIndex][rowIndex];
			}
		}
		return table;
	}
}
