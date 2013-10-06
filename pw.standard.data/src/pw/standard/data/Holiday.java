/*
 *  $Id: Holiday.java 2013/09/21 17:59:13 masamitsu $
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

package pw.standard.data;

import java.util.Date;
import java.util.UUID;

import pw.core.PWItem;
import pw.core.PWUtilities;
import pw.core.annotation.DateTimeInfo;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;

/**
 * @author masamitsu
 *
 */
@PWItemBasicInfo(caption = "Holidays", tableName = "Holidays")
public class Holiday extends PWItem {

	@PWFieldBasicInfo(caption = "UUID", type = "uuid", primary = true)
	private UUID uuid;

	@PWFieldBasicInfo(caption = "Creator ID", type = "varchar(100)", unique = true)
	private String creatorId;
	
	@PWFieldBasicInfo(caption = "Start Date", type = "datetime", unique = true)
	@DateTimeInfo(format = "yyyy-MM-dd")
	private Date startDate;
	
	@PWFieldBasicInfo(caption = "End Date", type = "datetime", unique = true)
	@DateTimeInfo(format = "yyyy-MM-dd")
	private Date endDate;
	
	@PWFieldBasicInfo(caption = "Division ID", type = "varchar(100)")
	private String divisionId;
	
	@PWFieldBasicInfo(caption = "Reason", type = "text")
	private String reason;
	
	@PWFieldBasicInfo(caption = "Submitted Date", type = "datetime")
	@DateTimeInfo(format = "yyyy-MM-dd")
	private Date submittedDate;
	
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	static {
		PWUtilities.prepareTable(Holiday.class);
	}
}
