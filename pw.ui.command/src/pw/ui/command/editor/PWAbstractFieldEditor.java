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

package pw.ui.command.editor;

import pw.core.PWError;
import pw.core.PWField;
import pw.core.item.PWItem;
import pw.ui.command.PWFieldEditor;
import pw.ui.command.PaperWorker;

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
		String format = String.format("%%-%ds >> ", captionLength);
		return PaperWorker.prompt(format, caption);
	}
}
