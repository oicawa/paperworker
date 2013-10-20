/*
 *  $Id: Approval.java 2013/10/08 23:22:49 masamitsu $
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

package pw.standard.item;

import java.util.Date;
import java.util.UUID;

import pw.core.PWUtilities;
import pw.core.annotation.PWDateTimeInfo;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;
import pw.core.item.PWItem;
import pw.standard.item.division.ApprovalStatus;

/**
 * @author masamitsu
 *
 */
@PWItemBasicInfo(caption = "Approvals", tableName = "Approvals")
public class Approval extends PWItem {
	
	@PWFieldBasicInfo(caption = "Document ID", type = "uuid", primary = true, unique = true)
	private UUID documentId;
	
	@PWFieldBasicInfo(caption = "Approver ID", type = "varchar(100)", primary = true)
	private String approverId;
	
	@PWFieldBasicInfo(caption = "Approval Order No", type = "int", unique = true)
	private int orderNo;
	
	@PWFieldBasicInfo(caption = "Table Name", type = "varchar(100)")
	private String tableName;
	
	@PWFieldBasicInfo(caption = "Judged DateTime", type = "datetime")
	@PWDateTimeInfo(format = "yyyy-MM-dd HH:mm:ss")
	private Date judgedDateTime;
	
	@PWFieldBasicInfo(caption = "Approver ID", type = "varchar(100)")
	private ApprovalStatus status;

	
	public UUID getDocumentId() {
		return documentId;
	}

	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public Date getJudgedDateTime() {
		return judgedDateTime;
	}

	public void setJudgedDateTime(Date judgedDateTime) {
		this.judgedDateTime = judgedDateTime;
	}

	public ApprovalStatus getStatus() {
		return status;
	}

	public void setStatus(ApprovalStatus status) {
		this.status = status;
	}

	static {
		PWUtilities.prepareTable(Approval.class);
	}
}
