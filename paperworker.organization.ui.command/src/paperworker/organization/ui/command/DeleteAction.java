package paperworker.organization.ui.command;

import paperworker.master.ui.command.MasterDeleteAction;
import paperworker.organization.core.Organization;
import paperworker.organization.core.OrganizationController;

public class DeleteAction extends MasterDeleteAction<Organization, OrganizationController> {

	public DeleteAction() {
		super();
	}

	@Override
	protected Class<Organization> getItemType() {
		return Organization.class;
	}
}
