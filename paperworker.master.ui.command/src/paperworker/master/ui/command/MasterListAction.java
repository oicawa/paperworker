package paperworker.master.ui.command;

import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.master.core.MasterController;
import paperworker.master.core.MasterItem;

public abstract class MasterListAction<TItem extends MasterItem, TController extends MasterController<TItem>>
					extends MasterAction<TItem, TController> {

	public MasterListAction() {
		super();
	}

	public String getName() {
		return "list";
	}

	public String[] getDescription() {
		final String[] description = {
			String.format("Print all %ss information.", getCommandName()),
			"  ---------",
			String.format("  FORMAT > %s list", getCommandName())
		};
		return description;
	}

	public void run(String[] args) throws PWError, PWWarning {
		List<TItem> items = controller.get();
		for (TItem item : items) {
			printItem(item);
		}
	}
	
	protected abstract void printItem(TItem item);

	@Override
	protected String getRegexForParse() {
		return String.format("^%s %s", getCommandName(), getName());
	}

}
