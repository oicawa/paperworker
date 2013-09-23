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

package paperworker.holidaydivision.core;

import paperworker.core.PWItem;
import paperworker.core.PWError;
import paperworker.core.annotation.PWFieldBasicInfo;
import paperworker.core.annotation.PWItemBasicInfo;

@PWItemBasicInfo(caption = "Holiday Divisions", tableName = "HolidayDivisions")
public class HolidayDivision extends PWItem {
	
	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@PWFieldBasicInfo(caption = "Holiday Division ID", type = "varchar(100)", primary = true)
	private String divisionId;

	@PWFieldBasicInfo(caption = "Holiday Division Name", type = "varchar(100)")
	private String name;
	
	@PWFieldBasicInfo(caption = "Holiday Division Short Name", type = "varchar(100)")
	private String shortName;

	/* (non-Javadoc)
	 * @see paperworker.core.PWItem#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String fieldName) throws PWError {
		return PWItem.getValue(this, fieldName);
	}

	/* (non-Javadoc)
	 * @see paperworker.core.PWItem#setValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setValue(String fieldName, Object value) throws PWError {
		PWItem.setValue(this, fieldName, value);
	}
}
