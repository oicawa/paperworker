package paperworker.member.ui.command;

import paperworker.master.ui.command.MasterDeleteAction;
import paperworker.member.core.Member;
import paperworker.member.core.MemberController;

public class DeleteAction extends MasterDeleteAction<Member, MemberController> {

	public DeleteAction() {
		super();
	}

	@Override
	protected Class<Member> getItemType() {
		return Member.class;
	}
}
