/*
 *  $Id: PWApprovalRequestAction.java 2013/10/09 0:32:25 masamitsu $
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

package pw.action.approval;

import java.util.UUID;

import pw.action.basic.PWAddAction;
import pw.core.PWAction;
import pw.core.PWError;
import pw.core.PWUtilities;
import pw.core.accesser.PWQuery;
import pw.core.item.PWItem;
import pw.item.division.ApprovalStatus;
import pw.item.routine.Approval;

/**
 * @author masamitsu
 *
 */
public class PWRequestAction extends PWAction {

	private String tableName;
	
	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#parseArguments(java.lang.String[])
	 */
	@Override
	protected void parseArguments(String[] arguments) {
		if (arguments == null || arguments.length != 1) {
			throw new PWError("%s requires a target bean classpath.", this.getClass().getName());
		}
		
		String classPath = arguments[0];
		@SuppressWarnings("unchecked")
		Class<? extends PWItem> itemType = (Class<? extends PWItem>)PWUtilities.getClass(classPath);
		tableName = PWItem.getTableName(itemType);
	}

	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#run(java.lang.Object[])
	 */
	@Override
	public Object run(Object... objects) {
		if (objects.length != 2) {
			throw new PWError("Document ID & approver IDs are required.");
		}
		
		UUID documentId = (UUID)objects[0];
		String[] approverIds = (String[])objects[1];
		PWQuery[] queries = new PWQuery[approverIds.length];
		for (int i = 0; i < approverIds.length; i++) {
			Approval approval = new Approval();
			approval.setDocumentId(documentId);
			approval.setApproverId(approverIds[i]);
			approval.setTableName(tableName);
			approval.setOrderNo(i);
			approval.setStatus(i == 0 ? ApprovalStatus.Request : ApprovalStatus.None);
			PWQuery query = PWAddAction.getQuery(approval);
			queries[i] = query;
		}
		
		session.getAccesser().execute(queries);
		return null;
	}

}
