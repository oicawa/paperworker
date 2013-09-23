/*
 *  $Id: HolidayController.java 2013/09/21 18:31:07 masamitsu $
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

package paperworker.holiday.core;

import paperworker.core.PWBasicController;
import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.holidaydivision.core.HolidayDivision;
import paperworker.holidaydivision.core.HolidayDivisionController;
import paperworker.member.core.Member;
import paperworker.member.core.MemberController;
import paperworker.organization.core.Organization;
import paperworker.organization.core.OrganizationController;

/**
 * @author masamitsu
 *
 */
public class HolidayController extends PWBasicController<Holiday> {

	private HolidayDivisionController holidayDivisionController;
	private MemberController memberController;
	private OrganizationController organizationController;
	
	/**
	 * @throws PWError
	 * @throws PWWarning 
	 */
	public HolidayController() throws PWError, PWWarning {
		super();
		holidayDivisionController = new HolidayDivisionController();
		memberController = new MemberController();
		organizationController = new OrganizationController();
	}

	/* (non-Javadoc)
	 * @see paperworker.core.PWBasicController#getItemType()
	 */
	@Override
	protected Class<Holiday> getItemType() {
		return Holiday.class;
	}

	public Member getCreator(String creatorId) throws PWError, PWWarning {
		Member creator = memberController.get(creatorId);
		return creator;
	}

	public HolidayDivision getDivision(String holidayDivisionId) throws PWError, PWWarning {
		HolidayDivision division = holidayDivisionController.get(holidayDivisionId);
		return division;
	}
	
	public Organization getOrganization(String creatorId) throws PWError, PWWarning {
		Member creator = memberController.get(creatorId);
		if (creator == null) {
			return null;
		}
		Organization organization = organizationController.get(creator.getGroupId());
		return organization;
	}
}
