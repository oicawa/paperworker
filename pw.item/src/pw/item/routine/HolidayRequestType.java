/*
 *  $Id: HolidayDivision.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

package pw.item.routine;

import java.util.UUID;

import pw.core.PWUtilities;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;
import pw.core.item.PWItem;

@PWItemBasicInfo(caption = "Holiday Request Types", tableName = "HolidayRequestTypes")
public class HolidayRequestType extends PWItem {

	@PWFieldBasicInfo(caption = "UUID", type = "uuid", primary = true, auto = true)
	private UUID uuid;

	@PWFieldBasicInfo(caption = "Order No", type = "int", unique = true)
	private int orderNo;

	@PWFieldBasicInfo(caption = "Holiday Division Name", type = "varchar(100)")
	private String name;
	
	@PWFieldBasicInfo(caption = "Holiday Division Short Name", type = "varchar(100)")
	private String shortName;
	
	static {
		PWUtilities.prepareTable(HolidayRequestType.class);
	}
}
