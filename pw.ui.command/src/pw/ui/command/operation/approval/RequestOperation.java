/*
 *  $Id: ApprovalRequestOperation.java 2013/10/14 20:14:15 masamitsu $
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

package pw.ui.command.operation.approval;

import java.util.UUID;

import pw.action.approval.PWRequestAction;
import pw.core.PWError;
import pw.core.PWUtilities;
import pw.ui.command.PWOperation;
import pw.ui.command.PaperWorker;

/**
 * @author masamitsu
 *
 */
public class RequestOperation extends PWOperation {

	public RequestOperation(PWRequestAction action) {
		super(action);
	}
	
	/* (non-Javadoc)
	 * @see pw.ui.command.PWOperation#run(java.lang.String[])
	 */
	@Override
	public void run(String... arguments) {

		// Target Document ID
		String uuid = PaperWorker.prompt("Document ID  >> ");
		UUID documentId = UUID.fromString(uuid);
		
		// Approver IDs
		System.out.print("Approver IDs >> ");
		String lines = PaperWorker.promptAsMultiLines("Approver IDs");
		if (lines == null) {
			throw new PWError("Approval Request requires 1 or more approver IDs.");
		}
		String[] approverIds = lines.split(PWUtilities.LINE_SEPARATOR);
		if (approverIds.length == 0) {
			throw new PWError("Approval Request requires 1 or more approver IDs.");
		}
		
		// Display
		String format = "                %s";
		for (int i = 0; i < approverIds.length; i++) {
			PaperWorker.message(i == 0 ? "%s" : format, approverIds[i]);
		}
		
		PaperWorker.message("------------------------------");
		if (PaperWorker.confirm("Do you request? [Y/N] >> ", "*** Input 'Y' or 'N'. ***", "Y", "N")) {
			action.run(documentId, approverIds);
			PaperWorker.message("");
			PaperWorker.message("Requested.");
		} else {
			PaperWorker.message("");
			PaperWorker.message("Canceled.");
		}
		
	}

}
