/*
 *  $Id: PWCellRenderer.java 2014/01/29 0:39:25 masamitsu $
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

package pw.ui.swing.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * @author masamitsu
 *
 */
public abstract class PWCellRenderer<T> extends DefaultTableCellRenderer implements TableCellRenderer {

	protected JLabel label;
	
	public PWCellRenderer() {
		label = new JLabel();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocused, int row, int column) {
		// *** !! CAUTION !! ***
		// JLabel's default "opaque" field value is "false".
		// This means JLabel is always shown as transparency.
		// So, without setting that opaque "true",
		// JLabel seems no background color even if you set some color.
		
		if (isSelected) {
			Color foreground = UIManager.getColor("Table[Enabled+Selected].textForeground");
			Color background = UIManager.getColor("Table[Enabled+Selected].textBackground");
			label.setForeground(foreground);
			label.setBackground(background);
			label.setOpaque(true);
		} else if (row % 2 == 0) {
			Color foreground = UIManager.getColor("Table.foreground");
			Color background = UIManager.getColor("Table.alternateRowColor");
			label.setForeground(foreground);
			label.setBackground(background);
			label.setOpaque(true);
		} else {
			Color foreground = UIManager.getColor("Table.foreground");
			label.setForeground(foreground);
			label.setOpaque(false);
		}
		return getRendererComponent(table, (T)value, isSelected, isFocused, row, column);
	}

	protected abstract Component getRendererComponent(JTable table, T value, boolean isSelected, boolean isFocused, int row, int column);
}
