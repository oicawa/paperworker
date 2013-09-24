/*
 *  $Id: PWAddAction.java 2013/09/23 12:37:49 masamitsu $
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

package paperworker.core.ui.command;

import java.util.List;

import paperworker.core.PWBasicController;
import paperworker.core.PWError;
import paperworker.core.PWField;
import paperworker.core.PWItem;
import paperworker.core.PWUtilities;
import paperworker.core.PWWarning;

/**
 * @author masamitsu
 *
 */
public abstract class PWAddAction<TItem extends PWItem, TController extends PWBasicController<TItem>> extends PWAction<TItem, TController> {

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getName()
	 */
	@Override
	public String getName() {
		return "add";
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#run(java.lang.String[])
	 */
	@Override
	public void run(String[] args) throws PWError, PWWarning {
		List<PWField> fields = PWItem.getPrimaryFields(getItemType());
		Object[] keyValues = PWUtilities.getKeyValuesFromArgumants(fields, ACTION_ARG_START_INDEX, args);
		
		controller.add(keyValues);

		PaperWorker.message("Created a new %s. [%s]", getCommandName(), getTitle(fields, keyValues));
		PaperWorker.message("Input detail information below.");
		PaperWorker.message("--------------------------------------------------");
		PWUpdateAction.update(controller, keyValues);
	}
}
