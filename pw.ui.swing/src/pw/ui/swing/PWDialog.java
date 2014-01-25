/*
 *  $Id: PWDialog.java 2013/12/29 17:48:09 masamitsu $
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

package pw.ui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author masamitsu
 *
 */
public class PWDialog<TComponent extends Component> extends JDialog{
	protected JButton okButton;
	protected JButton cancelButton;
	protected TComponent component;
	
	class CloseActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setVisible(false);
					dispose();
				}
			});
		}
	}
	protected CloseActionListener closeActionListener;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6869670317563198032L;

	public PWDialog(TComponent component) {
		this.component = component;
		
		Dimension defaultSize = new Dimension(400, 300);
		setMinimumSize(defaultSize);
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout(0, 0));
		add(createButtonsPanel(), BorderLayout.SOUTH);
		add(component, BorderLayout.CENTER);
	}

	/**
	 * @return
	 */
	private Component createButtonsPanel() {
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		closeActionListener = new CloseActionListener();
		cancelButton.addActionListener(closeActionListener);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		return buttonsPanel;
	}
	
	public TComponent getComponent() {
		return component;
	}
	
	public void setOkAction(final ActionListener newListener) {
		// Delete All Action Listeners
		for (ActionListener listener : okButton.getActionListeners()) {
			okButton.removeActionListener(listener);
		}
		
		// I wanted to call CloseActionListener after newListener.
		// But Java Specification does not guarantee the calling order of two or more added ActionListeners.
		// So, I should add one ActionListener like below.
		
		// Add New ActionListener
		okButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				newListener.actionPerformed(e);
				closeActionListener.actionPerformed(e);
			}
		});
	}
}
