package paperworker.master.ui.command;

import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.PWCommand;
import paperworker.master.core.MasterController;
import paperworker.master.core.MasterItem;

public abstract class MasterCommand<TItem extends MasterItem, TController extends MasterController<TItem>>
							extends PWCommand<TController> {
	
	protected MasterController<TItem> controller;
	
	@SuppressWarnings("unchecked")
	public MasterCommand() throws PWError, PWWarning {
		super();
		
		MasterDeleteAction<TItem, TController> deleteAction = (MasterDeleteAction<TItem, TController>)getAction("delete");
		MasterDetailAction<TItem, TController> detailAction = (MasterDetailAction<TItem, TController>)getAction("detail");
		deleteAction.setDetailAction(detailAction);
	}

	protected String getDescription() {
		return String.format("Maintenance tool for %s master.", getName());
	}
}
