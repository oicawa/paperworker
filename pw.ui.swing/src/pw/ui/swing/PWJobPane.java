/*
 *  $Id: PWJobPane.java 2013/11/10 21:39:46 masamitsu $
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import pw.ui.swing.preference.PWPreferencesDialog;
import pw.ui.swing.preference.PWSettings;

/**
 * @author masamitsu
 *
 */
public abstract class PWJobPane<TSettingPane extends PWJobSettingPanel<? extends PWSettings>> extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8537665612143042807L;
	
	private JPopupMenu popup;
	protected TSettingPane settingPane;
	
	public PWJobPane(TSettingPane settingPane) {
		this.settingPane = settingPane;
		
		InitPopupMenu();
		
		// Add Listener
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				final MouseEvent mouseEvent = e;
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(SwingUtilities.isRightMouseButton(mouseEvent)){
							popup.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
						}
					}
				});
			}
		});
	}
	
	private void InitPopupMenu() {
		popup = new JPopupMenu();
		addPopupMenu("Settings", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						PWPreferencesDialog dialog = new PWPreferencesDialog(settingPane);
						dialog.setVisible(true);
						PWJobPane.this.update();
						PWJobPane.this.repaint();
					}
				});
			}
		});
	}
	
	protected void addPopupMenu(String name, ActionListener actionListener) {
	    JMenuItem item = new JMenuItem(name);
	    item.addActionListener(actionListener);
	    popup.add(item);
	}
	
	public abstract String getName();
	
	public abstract void update();

}
