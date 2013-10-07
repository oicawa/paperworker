/*
 *  $Id: PWFixedFieldEditor.java 2013/10/07 22:36:19 masamitsu $
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

package pw.core.ui.command.editor;

import pw.core.PWError;
import pw.core.PWField;
import pw.core.PWItem;
import pw.core.ui.command.PaperWorker;

/**
 * @author masamitsu
 *
 */
public class PWFixedFieldEditor extends PWAbstractFieldEditor {

	private String defaultValue;
	
	/**
	 * @param field
	 * @param captionLength
	 */
	public PWFixedFieldEditor(PWField field, int captionLength, String defaultValue) {
		super(field, captionLength);
		this.defaultValue = defaultValue;
	}
	
	public void print(PWItem dst, String prompt) {
		String input = prompt();
		try {
			if (input == null || input.equals("")) {
				PWItem.setValue(dst, field.getName(), null);
			} else {
				PWItem.setValue(dst, field.getName(), field.parse(input));
			}
		} catch (PWError e) {
			PaperWorker.error(e);
		}
	}
	
	public String prompt() {
		String caption = field.getCaption();
		String format = String.format("%%-%ds >> %%s", captionLength);
		PaperWorker.message(String.format(format, caption, defaultValue));
		return defaultValue;
	}


}
