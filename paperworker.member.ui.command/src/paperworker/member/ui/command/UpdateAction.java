package paperworker.member.ui.command;

import paperworker.master.ui.command.MasterUpdateAction;
import paperworker.member.core.Member;
import paperworker.member.core.MemberController;

public class UpdateAction extends MasterUpdateAction<Member, MemberController> {

	@Override
	protected Class<Member> getItemType() {
		return Member.class;
	}
//
//	public static void update(MemberController controller, String memberId) throws PWError, PWWarning {
//		Member src = controller.get(memberId);
//		if (src == null) {
//			PaperWorker.message("The member is not found. [memberId: %s]", memberId);
//			return;
//		}
//		Member dst = new Member();
//		dst.setMemberId(src.getMemberId());
//
//		int maxLength = Action.getMaxLengthOfCaptions(Member.class);
//		
//		List<PWField> fieldInfos = PWItem.getFields(Member.class);
//		for (int i = 1; i < fieldInfos.size(); i++) {
//			promptField(dst, src, fieldInfos.get(i), maxLength);
//		}
//		
//		PaperWorker.message("  ------------------------------");
//		if (PaperWorker.confirm("  Do you save? [Y/N] >> ", "  *** Input 'Y' or 'N'. ***", "Y", "N")) {
//			controller.update(dst);
//			PaperWorker.message("");
//			PaperWorker.message("  Saved.");
//		} else {
//			PaperWorker.message("");
//			PaperWorker.message("  Canceled.");
//		}
//	}
//
//	@Override
//	public void run(String[] args) throws PWError, PWWarning {
//		String memberId = args[2];
//		PWField fieldInfo = PWField.getField(Member.class, "memberId");
//		PaperWorker.message("  << UPDATE >> [%s: %s]", fieldInfo.getCaption(), memberId);
//		PaperWorker.message("  * If you input no value (just only the ENTER key), the field value doesn't change.");
//		update(controller, memberId);
//	}
}
