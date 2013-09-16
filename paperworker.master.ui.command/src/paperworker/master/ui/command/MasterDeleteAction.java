package paperworker.master.ui.command;

import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.PaperWorker;
import paperworker.master.core.MasterController;
import paperworker.master.core.MasterItem;

public abstract class MasterDeleteAction<TItem extends MasterItem, TController extends MasterController<TItem>>
	extends MasterAction<TItem, TController> {

	private MasterDetailAction<TItem, TController> detailAction;
	
	public MasterDeleteAction() {
		super();
	}

	public String getName() {
		return "delete";
	}

	public String[] getDescription() {
		final String[] description = {
			String.format("Delete a %s record by specified ID.", getCommandName()),
			"  ---------",
			String.format("  FORMAT > %s %s [ID]", getCommandName(), getName()),
		};
		return description;
	}

	public void run(String[] args) throws PWError, PWWarning {
		delete(controller, args[2]);
	}

	public void delete(TController controller, String memberId) throws PWError, PWWarning {
		PaperWorker.message("  << DELETE >>");
		detailAction.print(memberId);
		
		PaperWorker.message("  ------------------------------");
		if (PaperWorker.confirm("  Do you delete? [Y/N] >> ", "  *** Input 'Y' or 'N'. ***", "Y", "N")) {
			controller.delete(memberId);
			PaperWorker.message("Deleted.");
		} else {
			PaperWorker.message("Canceled.");
		}
	}

	@Override
	protected String getRegexForParse() {
		return String.format("^%s %s [0-9a-zA-Z]+", getCommandName(), getName());
	}
	
	public void setDetailAction(MasterDetailAction<TItem, TController> detailAction) {
		this.detailAction = detailAction;
	}

}
