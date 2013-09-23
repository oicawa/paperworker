/*
 *  $Id: AddAction.java 2013/09/22 21:00:31 masamitsu $
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

import java.util.List;

import paperworker.core.PWField;
import paperworker.core.ui.command.PWAddAction;
import paperworker.holiday.core.Holiday;
import paperworker.holiday.core.HolidayController;

/**
 * @author masamitsu
 *
 */
public class AddAction extends PWAddAction<Holiday, HolidayController> {
	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getRegexForParse()
	 */
	@Override
	protected String getRegexForParse() {
		return HolidayUtilities.getRegexForParse(getName());
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWAction#getDescription()
	 */
	@Override
	public String[] getDescription() {
		return HolidayUtilities.getDescription("Create a new holiday request item.", getName());
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
	 * @see paperworker.core.ui.command.PWAction#getItemType()
	 */
	@Override
	protected Class<Holiday> getItemType() {
		return Holiday.class;
	}

}
