/*
 *  $Id: AbstractBasicOperation.java 2013/09/29 19:41:33 masamitsu $
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

package pw.core.ui.command.operation;

import java.util.ArrayList;
import java.util.List;

import pw.core.PWField;
import pw.core.PWItem;
import pw.core.action.AbstractBasicAction;
import pw.core.ui.command.editor.PWFieldEditor;
import pw.core.ui.command.editor.PWStringFieldEditor;

/**
 * @author masamitsu
 *
 */
public abstract class AbstractBasicOperation extends PWBasicOperation {
	
	protected AbstractBasicAction action;
	
	public AbstractBasicOperation(AbstractBasicAction action) {
		super();
		this.action = action;
	}
	
	public static List<PWFieldEditor> getDefaultFieldEditors(Class<? extends PWItem> itemType) {
		List<PWFieldEditor> editors = new ArrayList<PWFieldEditor>();
		List<PWField> fields = PWItem.getFields(itemType);
		int maxLength = getMaxLengthOfCaptions(itemType);
		for (PWField field : fields) {
			PWFieldEditor editor = new PWStringFieldEditor(field, maxLength);
			editors.add(editor);
		}
		return editors;
	}
	
	protected String getKeyParameters(List<PWField> keyFields, Object[] keyValues) {
		
		StringBuffer buffer = new StringBuffer();
		final String SEPARATOR = ",";
		for (int i = 0; i < keyFields.size(); i++) {
			PWField keyField = keyFields.get(i);
			String name = keyField.getName();
			String value = keyField.toString(keyValues[i]);
			buffer.append(SEPARATOR);
			buffer.append(name);
			buffer.append(": ");
			buffer.append(value);
		}
		return buffer.substring(SEPARATOR.length());
	}
}
