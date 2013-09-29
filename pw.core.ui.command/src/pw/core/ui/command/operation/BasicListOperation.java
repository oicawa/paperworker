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

package pw.core.ui.command.operation;

import java.util.List;

import pw.core.PWField;
import pw.core.PWGeneralController;
import pw.core.PWItem;
import pw.core.ui.command.PaperWorker2;

/**
 * @author masamitsu
 *
 */
public class BasicListOperation extends AbstractBasicOperation {

	/**
	 * @param itemType
	 */
	public BasicListOperation(PWGeneralController controller, Class<? extends PWItem> itemType) {
		super(controller, itemType);
	}

	/* (non-Javadoc)
	 * @see pw.core.ui.command.PWBasicOperation#run(java.lang.String[])
	 */
	@Override
	public void run(String... argument) {
		String actionName = argument[1];
		@SuppressWarnings("unchecked")
		List<Object> objects = (List<Object>)controller.invoke(actionName);
		for (Object object : objects) {
			PWItem item = (PWItem)object;
			printItem(item);
		}
	}
	
	protected void printItem(PWItem item) {
		List<PWField> fields = PWItem.getFields(item.getClass(), PWField.KeyType.Primary);
		StringBuffer buffer = new StringBuffer();
		final String TAB = "\t";
		for (PWField field : fields) {
			String value = PWItem.getValueAsString(item, field.getName());
			buffer.append(TAB);
			buffer.append(value);
		}
		PaperWorker2.message(buffer.substring(TAB.length()));
	}

}
