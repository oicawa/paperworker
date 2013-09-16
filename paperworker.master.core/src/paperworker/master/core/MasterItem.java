package paperworker.master.core;

import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWField;
import paperworker.core.PWItem;

public abstract class MasterItem extends PWItem {
	
	public abstract String getId();
	public abstract void setId(String itemId);
	
	public static PWField getPrimaryField(Class<? extends MasterItem> masterItemClass) throws PWError {
		List<PWField> fields = PWItem.getFields(masterItemClass);
		for (PWField field : fields) {
			if (field.isPrimary()) {
				return field;
			}
		}
		throw new PWError("No primary key field in %s table.", masterItemClass.getName());
	}
}
