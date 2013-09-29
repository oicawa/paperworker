/*
 *  $Id: DetailAction.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

package pw.holidaydivision.ui.command;

import java.util.List;

import pw.core.PWField;
import pw.core.ui.command.PaperWorker;
import pw.core.ui.command.operation.PWDetailOperation;
import pw.core.ui.command.operation.PWOperation;
import pw.holidaydivision.core.HolidayDivision;
import pw.holidaydivision.core.HolidayDivisionController;

public class DetailAction extends PWDetailOperation<HolidayDivision, HolidayDivisionController> {

	public DetailAction() {
		super();
	}
	
	@Override
	protected void printDetail(HolidayDivision item) {
		String[] fieldNames = { "name", "shortName" };
		int maxLength = PWOperation.getMaxLengthOfCaptions(HolidayDivision.class);
		
		PaperWorker.message("[%s]", item.getDivisionId());
		for (String fieldName : fieldNames) {
			printField(item, fieldName, maxLength);
		}
	}

	@Override
	protected Class<HolidayDivision> getItemType() {
		return HolidayDivision.class;
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getDescription()
	 */
	@Override
	public String[] getDescription() {
		final String[] description = {
			String.format("Delete a %s record by specified ID.", getCommandName()),
			"  ---------",
			String.format("  FORMAT > %s %s [ID]", getCommandName(), getName()),
		};
		return description;
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getRegexForParse()
	 */
	@Override
	protected String getRegexForParse() {
		return String.format("^%s %s [0-9a-zA-Z]+", getCommandName(), getName());
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getTitle(java.util.List, java.lang.Object[])
	 */
	@Override
	protected String getTitle(List<PWField> fields, Object... keyValues) {
		return "";
	}
}
