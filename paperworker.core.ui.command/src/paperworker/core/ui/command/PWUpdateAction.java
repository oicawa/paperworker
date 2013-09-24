/*
 *  $Id: PWUpdateAction.java 2013/09/23 9:53:55 masamitsu $
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
public abstract class PWUpdateAction<TItem extends PWItem, TController extends PWBasicController<TItem>> extends PWAction<TItem, TController> {

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getName()
	 */
	@Override
	public String getName() {
		return "update";
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#run(java.lang.String[])
	 */
	@Override
	public void run(String[] args) throws PWError, PWWarning {
		List<PWField> fields = PWItem.getPrimaryFields(getItemType());
		Object[] keyValues = PWUtilities.getKeyValuesFromArgumants(fields, ACTION_ARG_START_INDEX, args);
		
		PaperWorker.message("<< UPDATE >>" + getTitle(fields, keyValues));
		PaperWorker.message("* If you input no value (just only the ENTER key), the field value doesn't change.");
		update(controller, keyValues);
	}

	public static <TItem extends PWItem, TController extends PWBasicController<TItem>> void update(TController controller, Object... keyValues) throws PWError, PWWarning {
		TItem src = controller.get(keyValues);
		if (src == null) {
			PaperWorker.message("The target item is not found.");
			return;
		}
		@SuppressWarnings("unchecked")
		Class<TItem> itemType = (Class<TItem>) src.getClass();
		
		TItem dst = (TItem) PWUtilities.createInstance(itemType);
		List<PWField> primaryFields = PWItem.getPrimaryFields(itemType);
		for (PWField primaryField : primaryFields) {
			Object value = src.getValue(primaryField.getName());
			dst.setValue(primaryField.getName(), value);
		}

		int maxLength = PWAction.getMaxLengthOfCaptions(itemType);
		
		List<PWField> fields = PWItem.getFields(itemType);
		for (PWField field : fields) {
			if (field.isPrimary()) {
				continue;
			}
			promptField(dst, src, field, maxLength);
		}
		
		PaperWorker.message("------------------------------");
		if (PaperWorker.confirm("Do you save? [Y/N] >> ", "*** Input 'Y' or 'N'. ***", "Y", "N")) {
			controller.update(dst);
			PaperWorker.message("");
			PaperWorker.message("Saved.");
		} else {
			PaperWorker.message("");
			PaperWorker.message("Canceled.");
		}
	}
}
