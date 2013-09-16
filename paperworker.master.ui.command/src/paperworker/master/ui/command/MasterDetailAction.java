package paperworker.master.ui.command;

import paperworker.core.PWField;
import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.PaperWorker;
import paperworker.master.core.MasterController;
import paperworker.master.core.MasterItem;

public abstract class MasterDetailAction<TItem extends MasterItem, TController extends MasterController<TItem>>
	extends MasterAction<TItem, TController> {

	public MasterDetailAction() {
		super();
	}
	
	public String getName() {
		return "detail";
	}

	public String[] getDescription() {
		final String[] description = {
			String.format("Print a %s information by specified ID.", getCommandName()),
			"  ---------",
			String.format("  FORMAT > %s %s [ID]", getCommandName(), getName()),
		};
		return description;
	}

	public void run(String[] args) throws PWError, PWWarning {
		print(args[2]);
	}
	
	public void print(String itemId) throws PWError, PWWarning {
		TItem item = controller.get(itemId);
		if (item == null) {
			PWField field = MasterItem.getPrimaryField(getItemType());
			PaperWorker.message("The %s is not found. [%s: %s]", getCommandName(), field.getCaption(), itemId);
			return;
		}

		printDetail(item);
	}
	
	protected abstract void printDetail(TItem item) throws PWError;
	
	@Override
	protected String getRegexForParse() {
		return String.format("^%s %s [0-9a-zA-Z]+", getCommandName(), getName());
	}
}
