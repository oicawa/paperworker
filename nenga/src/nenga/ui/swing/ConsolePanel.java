/*
 *  $Id: ConsolePanel.java 2013/12/14 19:40:44 masamitsu $
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

package nenga.ui.swing;

import java.awt.GridBagLayout;

import pw.core.PWAction;
import pw.core.table.PWTable;
import pw.ui.swing.PWJobPane;
import pw.ui.swing.table.PWTableViewPanel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.UUID;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import nenga.ui.swing.parts.ReceiversPartPanel;
import nenga.ui.swing.parts.SenderPartPanel;
import nenga.ui.swing.parts.YearPartPanel;

/**
 * @author masamitsu
 *
 */
public class ConsolePanel extends PWJobPane<SettingPanel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConsolePanel() {
		super(new SettingPanel());
		setForeground(Color.LIGHT_GRAY);

		// Year
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		if (11 <= month)
			year += 1;
		
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[]{448, 0};
		gridBagLayout_1.rowHeights = new int[]{71, 101, 234, 0};
		gridBagLayout_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout_1);
		YearPartPanel yearPartPanel = new YearPartPanel();
		GridBagLayout gridBagLayout = (GridBagLayout) yearPartPanel.getLayout();
		gridBagLayout.rowHeights = new int[] {25};
		GridBagConstraints gbc_yearPartPanel = new GridBagConstraints();
		gbc_yearPartPanel.weightx = 1.0;
		gbc_yearPartPanel.fill = GridBagConstraints.BOTH;
		gbc_yearPartPanel.insets = new Insets(0, 0, 5, 0);
		gbc_yearPartPanel.gridx = 0;
		gbc_yearPartPanel.gridy = 0;
		add(yearPartPanel, gbc_yearPartPanel);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		final SenderPartPanel senderPartPanel = new SenderPartPanel();
		add(senderPartPanel, gbc);
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.weightx = 1.0;
		gbc_1.weighty = 1.0;
		gbc_1.fill = GridBagConstraints.BOTH;
		gbc_1.gridx = 0;
		gbc_1.gridy = 2;
		final ReceiversPartPanel receiversPartPanel = new ReceiversPartPanel();
		add(receiversPartPanel, gbc_1);
		
		senderPartPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Get Data
				PWAction action = PWAction.getAction("address", "list");
				PWTable table = (PWTable)action.run();
				
				// Table View
				final PWTableViewPanel tableView = senderPartPanel.createAddressTableView();
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
				
				// Check Selected Count
				int[] modelIndexes = tableView.getSelectedRowIndexes();
				if (modelIndexes.length == 0) {
					return;
				}
				
				// Set Selected Data to GUI
				int modelIndex = modelIndexes[0];
				UUID addressId = (UUID)tableView.getValueAt(modelIndex, "UUID");
				String zipcode = tableView.getValueAt(modelIndex, "ZIPCODE").toString();
				String address = tableView.getValueAt(modelIndex, "ADDRESS").toString();
				String familyName = tableView.getValueAt(modelIndex, "FAMILYNAME").toString();
				String firstNames = tableView.getValueAt(modelIndex, "FIRSTNAMES").toString();
				
				String name = familyName + " " + firstNames;
				senderPartPanel.setSenderName(name);
				senderPartPanel.setSenderAddress(String.format("〒%s %s", zipcode, address));
				senderPartPanel.setSenderAddressId(addressId);
				
				receiversPartPanel.setSenderAddressId(addressId, zipcode, address, familyName, firstNames.split("/"));
			}
		});
		
		yearPartPanel.setYear(year);
		yearPartPanel.setItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JComboBox<?> combobox = (JComboBox<?>)e.getSource();
				receiversPartPanel.setYear((Integer)combobox.getSelectedItem());
			}
		});
		
		receiversPartPanel.setYear(year);
	}

	/* (non-Javadoc)
	 * @see pw.ui.swing.PWJobPane#getName()
	 */
	@Override
	public String getName() {
		return "Nenga";
	}

	/* (non-Javadoc)
	 * @see pw.ui.swing.PWJobPane#update()
	 */
	@Override
	public void update() {
		
		
	}
}
