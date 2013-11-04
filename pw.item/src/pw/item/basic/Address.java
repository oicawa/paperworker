/*
 *  $Id: Address.java 2013/11/02 13:40:46 masamitsu $
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

package pw.item.basic;

import java.util.UUID;

import pw.core.PWUtilities;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
@PWItemBasicInfo(caption = "Addresses", tableName = "Addresses")
public class Address extends PWItem {

	@PWFieldBasicInfo(caption = "UUID", type = "uuid", primary = true, auto = true)
	private UUID uuid;

	@PWFieldBasicInfo(caption = "Zip Code", type = "varchar(100)", unique = true)
	private String zipCode;

	@PWFieldBasicInfo(caption = "Zip Code Address", type = "varchar(200)")
	private String addressFromZipCode;

	@PWFieldBasicInfo(caption = "Street Numbers", type = "varchar(100)", unique = true)
	private String streetNumbers;

	@PWFieldBasicInfo(caption = "Formal Street Numbers", type = "varchar(100)")
	private String formalStreetNumbers;

	@PWFieldBasicInfo(caption = "Caption", type = "varchar(100)")
	private String caption;

	@PWFieldBasicInfo(caption = "Memo", type = "varchar(200)")
	private String memo;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddressFromZipCode() {
		return addressFromZipCode;
	}

	public void setAddressFromZipCode(String addressFromZipCode) {
		this.addressFromZipCode = addressFromZipCode;
	}

	public String getStreetNumbers() {
		return streetNumbers;
	}

	public void setStreetNumbers(String streetNumbers) {
		this.streetNumbers = streetNumbers;
	}

	public String getFormalStreetNumbers() {
		return formalStreetNumbers;
	}

	public void setFormalStreetNumbers(String formalStreetNumbers) {
		this.formalStreetNumbers = formalStreetNumbers;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	static {
		PWUtilities.prepareTable(Address.class);
	}
}
