/*
 *  $Id: PWPortal.java 2013/11/09 18:59:19 masamitsu $
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

package pw.ui.swing.portal;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import pw.ui.swing.PWJobPane;

/**
 * @author masamitsu
 *
 */
public class PWPortalPane extends PWJobPane<PWPortalSettingPane> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6355586298488184618L;
	
	public PWPortalPane() {
		super(new PWPortalSettingPane());
		update();
	}
	
	public String getName() {
		return "Portal";
	}
	
	public void update() {
		PWPortalPreferences prefs = settingPane.getPreferences();
		int columnLength = prefs.getLayout().getColumnLength();
		int rowLength = prefs.getLayout().getRowLength();
		final int INSET = 5;
		
		GridBagLayout gridLayout = new GridBagLayout();
		setLayout(gridLayout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0 / columnLength;
		gbc.weighty = 1.0 / rowLength;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(INSET, INSET, INSET, INSET);
		
		for (int columnIndex = 0; columnIndex < columnLength; columnIndex++) {
			for (int rowIndex = 0; rowIndex < rowLength; rowIndex++) {
				gbc.gridx = columnIndex;
				gbc.gridy = rowIndex;
				gbc.gridwidth = 1;
				gbc.gridheight = 1;
				JPanel panel = createGadget(String.format("%d, %d", columnIndex, rowIndex));
				gridLayout.setConstraints(panel, gbc);
				add(panel);
			}
		}
	}
	
	private JPanel createGadget(String title) {
		JPanel panel = new JPanel();
		panel.add(new JLabel(title));
		panel.setBorder(new LineBorder(Color.RED));
		return panel;
	}
}
