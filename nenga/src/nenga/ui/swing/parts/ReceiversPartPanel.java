/*
 *  $Id: ReceiversPartPanel.java 2013/12/22 22:37:35 masamitsu $
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

package nenga.ui.swing.parts;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.TableView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import nenga.item.NengaHistory;
import pw.action.basic.PWAddAction;
import pw.action.basic.PWUpdateAction;
import pw.action.basic.PWViewAction;
import pw.core.PWAction;
import pw.core.table.PWTable;
import pw.ui.swing.basic.PWSelectionMode;
import pw.ui.swing.table.PWCellRenderer;
import pw.ui.swing.table.PWCheckBoxCellEditor;
import pw.ui.swing.table.PWComboBoxCellEditor;
import pw.ui.swing.table.PWTableViewPanel;
import pw.ui.swing.table.PWTableViewRowState;
import pw.ui.swing.table.PWTableViewSearchPanel;

/**
 * @author masamitsu
 *
 */
public class ReceiversPartPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2319250559294999837L;
	private PWTableViewPanel receiversTable;
	private int year;
	private UUID senderAddressId;
	public ReceiversPartPanel() {
		setBorder(new TitledBorder(null, "宛先一覧", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		year = -1;
		senderAddressId = null;
		
		JPanel buttonsPanel = new JPanel();
		FlowLayout fl_buttonsPanel = (FlowLayout) buttonsPanel.getLayout();
		fl_buttonsPanel.setAlignment(FlowLayout.RIGHT);
		add(buttonsPanel, BorderLayout.NORTH);
		
		JButton addButton = createAddButton();
		buttonsPanel.add(addButton);
		
		JButton deleteButton = createDeleteButton();
		buttonsPanel.add(deleteButton);
		
		JButton modifyButton = createSaveButton();
		buttonsPanel.add(modifyButton);
		
		JButton printButton = new JButton("印刷");
		buttonsPanel.add(printButton);
		
		receiversTable = new PWTableViewPanel();
		receiversTable.addColumn("UUID", "");
		receiversTable.addColumn("NAME", "宛先");
		receiversTable.addColumn("HONORIFIC", "敬称");
		receiversTable.addColumn("ZIPCODE", "郵便番号");
		receiversTable.addColumn("ADDRESS", "住所");
		receiversTable.addColumn("MOURNING", "喪中");
		receiversTable.addColumn("SENTDATE", "送付日");
		receiversTable.addColumn("RECEIVEDDATE", "受取日");
		receiversTable.setColumnVisible("UUID", false);
		receiversTable.setColumnVisible("STATE", false);
		
		PWComboBoxCellEditor<String> editor = new PWComboBoxCellEditor<String>(receiversTable, "樣", "御中");
		receiversTable.setColumnCellEditor("HONORIFIC", editor);
		
		PWCheckBoxCellEditor checkBox = new PWCheckBoxCellEditor(receiversTable);
		receiversTable.setColumnCellEditor("MOURNING", checkBox);
		receiversTable.setColumnCellRenderer("MOURNING", new PWCellRenderer<Boolean>() {
			private static final long serialVersionUID = 3030047032687724678L;
			@Override
			protected Component getRendererComponent(JTable table, Boolean value, boolean isSelected, boolean isFocused, int row, int column) {
				label.setText((Boolean)value ? "喪中" : "");
				return label;
			}
		});
		
		add(receiversTable, BorderLayout.CENTER);
	}
	
	public void setYear(int year) {
		this.year = year;
		updateData();
	}
	
	public void setSenderAddressId(UUID senderAddressId) {
		this.senderAddressId = senderAddressId;
		updateData();
	}
	
	protected void updateData() {
		if (year == -1 || senderAddressId == null) {
			return;
		}
		PWViewAction action = (PWViewAction)PWAction.getAction("nenga", "listhistories");
		PWTable tableData = (PWTable)action.run(year, senderAddressId);
		receiversTable.setData(tableData);
	}
	
	/**
	 * @return
	 */
	private JButton createSaveButton() {
		JButton button = new JButton("保存");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(null, "追加・変更した行を保存しますか？", "保存", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.NO_OPTION) {
					return;
				}
				
				// Add Rows
				int[] addedIndexes = receiversTable.getRowIndexesByState(PWTableViewRowState.Added);
				if (0 < addedIndexes.length) {
					List<NengaHistory> addedHistories = getHistories(addedIndexes);
					Object[] addedObjects = addedHistories.toArray();
					PWAddAction addAction = (PWAddAction)PWAction.getAction("nenga", "addhistories");
					addAction.run(addedObjects);
					for (int i = 0; i < addedIndexes.length; i++) {
						receiversTable.setRowState(i, PWTableViewRowState.None);
					}
				}
				
				// Update Rows
				int[] modifiedIndexes = receiversTable.getRowIndexesByState(PWTableViewRowState.Modified);
				if (0 < modifiedIndexes.length) {
					List<NengaHistory> modifiedHistories = getHistories(modifiedIndexes);
					Object[] modifiedObjects = modifiedHistories.toArray();
					PWUpdateAction updateAction = (PWUpdateAction)PWAction.getAction("nenga", "updatehistories");
					updateAction.run(modifiedObjects);
					for (int i = 0; i < modifiedIndexes.length; i++) {
						receiversTable.setRowState(i, PWTableViewRowState.None);
					}
				}
			}
		});
		return button;
	}
	/**
	 * @return
	 */
	private JButton createDeleteButton() {
		JButton button = new JButton("削除");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(null, "選択した行を削除しますか？", "Hoge", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.NO_OPTION) {
					return;
				}
				int[] indexes = receiversTable.getSelectedRowIndexes();
				receiversTable.deleteRows(indexes);
			}
		});
		return button;
	}
	/**
	 * @return
	 */
	private JButton createAddButton() {
		JButton button = new JButton("追加");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get Data
				PWAction action = PWAction.getAction("address", "list");
				PWTable table = (PWTable)action.run();
				
				// Table View
				final PWTableViewPanel tableView = new PWTableViewPanel();
				tableView.setSelectionMode(PWSelectionMode.Multi);
				tableView.addColumn("UUID", "");
				tableView.addColumn("ZIPCODE", "郵便番号");
				tableView.addColumn("ADDRESS", "住所");
				tableView.addColumn("FAMILYNAME", "姓");
				tableView.addColumn("FIRSTNAMES", "名");
				tableView.setColumnVisible("UUID", false);
				tableView.add(new PWTableViewSearchPanel(tableView), BorderLayout.NORTH);
				tableView.setData(table);
				
				// Show Dialog
				int res = JOptionPane.showConfirmDialog(
						null,
						tableView,
						"住所選択",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				if (res == JOptionPane.CANCEL_OPTION) {
					return;
				}
				
				// Set Selected Data to GUI
				int[] modelIndexes = tableView.getSelectedRowIndexes();
				for (int i = 0; i < modelIndexes.length; i++) {
					int index = modelIndexes[i];
					UUID uuid = (UUID)tableView.getValueAt(index, "UUID");
					String familyName = tableView.getValueAt(index, "FAMILYNAME") == null ? "" : (String)tableView.getValueAt(index, "FAMILYNAME");
					String firstNames = tableView.getValueAt(index, "FIRSTNAMES") == null ? "" : (String)tableView.getValueAt(index, "FIRSTNAMES");
					
					String name = familyName + " " + firstNames;
					String honorific = "樣";
					String zipcode = tableView.getValueAt(index, "ZIPCODE") == null ? "" : (String)tableView.getValueAt(index, "ZIPCODE");
					String address = tableView.getValueAt(index, "ADDRESS") == null ? "" : (String)tableView.getValueAt(index, "ADDRESS");
					boolean mourning = false;
					String sentDate = "";
					String receivedDate = "";
					
					receiversTable.addRow(uuid, name, honorific, zipcode, address, mourning, sentDate, receivedDate, PWTableViewRowState.Added);
				}
			}
		});
		return button;
	}
	
	private List<NengaHistory> getHistories(int[] indexes) {
		List<NengaHistory> histories = new ArrayList<NengaHistory>();
		for (int i = 0; i < indexes.length; i++) {
			NengaHistory history = new NengaHistory();
			history.setYear(year);
			history.setSenderAddressId(senderAddressId);
			history.setReceiverAddressId((UUID)receiversTable.getValueAt(indexes[i], "UUID"));
			history.setHonorific((String)receiversTable.getValueAt(indexes[i], "HONORIFIC"));
			history.setMourning((Boolean)receiversTable.getValueAt(indexes[i], "MOURNING"));
			history.setSentDate(null);
			history.setReceivedDate(null);
			histories.add(history);
		}
		return histories;
	}
}
