package paperworker.core.ui.command;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

import paperworker.core.PWController;
import paperworker.core.PWItem;
import paperworker.core.PWField;
import paperworker.core.PWError;
import paperworker.core.PWWarning;

public abstract class Action<TController extends PWController> {
	
	protected TController controller;
	
	public Action() {
	}

	public void setController(TController controller) {
		this.controller = controller;
	}
	
	public abstract String getName();
	public abstract String[] getDescription();
	public abstract void run(String[] args) throws PWError, PWWarning;
	protected abstract String getRegexForParse();
	
	public boolean parse(String[] args) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(" " + args[i]);
		}
		String commandLine = buffer.toString().substring(1);
		String regexp = getRegexForParse();
		return Pattern.matches(regexp, commandLine);
	}
	
	public static void printField(PWItem item, String fieldName, int captionLength) throws PWError {
		PWField fieldInfo = PWField.getField(item.getClass(), fieldName);
		String caption = fieldInfo.getCaption();
		String format = String.format("    %%-%ds : %%s", captionLength);
		try {
			if (fieldInfo.isDate()) {
				Object value = fieldInfo.getValue(item);
				if (value == null) {
					PaperWorker.message(format, caption, null);
				} else {
					SimpleDateFormat formatter = new SimpleDateFormat(fieldInfo.getDateTimeFormat());
					PaperWorker.message(format, caption, formatter.format(value));
				}
			} else {
				PaperWorker.message(format, caption, fieldInfo.getValue(item));
			}
		} catch (PWError e) {
			PaperWorker.error(e);
		}
	}
	
	public static void promptField(PWItem dst, PWItem src, PWField fieldInfo, int captionLength) throws PWError {
		String caption = fieldInfo.getCaption();
		String format = String.format("  %%-%ds >> ", captionLength);
		
		String input = PaperWorker.prompt(format, caption);
		try {
			if (input.equals("")) {
				dst.setValue(fieldInfo.getName(), src.getValue(fieldInfo.getName()));
			} else {
				dst.setValue(fieldInfo.getName(), fieldInfo.parse(input));
			}
		} catch (PWError e) {
			PaperWorker.error(e);
		}
	}
	
	public static int getMaxLengthOfCaptions(Class<? extends PWItem> type) {
		List<String> captions = PWItem.getCaptions(type);
		int max = 0;
		for (String caption : captions) {
			max = max < caption.length() ? caption.length() : max; 
		}
		return max;
	}
}
