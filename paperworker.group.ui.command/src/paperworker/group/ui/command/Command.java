package paperworker.group.ui.command;

import java.util.ArrayList;
import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.Action;
import paperworker.group.core.Group;
import paperworker.group.core.GroupController;
import paperworker.master.ui.command.MasterCommand;

public class Command extends MasterCommand<Group, GroupController> {
	public Command() throws PWError, PWWarning {
		super();
	}
	
	protected String getName() {
		return "group";
	}

	@Override
	protected List<Action<GroupController>> getActions() {
		List<Action<GroupController>> actions = new ArrayList<Action<GroupController>>();
		actions.add(new ListAction());
		actions.add(new AddAction());
		actions.add(new DetailAction());
		actions.add(new UpdateAction());
		actions.add(new DeleteAction());
		return actions;
	}

	@Override
	protected GroupController getController() throws PWError, PWWarning {
		return new GroupController();
	}
}
