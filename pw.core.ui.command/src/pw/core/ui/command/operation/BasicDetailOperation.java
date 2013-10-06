/*
 *  $Id: BasicDetailOperation.java 2013/09/29 20:25:49 masamitsu $
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

import pw.core.PWError;
import pw.core.PWField;
import pw.core.PWItem;
import pw.core.PWUtilities;
import pw.core.action.AbstractBasicAction;
import pw.core.ui.command.PaperWorker;

/**
 * @author masamitsu
 *
 */
public class BasicDetailOperation extends AbstractBasicOperation {

	/**
	 * @param itemType
	 */
	public BasicDetailOperation(AbstractBasicAction action) {
		super(action);
	}

	/* (non-Javadoc)
	 * @see pw.core.ui.command.PWBasicOperation#run(java.lang.String[])
	 */
	@Override
	public void run(String... arguments) {
		if (arguments.length < 3) {
			throw new PWError("require key field values.");
		}
		
		List<PWField> keyFields = PWItem.getFields(action.getItemType(), action.getKeyType());
		if (keyFields.size() == 0) {
			throw new PWError("No '%s' key fields.", action.getKeyType().toString());
		}
		
		Object[] keyValues = PWUtilities.getKeyValuesFromArgumants(keyFields, ACTION_ARG_START_INDEX, arguments);
		if (keyFields.size() != keyValues.length) {
			throw new PWError("The number of key values is different from the number of '%s' key fields.", action.getKeyType().toString());
		}
			
		PWItem item = (PWItem)action.run(keyValues);
		if (item == null) {
			PaperWorker.message("Not found. [KeyType: '%s', %s]", action.getKeyType().toString(), getKeyParameters(keyFields, keyValues));
			return;
		}

		List<PWField> fields = PWItem.getFields(action.getItemType());
		int length = PWUtilities.getMaxLength(PWItem.getCaptions(action.getItemType()));
		for (int i = 0; i < fields.size(); i++) {
			printField(item, fields.get(i), length);
		}
	}
	
	public void printField(PWItem item, PWField field, int captionLength) {
		String caption = field.getCaption();
		String format = String.format("%%-%ds : %%s", captionLength);
		String value = field.toString(field.getValue(item));
		PaperWorker.message(format, caption, value);
	}
}
