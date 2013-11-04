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

package pw.core.accesser;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import pw.core.PWError;
import pw.core.table.PWTable;
import pw.core.table.PWTableColumn;
import pw.core.table.PWTableRow;

/**
 * @author masamitsu
 *
 */
public class PWAfterViewQuery implements PWAfterQuery<ResultSet> {

	private PWTable view;
	
	public PWAfterViewQuery() {
	}
	
	@Override
	public void run(ResultSet resultSet) {
        try {
        	// Get metadata
        	ResultSetMetaData meta = resultSet.getMetaData();
        	int columnCount = meta.getColumnCount();
        	view = new PWTable(columnCount);
        	for (int i = 0; i < columnCount; i++) {
        		PWTableColumn column = view.getColumn(i);
        		column.setLabel(meta.getColumnLabel(i + 1));
        		column.setName(meta.getColumnName(i + 1));
        		column.setDbType(meta.getColumnTypeName(i + 1));
        	}
        	
        	// Get all row data
			while (resultSet.next()) {
				PWTableRow row = view.addRow();
				for (int i = 0; i < columnCount; i++) {
					row.setValue(i, resultSet.getObject(i + 1));
				}
			}
		} catch (SQLException e) {
			throw new PWError(e, e.getMessage());
		}
	}

	public PWTable getView() {
		return view;
	}

}