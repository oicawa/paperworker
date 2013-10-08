/*
 *  $Id: Organization.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

import pw.core.PWUtilities;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;
import pw.core.item.PWItem;
import pw.standard.item.division.OrganizationDivision;

@PWItemBasicInfo(caption = "Organizations", tableName = "Organizations")
public class Organization extends PWItem {

	@PWFieldBasicInfo(caption = "Organization ID", type = "varchar(100)", primary = true)
	private String organizationId;

	@PWFieldBasicInfo(caption = "Parent Organization ID", type = "varchar(100)")
	private String parentOrganizationId;
	
	@PWFieldBasicInfo(caption = "Name", type = "varchar(100)")
	private String name;
	
	@PWFieldBasicInfo(caption = "Short Name", type = "varchar(100)")
	private String shortName;
	
	@PWFieldBasicInfo(caption = "Leader ID", type = "varchar(100)")
	private String leaderId;
	
	@PWFieldBasicInfo(caption = "Subleader ID", type = "varchar(100)")
	private String subleaderId;
	
	@PWFieldBasicInfo(caption = "Division", type = "varchar(100)")
	private OrganizationDivision division;
	
	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
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

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getSubleaderId() {
		return subleaderId;
	}

	public void setSubleaderId(String subleaderId) {
		this.subleaderId = subleaderId;
	}

	public OrganizationDivision getDivision() {
		return division;
	}

	public void setDivision(OrganizationDivision division) {
		this.division = division;
	}
	
	static {
		PWUtilities.prepareTable(Organization.class);
	}
}
