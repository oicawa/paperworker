/*
 *  $Id: BasicListOperation.java 2013/09/29 20:26:26 masamitsu $
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

package pw.ui.command.operation.basic;

import java.util.Arrays;
import java.util.HashMap;

import pw.action.basic.PWViewAction;
import pw.core.PWConverter;
import pw.core.PWUtilities;
import pw.core.converter.PWClobConverter;
import pw.core.converter.PWDateConverter;
import pw.core.converter.PWStringConverter;
import pw.core.converter.PWUuidConverter;
import pw.core.table.PWTable;
import pw.core.table.PWTableColumn;
import pw.core.table.PWTableRow;
import pw.ui.command.PWOperation;
import pw.ui.command.PaperWorker;

/**
 * @author masamitsu
 *
 */
public class ViewOperation extends PWOperation {

	private final String COLUMN_SEPARATOR = "|";
	private final String COLUMN_SEPARATOR2 = "+";
	
	private HashMap<String, PWConverter> converters = new HashMap<String, PWConverter>();
	
	/**
	 * @param itemType
	 */
	public ViewOperation(PWViewAction action) {
		super(action);
		converters.put("VARCHAR", new PWStringConverter());
		converters.put("TIMESTAMP", new PWDateConverter());
		converters.put("UUID", new PWUuidConverter());
		converters.put("CLOB", new PWClobConverter());
	}

	/* (non-Javadoc)
	 * @see pw.core.ui.command.PWBasicOperation#run(java.lang.String[])
	 */
	@Override
	public void run(String... arguments) {
		Object[] parameters = Arrays.copyOfRange(arguments, ACTION_ARG_START_INDEX, arguments.length, Object[].class);
		
		// Get view
		PWTable view = (PWTable)action.run(parameters);
		
		int widthes[] = calculateColumnWidthes(view);
		
		// Print column header
		int columnCount = view.getColumnCount();
		String[] columnFormats = new String[columnCount];
		StringBuffer header = new StringBuffer();
		StringBuffer separator = new StringBuffer();
		for (int i = 0; i < columnCount; i++) {
			PWTableColumn column = view.getColumn(i);
			columnFormats[i] = String.format("%s%%-%ds", COLUMN_SEPARATOR, widthes[i]);
			String caption = String.format(columnFormats[i], column.getName());
			header.append(caption);
			for (int j = 0; j < caption.length(); j++) {
				separator.append(j == 0 ? COLUMN_SEPARATOR2 : "-");
			}
		}
		PaperWorker.message(header.substring(COLUMN_SEPARATOR.length()));
		PaperWorker.message(separator.substring(COLUMN_SEPARATOR2.length()));
		
		// Print rows
		for (PWTableRow row : view) {
			printItem(view, row, widthes);
		}
	}
	
	/**
	 * @param view
	 * @return
	 */
	private int[] calculateColumnWidthes(PWTable view) {
		// Column Header
		int columnCount = view.getColumnCount();
		int widthes[] = new int[columnCount];
		for (int i = 0; i < columnCount; i++) {
			PWTableColumn column = view.getColumn(i);
			widthes[i] = PWUtilities.getWidthOfZenkakuHankakuString(column.getName());
		}
		
		// Rows
		for (PWTableRow row : view) {
			for (int i = 0; i < columnCount; i++) {
				PWTableColumn column = view.getColumn(i);
				PWConverter converter = converters.get(column.getDbType());
				Object value = row.getValue(i);
				int width = PWUtilities.getWidthOfZenkakuHankakuString(converter.toString(value));
				widthes[i] = widthes[i] < width ? width : widthes[i];
			}
		}
		return widthes;
	}

	protected void printItem(PWTable view, PWTableRow row, int[] widthes) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < view.getColumnCount(); i++) {
			PWTableColumn column = view.getColumn(i);
			PWConverter converter = converters.get(column.getDbType());
			Object value = row.getValue(i);
			String display = converter.toString(value).replaceAll(PWUtilities.LINE_SEPARATOR_PATTERN, " ");
			buffer.append(COLUMN_SEPARATOR);
			buffer.append(display);
			
			int width = PWUtilities.getWidthOfZenkakuHankakuString(display);
			int diff = widthes[i] - width;
			for (int j = 0; j < diff; j++) {
				buffer.append(" ");
			}
		}
		PaperWorker.message(buffer.substring(COLUMN_SEPARATOR.length()));
	}

}
