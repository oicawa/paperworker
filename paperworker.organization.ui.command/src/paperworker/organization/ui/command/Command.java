package paperworker.organization.ui.command;

import java.util.ArrayList;
import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.PWAction;
import paperworker.master.ui.command.MasterCommand;
import paperworker.organization.core.Organization;
import paperworker.organization.core.OrganizationController;

public class Command extends MasterCommand<Organization, OrganizationController> {
	public Command() throws PWError, PWWarning {
		super();
	}
	
	protected String getName() {
		return "organization";
	}

	@Override
	protected List<PWAction<OrganizationController>> getActions() {
		List<PWAction<OrganizationController>> actions = new ArrayList<PWAction<OrganizationController>>();
		actions.add(new ListAction());
		actions.add(new AddAction());
		actions.add(new DetailAction());
		actions.add(new UpdateAction());
		actions.add(new DeleteAction());
		return actions;
	}

	@Override
	protected OrganizationController getController() throws PWError, PWWarning {
		return new OrganizationController();
	}
}
