/*
 *  $Id: MasterListAction.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.master.core.MasterController;
import paperworker.master.core.MasterItem;

public abstract class MasterListAction<TItem extends MasterItem, TController extends MasterController<TItem>>
					extends MasterAction<TItem, TController> {

	public MasterListAction() {
		super();
	}

	public String getName() {
		return "list";
	}

	public String[] getDescription() {
		final String[] description = {
			String.format("Print all %ss information.", getCommandName()),
			"  ---------",
			String.format("  FORMAT > %s list", getCommandName())
		};
		return description;
	}

	public void run(String[] args) throws PWError, PWWarning {
		List<TItem> items = controller.get();
		for (TItem item : items) {
			printItem(item);
		}
	}
	
	protected abstract void printItem(TItem item);

	@Override
	protected String getRegexForParse() {
		return String.format("^%s %s", getCommandName(), getName());
	}

}
