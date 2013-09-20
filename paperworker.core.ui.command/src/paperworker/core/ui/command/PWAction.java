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

public abstract class PWAction<TController extends PWController> {
	
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
