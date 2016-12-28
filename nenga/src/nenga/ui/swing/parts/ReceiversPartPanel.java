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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import nenga.core.NengaAtenaWriter;
import nenga.core.NengaHistory;
import pw.action.basic.PWAddAction;
import pw.action.basic.PWCommandAction;
import pw.action.basic.PWDeleteAction;
import pw.action.basic.PWUpdateAction;
import pw.action.basic.PWViewAction;
import pw.core.PWAction;
import pw.core.PWError;
import pw.core.table.PWTable;
import pw.ui.swing.basic.PWSelectionMode;
import pw.ui.swing.table.PWTableViewPanel;
import pw.ui.swing.table.PWTableViewRowState;
import pw.ui.swing.table.PWTableViewSearchPanel;
import pw.ui.swing.table.editor.PWCheckBoxCellEditor;
import pw.ui.swing.table.editor.PWComboBoxCellEditor;
import pw.ui.swing.table.editor.PWDateTimeCellEditor;
import pw.ui.swing.table.renderer.PWCellRenderer;
import pw.ui.swing.table.renderer.PWDateTimeCellRenderer;

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
	private String senderZipcode;
	private String senderAddress;
	private String senderFamilyName;
	private String[] senderFirstNames;
	
	public ReceiversPartPanel() {
		setBorder(new TitledBorder(null, "宛先一覧", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		year = -1;
		
		senderAddressId = null;
		senderZipcode = null;
		senderAddress = null;
		senderFamilyName = null;
		senderFirstNames = null;
		
		JPanel buttonsPanel = new JPanel();
		FlowLayout fl_buttonsPanel = (FlowLayout) buttonsPanel.getLayout();
		fl_buttonsPanel.setAlignment(FlowLayout.RIGHT);
		add(buttonsPanel, BorderLayout.NORTH);
		
		JButton importButton = createImportButton();
		buttonsPanel.add(importButton);
		
		JButton addButton = createAddButton();
		buttonsPanel.add(addButton);
		
		JButton deleteButton = createDeleteButton();
		buttonsPanel.add(deleteButton);
		
		JButton modifyButton = createSaveButton();
		buttonsPanel.add(modifyButton);
		
		JButton pdfButton = createPdfButton();
		buttonsPanel.add(pdfButton);
		
		receiversTable = new PWTableViewPanel();
		receiversTable.addColumn("UUID", "");
		receiversTable.addColumn("FAMILYNAME", "姓");
		receiversTable.addColumn("FIRSTNAMES", "名(複数可)");
		receiversTable.addColumn("HONORIFIC", "敬称");
		receiversTable.addColumn("ZIPCODE", "郵便番号");
		receiversTable.addColumn("ADDRESS", "住所");
		receiversTable.addColumn("MOURNING", "喪中");
		receiversTable.addColumn("SENTDATE", "送付日");
		receiversTable.addColumn("RECEIVEDDATE", "受取日");
		receiversTable.setColumnVisible("UUID", false);
		receiversTable.setColumnVisible("STATE", false);
		
		PWComboBoxCellEditor<String> editor = new PWComboBoxCellEditor<String>(receiversTable, "様", "御中");
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
		
		PWDateTimeCellEditor sentDateEditor = new PWDateTimeCellEditor(receiversTable, "yyyy/MM/dd");
		PWDateTimeCellRenderer sentDateRenderer = new PWDateTimeCellRenderer("yyyy/MM/dd");
		receiversTable.setColumnCellEditor("SENTDATE", sentDateEditor);
		receiversTable.setColumnCellRenderer("SENTDATE", sentDateRenderer);
		receiversTable.setColumnClass("SENTDATE", Date.class);
		
		PWDateTimeCellEditor receiveDateEditor = new PWDateTimeCellEditor(receiversTable, "yyyy/MM/dd");
		PWDateTimeCellRenderer receiveDateRenderer = new PWDateTimeCellRenderer("yyyy/MM/dd");
		receiversTable.setColumnCellEditor("RECEIVEDDATE", receiveDateEditor);
		receiversTable.setColumnCellRenderer("RECEIVEDDATE", receiveDateRenderer);
		receiversTable.setColumnClass("RECEIVEDDATE", Date.class);
		
		receiversTable.setTotalCountCaption("%d 件");
		receiversTable.setSelectedCountCaption("選択 %d 件");
		
		add(receiversTable, BorderLayout.CENTER);
	}

	private JButton createImportButton() {
		JButton button = new JButton("インポート");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (senderAddressId == null) {
					JOptionPane.showMessageDialog(null, "差出人の指定が必要です", "インポート", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				String input = JOptionPane.showInputDialog(null, "インポートしたい年を入力してください");
				if (input == null) {
					return;
				}
				
				int inputYear = -1;
				try {
					inputYear = Integer.parseInt(input, 10);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "整数値を入力してください");
					return;
				}
				
				PWViewAction action = (PWViewAction)PWAction.getAction("nenga", "listhistories");
				PWTable tableData = (PWTable)action.run(inputYear, senderAddressId);
				
				if (tableData.getRows().size() == 0) {
					JOptionPane.showMessageDialog(null, String.format("[%d]年における、[%s %s]が差出人になっている年賀状送付履歴はありませんでした", inputYear, senderFamilyName, senderFirstNames));
					return;
				}
				receiversTable.setData(tableData, PWTableViewRowState.Added);
			}
		});
		return button;
	}

	public void setYear(int year) {
		this.year = year;
		updateData();
	}
	
	public void setSenderAddressId(UUID addressId, String zipcode, String address, String familyName, String[] firstNames) {
		senderAddressId = addressId;
		senderZipcode = zipcode;
		senderAddress = address;
		senderFamilyName = familyName;
		senderFirstNames = firstNames;
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
	
	private JButton createAddButton() {
		JButton button = new JButton("追加");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (senderAddressId == null) {
					JOptionPane.showMessageDialog(null, "差出人を指定してから送付先を追加してください", "追加", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
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
				
				HashMap<UUID, Integer> map = new HashMap<UUID, Integer>();
				for (int i = 0; i < receiversTable.getRowCount(); i++) {
					UUID receiverAddressId = (UUID)receiversTable.getValueAt(i, "UUID");
					map.put(receiverAddressId, i);
				}
				
				// Set Selected Data to GUI
				boolean overWrite = false;
				int[] modelIndexes = tableView.getSelectedRowIndexes();
				for (int i = 0; i < modelIndexes.length; i++) {
					int index = modelIndexes[i];
					UUID uuid = (UUID)tableView.getValueAt(index, "UUID");
					String familyName = tableView.getValueAt(index, "FAMILYNAME") == null ? "" : (String)tableView.getValueAt(index, "FAMILYNAME");
					String firstNames = tableView.getValueAt(index, "FIRSTNAMES") == null ? "" : (String)tableView.getValueAt(index, "FIRSTNAMES");
					String name = familyName + " " + firstNames;
					String honorific = "様";
					String zipcode = tableView.getValueAt(index, "ZIPCODE") == null ? "" : (String)tableView.getValueAt(index, "ZIPCODE");
					String address = tableView.getValueAt(index, "ADDRESS") == null ? "" : (String)tableView.getValueAt(index, "ADDRESS");
					boolean mourning = false;
					Date sentDate = null;
					Date receivedDate = null;
					
					if (!map.containsKey(uuid)) {
						receiversTable.addRow(uuid, familyName, firstNames, honorific, zipcode, address, mourning, sentDate, receivedDate, PWTableViewRowState.Added);
						continue;
					}
					
					int modelIndex = map.get(uuid);
					if (overWrite) {
						receiversTable.setRow(modelIndex, uuid, familyName, firstNames, honorific, zipcode, address, mourning, sentDate, receivedDate, PWTableViewRowState.Added);
						continue;
					}
					
					final String buttons[] = {"上書き","すべて上書き","スキップ"};
					int select = JOptionPane.showOptionDialog(
							null,
							String.format("%s は既に追加されています。上書きしますか？\n(「すべて上書き」を選択した場合、残りのすべての重複した宛先が上書きされます。)", name),
							"追加",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							buttons,
							buttons[2]);
					if (select == 2) {
						continue;
					}
					
					if (select == 1) {
						overWrite = true;
					}
					
					receiversTable.setRow(modelIndex, uuid, familyName, firstNames, honorific, zipcode, address, mourning, sentDate, receivedDate, PWTableViewRowState.Added);
				}
			}
		});
		return button;
	}
	
	private JButton createDeleteButton() {
		JButton button = new JButton("削除");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexes = receiversTable.getSelectedRowIndexes();
				if (indexes.length == 0) {
					JOptionPane.showConfirmDialog(null, "１件以上の宛先を選択してください", "削除", JOptionPane.OK_OPTION);
					return;
				}
				
				int res = JOptionPane.showConfirmDialog(null, "選択した行を削除しますか？", "削除", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.NO_OPTION) {
					return;
				}
				
				List<NengaHistory> histories = new ArrayList<NengaHistory>();
				for (int i = 0; i < indexes.length; i++) {
					if (receiversTable.getRowState(indexes[i]) != PWTableViewRowState.None) {
						continue;
					}
					NengaHistory history = new NengaHistory();
					history.setYear(year);
					history.setSenderAddressId(senderAddressId);
					history.setReceiverAddressId((UUID)receiversTable.getValueAt(indexes[i], "UUID"));
					histories.add(history);
				}
				Object[] objects = histories.toArray();
				
				
				PWDeleteAction deleteAction = (PWDeleteAction)PWAction.getAction("nenga", "deletehistories");
				deleteAction.run(objects);
				
				receiversTable.deleteRows(indexes);
			}
		});
		return button;
	}
	
	/**
	 * @return
	 */
	private JButton createSaveButton() {
		JButton button = new JButton("保存");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] addedIndexes = receiversTable.getRowIndexesByState(PWTableViewRowState.Added);
				int[] modifiedIndexes = receiversTable.getRowIndexesByState(PWTableViewRowState.Modified);
				
				if (addedIndexes.length + modifiedIndexes.length == 0) {
					JOptionPane.showMessageDialog(null, "追加・変更した行はありません", "保存", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				String caption;
				if (0 < addedIndexes.length && 0 < modifiedIndexes.length) {
					caption = "追加・変更";
				} else if (0 < addedIndexes.length) {
					caption = "追加";
				} else {
					caption = "変更";
				}
				
				int res = JOptionPane.showConfirmDialog(null, caption + "した行を保存しますか？", "保存", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.NO_OPTION) {
					return;
				}
				
				// Add Rows
				if (0 < addedIndexes.length) {
					List<NengaHistory> addedHistories = getHistories(addedIndexes);
					Object[] addedObjects = addedHistories.toArray();
					PWAddAction addAction = (PWAddAction)PWAction.getAction("nenga", "addhistories");
					addAction.run(addedObjects);
					for (int i = 0; i < addedIndexes.length; i++) {
						receiversTable.setRowState(addedIndexes[i], PWTableViewRowState.None);
					}
				}
				
				// Update Rows
				if (0 < modifiedIndexes.length) {
					List<NengaHistory> modifiedHistories = getHistories(modifiedIndexes);
					Object[] modifiedObjects = modifiedHistories.toArray();
					PWUpdateAction updateAction = (PWUpdateAction)PWAction.getAction("nenga", "updatehistories");
					updateAction.run(modifiedObjects);
					for (int i = 0; i < modifiedIndexes.length; i++) {
						receiversTable.setRowState(modifiedIndexes[i], PWTableViewRowState.None);
					}
				}
			}
		});
		return button;
	}
	
	/**
	 * @return
	 */
	private JButton createPdfButton() {
		JButton button = new JButton("PDF");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexes = receiversTable.getSelectedRowIndexes();
				if (indexes.length == 0) {
					JOptionPane.showMessageDialog(null, "宛先を選択してください。", "PDF", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				NengaAtenaWriter writer = new NengaAtenaWriter();
				writer.setSenderAddress(senderZipcode, senderAddress, senderFamilyName, senderFirstNames);
				for (int i = 0; i < indexes.length; i++) {
					String zipcode = (String)receiversTable.getValueAt(indexes[i], "ZIPCODE");
					String address = (String)receiversTable.getValueAt(indexes[i], "ADDRESS");
					String familyName = (String)receiversTable.getValueAt(indexes[i], "FAMILYNAME");
					String firstNames = (String)receiversTable.getValueAt(indexes[i], "FIRSTNAMES");
					String honorific = (String)receiversTable.getValueAt(indexes[i], "HONORIFIC");
					writer.addReceiverAddress(zipcode, address, honorific, familyName, firstNames.split("/"));
				}
				
				File tmpPdfFile;
				try {
					tmpPdfFile = File.createTempFile("atena", ".pdf");
				} catch (IOException ex) {
					throw new PWError(ex, "Failed to create temporary PDF file.");
				}
				writer.save(tmpPdfFile);
				
				PWCommandAction action = (PWCommandAction)PWAction.getAction("nenga", "pdf");
				action.run(tmpPdfFile.getAbsolutePath());
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
			history.setSentDate((Date)receiversTable.getValueAt(indexes[i], "SENTDATE"));
			history.setReceivedDate((Date)receiversTable.getValueAt(indexes[i], "RECEIVEDDATE"));
			histories.add(history);
		}
		return histories;
	}
}
