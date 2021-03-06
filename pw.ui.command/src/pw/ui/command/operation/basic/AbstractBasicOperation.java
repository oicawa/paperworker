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

package pw.ui.command.operation.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pw.action.basic.AbstractBasicAction;
import pw.core.PWField;
import pw.core.PWUtilities;
import pw.core.item.PWItem;
import pw.ui.command.PWFieldEditor;
import pw.ui.command.PWOperation;
import pw.ui.command.editor.PWFixedFieldEditor;
import pw.ui.command.editor.PWStringFieldEditor;

/**
 * @author masamitsu
 *
 */
public abstract class AbstractBasicOperation extends PWOperation {
	
	public AbstractBasicOperation(AbstractBasicAction action) {
		super(action);
	}
	
	public static List<PWFieldEditor> getDefaultFieldEditors(Class<? extends PWItem> itemType) {
		List<PWFieldEditor> editors = new ArrayList<PWFieldEditor>();
		List<PWField> fields = PWItem.getFields(itemType);
		int maxLength = getMaxLengthOfCaptions(itemType);
		for (PWField field : fields) {
			PWFieldEditor editor;
			if (field.isKey(PWField.KeyType.Primary) && field.isUuid()) {
				UUID uuid = PWUtilities.createNewUuid();
				editor = new PWFixedFieldEditor(field, maxLength, uuid.toString());
				editors.add(editor);
			} else {
				editor = new PWStringFieldEditor(field, maxLength);
				editors.add(editor);
			}
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
	
	public static int getMaxLengthOfCaptions(Class<? extends PWItem> type) {
		List<String> captions = PWItem.getCaptions(type);
		int max = 0;
		for (String caption : captions) {
			max = max < caption.length() ? caption.length() : max; 
		}
		return max;
	}
}
