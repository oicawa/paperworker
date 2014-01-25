/*
 *  $Id: PWExportCsvAction.java 2013/10/09 6:08:45 masamitsu $
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

package pw.action.file;

import java.io.File;

import pw.core.PWAction;
import pw.core.PWError;
import pw.core.PWUtilities;
import pw.core.accesser.PWAccesser;
import pw.core.accesser.PWAfterViewQuery;
import pw.core.accesser.PWQuery;
import pw.core.csv.PWCsvFile;
import pw.core.table.PWTable;

/**
 * @author masamitsu
 *
 */
public class PWExportCsvAction extends PWAction {
	
	private String sqlFilePath;

	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#parseArguments(java.lang.String[])
	 */
	@Override
	protected void parseSettingParameters(String[] arguments) {
		if (arguments.length != 1) {
			throw new PWError("Required 1 parameter as the target SQL file path.");
		}
		
		sqlFilePath = arguments[0];
	}

	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#run(java.lang.Object[])
	 */
	@Override
	public Object run(Object... objects) {
		if (objects.length != 1) {
			throw new PWError("Required the 3rd argument as an export file path.");
		}
		
		// Get data
		File sqlFile = PWUtilities.getFileInResourceDirectory(sqlFilePath);
		PWQuery query = new PWQuery(sqlFile);
		PWAfterViewQuery afterQuery = new PWAfterViewQuery();
		PWAccesser.getDefaultAccesser().select(query, afterQuery);
		PWTable view = afterQuery.getView();
		
		// Create file
		String exportFilePath = (String)objects[0];
		PWCsvFile csvFile = new PWCsvFile();
		csvFile.read(view);
		csvFile.write(exportFilePath);
		return null;
	}
}
