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

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import pw.core.PWError;
import pw.core.table.PWTable;
import pw.core.table.PWTableColumns;
import pw.core.table.PWTableRow;

/**
 * @author masamitsu
 *
 */
public class PWAfterViewQuery implements PWAfterQuery<ResultSet> {

	private PWTable table;
	
	public PWAfterViewQuery() {
	}
	
	@Override
	public void run(ResultSet resultSet) {
        try {
        	// Get metadata
        	ResultSetMetaData meta = resultSet.getMetaData();
        	int columnCount = meta.getColumnCount();
        	PWTableColumns columns = new PWTableColumns();
        	for (int i = 0; i < columnCount; i++) {
        		//String name = meta.getColumnName(i + 1);
        		String name = meta.getColumnLabel(i + 1);
        		String dbType = meta.getColumnTypeName(i + 1);
        		columns.add(name, dbType);
        	}
        	table = new PWTable(columns);
        	
        	// Get all row data
			while (resultSet.next()) {
				PWTableRow row = table.addRow();
				for (int i = 0; i < columnCount; i++) {
					Object object = resultSet.getObject(i + 1);
					if (object instanceof Clob) {
		    			Clob clob = resultSet.getClob(i + 1);
		    			String value = clob == null ? null : clob.getSubString(1, (int) clob.length());
		    			object = value;
					}
					row.setValue(i, object);
				}
			}
		} catch (SQLException e) {
			throw new PWError(e, e.getMessage());
		}
	}

	public PWTable getView() {
		return table;
	}

}
