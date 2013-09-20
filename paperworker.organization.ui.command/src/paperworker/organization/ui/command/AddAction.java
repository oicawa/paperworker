package paperworker.organization.ui.command;

import paperworker.master.ui.command.MasterAddAction;
import paperworker.organization.core.Organization;
import paperworker.organization.core.OrganizationController;

public class AddAction extends MasterAddAction<Organization, OrganizationController> {

	public AddAction() {
		super();
	}

	@Override
	protected Class<Organization> getItemType() {
		return Organization.class;
	}
}
