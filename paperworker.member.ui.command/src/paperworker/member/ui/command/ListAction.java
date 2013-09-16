package paperworker.member.ui.command;

import paperworker.core.ui.command.PaperWorker;
import paperworker.master.ui.command.MasterListAction;
import paperworker.member.core.Member;
import paperworker.member.core.MemberController;

public class ListAction extends MasterListAction<Member, MemberController> {

	public ListAction() {
		super();
	}

	@Override
	protected void printItem(Member item) {
		PaperWorker.message("%s : %s %s (%s %s)",
				item.getMemberId(),
				item.getFamilyName(),
				item.getFirstName(),
				item.getFamilyKana(),
				item.getFirstKana());
	}

	@Override
	protected Class<Member> getItemType() {
		return Member.class;
	}
}
