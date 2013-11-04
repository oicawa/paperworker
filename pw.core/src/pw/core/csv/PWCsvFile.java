/*
 *  $Id: PWCsvFile.java 2013/11/02 20:43:03 masamitsu $
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

package pw.core.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import pw.core.PWConverter;
import pw.core.PWError;
import pw.core.converter.PWClobConverter;
import pw.core.converter.PWDateConverter;
import pw.core.converter.PWStringConverter;
import pw.core.converter.PWUuidConverter;
import pw.core.table.PWTable;
import pw.core.table.PWTableColumn;
import pw.core.table.PWTableRow;

/**
 * @author masamitsu
 *
 */
public class PWCsvFile {
	
	private final String COLUMN_SEPARATOR = ",";
	private static HashMap<String, PWConverter> converters = new HashMap<String, PWConverter>();
	
	private PWTable table;
	
	static {
		converters.put("VARCHAR", new PWStringConverter());
		converters.put("TIMESTAMP", new PWDateConverter());
		converters.put("UUID", new PWUuidConverter());
		converters.put("CLOB", new PWClobConverter());
	}
	
	public PWCsvFile() {
	}
	
	public void write(String filePath) {
		// Create writer
		FileWriter fileWriter;
		try {
			File file = (new File(filePath)).getAbsoluteFile();
			if (!file.exists()) {
				file.createNewFile();
			}
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			throw new PWError(e, e.getMessage());
		}
		PrintWriter writer = new PrintWriter(new BufferedWriter(fileWriter));
		
		try {
			// Write header
			StringBuffer columnNamesBuffer = new StringBuffer();
			StringBuffer columnTypesBuffer = new StringBuffer();
			for (int i = 0; i < table.getColumnCount(); i++) {
				PWTableColumn column = table.getColumn(i);
				columnNamesBuffer.append(COLUMN_SEPARATOR);
				columnNamesBuffer.append(column.getName());
				columnTypesBuffer.append(COLUMN_SEPARATOR);
				columnTypesBuffer.append(column.getDbType());
			}
			String columnNames = table.getColumnCount() == 0 ? "" : columnNamesBuffer.substring(COLUMN_SEPARATOR.length());
			String columnTypes = table.getColumnCount() == 0 ? "" : columnTypesBuffer.substring(COLUMN_SEPARATOR.length());
			writer.println(columnNames);
			writer.println(columnTypes);

			// Write rows
			for (PWTableRow row : table) {
				StringBuffer rowBuffer = new StringBuffer();
				for (int i = 0; i < table.getColumnCount(); i++) {
					PWTableColumn column = table.getColumn(i);
					String value = converters.get(column.getDbType()).toString(row.getValue(i));
					if (value != null) {
						value = value.replace("\r", "\\r");
						value = value.replace("\n", "\\n");
						value = value.replace(",", "\\,");
					}
					rowBuffer.append(COLUMN_SEPARATOR);
					rowBuffer.append(value);
				}
				String values = table.getColumnCount() == 0 ? "" : rowBuffer.substring(COLUMN_SEPARATOR.length());
				writer.println(values);
			}
			
		} finally {
			writer.close();
		}
	}
	
	public void read(String filePath) {
		// Create writer
		FileReader fileReader;
		try {
			File file = (new File(filePath)).getAbsoluteFile();
			fileReader = new FileReader(file);
		} catch (IOException e) {
			throw new PWError(e, e.getMessage());
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		try {
			// Get columns
			String[] columns = reader.readLine().split(",");
			String[] types = reader.readLine().split(",");
			
			// Create view & columns
			PWTable table = new PWTable(columns.length);
			for (int i = 0; i < table.getColumnCount(); i++) {
				PWTableColumn column = table.getColumn(i);
				column.setName(columns[i]);
				column.setDbType(types[i]);
			}
			
			// Create rows
            while (reader.ready()) {
                String line = reader.readLine();
                Object[] objects = toObjects(line, types);
                if (objects.length != table.getColumnCount()) {
                	throw new PWError("The column count of the row line is not correct.");
                }
                PWTableRow row = table.addRow();
                for (int i = 0; i < table.getColumnCount(); i++) {
                	row.setValue(i, objects[i]);
                }
            }
            this.table = table;
			
		} catch (IOException e) {
			throw new PWError(e, e.getMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Output to logger
			}
		}
		
	}
	
	public void read(PWTable view) {
		this.table = view;
	}
	
	public Object[] toObjects(String line, String[] types) {
		Object[] objects = new Object[types.length];
		int begin = 0;
		int cursor = 0;
		int escape = -1;
		int index = 0;
		while(begin < line.length()) {
			// Get comma index, and escape index
			//escape = line.indexOf('\\', cursor);
			cursor = line.indexOf(',', cursor);
			
			// Check escaped comma
			if (0 < cursor && line.charAt(cursor - 1) == '\\') {
				cursor++;
				continue;
			}
			
			// Get column value as string
			int end = cursor < 0 ? line.length() : cursor;
			String value = line.substring(begin, end);
			value = value.replace("\\r", "\r");
			value = value.replace("\\n", "\n");
			value = value.replace("\\,", ",");
			
			// Convert to object
			objects[index] = converters.get(types[index]).toObject(value);
			
			// break
			if (cursor < 0) {
				break;
			}
			
			// Set next parameters
			cursor++;
			begin = cursor;
			index++;
		}
		
		return objects;
	}

	/**
	 * @return
	 */
	public PWTable getTable() {
		return this.table;
	}

}
