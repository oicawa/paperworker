package paperworker.member.ui.command;

import java.util.ArrayList;
import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.Action;
import paperworker.master.ui.command.MasterCommand;
import paperworker.member.core.Member;
import paperworker.member.core.MemberController;

public class Command extends MasterCommand<Member, MemberController> {
	public Command() throws PWError, PWWarning {
		super();
	}
	
	protected String getName() {
		return "member";
	}

	@Override
	protected List<Action<MemberController>> getActions() {
		List<Action<MemberController>> actions = new ArrayList<Action<MemberController>>();
		actions.add(new ListAction());
		actions.add(new AddAction());
		actions.add(new DetailAction());
		actions.add(new UpdateAction());
		actions.add(new DeleteAction());
		return actions;
	}

	@Override
	protected MemberController getController() throws PWError, PWWarning {
		return new MemberController();
	}
}
