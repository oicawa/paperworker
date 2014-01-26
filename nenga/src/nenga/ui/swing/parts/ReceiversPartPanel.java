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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
public class ReceiversPartPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2319250559294999837L;
	private PWTableViewPanel receiversTable;
	public ReceiversPartPanel() {
		setBorder(new TitledBorder(null, "\u5B9B\u5148\u4E00\u89A7", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonsPanel = new JPanel();
		FlowLayout fl_buttonsPanel = (FlowLayout) buttonsPanel.getLayout();
		fl_buttonsPanel.setAlignment(FlowLayout.RIGHT);
		add(buttonsPanel, BorderLayout.NORTH);
		
		JButton addButton = createAddButton();
		buttonsPanel.add(addButton);
		
		JButton modifyButton = new JButton("変更");
		buttonsPanel.add(modifyButton);
		
		JButton deleteButton = new JButton("削除");
		buttonsPanel.add(deleteButton);
		
		JButton printButton = new JButton("印刷");
		buttonsPanel.add(printButton);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		receiversTable = new PWTableViewPanel();
		receiversTable.addColumnHeader("NAME", "宛先");
		receiversTable.addColumnHeader("HONORIFIC", "敬称");
		receiversTable.addColumnHeader("ZIPCODE", "郵便番号");
		receiversTable.addColumnHeader("ADDRESS", "住所");
		receiversTable.addColumnHeader("MOURNING", "喪中");
		receiversTable.addColumnHeader("SENTDATE", "送付日");
		receiversTable.addColumnHeader("RECEIVEDDATE", "受取日");
		
		scrollPane.setViewportView(receiversTable);
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
				
				// Set Selected Data to GUI
				List<PWTableRow> rows = tableView.getSelectedRows();
				for (int i = 0; i < rows.size(); i++) {
					PWTableRow row = rows.get(i);
					
					String name = row.getValue("FAMILYNAME").toString() + " " + row.getValue("FIRSTNAMES").toString();
					String honorific = "樣";
					String zipcode = row.getValue("ZIPCODE").toString();
					String address = row.getValue("ADDRESS").toString();
					String mourning = "";
					String sentDate = "";
					String receivedDate = "";
					
					receiversTable.addRow(new Object[] { name, honorific, zipcode, address, mourning, sentDate, receivedDate });
				}
			}
		});
		return button;
	}
}
