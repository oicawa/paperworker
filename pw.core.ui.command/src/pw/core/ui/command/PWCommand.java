/*
 *  $Id: PWCommand.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

package pw.core.ui.command;

import java.io.Closeable;
import java.util.HashMap;
import java.util.List;

import pw.core.PWBasicController;
import pw.core.PWItem;
import pw.core.ui.command.operation.PWAddOperation;
import pw.core.ui.command.operation.PWDeleteOperation;
import pw.core.ui.command.operation.PWDetailOperation;
import pw.core.ui.command.operation.PWOperation;
import pw.core.ui.command.operation.PWUpdateOperation;

public abstract class PWCommand<TItem extends PWItem, TController extends PWBasicController<TItem>> implements Closeable {

	private TController controller;
	private HashMap<String, PWOperation<TItem, TController>> actions = new HashMap<String, PWOperation<TItem, TController>>();
	
	public PWCommand() {
		this.controller = createController();
		for (PWOperation<TItem, TController> action : getActions()) {
			action.setController(controller);
			actions.put(action.getName(), action);
		}
		
		PWAddOperation<TItem, TController> addAction = (PWAddOperation<TItem, TController>)getAction("add");
		PWUpdateOperation<TItem, TController> updateAction = (PWUpdateOperation<TItem, TController>)getAction("update");
		addAction.setUpdateAction(updateAction);
		
		PWDeleteOperation<TItem, TController> deleteAction = (PWDeleteOperation<TItem, TController>)getAction("delete");
		PWDetailOperation<TItem, TController> detailAction = (PWDetailOperation<TItem, TController>)getAction("detail");
		deleteAction.setDetailAction(detailAction);
	}
	
	protected abstract String getName();
	
	protected abstract String getDescription();
	
	protected abstract TController createController();
	
	protected abstract List<PWOperation<TItem, TController>> getActions();
	
	public PWOperation<TItem, TController> getAction(String actionName) {
		if (actions.containsKey(actionName)) {
			return actions.get(actionName);
		}
		return null;
	}
	
	public void printActionList() {
		PaperWorker.message("  --------------------------------------------------");
		for (PWOperation<TItem, TController> action : actions.values()) {
			PaperWorker.message("  %s", action.getName());
			for (String line : action.getDescription()) {
				PaperWorker.message("      %s", line);
			}
			PaperWorker.message("");
		}
		PaperWorker.message("  --------------------------------------------------");
		PaperWorker.message("");
	}
	
	public void run(String[] args) {
		if (args.length == 0) {
			printActionList();
			return;
		}
		
		for (PWOperation<TItem, TController> action : actions.values()) {
			if (action.parse(args)) {
				action.run(args);
				return;
			}
		}
		PaperWorker.error("Confirm the input command line.");
	}

	public void close() {
		controller.close();
	}
}
