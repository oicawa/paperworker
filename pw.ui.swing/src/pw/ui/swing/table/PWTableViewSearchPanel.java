/*
 *  $Id: PWTableViewSearchPanel.java 2014/01/18 14:08:51 masamitsu $
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

package pw.ui.swing.table;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * @author masamitsu
 *
 */
public class PWTableViewSearchPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2619341188859492874L;
	
	protected PWTableViewPanel viewPanel;
	protected JTextField searchTextField;
	
	class PWTableViewRowFillter extends RowFilter<TableModel, Integer> {
		protected Pattern pattern;
		
		PWTableViewRowFillter(Pattern pattern) {
			this.pattern = pattern;
		}
		
		@Override
		public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
			int count = entry.getValueCount();
			for (int i = 0; i < count; i++) {
				// Ignore the value of the column which is not visible
				if (!viewPanel.isColumnVisible(i)) {
					continue;
				}
				
				Object object = entry.getValue(i);
				String value = object == null ? "" : object.toString();
				Matcher matcher = pattern.matcher(value);
				if (matcher.find()) {
					return true;
				}
			}
			return false;
		}
		
	}
	
	class SeachDocumentListener implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent event) {
			search(event);
		}
		
		@Override
		public void insertUpdate(DocumentEvent event) {
			search(event);
		}

		@Override
		public void removeUpdate(DocumentEvent event) {
			search(event);
		}
		
		private void search(DocumentEvent event) {
			Document document = event.getDocument();
			String value;
			try {
				value = document.getText(0, document.getLength());
			} catch (BadLocationException e) {
				return;
			}
			
			Pattern pattern = Pattern.compile(value);
			PWTableViewRowFillter filter = new PWTableViewRowFillter(pattern);
			viewPanel.setFilter(filter);
		}
		
	}

	public PWTableViewSearchPanel(PWTableViewPanel viewPanel) {
		this.viewPanel = viewPanel;
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc_searchCaptionLabel = new GridBagConstraints();
		gbc_searchCaptionLabel.insets = new Insets(0, 0, 0, 5);
		gbc_searchCaptionLabel.gridx = 0;
		gbc_searchCaptionLabel.gridy = 0;
		gbc_searchCaptionLabel.weightx = 0.0;
		JLabel captionLabel = new JLabel("Search");
		add(captionLabel, gbc_searchCaptionLabel);
		
		GridBagConstraints gbc_searchSeparatorLabel = new GridBagConstraints();
		gbc_searchSeparatorLabel.insets = new Insets(0, 0, 0, 5);
		gbc_searchSeparatorLabel.anchor = GridBagConstraints.EAST;
		gbc_searchSeparatorLabel.gridx = 1;
		gbc_searchSeparatorLabel.gridy = 0;
		gbc_searchSeparatorLabel.weightx = 0.0;
		JLabel separatorLabel = new JLabel(":");
		add(separatorLabel, gbc_searchSeparatorLabel);
		
		GridBagConstraints gbc_searchTextField = new GridBagConstraints();
		gbc_searchTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchTextField.gridx = 2;
		gbc_searchTextField.gridy = 0;
		gbc_searchTextField.weightx = 1.0;
		searchTextField = new JTextField();
		searchTextField.getDocument().addDocumentListener(new SeachDocumentListener());
		searchTextField.setColumns(10);
		add(searchTextField, gbc_searchTextField);
		
		setBorder(new LineBorder(Color.GRAY));
	}
}
