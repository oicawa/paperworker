/*
 *  $Id: BasicDeleteOperation.java 2013/09/29 20:25:10 masamitsu $
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

import pw.action.basic.AbstractBasicAction;
import pw.core.PWError;
import pw.core.PWField;
import pw.core.PWUtilities;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
public class DeleteOperation extends AbstractBasicOperation {

	/**
	 * @param itemType
	 */
	public DeleteOperation(AbstractBasicAction action) {
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
		
		AbstractBasicAction basicAction = (AbstractBasicAction)action;
		
		List<PWField> keyFields = PWItem.getFields(basicAction.getItemType(), basicAction.getKeyType());
		if (keyFields.size() == 0) {
			throw new PWError("No '%s' key fields.", basicAction.getKeyType().toString());
		}
		
		Object[] keyValues = PWUtilities.getKeyValuesFromArgumants(keyFields, ACTION_ARG_START_INDEX, arguments);
		if (keyFields.size() != keyValues.length) {
			throw new PWError("The number of key values is different from the number of '%s' key fields.", basicAction.getKeyType().toString());
		}
		
		action.run(keyValues);
	}

}
