/*
 *  $Id: PWUtilities.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import pw.core.accesser.PWAccesser;
import pw.core.accesser.PWQuery;
import pw.core.item.PWItem;

public class PWUtilities {
	
	public static final String LINE_SEPARATOR_PATTERN =  "\r\n|[\n\r\u2028\u2029\u0085]";
	public static final String LINE_SEPARATOR =  System.getProperty("line.separator");

	public static <T> T createInstance(Class<T> type) {
		try {
			Constructor<T> constructor = type.getConstructor();
			return constructor.newInstance();
		} catch (InstantiationException e) {
			throw new PWError(e, e.getMessage());
		} catch (IllegalAccessException e) {
			throw new PWError(e, e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new PWError(e, e.getMessage());
		} catch (InvocationTargetException e) {
			throw new PWError(e, e.getMessage());
		} catch (SecurityException e) {
			throw new PWError(e, e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new PWError(e, e.getMessage());
		}
	}
	
	public static Class<?> getClass(String classPath) {
		Class<?> type;
		try {
			type = (Class<?>)Class.forName(classPath);
		} catch (ClassNotFoundException e) {
			throw new PWError(e, e.getMessage());
		}
		return type;
	}

	public static String getCommandName(String packageName) {
		final String prefix = "paperworker.";
		final String suffix = ".ui.command";
		
		// Suffix
		if (!packageName.endsWith(suffix)) {
			return null;
		}
		String commandName = packageName.substring(0, packageName.length() - suffix.length());
		
		// Prefix
		if (commandName.startsWith(prefix)) {
			commandName = commandName.substring(prefix.length());
		}
		
		return commandName;
	}

	public static <TItem extends PWItem> Object[] getKeyValuesFromArgumants(List<PWField> keyFields, int from, String[] args, String... defaultValues) {
		int size = args.length - from;
		assert(size + defaultValues.length == keyFields.size());
		
		Object[] keyValues = new Object[keyFields.size()];
		
		for (int i = 0; i < keyFields.size(); i++) {
			PWField keyField = keyFields.get(i);
			String value = i < size ? args[from + i] : defaultValues[i - size];
			keyValues[i] = keyField.parse(value);
		}
		return keyValues;
	}
	
	public static UUID createNewUuid() {
		// TODO: Must decides what version UUID to use.
		return UUID.randomUUID();
	}
	
	public static int getMaxLength(List<String> values) {
		int max = 0;
		for (String value : values) {
			max = max < value.length() ? value.length() : max; 
		}
		return max;
	}
	
	public static void prepareTable(Class<? extends PWItem> itemType) {
		PWAccesser accesser = PWAccesser.getAccesser("tablecreator");
		if (accesser.existTable(PWItem.getTableName(itemType))) {
			return;
		}
		
    	PWQuery query = PWQuery.getCreateTableQuery(itemType);
    	accesser.execute(query);
	}
}
