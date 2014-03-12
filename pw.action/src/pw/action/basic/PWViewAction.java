/*
 *  $Id: PWViewAction.java 2013/10/20 17:04:06 masamitsu $
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

package pw.action.basic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pw.core.PWAction;
import pw.core.PWConverter;
import pw.core.PWError;
import pw.core.PWSession;
import pw.core.PWUtilities;
import pw.core.accesser.PWAccesser;
import pw.core.accesser.PWAfterViewQuery;
import pw.core.accesser.PWQuery;

/**
 * @author masamitsu
 *
 */
public class PWViewAction extends PWAction {
	
	public static final String PARAMETER_USERID = "$UserId";
	public static final String PARAMETER_NOW = "$Now";
	public static final Pattern PARAMETER_INPUT = Pattern.compile("^\\$Input,([\\w]+),([\\w.]+)");
	
	private HashMap<String, Object> buildInParameters = new HashMap<String, Object>();
	private File sqlFile;
	private List<String> parameters = new ArrayList<String>();
	private LinkedHashMap<String, PWConverter> converters = new LinkedHashMap<String, PWConverter>();

	public PWViewAction() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#parseArguments(java.lang.String[])
	 */
	@Override
	protected void parseSettingParameters(String[] settingParameters) {
		if (settingParameters == null || settingParameters.length == 0) {
			throw new PWError("[%s#constructor] requires SQL SELECT statement.", getClass().getName());
		}
		
		// Set built in parameters
		Calendar now = Calendar.getInstance();
		buildInParameters.put(PARAMETER_USERID, PWSession.getUserId());
		buildInParameters.put(PARAMETER_NOW, now.getTime());

		// Get SQL file path
		String firstLine = settingParameters[0];
		String[] firstLineFields = firstLine.split(",");
		String relationalPath = firstLineFields[0];
		sqlFile = PWUtilities.getFileInResourceDirectory(relationalPath);
		
		// Get using Item class path
		String[] classPathes = Arrays.copyOfRange(firstLineFields, 1, firstLineFields.length);
		for (String classPath : classPathes) {
			PWUtilities.getClass(classPath);
		}
		
		// If 2 or more arguments, get all parameter names.
		for (int i = 1; i < settingParameters.length; i++) {
			String argument = settingParameters[i];
			if (buildInParameters.containsKey(argument)) {
				parameters.add(argument);
				continue;
			}
			
			Matcher matcher = PARAMETER_INPUT.matcher(argument);
			if (!matcher.matches()) {
				throw new PWError("'%s' is not a buildin parameter.", argument);
			}
			
			String name = matcher.group(1);
			String classPath = String.format("pw.core.converter.PW%sConverter", matcher.group(2));
			
			parameters.add(name);
			
			@SuppressWarnings("unchecked")
			Class<PWConverter> type = (Class<PWConverter>)PWUtilities.getClass(classPath);
			PWConverter converter = (PWConverter)PWUtilities.createInstance(type);
			converters.put(name, converter);	// Set classpath for convert from string value.
		}
	}

	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#run(java.lang.Object[])
	 */
	@Override
	public Object run(Object... objects) {
		if (converters.size() != objects.length) {
			final String SEPARATOR = ", ";
			StringBuffer buffer = new StringBuffer();
			for (String name : converters.keySet()) {
				buffer.append(SEPARATOR);
				buffer.append(name);
			}
			String names = buffer.length() == 0 ? "" : " [" + buffer.substring(SEPARATOR.length()) + "]";
			throw new PWError("Specified %d parameter(s)%s for this action.", converters.size(), names);
		}
		
		// Create SQL Query
		PWQuery query = new PWQuery(sqlFile);
		
		// parameters
		int index = 0;
		for (String parameterName : parameters) {
			if (buildInParameters.containsKey(parameterName)) {
				query.addValue(buildInParameters.get(parameterName));
				continue;
			}
			
			if (converters.containsKey(parameterName)) {
				query.addValue(objects[index]);
				index++;
				continue;
			}
			
			throw new PWError("Illegal case.");
		}
		
		PWAfterViewQuery afterQuery = new PWAfterViewQuery();
		PWAccesser.getDefaultAccesser().select(query, afterQuery);
		return afterQuery.getView();
	}

}
