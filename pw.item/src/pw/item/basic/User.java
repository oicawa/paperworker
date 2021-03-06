/*
 *  $Id: User.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import pw.core.PWUtilities;
import pw.core.annotation.PWDateTimeInfo;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;
import pw.core.item.PWItem;
import pw.item.division.Gender;

@PWItemBasicInfo(caption = "Users", tableName = "Users")
public class User extends PWItem {
	
	@PWFieldBasicInfo(caption = "User ID", type = "uuid", primary = true)
	private UUID uuid;
	
	@PWFieldBasicInfo(caption = "User Account", type = "varchar(100)", unique = true)
	private String account;
	
	@PWFieldBasicInfo(caption = "Family Name", type = "varchar(100)")
	private String familyName;
	
	@PWFieldBasicInfo(caption = "First Name", type = "varchar(100)")
	private String firstName;
	
	@PWFieldBasicInfo(caption = "Family Kana", type = "varchar(100)")
	private String familyKana;
	
	@PWFieldBasicInfo(caption = "First Kana", type = "varchar(100)")
	private String firstKana;
	
	@PWFieldBasicInfo(caption = "Birthday", type = "datetime")
	@PWDateTimeInfo(format = "yyyy-MM-dd")
	private Date birthday;
	
	@PWFieldBasicInfo(caption = "Entered Date", type = "datetime")
	@PWDateTimeInfo(format = "yyyy-MM-dd")
	private Date enteredDate;
	
	@PWFieldBasicInfo(caption = "Gender", type = "varchar(100)")
	private Gender gender;
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFamilyKana() {
		return familyKana;
	}

	public void setFamilyKana(String familyKana) {
		this.familyKana = familyKana;
	}

	public String getFirstKana() {
		return firstKana;
	}

	public void setFirstKana(String firstKana) {
		this.firstKana = firstKana;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getEnteredDate() {
		return enteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Object getValue(String fieldName) {
		return PWItem.getValue(this, fieldName);
	}
	
	public void setValue(String fieldName, Object value) {
		PWItem.setValue(this, fieldName, value);
	}

	public int getAge() {
		return getYearsFromNow(birthday);
	}
	
	public int getWorkingYears() {
		return getYearsFromNow(enteredDate);
	}
	
	private static int getYearsFromNow(Date date) {
		Calendar now = Calendar.getInstance();
		Calendar base = Calendar.getInstance();
		base.setTime(date);
		return now.get(Calendar.YEAR) - base.get(Calendar.YEAR);
	}
	
	static {
		PWUtilities.prepareTable(User.class);
	}
}
