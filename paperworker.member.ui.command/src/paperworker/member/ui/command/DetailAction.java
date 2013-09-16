package paperworker.member.ui.command;

import paperworker.core.PWError;
import paperworker.core.ui.command.Action;
import paperworker.core.ui.command.PaperWorker;
import paperworker.master.ui.command.MasterDetailAction;
import paperworker.member.core.Member;
import paperworker.member.core.MemberController;

public class DetailAction extends MasterDetailAction<Member, MemberController> {

	public DetailAction() {
		super();
	}
	
	@Override
	protected void printDetail(Member item) throws PWError {
		String[] fieldNames = { "groupId", "birthday", "enteredDate", "gender", "telephone", "address", "email" };
		int maxLength = Action.getMaxLengthOfCaptions(Member.class);
		
		PaperWorker.message("  [%s] %s %s (%s %s)",
				item.getMemberId(),
				item.getFamilyName(),
				item.getFirstName(),
				item.getFamilyKana(),
				item.getFirstKana());
		for (String fieldName : fieldNames) {
			printField(item, fieldName, maxLength);
		}
	}

	@Override
	protected Class<Member> getItemType() {
		return Member.class;
	}
}
