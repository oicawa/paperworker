/*
 *  $Id: UpdateAction.java 2013/09/23 8:00:46 masamitsu $
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

package paperworker.holiday.ui.command;

import java.util.ArrayList;
import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWField;
import paperworker.core.PWItem;
import paperworker.core.PWUtilities;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.PWFieldEditor;
import paperworker.core.ui.command.PWStringFieldEditor;
import paperworker.core.ui.command.PWUpdateAction;
import paperworker.core.ui.command.PaperWorker;
import paperworker.holiday.core.Holiday;
import paperworker.holiday.core.HolidayController;
import paperworker.holidaydivision.ui.command.HolidayDivisionSingleSelectFieldEditor;

/**
 * @author masamitsu
 *
 */
public class UpdateAction extends PWUpdateAction<Holiday, HolidayController> {

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#run(java.lang.String[])
	 */
	@Override
	public void run(String[] args) throws PWError, PWWarning {
		List<PWField> fields = PWItem.getUniqueFields(getItemType());
		Object[] keyValues = PWUtilities.getKeyValuesFromArgumants(fields, ACTION_ARG_START_INDEX, args);
		
		PaperWorker.message("<< UPDATE >>" + getTitle(fields, keyValues));
		PaperWorker.message("* If you input no value (just only the ENTER key), the field value doesn't change.");
		update(PWField.KeyType.Unique, keyValues);
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWUpdateAction#getTitle(java.util.List, java.lang.Object[])
	 */
	@Override
	protected String getTitle(List<PWField> fields, Object... keyValues) {
		String title = String.format("[%s, (%s - %s)]", keyValues);
		return title;
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWUpdateAction#getItemType()
	 */
	@Override
	protected Class<Holiday> getItemType() {
		return Holiday.class;
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getDescription()
	 */
	@Override
	public String[] getDescription() {
		return HolidayUtilities.getDescription("Update a specified holiday request item.", getName());
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getRegexForParse()
	 */
	@Override
	protected String getRegexForParse() {
		return HolidayUtilities.getRegexForParse(getName());
	}
	
	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWUpdateAction#getFieldEditors()
	 */
	@Override
	public List<PWFieldEditor> getFieldEditors() throws PWError, PWWarning {
		List<PWFieldEditor> editors = new ArrayList<PWFieldEditor>();
		List<PWField> fields = PWItem.getFields(getItemType());
		int maxLength = getMaxLengthOfCaptions(getItemType());
		for (PWField field : fields) {
			if (field.getName().equals("divisionId")) {
				PWFieldEditor editor = new HolidayDivisionSingleSelectFieldEditor(field, maxLength);
				editors.add(editor);
			} else {
				PWFieldEditor editor = new PWStringFieldEditor(field, maxLength);
				editors.add(editor);
			}
		}
		return editors;
	}

}
