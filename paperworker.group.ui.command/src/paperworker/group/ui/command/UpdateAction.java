package paperworker.group.ui.command;

import paperworker.group.core.Group;
import paperworker.group.core.GroupController;
import paperworker.master.ui.command.MasterUpdateAction;

public class UpdateAction extends MasterUpdateAction<Group, GroupController> {

	@Override
	protected Class<Group> getItemType() {
		return Group.class;
	}
}
