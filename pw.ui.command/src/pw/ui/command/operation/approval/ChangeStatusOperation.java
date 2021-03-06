/*
 *  $Id: ChangeStateOperation.java 2013/10/19 15:56:20 masamitsu $
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

import pw.action.approval.AbstractChangeStatusAction;
import pw.core.PWError;
import pw.ui.command.PWOperation;

/**
 * @author masamitsu
 *
 */
public class ChangeStatusOperation extends PWOperation {

	public ChangeStatusOperation(AbstractChangeStatusAction action) {
		super(action);
	}

	/* (non-Javadoc)
	 * @see pw.ui.command.PWOperation#run(java.lang.String[])
	 */
	@Override
	public void run(String... arguments) {
		if (arguments == null || arguments.length < 3) {
			throw new PWError("Document ID is required.");
		}
		String uuid = arguments[2];
		UUID documentId = UUID.fromString(uuid);
		action.run(documentId);
	}

}
