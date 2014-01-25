/*
 *  $Id: PWPreferenceDialog.java 2013/11/17 12:03:42 masamitsu $
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

package pw.ui.swing.preference;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pw.ui.swing.PWJobSettingPanel;

/**
 * @author masamitsu
 *
 */
public class PWPreferencesDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8909778782062290652L;
	
	private PWJobSettingPanel<? extends PWSettings> settingPane;
	
	private static final Dimension BUTTON_SIZE = new Dimension(100, 30);
	
	public PWPreferencesDialog(PWJobSettingPanel<? extends PWSettings> settingPane) {
		this.settingPane = settingPane;
		
		setModal(true);
		setLayout(new BorderLayout());
		add(this.settingPane, BorderLayout.CENTER);
		setMinimumSize(new Dimension(400, 300));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JButton cancel = createCancelButton();
		JButton save = createSaveButton();
		JPanel buttons = createButtonsPanel(cancel, save);
		add(buttons, BorderLayout.SOUTH);
	}

	/**
	 * @param cancel
	 * @param save
	 * @return
	 */
	private JPanel createButtonsPanel(JButton cancel, JButton save) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Filler
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		panel.add(new JLabel(""), gbc);

		// Cancel Button
		gbc.gridx = 1;
		gbc.weightx = 0.0;
		panel.add(cancel, gbc);
		
		// Save Button
		gbc.gridx = 2;
		gbc.weightx = 0.0;
		panel.add(save, gbc);
		
		return panel;
	}

	/**
	 * @return
	 */
	private JButton createCancelButton() {
		JButton button = new JButton("Cancel");
		button.setPreferredSize(BUTTON_SIZE);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						PWPreferencesDialog.this.setVisible(false);
						PWPreferencesDialog.this.dispose();
					}
				});
			}
		});
		return button;
	}

	/**
	 * @return
	 */
	private JButton createSaveButton() {
		JButton button = new JButton("Save");
		button.setPreferredSize(BUTTON_SIZE);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						PWPreferencesDialog.this.settingPane.getPreferences().save();
						PWPreferencesDialog.this.setVisible(false);
						PWPreferencesDialog.this.dispose();
					}
				});
			}
		});
		return button;
	}

}
