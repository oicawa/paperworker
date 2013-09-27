/*
 *  $Id: ListAction.java 2013/09/23 14:13:41 masamitsu $
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

import java.text.SimpleDateFormat;
import java.util.List;

import pw.core.PWError;
import pw.core.PWField;
import pw.core.PWWarning;
import pw.core.ui.command.PWListAction;
import pw.core.ui.command.PaperWorker;
import pw.holiday.core.Holiday;
import pw.holiday.core.HolidayController;
import pw.holidaydivision.core.HolidayDivision;
import pw.member.core.Member;

/**
 * @author masamitsu
 *
 */
public class ListAction extends PWListAction<Holiday, HolidayController> {

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getDescription()
	 */
	@Override
	public String[] getDescription() {
		return HolidayUtilities.getDescription("Show all holiday request item.", getName());
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getRegexForParse()
	 */
	@Override
	protected String getRegexForParse() {
		return "^holiday list$";
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
	 * @see paperworker.core.ui.command.PWListAction#printItem(paperworker.core.PWItem)
	 */
	@Override
	protected void printItem(Holiday item) throws PWError, PWWarning {
		PWField startDateField = PWField.getField(Holiday.class, "startDate");
		SimpleDateFormat startDateFormat = new SimpleDateFormat(startDateField.getDateTimeFormat());
		
		PWField endDateField = PWField.getField(Holiday.class, "endDate");
		SimpleDateFormat endDateFormat = new SimpleDateFormat(endDateField.getDateTimeFormat());
		
		String fullName;
		Member creator = controller.getCreator(item.getCreatorId());
		fullName = creator == null ? null : creator.getFamilyName() + " " + creator.getFirstName();
		
		String divisionName;
		HolidayDivision division = controller.getDivision(item.getDivisionId());
		divisionName = division == null ? null : division.getShortName();
		
		PaperWorker.message("[%s - %s] [%s %s] [%s] %s",
				startDateFormat.format(item.getStartDate()),
				endDateFormat.format(item.getEndDate()),
				item.getCreatorId(),
				fullName,
				divisionName,
				item.getReason());
	}

}
