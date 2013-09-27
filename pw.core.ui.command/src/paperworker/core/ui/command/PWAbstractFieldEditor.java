/*
 *  $Id: PWFieldEditor.java 2013/09/25 5:43:12 masamitsu $
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

import paperworker.core.PWError;
import paperworker.core.PWField;
import paperworker.core.PWItem;

/**
 * @author masamitsu
 *
 */
public abstract class PWAbstractFieldEditor implements PWFieldEditor {
	
	protected PWField field;
	protected int captionLength;
	
	public PWAbstractFieldEditor(PWField field, int captionLength) {
		this.field = field;
		this.captionLength = captionLength;
	}
	
	public PWField getField() {
		return field;
	}
	
	public void print(PWItem src, PWItem dst, String prompt) throws PWError {
		String caption = field.getCaption();
		String value = PWItem.getValueAsString(src, field.getName());
		String format = String.format("%%-%ds [%%s]", captionLength);
		PaperWorker.message(format, caption, value);
		String input = prompt("  >> ");
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
	
	public abstract String prompt(String prompt) throws PWError;
}
