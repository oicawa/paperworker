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

import java.util.List;

import pw.core.PWField;
import pw.core.item.PWItem;
import pw.standard.action.basic.AbstractBasicAction;
import pw.ui.command.PaperWorker;

/**
 * @author masamitsu
 *
 */
public class ListOperation extends AbstractBasicOperation {

	/**
	 * @param itemType
	 */
	public ListOperation(AbstractBasicAction action) {
		super(action);
	}

	/* (non-Javadoc)
	 * @see pw.core.ui.command.PWBasicOperation#run(java.lang.String[])
	 */
	@Override
	public void run(String... arguments) {
//		Object[] keyValues = PWUtilities.getKeyValuesFromArgumants(keyFields, ACTION_ARG_START_INDEX, arguments);
//		if (keyFields.size() != keyValues.length) {
//			throw new PWError("The number of key values is different from the number of '%s' key fields.", action.getKeyType().toString());
//		}
		
		@SuppressWarnings("unchecked")
		List<Object> objects = (List<Object>)action.run();
		for (Object object : objects) {
			PWItem item = (PWItem)object;
			printItem(item);
		}
	}
	
	protected void printItem(PWItem item) {
		List<PWField> fields = PWItem.getFields(item.getClass(), action.getKeyType());
		StringBuffer buffer = new StringBuffer();
		final String TAB = "\t";
		for (PWField field : fields) {
			String value = PWItem.getValueAsString(item, field.getName());
			buffer.append(TAB);
			buffer.append(value);
		}
		PaperWorker.message(buffer.substring(TAB.length()));
	}

}
