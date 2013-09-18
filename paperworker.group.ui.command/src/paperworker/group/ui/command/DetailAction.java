package paperworker.group.ui.command;

import paperworker.core.PWError;
import paperworker.core.ui.command.Action;
import paperworker.core.ui.command.PaperWorker;
import paperworker.group.core.Group;
import paperworker.group.core.GroupController;
import paperworker.master.ui.command.MasterDetailAction;

public class DetailAction extends MasterDetailAction<Group, GroupController> {

	public DetailAction() {
		super();
	}
	
	@Override
	protected void printDetail(Group item) throws PWError {
		String[] fieldNames = { "parentGroupId", "leaderId", "subleaderId", "division" };
		int maxLength = Action.getMaxLengthOfCaptions(Group.class);
		
		PaperWorker.message("  [%s] %s (%s)",
				item.getGroupId(),
				item.getName(),
				item.getShortName());
		for (String fieldName : fieldNames) {
			printField(item, fieldName, maxLength);
		}
	}

	@Override
	protected Class<Group> getItemType() {
		return Group.class;
	}
}
