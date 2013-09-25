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

import java.util.ArrayList;
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
		update(PWField.KeyType.Primary, keyValues);
	}

	public void update(PWField.KeyType keyType, Object... keyValues) throws PWError, PWWarning {
		TItem src = controller.get(keyType, keyValues);
		if (src == null) {
			PaperWorker.message("The target item is not found.");
			return;
		}
		Class<TItem> itemType = getItemType();
		
		TItem dst = (TItem) PWUtilities.createInstance(itemType);
		List<PWField> primaryFields = PWItem.getPrimaryFields(itemType);
		for (PWField primaryField : primaryFields) {
			Object value = src.getValue(primaryField.getName());
			dst.setValue(primaryField.getName(), value);
		}
		
		for (PWFieldEditor editor : getFieldEditors()) {
			if (editor.getField().isPrimary()) {
				continue;
			}
			editor.print(src, dst, "  >> ");
		}
		
		PaperWorker.message("------------------------------");
		if (PaperWorker.confirm("Do you save? [Y/N] >> ", "*** Input 'Y' or 'N'. ***", "Y", "N")) {
			controller.update(keyType, dst);
			PaperWorker.message("");
			PaperWorker.message("Saved.");
		} else {
			PaperWorker.message("");
			PaperWorker.message("Canceled.");
		}
	}
	
	public List<PWFieldEditor> getFieldEditors() throws PWError, PWWarning {
		List<PWFieldEditor> editors = new ArrayList<PWFieldEditor>();
		List<PWField> fields = PWItem.getFields(getItemType());
		int maxLength = getMaxLengthOfCaptions(getItemType());
		for (PWField field : fields) {
			PWFieldEditor editor = new PWStringFieldEditor(field, maxLength);
			editors.add(editor);
		}
		return editors;
	}
	
	public static void promptField(PWItem dst, PWItem src, PWField field, int captionLength) throws PWError {
		promptField(dst, src, field, captionLength, null);
	}
	
	public static void promptField(PWItem dst, PWItem src, PWField field, int captionLength, PWSelector selector) throws PWError {
		String caption = field.getCaption();
		String value = PWItem.getValueAsString(src, field.getName());
		String format = String.format("%%-%ds [%%s]", captionLength);
		PaperWorker.message(format, caption, value);
		String input;
		if (selector == null) {
			input = PaperWorker.prompt("  >> ");
		} else {
			input = selector.prompt("  >> ");
		}
		try {
			if (input.equals("")) {
				dst.setValue(field.getName(), src.getValue(field.getName()));
			} else {
				dst.setValue(field.getName(), field.parse(input));
			}
		} catch (PWError e) {
			PaperWorker.error(e);
		}
	}
	
}
