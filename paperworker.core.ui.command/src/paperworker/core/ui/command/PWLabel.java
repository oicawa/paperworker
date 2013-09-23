/*
 *  $Id: PWRenderer.java 2013/09/23 20:48:38 masamitsu $
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

import paperworker.core.PWError;
import paperworker.core.PWField;
import paperworker.core.PWItem;

/**
 * @author masamitsu
 *
 */
public abstract class PWLabel<TItem extends PWItem> {
	
	private String[] fieldNames;
	private String format;
	private TItem item;
	
	public PWLabel(TItem item, String format, String... fieldNames) {
		this.item = item;
		this.format = format;
		this.fieldNames = fieldNames;
	}
	
	public String getText() throws PWError {
		Object[] values = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			PWField field = PWField.getField(item.getClass(), fieldNames[i]);
			if (field.isDate()) {
				Object value = field.getValue(item);
				if (value == null) {
					values[i] = "";
				} else {
					SimpleDateFormat formatter = new SimpleDateFormat(field.getDateTimeFormat());
					values[i] = formatter.format(value);
				}
			} else {
				values[i] = field.getValue(item);
			}
		}
		return String.format(format, values);
	}

}
