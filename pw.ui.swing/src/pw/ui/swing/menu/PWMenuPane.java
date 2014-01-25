/*
 *  $Id: PWMenu.java 2013/11/10 15:40:26 masamitsu $
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;

import pw.ui.swing.PWJobPane;
import pw.ui.swing.PWLayout;
import pw.ui.swing.PWLayoutUnit;

/**
 * @author masamitsu
 *
 */
public class PWMenuPane extends PWJobPane<PWMenuSettingPane> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8508897818695293713L;

	private HashMap<Point, JPanel> panels = new HashMap<Point, JPanel>();
	private HashMap<Point, PWMenuButton> buttons = new HashMap<Point, PWMenuButton>();
	
	public PWMenuPane() {
		super(new PWMenuSettingPane());
		update();
	}
	
	public String getName() {
		return "Menu";
	}
	
	@Override
	public void update() {
		panels.clear();
		buttons.clear();
		removeAll();
		
		PWMenuPreferences prefs = settingPane.getPreferences();
		PWLayout layout = prefs.getLayout();
		PWLayoutUnit[][] table = layout.getTable();
		int columnLength = layout.getColumnLength();
		int rowLength = layout.getRowLength();
		final int INSET = 5;
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(INSET, INSET, INSET, INSET);
		
		// Set layout panel
		GridBagLayout gridLayout = new GridBagLayout();
		setLayout(gridLayout);
		for (int columnIndex = 0; columnIndex < columnLength; columnIndex++) {
			for (int rowIndex = 0; rowIndex < rowLength; rowIndex++) {
				PWLayoutUnit unit = table[columnIndex][rowIndex];
				gbc.gridx = columnIndex;
				gbc.gridy = rowIndex;
				gbc.gridwidth = unit.getColumnSpan();
				gbc.gridheight = unit.getRowSpan();
				JPanel panel = getPanel();
				panels.put(new Point(columnIndex, rowIndex), panel);
				gridLayout.setConstraints(panel, gbc);
				add(panel);
			}
		}

		// Set buttons
		for (Point key : buttons.keySet()) {
			PWMenuButtonSetting setting = prefs.getButtonSetting(key);
			if (setting == null) {
				continue;
			}
			JPanel panel = panels.get(key);
			if (!buttons.containsKey(key)) {
				buttons.put(key, createButton(setting));
			}
			PWMenuButton button = buttons.get(key);
			panel.add(button);
		}
		
		
		// Filler
		gbc.gridx = columnLength;
		gbc.gridy = rowLength;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		JLabel filler = new JLabel("");
		gridLayout.setConstraints(filler, gbc);
		add(filler);
	}
	
	private PWMenuButton createButton(PWMenuButtonSetting setting) {
		PWMenuButton button = new PWMenuButton();
		button.setTitle(setting.getTitle());
		Dimension size = new Dimension(100, 100);
		button.setPreferredSize(size);
		return button;
	}
	
	private JPanel getPanel() {
		//DropTargetAdapter	
		JPanel panel = new JPanel();
		panel.setTransferHandler(new TransferHandler("MenuButton"));
		panel.setLayout(new BorderLayout());
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setPreferredSize(new Dimension(100, 100));
		panel.setTransferHandler(new PWMenuButtonTransferHandler());
		return panel;
	}
}
