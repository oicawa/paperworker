package paperworker.core.ui.command;

import java.io.Closeable;
import java.util.HashMap;
import java.util.List;

import paperworker.core.PWController;
import paperworker.core.PWError;
import paperworker.core.PWWarning;

public abstract class PWCommand<T extends PWController> implements Closeable {

	private T controller;
	private HashMap<String, PWAction<T>> actions = new HashMap<String, PWAction<T>>();
	
	public PWCommand() throws PWError, PWWarning {
		this.controller = getController();
		for (PWAction<T> action : getActions()) {
			action.setController(controller);
			actions.put(action.getName(), action);
		}
	}
	
	protected abstract String getName();
	
	protected abstract String getDescription();
	
	protected abstract T getController() throws PWError, PWWarning;
	
	protected abstract List<PWAction<T>> getActions();
	
	public PWAction<T> getAction(String actionName) {
		if (actions.containsKey(actionName)) {
			return actions.get(actionName);
		}
		return null;
	}
	
	public void printActionList() {
		PaperWorker.message("  --------------------------------------------------");
		for (PWAction<T> action : actions.values()) {
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
		
		for (PWAction<T> action : actions.values()) {
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
