/*
 *  $Id: PWDetailAction.java 2013/09/23 13:52:09 masamitsu $
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

import pw.core.PWBasicController;
import pw.core.PWField;
import pw.core.PWItem;
import pw.core.PWUtilities;
import pw.core.ui.command.PaperWorker;

/**
 * @author masamitsu
 *
 */
public abstract class PWDetailOperation<TItem extends PWItem, TController extends PWBasicController<TItem>> extends PWOperation<TItem, TController> {

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getName()
	 */
	@Override
	public String getName() {
		return "detail";
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#run(java.lang.String[])
	 */
	@Override
	public void run(String[] args) {
		List<PWField> fields = PWItem.getPrimaryFields(getItemType());
		Object[] keyValues = PWUtilities.getKeyValuesFromArgumants(fields, ACTION_ARG_START_INDEX, args);
		print(PWField.KeyType.Primary, keyValues);
	}
	
	public void print(PWField.KeyType keyType, Object... keyValues) {
		TItem item = controller.get(keyType, keyValues);
		if (item == null) {
			List<PWField> fields = PWItem.getPrimaryFields(getItemType());
			PaperWorker.message("The %s is not found. [%s]", getCommandName(), getTitle(fields, keyValues));
			return;
		}

		printDetail(item);
	}
	
	protected abstract void printDetail(TItem item);
}
