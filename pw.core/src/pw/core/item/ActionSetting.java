/*
 *  $Id: ActionSetting.java 2013/09/28 14:05:49 masamitsu $
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

package pw.core.item;

import pw.core.PWUtilities;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;

/**
 * @author masamitsu
 *
 */
@PWItemBasicInfo(caption = "Action Settings", tableName = "ActionSettings")
public class ActionSetting extends PWItem {
	
	@PWFieldBasicInfo(caption = "Job Name", type = "varchar(100)", primary = true)
	private String jobName;
	
	@PWFieldBasicInfo(caption = "Action Name", type = "varchar(100)", primary = true)
	private String actionName;
	
	@PWFieldBasicInfo(caption = "Action Class Path", type = "varchar(100)")
	private String actionClassPath;

	@PWFieldBasicInfo(caption = "Default Arguments", type = "text")
	private String arguments;
	
	@PWFieldBasicInfo(caption = "Description", type = "text")
	private String description;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionClassPath() {
		return actionClassPath;
	}

	public void setActionClassPath(String actionClassPath) {
		this.actionClassPath = actionClassPath;
	}

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String[] getArgumentArray() {
		return arguments.split(PWUtilities.LINE_SEPARATOR_PATTERN);
	}
	
	static {
		PWUtilities.prepareTable(ActionSetting.class);
	}
}
