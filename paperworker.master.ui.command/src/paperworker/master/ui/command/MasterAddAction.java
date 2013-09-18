package paperworker.master.ui.command;

import paperworker.core.PWField;
import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.PaperWorker;
import paperworker.master.core.MasterController;
import paperworker.master.core.MasterItem;

public abstract class MasterAddAction<TItem extends MasterItem, TController extends MasterController<TItem>>
	extends MasterAction<TItem, TController> {

	public MasterAddAction() {
		super();
	}

	public String getName() {
		return "add";
	}

	public String[] getDescription() {
		final String[] description = {
			String.format("Create a new %s record.", getCommandName()),
			"  ---------",
			String.format("  FORMAT > %s add [ID]", getCommandName(), getName())
		};
		return description;
	}

	public void run(String[] args) throws PWError, PWWarning {
		String newItemId = args[2];
		controller.add(newItemId);
		PWField fieldInfo = MasterItem.getPrimaryField(getItemType());
		PaperWorker.message("  Created a new %s. [%s: %s]", getCommandName(), fieldInfo.getCaption(), newItemId);
		PaperWorker.message("  Input detail information below.");
		PaperWorker.message("  --------------------------------------------------");
		MasterUpdateAction.update(controller, newItemId, getItemType());
	}
	
	protected String getRegexForParse() {
		return String.format("^%s %s [0-9a-zA-Z]+", getCommandName(), getName());
	}
}
