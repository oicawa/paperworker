/*
 *  $Id: PWMenuTabSetting.java 2013/11/16 19:44:12 masamitsu $
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

package pw.ui.swing.menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//import javax.swing.DefaultListModel;
import javax.swing.JLabel;
//import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pw.ui.swing.PWJobSettingPanel;
import pw.ui.swing.PWLayout;
//import pw.ui.swing.utilities.ListTransferHandler;

/**
 * @author masamitsu
 *
 */
public class PWMenuSettingPane extends PWJobSettingPanel<PWMenuPreferences> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2955656042074672637L;

	/**
	 * 
	 */
	public PWMenuSettingPane() {
		super(new PWMenuPreferences());
		
		JLabel columnCountCaption = new JLabel("Column Count");
		JSpinner columnCountSpinner = createColumnCountSpinner();
		JLabel rowCountCaption = new JLabel("Row Count");
		JSpinner rowCountSpinner = createRowCountSpinner();
		JLabel buttonListCaption = new JLabel("Menus");
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.anchor = GridBagConstraints.NORTH;

		// Column Count
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		add(columnCountCaption, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		add(getColonLabel(), gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		add(columnCountSpinner, gbc);
		
		// Row Count
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		add(rowCountCaption, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		add(getColonLabel(), gbc);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		add(rowCountSpinner, gbc);
		
		// Menu List
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		add(buttonListCaption, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		add(getColonLabel(), gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		//add(getMenuList(), gbc);
		
	}

	/**
	 * @return
	 */
	private JSpinner createColumnCountSpinner() {
		JSpinner spinner = new JSpinner();
		PWLayout layout = PWMenuSettingPane.this.preferences.getLayout();
		spinner.setValue(layout.getColumnLength());
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				final JSpinner spinner = (JSpinner)event.getSource();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						PWLayout layout = PWMenuSettingPane.this.preferences.getLayout();
						layout.setColumnLength((Integer)spinner.getValue());
					}
				});
			}
		});
		return spinner;
	}

	/**
	 * @return
	 */
	private JSpinner createRowCountSpinner() {
		JSpinner spinner = new JSpinner();
		PWLayout layout = PWMenuSettingPane.this.preferences.getLayout();
		spinner.setValue(layout.getRowLength());
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				final JSpinner spinner = (JSpinner)event.getSource();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						PWLayout layout = PWMenuSettingPane.this.preferences.getLayout();
						layout.setRowLength((Integer)spinner.getValue());
					}
				});
			}
		});
		return spinner;
	}
	
	@SuppressWarnings("unused")
	private JScrollPane getMenuList() {
		JLabel address = new JLabel("Address");
		address.setTransferHandler(new TransferHandler("text"));
		address.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent event) {
		    	JLabel label = (JLabel)event.getSource();
		        TransferHandler handler = label.getTransferHandler();
		        handler.exportAsDrag(label, event, TransferHandler.COPY);
		    }
		});
		
		Menu menuManager = new Menu();
		menuManager.setUuid(null);
		menuManager.setName("メニュー管理");
		menuManager.setDescription("実行可能なメニューの登録、変更、削除を行います。");
		menuManager.setClassPath("pw.ui.swing.menu.Menu");

//		DefaultListModel model = new DefaultListModel();
//		model.addElement("Menu Management");
		
//		JList buttonList = new JList(model);
//		buttonList.setDragEnabled(true);
//		buttonList.setTransferHandler(new ListTransferHandler());
//		return new JScrollPane(buttonList);
		return null;
	}
	
	private JLabel getColonLabel() {
		JLabel colon = new JLabel(":");
		colon.setPreferredSize(new Dimension(10, 10));
		return colon;
	}
}
