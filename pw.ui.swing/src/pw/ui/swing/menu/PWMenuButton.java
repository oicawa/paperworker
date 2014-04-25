/*
 *  $Id: PWMenuButton.java 2013/11/13 19:36:59 masamitsu $
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
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author masamitsu
 *
 */
public class PWMenuButton extends JPanel {
	
	private JButton button;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8113038589155117996L;
	
	public PWMenuButton() {
		button = new JButton();
		setLayout(new BorderLayout());
		add(button, BorderLayout.CENTER);
	}
	
	public void setTitle(String title) {
		button.setText(title);
	}
	
	public void setAction() {
		button.addActionListener(new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -9114080212524772042L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog((Component) arg0.getSource(), (Object)"Do Action!");
				
			}
		});
	}
}
