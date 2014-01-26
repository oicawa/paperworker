/*
 *  $Id: PWViewPanel.java 2013/12/28 22:25:18 masamitsu $
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

package pw.ui.swing.basic;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import pw.core.table.PWTable;
import pw.core.table.PWTableColumn;
import pw.core.table.PWTableRow;

/**
 * @author masamitsu
 *
 */
public class PWTableViewPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7115936873190034551L;
	protected DefaultTableModel tableModel;
	protected DefaultTableColumnModel tableColumns;
	protected JTable tableView;
	protected PWTable tableData;
	protected HashMap<String, TableColumn> columnMap;
	protected PWTableRow selectedRow;
	
	class PWViewCellRenderer implements TableCellRenderer {
		private JLabel captionLabel;
		PWViewCellRenderer(String caption) {
			this.captionLabel = new JLabel(caption);
		}
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			return captionLabel;
		}
		
	}
	
	public PWTableViewPanel() {
		setLayout(new BorderLayout(0, 0));
		add(createTable(), BorderLayout.CENTER);
	}

	/**
	 * @return
	 */
	private Component createTable() {
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -1378660522664810408L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableColumns = new DefaultTableColumnModel();
		tableView = new JTable(tableModel);
		tableView.setAutoCreateColumnsFromModel(false);
		tableView.setAutoCreateRowSorter(true);
		tableView.setColumnModel(tableColumns);
		columnMap = new HashMap<String, TableColumn>();
		return new JScrollPane(tableView);
	}
	
	protected void removeAllColumns() {
		while (0 < tableColumns.getColumnCount()) {
			TableColumn column = tableColumns.getColumn(0);
			tableColumns.removeColumn(column);
			String headerName = (String)column.getHeaderValue();
			columnMap.remove(headerName);
		}
	}

	public void addColumnHeader(String mappingName, String displayName) {
		if (!columnMap.containsKey(mappingName)) {
			int count = columnMap.size();
			TableColumn column = new TableColumn();
			column.setHeaderValue(mappingName);
			column.setModelIndex(count);	// Require. If not set, all column header value is set by last specified header name.
			tableColumns.addColumn(column);
			tableModel.addColumn(column);
			columnMap.put(mappingName, column);
		}
		TableColumn column = columnMap.get(mappingName);
		column.setHeaderRenderer(new PWViewCellRenderer(displayName));
	}
	
	public void setColumnVisible(String headerName, boolean isVisible) {
		TableColumn column = columnMap.get(headerName);
		if (isVisible) {
			tableColumns.addColumn(column);
			int columnIndex = tableColumns.getColumnCount();
			tableColumns.moveColumn(columnIndex, column.getModelIndex());
		} else {
			tableColumns.removeColumn(column);
		}
	}
	
	private TableColumn getColumn(int modelIndex) {
		for (TableColumn column : columnMap.values()) {
			if (column.getModelIndex() == modelIndex) {
				return column;
			}
		}
		return null;
	}
	
	private TableColumn getColumn(String headerName) {
		if (!columnMap.containsKey(headerName)) {
			return null;
		}
		return columnMap.get(headerName);
	}
	
	public boolean isColumnVisible(TableColumn targetColumn) {
		if (targetColumn == null) {
			return false;
		}
		
		int count = tableColumns.getColumnCount();
		for (int i = 0; i < count; i++) {
			TableColumn column = tableColumns.getColumn(i);
			if (column == targetColumn) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isColumnVisible(String headerName) {
		return isColumnVisible(getColumn(headerName));
	}
	
	public boolean isColumnVisible(int modelIndex) {
		return isColumnVisible(getColumn(modelIndex));
	}
	
	public int getColumnModelIndex(String headerName) {
		TableColumn column = columnMap.get(headerName);
		return column.getModelIndex();
	}
	
	public void removeRow(int rowIndex) {
		tableModel.removeRow(rowIndex);
	}
	
	public void removeAllRows() {
		int count = tableModel.getRowCount();
		for (int i = count - 1; 0 <= i; i--) {
			tableModel.removeRow(i);
		}
	}
	
	public void addRow(Object[] values) {
		tableModel.addRow(values);
	}
	
	public void setData(PWTable dataTable) {
		// Reset
		removeAllRows();
		
		this.tableData = dataTable;
		
		// Set Columns
		int count = tableColumns.getColumnCount();
		List<String> headerValues = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			headerValues.add(tableColumns.getColumn(i).getHeaderValue().toString());
		}
		
		// Set Rows
		for (PWTableRow row : dataTable.getRows()) {
			Object[] values = new Object[count];
			for (int i = 0; i < count; i++) {
				String headerValue = headerValues.get(i);
				values[i] = row.getValue(headerValue);
			}
			addRow(values);
		}
	}
	
	public enum SelectionMode {
		Multi,
		Single,
	}
	
	public void setSelectionMode(PWSelectionMode selectionMode) {
		if (selectionMode == PWSelectionMode.Multi) {
			tableView.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else {
			tableView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}

	/**
	 * @return
	 */
	public List<PWTableRow> getSelectedRows() {
		int[] indexes = tableView.getSelectedRows();
		List<PWTableRow> rows = new ArrayList<PWTableRow>();
		for (int index : indexes) {
			rows.add(tableData.getRow(index));
		}
		return rows;
	}
	
	public void setFilter(RowFilter<TableModel, Integer> filter) {
		RowSorter<? extends TableModel> sorter = tableView.getRowSorter();
		if (!(sorter instanceof TableRowSorter<?>)) {
			return;
		}
		
		TableRowSorter<? extends TableModel> tableRowSorter = (TableRowSorter<? extends TableModel>)sorter;
		tableRowSorter.setRowFilter(filter);
	}
}
