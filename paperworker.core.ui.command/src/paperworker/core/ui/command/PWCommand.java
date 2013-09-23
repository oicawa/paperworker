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

package paperworker.core.ui.command;

import java.io.Closeable;
import java.util.HashMap;
import java.util.List;

import paperworker.core.PWBasicController;
import paperworker.core.PWError;
import paperworker.core.PWItem;
import paperworker.core.PWWarning;

public abstract class PWCommand<TItem extends PWItem, TController extends PWBasicController<TItem>> implements Closeable {

	private TController controller;
	private HashMap<String, PWAction<TItem, TController>> actions = new HashMap<String, PWAction<TItem, TController>>();
	
	public PWCommand() throws PWError, PWWarning {
		this.controller = createController();
		for (PWAction<TItem, TController> action : getActions()) {
			action.setController(controller);
			actions.put(action.getName(), action);
		}
	}
	
	protected abstract String getName();
	
	protected abstract String getDescription();
	
	protected abstract TController createController() throws PWError, PWWarning;
	
	protected abstract List<PWAction<TItem, TController>> getActions();
	
	public PWAction<TItem, TController> getAction(String actionName) {
		if (actions.containsKey(actionName)) {
			return actions.get(actionName);
		}
		return null;
	}
	
	public void printActionList() {
		PaperWorker.message("  --------------------------------------------------");
		for (PWAction<TItem, TController> action : actions.values()) {
			PaperWorker.message("  %s", action.getName());
			for (String line : action.getDescription()) {
				PaperWorker.message("      %s", line);
			}
			PaperWorker.message("");
		}
		PaperWorker.message("  --------------------------------------------------");
		PaperWorker.message("");
	}
	
	public void run(String[] args) throws PWError, PWWarning {
		if (args.length == 0) {
			printActionList();
			return;
		}
		
		for (PWAction<TItem, TController> action : actions.values()) {
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
