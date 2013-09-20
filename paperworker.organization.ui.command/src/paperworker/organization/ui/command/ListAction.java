package paperworker.organization.ui.command;

import paperworker.core.ui.command.PaperWorker;
import paperworker.master.ui.command.MasterListAction;
import paperworker.organization.core.Organization;
import paperworker.organization.core.OrganizationController;

public class ListAction extends MasterListAction<Organization, OrganizationController> {

	public ListAction() {
		super();
	}

	@Override
	protected void printItem(Organization item) {
		PaperWorker.message("%s : %s (%s)",
				item.getOrganizationId(),
				item.getName(),
				item.getShortName());
	}

	@Override
	protected Class<Organization> getItemType() {
		return Organization.class;
	}
}
