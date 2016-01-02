/*
 *  $Id: PWCheckBoxCellEditor.java 2014/01/28 22:02:51 masamitsu $
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

package pw.ui.swing.table.editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

import pw.ui.swing.table.PWTableViewPanel;
import pw.ui.swing.table.PWTableViewRowState;

/**
 * @author masamitsu
 *
 */
public class PWCheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -444438388252924616L;
	
	protected int rowIndex;
	protected JCheckBox checkBox;
	
	public PWCheckBoxCellEditor(final PWTableViewPanel tableView) {
		rowIndex = -1;
		checkBox = new JCheckBox();
		checkBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (rowIndex < 0) {
					return;
				}
				PWTableViewRowState state = tableView.getRowState(rowIndex);
				if (state != PWTableViewRowState.Added)
					tableView.setRowState(rowIndex, PWTableViewRowState.Modified);
			}
		});
	}
	
	public boolean isCellEditable(EventObject event) {
		if (!(event instanceof MouseEvent)) {
			return false;
		}
		if (((MouseEvent)event).getClickCount() == 1) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		return checkBox.isSelected();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		rowIndex = row;
		checkBox.setSelected((Boolean)value);
		return checkBox;
	}

}
