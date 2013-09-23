/*
 *  $Id: HolidayAction.java 2013/09/23 8:42:06 masamitsu $
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

/**
 * @author masamitsu
 *
 */
public class HolidayUtilities {
	
	public static String[] getDescription(String mainMassage, String actionName) {
		final String[] description = {
				mainMassage,
				"  ---------",
				String.format("  FORMAT > holiday %s [ID] [StartDate]", actionName),
				String.format("  FORMAT > holiday %s [ID] [StartDate] [EndDate]", actionName),
				"  * The format of StartDate and EndDate are 'yyyy-MM-dd'.",
				"  * If you input only StartDate, the EndDate is set by the same date as the StartDate."
			};
		return description;
	}

	protected static String getRegexForParse(String actionName) {
		return String.format("^holiday %s [0-9a-zA-Z]+( [\\d]{4}-[\\d]{2}-[\\d]{2}){1,2}", actionName);
	}

}
