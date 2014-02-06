/*
 *  $Id: History.java 2013/12/14 18:49:17 masamitsu $
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

package nenga.item;

import java.util.Date;
import java.util.UUID;

import pw.core.PWUtilities;
import pw.core.annotation.PWDateTimeInfo;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
@PWItemBasicInfo(caption = "Nenga Send/Receive History", tableName = "NengaHistory")
public class NengaHistory extends PWItem {
	
	@PWFieldBasicInfo(caption = "Year", type = "int", primary = true)
	private int year;
	
	@PWFieldBasicInfo(caption = "Sender Address ID", type = "uuid", primary = true)
	private UUID senderAddressId;
	
	@PWFieldBasicInfo(caption = "Receiver Address ID", type = "uuid", primary = true)
	private UUID receiverAddressId;
	
	@PWFieldBasicInfo(caption = "Honorific", type = "varchar(100)")
	private String honorific;
	
	@PWFieldBasicInfo(caption = "Sent Date", type = "datetime")
	@PWDateTimeInfo(format = "yyyy-MM-dd")
	private Date sentDate;
	
	@PWFieldBasicInfo(caption = "Received Date", type = "datetime")
	@PWDateTimeInfo(format = "yyyy-MM-dd")
	private Date receivedDate;
	
	@PWFieldBasicInfo(caption = "Mourning", type = "bool")
	private boolean mourning;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public UUID getSenderAddressId() {
		return senderAddressId;
	}
	public void setSenderAddressId(UUID senderAddressId) {
		this.senderAddressId = senderAddressId;
	}
	public UUID getReceiverAddressId() {
		return receiverAddressId;
	}
	public void setReceiverAddressId(UUID receiverAddressId) {
		this.receiverAddressId = receiverAddressId;
	}
	public String getHonorific() {
		return honorific;
	}
	public void setHonorific(String honorific) {
		this.honorific = honorific;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receiveDate) {
		this.receivedDate = receiveDate;
	}
	public boolean isMourning() {
		return mourning;
	}
	public void setMourning(boolean mourning) {
		this.mourning = mourning;
	}

	static {
		PWUtilities.prepareTable(NengaHistory.class);
	}
}
