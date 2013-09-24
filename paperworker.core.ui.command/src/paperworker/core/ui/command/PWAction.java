/*
 *  $Id: PWAction.java 2013/09/21 3:03:36 Masamitsu Oikawa $
 *
 *  ===============================================================================
 *
 *   Copyright (C) 2013  Masamitsu Oikawa  <oicawa@gmail.com>
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 *  ===============================================================================
 */

package paperworker.core.ui.command;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

import paperworker.core.PWController;
import paperworker.core.PWItem;
import paperworker.core.PWField;
import paperworker.core.PWError;
import paperworker.core.PWWarning;

public abstract class PWAction<TItem extends PWItem, TController extends PWController> {
	
	protected final int ACTION_ARG_START_INDEX = 2;	// 2 is 'command' and 'action'.
	
	protected TController controller;
	
	public PWAction() {
	}

	public void setController(TController controller) {
		this.controller = controller;
	}
	
	public abstract String getName();
	public abstract String[] getDescription();
	public abstract void run(String[] args) throws PWError, PWWarning;
	protected abstract String getRegexForParse();
	protected abstract Class<TItem> getItemType();
	protected abstract String getTitle(List<PWField> fields, Object... keyValues);

	public String getCommandName() {
		return getItemType().getSimpleName().toLowerCase();
	}
	
	public boolean parse(String[] args) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(" " + args[i]);
		}
		String commandLine = buffer.toString().substring(1);
		String regexp = getRegexForParse();
		return Pattern.matches(regexp, commandLine);
	}
	
	public static <TItem extends PWItem> void printField(TItem item, String fieldName, int captionLength) throws PWError {
		printField(item, fieldName, captionLength, null);
	}
	
	public static <TItem extends PWItem> void printField(TItem item, String fieldName, int captionLength, String label) throws PWError {
		PWField field = PWField.getField(item.getClass(), fieldName);
		String caption = field.getCaption();
		String format = String.format("%%-%ds : %%s", captionLength);
		try {
			if (label != null) {
				PaperWorker.message(format, caption, label);
				return;
			}
			
			Object value = field.getValue(item);
			if (!field.isDate()) {
				PaperWorker.message(format, caption, value);
				return;
			}
			
			if (value == null) {
				PaperWorker.message(format, caption, null);
				return;
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat(field.getDateTimeFormat());
			PaperWorker.message(format, caption, formatter.format(value));
		} catch (PWError e) {
			PaperWorker.error(e);
		}
	}
	
	public static void promptField(PWItem dst, PWItem src, PWField field, int captionLength) throws PWError {
		promptField(dst, src, field, captionLength, null);
	}
	
	public static void promptField(PWItem dst, PWItem src, PWField field, int captionLength, PWSelector selector) throws PWError {
		String caption = field.getCaption();
		String value = PWItem.getValueAsString(src, field.getName());
		String format = String.format("%%-%ds [%%s]", captionLength);
		PaperWorker.message(format, caption, value);
		String input;
		if (selector == null) {
			input = PaperWorker.prompt("  >> ");
		} else {
			input = selector.prompt("  >> ");
		}
		try {
			if (input.equals("")) {
				dst.setValue(field.getName(), src.getValue(field.getName()));
			} else {
				dst.setValue(field.getName(), field.parse(input));
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
