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

import java.util.List;
import java.util.UUID;

public class PWUtilities {
	public static <T> T createInstance(Class<T> type) throws PWError {
		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			throw new PWError(e, "The class could not be instantiate. [%s]", type.getName());
		} catch (IllegalAccessException e) {
			throw new PWError(e, "The class was accessed illegally. [%s]", type.getName());
		}
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

	public static <TItem extends PWItem> Object[] getKeyValuesFromArgumants(List<PWField> keyFields, int from, String[] args, String... defaultValues) throws PWError {
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
}