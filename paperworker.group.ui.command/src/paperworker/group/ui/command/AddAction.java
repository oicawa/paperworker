package paperworker.group.ui.command;

import paperworker.group.core.Group;
import paperworker.group.core.GroupController;
import paperworker.master.ui.command.MasterAddAction;

public class AddAction extends MasterAddAction<Group, GroupController> {

	public AddAction() {
		super();
	}

	@Override
	protected Class<Group> getItemType() {
		return Group.class;
	}
}
