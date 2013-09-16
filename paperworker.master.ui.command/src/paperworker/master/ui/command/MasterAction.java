package paperworker.master.ui.command;

import paperworker.core.ui.command.Action;
import paperworker.master.core.MasterController;
import paperworker.master.core.MasterItem;

public abstract class MasterAction<TItem extends MasterItem, TController extends MasterController<TItem>>
							extends Action<TController> {
	
	public MasterAction() {
		super();
	}

	public String getCommandName() {
		return getItemType().getSimpleName().toLowerCase();
	}
	
	protected abstract Class<TItem> getItemType();
}
