/*
 *  $Id: Member.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

package paperworker.member.core;

import java.util.Calendar;
import java.util.Date;

import paperworker.core.PWItem;
import paperworker.core.PWError;
import paperworker.core.annotation.PWFieldBasicInfo;
import paperworker.core.annotation.DateTimeInfo;
import paperworker.core.annotation.PWItemBasicInfo;

@PWItemBasicInfo(caption = "Members", tableName = "Members")
public class Member extends PWItem {
	
	@PWFieldBasicInfo(caption = "Member ID", type = "varchar(100)", primary = true)
	private String memberId;
	
	@PWFieldBasicInfo(caption = "Group ID", type = "varchar(100)")
	private String groupId;
	
	@PWFieldBasicInfo(caption = "Family Name", type = "varchar(100)")
	private String familyName;
	
	@PWFieldBasicInfo(caption = "First Name", type = "varchar(100)")
	private String firstName;
	
	@PWFieldBasicInfo(caption = "Family Kana", type = "varchar(100)")
	private String familyKana;
	
	@PWFieldBasicInfo(caption = "First Kana", type = "varchar(100)")
	private String firstKana;
	
	@PWFieldBasicInfo(caption = "Birthday", type = "datetime")
	@DateTimeInfo(format = "yyyy-MM-dd")
	private Date birthday;
	
	@PWFieldBasicInfo(caption = "Entered Date", type = "datetime")
	@DateTimeInfo(format = "yyyy-MM-dd")
	private Date enteredDate;
	
	@PWFieldBasicInfo(caption = "Gender", type = "varchar(100)")
	private Gender gender;
	
	@PWFieldBasicInfo(caption = "Telephone", type = "varchar(100)")
	private String telephone;
	
	@PWFieldBasicInfo(caption = "Address", type = "varchar(100)")
	private String address;
	
	@PWFieldBasicInfo(caption = "Email", type = "varchar(100)")
	private String email;
	
	public Object getValue(String fieldName) throws PWError {
		return PWItem.getValue(this, fieldName);
	}
	
	public void setValue(String fieldName, Object value) throws PWError {
		PWItem.setValue(this, fieldName, value);
	}
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	/* (non-Javadoc)
	 * @see paperworker.core.PWItem#getId()
	 */
	@Override
	public String getId() {
		return memberId;
	}
}
