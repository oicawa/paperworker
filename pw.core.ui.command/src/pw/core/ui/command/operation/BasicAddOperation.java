/*
 *  $Id: BasicAddOperation.java 2013/09/29 19:36:41 masamitsu $
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

package pw.core.ui.command.operation;

import java.util.List;

import pw.core.PWItem;
import pw.core.PWUtilities;
import pw.core.action.AbstractBasicAction;
import pw.core.ui.command.PaperWorker;
import pw.core.ui.command.editor.PWFieldEditor;

/**
 * @author masamitsu
 *
 */
public class BasicAddOperation extends AbstractBasicOperation {
	
	public BasicAddOperation(AbstractBasicAction action) {
		super(action);
	}

	/* (non-Javadoc)
	 * @see pw.core.ui.command.PWBasicOperation#run(java.lang.String[])
	 */
	@Override
	public void run(String... arguments) {
		PWItem item = (PWItem) PWUtilities.createInstance(action.getItemType());
		
		PaperWorker.message("<< ADD >>");
		for (PWFieldEditor editor : getFieldEditors()) {
			editor.print(item, " >> ");
		}
		
		PaperWorker.message("------------------------------");
		if (PaperWorker.confirm("Do you save? [Y/N] >> ", "*** Input 'Y' or 'N'. ***", "Y", "N")) {
			action.run(item);
			
			PaperWorker.message("");
			PaperWorker.message("Saved.");
		} else {
			PaperWorker.message("");
			PaperWorker.message("Canceled.");
		}
	}
	
	public List<PWFieldEditor> getFieldEditors() {
		return getDefaultFieldEditors(action.getItemType());
	}

}
