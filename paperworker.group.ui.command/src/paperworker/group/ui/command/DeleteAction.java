package paperworker.group.ui.command;

import paperworker.group.core.Group;
import paperworker.group.core.GroupController;
import paperworker.master.ui.command.MasterDeleteAction;

public class DeleteAction extends MasterDeleteAction<Group, GroupController> {

	public DeleteAction() {
		super();
	}

	@Override
	protected Class<Group> getItemType() {
		return Group.class;
	}
}
