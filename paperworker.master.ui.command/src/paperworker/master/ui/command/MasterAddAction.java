/*
 *  $Id: MasterAddAction.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

package paperworker.master.ui.command;

import paperworker.core.PWField;
import paperworker.core.PWError;
import paperworker.core.PWItem;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.PaperWorker;
import paperworker.master.core.MasterController;
import paperworker.master.core.MasterItem;

public abstract class MasterAddAction<TItem extends MasterItem, TController extends MasterController<TItem>>
	extends MasterAction<TItem, TController> {

	public MasterAddAction() {
		super();
	}

	public String getName() {
		return "add";
	}

	public String[] getDescription() {
		final String[] description = {
			String.format("Create a new %s record.", getCommandName()),
			"  ---------",
			String.format("  FORMAT > %s add [ID]", getCommandName(), getName())
		};
		return description;
	}

	public void run(String[] args) throws PWError, PWWarning {
		// TODO: Must correspond to multiple primary keys input.
		String newItemId = args[2];
		controller.add(newItemId);
		PWField fieldInfo = PWItem.getFields(getItemType(), PWField.KeyType.Primary).get(0);
		PaperWorker.message("  Created a new %s. [%s: %s]", getCommandName(), fieldInfo.getCaption(), newItemId);
		PaperWorker.message("  Input detail information below.");
		PaperWorker.message("  --------------------------------------------------");
		MasterUpdateAction.update(controller, newItemId, getItemType());
	}
	
	protected String getRegexForParse() {
		return String.format("^%s %s [0-9a-zA-Z]+", getCommandName(), getName());
	}
}
