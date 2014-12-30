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

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JButton;

import pw.ui.swing.basic.PWSelectionMode;
import pw.ui.swing.table.PWTableViewPanel;
import pw.ui.swing.table.PWTableViewSearchPanel;

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
	protected JButton selectButton;
	protected UUID senderAddressId;
	
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
		
		selectButton = new JButton("選択");
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
	
	public String getSenderName() {
		return nameLabel.getText();
	}
	
	public void setSenderName(String name) {
		nameLabel.setText(name);
	}
	
	public String getSenderAddress() {
		return addressLabel.getText();
	}
	
	public void setSenderAddress(String address) {
		addressLabel.setText(address);
	}
	
	public UUID getSelectedSenderAddressId() {
		return senderAddressId;
	}
	
	public void setSenderAddressId(UUID senderAddressId) {
		this.senderAddressId = senderAddressId;
	}
	
	public PWTableViewPanel createAddressTableView() {
		PWTableViewPanel tableView = new PWTableViewPanel();
		tableView.setSelectionMode(PWSelectionMode.Single);
		tableView.addColumn("UUID", "UUID");
		tableView.addColumn("ZIPCODE", "郵便番号");
		tableView.addColumn("ADDRESS", "住所");
		tableView.addColumn("FAMILYNAME", "姓");
		tableView.addColumn("FIRSTNAMES", "名");
		tableView.setColumnVisible("UUID", false);
		tableView.setTotalCountCaption("%d件");
		tableView.setSelectedCountCaption("選択%d件");
		tableView.add(new PWTableViewSearchPanel(tableView), BorderLayout.NORTH);
		return tableView;
	}
	
	public void addActionListener(ActionListener listener) {
		selectButton.addActionListener(listener);
	}
}
