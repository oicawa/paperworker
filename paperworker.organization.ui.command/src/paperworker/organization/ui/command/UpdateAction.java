package paperworker.organization.ui.command;

import paperworker.master.ui.command.MasterUpdateAction;
import paperworker.organization.core.Organization;
import paperworker.organization.core.OrganizationController;

public class UpdateAction extends MasterUpdateAction<Organization, OrganizationController> {

	@Override
	protected Class<Organization> getItemType() {
		return Organization.class;
	}
}
