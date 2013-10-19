/*
 *  $Id: PWApproveAction.java 2013/10/09 0:37:19 masamitsu $
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

package pw.standard.action.approval;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import pw.core.PWError;
import pw.core.accesser.PWAfterSqlQuery;
import pw.core.accesser.PWQuery;
import pw.core.action.PWAction;
import pw.standard.item.Approval;
import pw.standard.item.division.ApprovalStatus;

/**
 * @author masamitsu
 *
 */
public class PWChangeStatusAction extends PWAction {
	
	private static final String WHERE_FOR_REQUESTED = "documentId=? and approverId=? and judgedDateTime is NULL and status=?";
	private static final String WHERE_FOR_NEXTORDER = "documentId=? and orderNo=? and judgedDateTime is NULL and status=?";
	
	private ApprovalStatus status;
	
	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#parseArguments(java.lang.String[])
	 */
	@Override
	protected void parseArguments(String[] arguments) {
		if (arguments == null || arguments.length != 1) {
			throw new PWError("%s requires a ApprovalStatus string ('Approved' or 'Rejected').", this.getClass().getName());
		}
		
		ApprovalStatus status = ApprovalStatus.valueOf(arguments[0]);
		if (status != ApprovalStatus.Approved &&
			status != ApprovalStatus.Rejected) {
			throw new PWError("%s requires a ApprovalStatus string ('Approved' or 'Rejected').", this.getClass().getName());
		}
		
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#run(java.lang.Object[])
	 */
	@Override
	public Object run(Object... objects) {
		if (objects.length != 1) {
			throw new PWError("Document ID is required.");
		}
		
		UUID documentId = (UUID)objects[0];
		
		Approval requested = getRequestedApproval(documentId);
		if (requested == null) {
			throw new PWError("The document does not request you to approve. [DocumentID: %s]", documentId.toString());
		}
		List<PWQuery> queries = new ArrayList<PWQuery>();
		queries.add(getQueryForUpdateRequested(documentId, session.getUserId(), status));
		
		Approval next = getNextApproval(documentId, requested.getOrderNo() + 1);
		if (next != null && status == ApprovalStatus.Approved) {
			queries.add(getQueryForUpdateNext(documentId, next.getOrderNo()));
		}
		
		session.getAccesser().execute(queries.toArray(new PWQuery[queries.size()]));
		return null;
	}
	
	private Approval getRequestedApproval(UUID documentId) {
		PWQuery query = getQueryForRequested(documentId, session.getUserId());
		PWAfterSqlQuery afterQuery = new PWAfterSqlQuery(Approval.class);
		session.getAccesser().select(query, afterQuery);
		List<Object> items = afterQuery.getItemList();
		if (items == null || items.size() == 0) {
			return null;
		}
		return (Approval)items.get(0);
	}
	
	private Approval getNextApproval(UUID documentId, int nextOrderNo) {
		PWQuery query = getQueryForNext(documentId, nextOrderNo);
		PWAfterSqlQuery afterQuery = new PWAfterSqlQuery(Approval.class);
		session.getAccesser().select(query, afterQuery);
		List<Object> items = afterQuery.getItemList();
		if (items == null || items.size() == 0) {
			return null;
		}
		return (Approval)items.get(0);
	}
	
	private static PWQuery getQueryForRequested(UUID documentId, String yourId) {
    	String selectQuery = String.format("select * from Approvals where %s;", WHERE_FOR_REQUESTED);
    	PWQuery query = new PWQuery(selectQuery);
   		query.addValue(documentId);
   		query.addValue(yourId);
   		query.addValue(ApprovalStatus.Request);
    	return query;
	}

	private static PWQuery getQueryForNext(UUID documentId, int nextOrderNo) {
    	String selectQuery = String.format("select * from Approvals where %s;", WHERE_FOR_NEXTORDER);
    	PWQuery query = new PWQuery(selectQuery);
   		query.addValue(documentId);
   		query.addValue(nextOrderNo);
   		query.addValue(ApprovalStatus.None);
    	return query;
	}

	private static PWQuery getQueryForUpdateRequested(UUID documentId, String yourId, ApprovalStatus status) {
    	String updateQuery = String.format("update Approvals set judgedDateTime=?,status=? where %s;", WHERE_FOR_REQUESTED);
    	PWQuery query = new PWQuery(updateQuery);
    	query.addValue(Calendar.getInstance().getTime());
   		query.addValue(status);
   		query.addValue(documentId);
   		query.addValue(yourId);
   		query.addValue(ApprovalStatus.Request);
    	return query;
	}

	private static PWQuery getQueryForUpdateNext(UUID documentId, int nextOrderNo) {
    	String updateQuery = String.format("update Approvals set status=? where %s;", WHERE_FOR_NEXTORDER);
    	PWQuery query = new PWQuery(updateQuery);
   		query.addValue(ApprovalStatus.Request);
   		query.addValue(documentId);
   		query.addValue(nextOrderNo);
   		query.addValue(ApprovalStatus.None);
    	return query;
	}
}
