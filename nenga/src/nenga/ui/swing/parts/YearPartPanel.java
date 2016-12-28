/*
 *  $Id: YearPartPanel.java 2013/12/15 22:42:08 masamitsu $
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

/**
 * @author masamitsu
 *
 */
public class YearPartPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4697002083764833898L;
	private JPanel yearPanel;
	private JComboBox<Integer> yearComboBox;
	private JLabel yearLabel;
	private JLabel yearCaptionLabel;
	private JLabel fillerLabel;
	private JButton yearChangeButton;
	private int year;
	private ItemListener defaultListener;
	private ItemListener addedListener;
	public YearPartPanel() {
		setBorder(new TitledBorder(null, "対象年", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{32, 12, 55, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0};
		setLayout(gridBagLayout);
		
		yearLabel = new JLabel();
		yearLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		defaultListener = new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				@SuppressWarnings("unchecked")
				final JComboBox<Integer> combobox = (JComboBox<Integer>)arg0.getSource();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						year = (Integer)combobox.getSelectedItem();
						updateComboBox();
						yearComboBox.setVisible(false);
						yearLabel.setVisible(true);
						yearLabel.setText(String.format("%d", year));
						update();
					}
				});
			}
		};
		
		yearComboBox = new JComboBox<Integer>();
		
		yearPanel = new JPanel();
		yearPanel.setLayout(new FlowLayout());
		yearPanel.add(yearLabel);
		yearPanel.add(yearComboBox);
		GridBagConstraints gbc_yearPanel = new GridBagConstraints();
		gbc_yearPanel.anchor = GridBagConstraints.WEST;
		gbc_yearPanel.insets = new Insets(0, 0, 5, 5);
		gbc_yearPanel.gridx = 0;
		gbc_yearPanel.gridy = 0;
		add(yearPanel, gbc_yearPanel);
		
		yearCaptionLabel = new JLabel("年");
		GridBagConstraints gbc_yearCaptionLabel = new GridBagConstraints();
		gbc_yearCaptionLabel.anchor = GridBagConstraints.WEST;
		gbc_yearCaptionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_yearCaptionLabel.gridx = 1;
		gbc_yearCaptionLabel.gridy = 0;
		add(yearCaptionLabel, gbc_yearCaptionLabel);
		
		fillerLabel = new JLabel("");
		GridBagConstraints gbc_fillerLabel = new GridBagConstraints();
		gbc_fillerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fillerLabel.anchor = GridBagConstraints.WEST;
		gbc_fillerLabel.gridx = 2;
		gbc_fillerLabel.gridy = 0;
		add(fillerLabel, gbc_fillerLabel);
		
		yearChangeButton = new JButton("変更");
		yearChangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						yearComboBox.setVisible(true);
						yearLabel.setVisible(false);
						update();
					}
				});
			}
		});
		GridBagConstraints gbc_yearChangeButton = new GridBagConstraints();
		gbc_yearChangeButton.insets = new Insets(0, 0, 5, 0);
		gbc_yearChangeButton.gridx = 3;
		gbc_yearChangeButton.gridy = 0;
		add(yearChangeButton, gbc_yearChangeButton);
		
		initialize();
	}
	
	private void initialize() {
		yearLabel.setVisible(true);
		yearComboBox.setVisible(false);
	}
	
	public void setYear(int year) {
		this.year = year;
		yearLabel.setText(String.format("%d", year));
		updateComboBox();
	}
	
	/**
	 * 
	 */
	private void updateComboBox() {
		for (ItemListener listener : yearComboBox.getItemListeners()) {
			yearComboBox.removeItemListener(listener);
		}
		
		final int span = 5;
		int start = year - (span / 2);
		yearComboBox.removeAllItems();
		for (int i = start; i < start + span; i++) {
			yearComboBox.addItem(i);
		}
		yearComboBox.setSelectedItem(year);
		
		yearComboBox.addItemListener(defaultListener);
		yearComboBox.addItemListener(addedListener);
	}

	private void update() {
		yearLabel.setText(String.valueOf(year));
	}
	
	public void setItemListener(ItemListener listener) {
		addedListener = listener;
		yearComboBox.addItemListener(addedListener);
	}
}
