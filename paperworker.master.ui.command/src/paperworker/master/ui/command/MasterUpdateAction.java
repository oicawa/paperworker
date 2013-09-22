/*
 *  $Id: MasterUpdateAction.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

import paperworker.core.PWItem;
import paperworker.core.PWField;
import paperworker.core.PWError;
import paperworker.core.PWUtilities;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.PWAction;
import paperworker.core.ui.command.PaperWorker;
import paperworker.master.core.MasterController;
import paperworker.master.core.MasterItem;

public abstract class MasterUpdateAction<TItem extends MasterItem, TController extends MasterController<TItem>>
	extends MasterAction<TItem, TController> {

	public MasterUpdateAction() {
		super();
	}
	
	@Override
	public String getName() {
		return "update";
	}

	@Override
	public String[] getDescription() {
		final String[] description = {
			String.format("Update a %s information by specified ID.", getCommandName()),
			"  ---------",
			String.format("  FORMAT > %s %s [ID]", getCommandName(), getName())
		};
		return description;
	}

	public static <TItem extends MasterItem> void update(MasterController<TItem> controller, String itemId, Class<TItem> itemClass) throws PWError, PWWarning {
		TItem src = controller.get(itemId);
		if (src == null) {
			PaperWorker.message("The member is not found. [memberId: %s]", itemId);
			return;
		}
		
		TItem dst = PWUtilities.createInstance(itemClass);
		dst.setId(src.getId());

		int maxLength = PWAction.getMaxLengthOfCaptions(itemClass);
		
		List<PWField> fields = PWItem.getFields(itemClass);
		for (int i = 1; i < fields.size(); i++) {
			promptField(dst, src, fields.get(i), maxLength);
		}
		
		PaperWorker.message("  ------------------------------");
		if (PaperWorker.confirm("  Do you save? [Y/N] >> ", "  *** Input 'Y' or 'N'. ***", "Y", "N")) {
			controller.update(dst);
			PaperWorker.message("");
			PaperWorker.message("  Saved.");
		} else {
			PaperWorker.message("");
			PaperWorker.message("  Canceled.");
		}
	}

	@Override
	public void run(String[] args) throws PWError, PWWarning {
		// TODO: Must correspond to multiple primary keys input.
		String itemId = args[2];
		PWField field = PWItem.getFields(getItemType(), PWField.KeyType.Primary).get(0);
		PaperWorker.message("  << UPDATE >> [%s: %s]", field.getCaption(), itemId);
		PaperWorker.message("  * If you input no value (just only the ENTER key), the field value doesn't change.");
		update(controller, itemId, getItemType());
	}

	@Override
	protected String getRegexForParse() {
		return String.format("^%s %s [0-9a-zA-Z]+", getCommandName(), getName());
	}

}
