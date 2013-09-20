package paperworker.organization.ui.command;

import paperworker.core.PWError;
import paperworker.core.ui.command.PWAction;
import paperworker.core.ui.command.PaperWorker;
import paperworker.master.ui.command.MasterDetailAction;
import paperworker.organization.core.Organization;
import paperworker.organization.core.OrganizationController;

public class DetailAction extends MasterDetailAction<Organization, OrganizationController> {

	public DetailAction() {
		super();
	}
	
	@Override
	protected void printDetail(Organization item) throws PWError {
		String[] fieldNames = { "parentOrganizationId", "leaderId", "subleaderId", "division" };
		int maxLength = PWAction.getMaxLengthOfCaptions(Organization.class);
		
		PaperWorker.message("  [%s] %s (%s)",
				item.getOrganizationId(),
				item.getName(),
				item.getShortName());
		for (String fieldName : fieldNames) {
			printField(item, fieldName, maxLength);
		}
	}

	@Override
	protected Class<Organization> getItemType() {
		return Organization.class;
	}
}
