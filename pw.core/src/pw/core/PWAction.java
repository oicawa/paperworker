/*
 *  $Id: PWAction.java 2013/09/28 1:12:41 masamitsu $
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

package pw.core;

import java.io.File;

import pw.core.accesser.PWAccesser;
import pw.core.accesser.PWAfterViewQuery;
import pw.core.accesser.PWQuery;
import pw.core.item.PWItem;
import pw.core.table.PWTable;
import pw.core.table.PWTableRow;

/**
 * @author masamitsu
 *
 */
public abstract class PWAction {
	
	//protected PWSession session;
	
	public PWAction() {
	}
	
	/**
	 * @param settingParameters
	 */
	protected abstract void parseSettingParameters(String[] settingParameters);

	public abstract Object run(Object... objects);
	
//	public void setSession(PWSession session)
//	{
//		this.session = session;
//	}
	
	public void setParameters(String[] arguments) {
		parseSettingParameters(arguments);
	}
	
	public static String getWhereQueryByKeys(Class<? extends PWItem> itemType, PWField.KeyType keyType) {
    	StringBuffer buffer = new StringBuffer();
    	for (PWField field : PWItem.getFields(itemType, keyType)) {
 			buffer.append(PWQuery.AND);
 			buffer.append(field.getName());
 			buffer.append(" = ?");
    	}
    	String wheresQuery = buffer.substring(PWQuery.AND.length());
    	return wheresQuery;
	}
	
	public static String getWhereQuery(Class<? extends PWItem> itemType, PWItem item) {
    	StringBuffer buffer = new StringBuffer();
    	for (PWField field : PWItem.getFields(itemType)) {
    		Object value = field.getValue(item);
    		if (value == null) {
    			continue;
    		}
 			buffer.append(PWQuery.AND);
 			buffer.append(field.getName());
 			buffer.append(" = ?");
    	}
    	String wheresQuery = buffer.substring(PWQuery.AND.length());
    	return wheresQuery;
	}

	/**
	 * @param jobName
	 * @param actionName
	 * @return
	 */
	public static PWAction getAction(String jobName, String actionName) {
		// Get SQL Query File
		File sqlFile = PWUtilities.getFileInResourceDirectory("queries/action.sql");
		
		// Create Query
		PWQuery query = new PWQuery(sqlFile);
		query.addValue(jobName);
		query.addValue(actionName);

		// Do Query
		PWAfterViewQuery afterQuery = new PWAfterViewQuery();
		PWAccesser accesser = PWAccesser.getDefaultAccesser();
		accesser.select(query, afterQuery);
		
		// Get Data
		PWTable table = afterQuery.getView();
		if (table.getRows().size() == 0)
			throw new PWError("No such job/action name[JobName: %s, ActionName: %s]", jobName, actionName);
		PWTableRow row = afterQuery.getView().getRow(0);
		String classPath = (String)row.getValue(2);
		String[] parameters = (String[])((String)row.getValue(3)).split(PWUtilities.LINE_SEPARATOR_PATTERN);
		
		// Create Action Instance
		Class<?> actionClass = PWUtilities.getClass(classPath);
		PWAction action = (PWAction)PWUtilities.createInstance(actionClass);
		//action.setSession(this);
		action.parseSettingParameters(parameters);
		
		return action;
	}
}
