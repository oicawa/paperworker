package paperworker.member.ui.command;

import paperworker.master.ui.command.MasterAddAction;
import paperworker.member.core.Member;
import paperworker.member.core.MemberController;

public class AddAction extends MasterAddAction<Member, MemberController> {

	public AddAction() {
		super();
	}

	@Override
	protected Class<Member> getItemType() {
		return Member.class;
	}
}
