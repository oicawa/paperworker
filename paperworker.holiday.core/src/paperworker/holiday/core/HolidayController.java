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

import java.util.Date;

import paperworker.core.PWController;
import paperworker.core.PWError;
import paperworker.core.PWField;
import paperworker.core.PWQuery;
import paperworker.core.PWWarning;
import paperworker.holidaydivision.core.HolidayDivisionController;
import paperworker.member.core.MemberController;
import paperworker.organization.core.OrganizationController;

/**
 * @author masamitsu
 *
 */
public class HolidayController extends PWController {

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
		
		String tableName = PWQuery.getTableName(Holiday.class);
		if (!accesser.existTable(tableName)) {
			create();
		}
	}
	
	private void create() throws PWError, PWWarning {
    	PWQuery query = PWQuery.getCreateTableQuery(Holiday.class);
		accesser.execute(query);
	}
	
	public void get(String creatorId, Date startDate, Date endDate) throws PWError, PWWarning {
    	PWQuery query = PWQuery.getSelectQueryByKeys(Holiday.class, PWField.KeyType.Unique, creatorId, startDate, endDate);
		accesser.execute(query);
	}
	
	public void add(String creatorId, Date startDate, Date endDate) throws PWError, PWWarning {
    	PWQuery query = PWQuery.getInsertQuery(Holiday.class, creatorId, startDate, endDate);
		accesser.execute(query);
	}
	
	public void update(String creatorId, Date startDate, Date endDate) throws PWError, PWWarning {
    	Holiday holiday = new Holiday();
    	holiday.setCreatorId(creatorId);
    	holiday.setStartDate(startDate);
    	holiday.setEndDate(endDate);
    	
    	PWQuery query = PWQuery.getUpdateQueryByKey(holiday, PWField.KeyType.Unique);
		accesser.execute(query);
	}
	
	public void delete(String creatorId, Date startDate, Date endDate) throws PWError, PWWarning {
    	PWQuery query = PWQuery.getDeleteQueryByKey(Holiday.class, PWField.KeyType.Unique, creatorId, startDate, endDate);
		accesser.execute(query);
	}

}
