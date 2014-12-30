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

package pw.ui.swing.table;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import pw.core.table.PWTable;
import pw.core.table.PWTableRow;
import pw.ui.swing.basic.PWSelectionMode;

/**
 * @author masamitsu
 *
 */
public class PWTableViewPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7115936873190034551L;
	protected PWTableModel tableModel;					// Inner Data Storage 
	protected DefaultTableColumnModel tableColumns;	// For tableView
	protected JTable tableView;
	protected HashMap<String, TableColumn> columnMap;
	protected List<PWTableViewRowState> rowStateList;
	protected JLabel rowCountLabel;
	protected String selectedCountCaption;
	protected String totalCountCaption = "%d";
	
	public PWTableViewPanel() {
		setLayout(new BorderLayout(0, 0));
		add(createTable(), BorderLayout.CENTER);
		add(createRowCount(), BorderLayout.SOUTH);
	}

	private Component createRowCount() {
		rowCountLabel = new JLabel();
		rowCountLabel.setHorizontalAlignment(JLabel.RIGHT);
		return rowCountLabel;
	}
	
	public void setSelectedCountCaption(String caption) {
		selectedCountCaption = caption;
	}
	
	public void setTotalCountCaption(String caption) {
		totalCountCaption = caption;
	}
	
	private void updateCounts() {
		String caption;
		if (selectedCountCaption == null) {
			caption = String.format(totalCountCaption, tableView.getRowCount());
		} else {
			caption = String.format(selectedCountCaption + ", " + totalCountCaption, tableView.getSelectedRowCount(), tableView.getRowCount());
		}
		this.rowCountLabel.setText(caption);
	}

	/**
	 * @return
	 */
	private Component createTable() {
		tableModel = new PWTableModel();
		tableColumns = new DefaultTableColumnModel();
		tableView = new JTable(tableModel);
		tableView.setAutoCreateColumnsFromModel(false);
		tableView.setAutoCreateRowSorter(true);
		tableView.setColumnModel(tableColumns);
		tableView.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				updateCounts();
			}
			
		});
		columnMap = new HashMap<String, TableColumn>();
		rowStateList = new ArrayList<PWTableViewRowState>();
		return new JScrollPane(tableView);
	}

	public void addColumn(String mappingName, String displayName) {
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
		column.setHeaderRenderer(new PWTableViewHeaderRenderer(displayName));
	}
	
	public void setColumnVisible(String mappingName, boolean isVisible) {
		TableColumn column = columnMap.get(mappingName);
		if (isVisible) {
			tableColumns.addColumn(column);
			int columnIndex = tableColumns.getColumnCount();
			tableColumns.moveColumn(columnIndex, column.getModelIndex());
		} else {
			tableColumns.removeColumn(column);
		}
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
	
	public void setColumnCellEditor(String mappingName, TableCellEditor editor) {
		if (!columnMap.containsKey(mappingName)) {
			return;
		}
		TableColumn column = columnMap.get(mappingName);
		column.setCellEditor(editor);
		tableModel.setColumnEditable(column.getModelIndex(), true);
	}
	
	public void setColumnCellRenderer(String mappingName, TableCellRenderer renderer) {
		if (!columnMap.containsKey(mappingName)) {
			return;
		}
		TableColumn column = columnMap.get(mappingName);
		column.setCellRenderer(renderer);
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
	
	public int getColumnModelIndex(String headerName) {
		TableColumn column = columnMap.get(headerName);
		return column.getModelIndex();
	}
	
	public void setData(PWTable dataTable) {
		setData(dataTable, PWTableViewRowState.None);
	}
	
	public void setData(PWTable dataTable, PWTableViewRowState state) {
		// Reset
		deleteAllRows();
		
		// Set Rows
		int count = tableModel.getColumnCount();
		for (PWTableRow row : dataTable.getRows()) {
			Object[] values = new Object[count];
			for (int i = 0; i < count; i++) {
				values[i] = row.getValue(i);
			}
			tableModel.addRow(values);
			rowStateList.add(state);
		}
		updateCounts();
	}
	
	public void addRow(Object... values) {
		tableModel.addRow(values);
		rowStateList.add(PWTableViewRowState.Added);
		updateCounts();
	}

	public void setRow(int row, Object... values) {
		int count = tableModel.getColumnCount();
		if (values.length < count) {
			count = values.length;
		}
		
		for (int column = 0; column < count; column++) {
			tableModel.setValueAt(values[column], row, column);
		}
		
		if (rowStateList.get(row) == PWTableViewRowState.None) {
			rowStateList.set(row, PWTableViewRowState.Modified);
		}
	}
	
	public void deleteRows(int... modelIndexes) {
		List<Integer> modelIndexList = new ArrayList<Integer>();
		for (int i = 0; i < modelIndexes.length; i++) {
			modelIndexList.add(modelIndexes[i]);
		}
		
		Collections.sort(modelIndexList);
		Collections.reverse(modelIndexList);
		for (int modelIndex : modelIndexList) {
			tableModel.removeRow(modelIndex);
			rowStateList.remove(modelIndex);
		}
		updateCounts();
	}
	
	public void deleteAllRows() {
		int count = tableModel.getRowCount();
		for (int i = 0; i < count; i++) {
			tableModel.removeRow(0);
			rowStateList.remove(0);
		}
		updateCounts();
	}
	
	public void setSelectionMode(PWSelectionMode selectionMode) {
		if (selectionMode == PWSelectionMode.Multi) {
			tableView.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else {
			tableView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}
	
	public Object getValueAt(int modelRowIndex, int modelColumnIndex) {
		return tableModel.getValueAt(modelRowIndex, modelColumnIndex);
	}
	
	public Object getValueAt(int modelRowIndex, String columnName) {
		int modelColumnIndex = columnMap.get(columnName).getModelIndex();
		return tableModel.getValueAt(modelRowIndex, modelColumnIndex);
	}

	/**
	 * @return
	 */
	public int[] getSelectedRowIndexes() {
		int[] viewIndexes = tableView.getSelectedRows();
		int[] modelIndexes = new int[viewIndexes.length];
		for (int i = 0; i < viewIndexes.length; i++) {
			modelIndexes[i] = tableView.convertRowIndexToModel(viewIndexes[i]);
		}
		Arrays.sort(modelIndexes);
		return modelIndexes;
	}
	
	public void setFilter(RowFilter<TableModel, Integer> filter) {
		RowSorter<? extends TableModel> sorter = tableView.getRowSorter();
		if (!(sorter instanceof TableRowSorter<?>)) {
			return;
		}
		
		TableRowSorter<? extends TableModel> tableRowSorter = (TableRowSorter<? extends TableModel>)sorter;
		tableRowSorter.setRowFilter(filter);
		updateCounts();
	}
	
	public int[] getRowIndexesByState(PWTableViewRowState state) {
		int[] dstIndexes = new int[rowStateList.size()];
		int dstIndex = 0;
		for (int srcIndex = 0; srcIndex < rowStateList.size(); srcIndex++) {
			if (rowStateList.get(srcIndex) == state) {
				dstIndexes[dstIndex] = srcIndex;
				dstIndex++;
			}
		}
		int[] targetStateIndexes = Arrays.copyOf(dstIndexes, dstIndex);
		return targetStateIndexes;
	}
	
	public PWTableViewRowState getRowState(int modelIndex) {
		return rowStateList.get(modelIndex);
	}
	
	public void setRowState(int modelIndex, PWTableViewRowState state) {
		rowStateList.set(modelIndex, state);
	}

	/**
	 * @return
	 */
	public int getRowCount() {
		return tableModel.getRowCount();
	}
}
