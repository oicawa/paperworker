/*
 *  $Id: SenderPartPanel.java 2013/12/22 22:25:48 masamitsu $
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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import pw.core.PWAction;
import pw.core.table.PWTable;
import pw.core.table.PWTableRow;
import pw.ui.swing.basic.PWSelectionMode;
import pw.ui.swing.basic.PWTableViewPanel;
import pw.ui.swing.basic.PWTableViewSearchPanel;

/**
 * @author masamitsu
 *
 */
public class SenderPartPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -828032176641938175L;
	
	protected JLabel nameLabel;
	protected JLabel addressLabel;
	protected PWTableRow selectedRow;
	
	public SenderPartPanel() {
		setBorder(new TitledBorder(null, "差出人情報", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel nameCaptionLabel = new JLabel("氏名等");
		GridBagConstraints gbc_nameCaptionLabel = new GridBagConstraints();
		gbc_nameCaptionLabel.anchor = GridBagConstraints.WEST;
		gbc_nameCaptionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameCaptionLabel.gridx = 0;
		gbc_nameCaptionLabel.gridy = 0;
		add(nameCaptionLabel, gbc_nameCaptionLabel);
		
		JLabel nameSeparatorLabel = new JLabel(":");
		GridBagConstraints gbc_nameSeparatorLabel = new GridBagConstraints();
		gbc_nameSeparatorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameSeparatorLabel.gridx = 1;
		gbc_nameSeparatorLabel.gridy = 0;
		add(nameSeparatorLabel, gbc_nameSeparatorLabel);
		
		nameLabel = new JLabel(" ");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.weightx = 1.0;
		gbc_nameLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameLabel.gridwidth = 3;
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 2;
		gbc_nameLabel.gridy = 0;
		add(nameLabel, gbc_nameLabel);
		
		JLabel fillerLabel = new JLabel("　　　　　");
		GridBagConstraints gbc_fillerLabel = new GridBagConstraints();
		gbc_fillerLabel.weightx = 1.0;
		gbc_fillerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fillerLabel.gridx = 4;
		gbc_fillerLabel.gridy = 0;
		add(fillerLabel, gbc_fillerLabel);
		
		JButton selectButton = createSelectButton();
		GridBagConstraints gbc_selectButton = new GridBagConstraints();
		gbc_selectButton.insets = new Insets(0, 0, 5, 0);
		gbc_selectButton.gridx = 5;
		gbc_selectButton.gridy = 0;
		add(selectButton, gbc_selectButton);
		
		JLabel addressCaptionLabel = new JLabel("住所");
		GridBagConstraints gbc_addressCaptionLabel = new GridBagConstraints();
		gbc_addressCaptionLabel.anchor = GridBagConstraints.WEST;
		gbc_addressCaptionLabel.insets = new Insets(0, 0, 0, 5);
		gbc_addressCaptionLabel.gridx = 0;
		gbc_addressCaptionLabel.gridy = 1;
		add(addressCaptionLabel, gbc_addressCaptionLabel);
		
		JLabel addressSeparatorLabel = new JLabel(":");
		GridBagConstraints gbc_addressSeparatorLabel = new GridBagConstraints();
		gbc_addressSeparatorLabel.insets = new Insets(0, 0, 0, 5);
		gbc_addressSeparatorLabel.gridx = 1;
		gbc_addressSeparatorLabel.gridy = 1;
		add(addressSeparatorLabel, gbc_addressSeparatorLabel);
		
		addressLabel = new JLabel(" ");
		GridBagConstraints gbc_addressLabel = new GridBagConstraints();
		gbc_addressLabel.weightx = 1.0;
		gbc_addressLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressLabel.anchor = GridBagConstraints.WEST;
		gbc_addressLabel.gridwidth = 4;
		gbc_addressLabel.gridx = 2;
		gbc_addressLabel.gridy = 1;
		add(addressLabel, gbc_addressLabel);
	}
	
	public PWTableRow getSelectedData() {
		return selectedRow;
	}
	
	private JButton createSelectButton() {
		
		JButton button = new JButton("選択");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Get Data
				PWAction action = PWAction.getAction("address", "list");
				PWTable table = (PWTable)action.run();
				
				// Table View
				final PWTableViewPanel tableView = new PWTableViewPanel();
				tableView.setSelectionMode(PWSelectionMode.Single);
				tableView.addColumnHeader("ZIPCODE", "郵便番号");
				tableView.addColumnHeader("ADDRESS", "住所");
				tableView.addColumnHeader("FAMILYNAME", "姓");
				tableView.addColumnHeader("FIRSTNAMES", "名");
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
				
				// Check Selected Count
				List<PWTableRow> rows = tableView.getSelectedRows();
				if (rows.size() == 0) {
					return;
				}
				
				// Set Selected Data to GUI
				PWTableRow row = rows.get(0);
				String name = row.getValue("FAMILYNAME").toString() + " " + row.getValue("FIRSTNAMES").toString();
				nameLabel.setText(name);
				String address = "〒" + row.getValue("ZIPCODE").toString() + " " + row.getValue("ADDRESS").toString();
				addressLabel.setText(address);
				selectedRow = row;
			}
		});
		return button;
	}
}
