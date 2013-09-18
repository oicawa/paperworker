package paperworker.group.ui.command;

import paperworker.core.ui.command.PaperWorker;
import paperworker.group.core.Group;
import paperworker.group.core.GroupController;
import paperworker.master.ui.command.MasterListAction;

public class ListAction extends MasterListAction<Group, GroupController> {

	public ListAction() {
		super();
	}

	@Override
	protected void printItem(Group item) {
		PaperWorker.message("%s : %s (%s)",
				item.getGroupId(),
				item.getName(),
				item.getShortName());
	}

	@Override
	protected Class<Group> getItemType() {
		return Group.class;
	}
}
