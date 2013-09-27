/*
 *  $Id: DetailAction.java 2013/09/23 14:10:40 masamitsu $
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

package pw.holiday.ui.command;

import java.util.List;

import pw.core.PWError;
import pw.core.PWField;
import pw.core.PWItem;
import pw.core.PWUtilities;
import pw.core.PWWarning;
import pw.core.ui.command.PWAction;
import pw.core.ui.command.PWDetailAction;
import pw.holiday.core.Holiday;
import pw.holiday.core.HolidayController;
import pw.holidaydivision.core.HolidayDivision;
import pw.member.core.Member;
import pw.member.ui.command.MemberLabel;

/**
 * @author masamitsu
 *
 */
public class DetailAction extends PWDetailAction<Holiday, HolidayController> {

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#run(java.lang.String[])
	 */
	@Override
	public void run(String[] args) throws PWError, PWWarning {
		List<PWField> fields = PWItem.getUniqueFields(getItemType());
		Object[] keyValues = PWUtilities.getKeyValuesFromArgumants(fields, ACTION_ARG_START_INDEX, args);
		print(PWField.KeyType.Unique, keyValues);
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getDescription()
	 */
	@Override
	public String[] getDescription() {
		return HolidayUtilities.getDescription("Show a specified holiday request item.", getName());
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getRegexForParse()
	 */
	@Override
	protected String getRegexForParse() {
		return HolidayUtilities.getRegexForParse(getName());
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getItemType()
	 */
	@Override
	protected Class<Holiday> getItemType() {
		return Holiday.class;
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getTitle(java.util.List, java.lang.Object[])
	 */
	@Override
	protected String getTitle(List<PWField> fields, Object... keyValues) {
		String title = String.format("[%s, (%s - %s)]", keyValues);
		return title;
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWDetailAction#printDetail(paperworker.core.PWItem)
	 */
	@Override
	protected void printDetail(Holiday item) throws PWError, PWWarning {
		int maxLength = PWAction.getMaxLengthOfCaptions(Holiday.class);
		
		Member creator = controller.getCreator(item.getCreatorId());
		MemberLabel creatorLabel = new MemberLabel(creator);
		
		HolidayDivision division = controller.getDivision(item.getDivisionId());
		
		printField(item, "startDate", maxLength);
		printField(item, "endDate", maxLength);
		printField(item, "creatorId", maxLength, creatorLabel.getText());
		printField(item, "divisionId", maxLength, division.getDivisionId() + " " + division.getName());
		printField(item, "reason", maxLength);
	}

}
