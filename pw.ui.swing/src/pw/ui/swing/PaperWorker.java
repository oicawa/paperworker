/*
 *  $Id: PaperWorker.java 2013/11/09 8:17:42 masamitsu $
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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pw.core.PWSession;
import pw.core.PWUtilities;
import pw.ui.swing.menu.PWMenuPane;
import pw.ui.swing.portal.PWPortalPane;
import pw.ui.swing.preference.PWSettings;
import pw.ui.swing.preference.PWPreferenceComponentAdapter;
import pw.ui.swing.preference.PWPreferenceWindowAdapter;

/**
 * @author masamitsu
 *
 */
public class PaperWorker extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InnerPreference pref = new InnerPreference();
	
	/**
	 * @param args
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("UnicodeWriter.writeUtf8BOM", "false");
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PaperWorker paperworker = new PaperWorker();
					paperworker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			}
		});

	}
	
	public PaperWorker() {
		// TODO: Implement Login System
		PWSession.setUserId("TODO");
		
		// Add Listeners
		this.addWindowListener(new PWPreferenceWindowAdapter());
		this.addComponentListener(new PWPreferenceComponentAdapter());
		
		// Set frame size and position
		if (pref.isEmpty()) {
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			setSize((int)(screen.getWidth() * 0.8), (int)(screen.getHeight() * 0.6));
			setLocationRelativeTo(null);
		} else {
			setSize(pref.getSize());
			setLocation(pref.getPosition());
		}
		
		// Create Default Pane
		PWMenuPane menu = new PWMenuPane();
		PWPortalPane portal = new PWPortalPane();
		Class<?> nengaType = PWUtilities.getClass("nenga.ui.swing.ConsolePanel");
		JPanel nenga = (JPanel)PWUtilities.createInstance(nengaType);
		
		// Tab pane
		PWTabbedPane tabPane = new PWTabbedPane();
		tabPane.addTab(menu.getName(), new JScrollPane(menu));
		tabPane.addTab(portal.getName(), new JScrollPane(portal));
		tabPane.addTab("年賀状", nenga);
		getContentPane().add(tabPane, BorderLayout.CENTER);
		
		// Other
		setTitle("PaperWorker");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void saveLocation() {
		pref.setLocation(getLocation());
		pref.setSize(getSize());
		pref.save();
	}
	
	private class InnerPreference extends PWSettings {
		
		private static final String LOCATION_X = "PaperWorker.Location.X";
		private static final String LOCATION_Y = "PaperWorker.Location.Y";
		private static final String SIZE_WIDTH = "PaperWorker.Size.Width";
		private static final String SIZE_HEIGHT = "PaperWorker.Size.Height";
		
		private Point location;
		private Dimension size;
		
		/**
		 * @param type
		 */
		private InnerPreference() {
			super(PaperWorker.class);
			int x = prefs.getInt(LOCATION_X, -1);
			int y = prefs.getInt(LOCATION_Y, -1);
			int width = prefs.getInt(SIZE_WIDTH, -1);
			int height = prefs.getInt(SIZE_HEIGHT, -1);
			location = new Point(x, y);
			size = new Dimension(width, height);
		}
		
		public void save() {
			prefs.putInt(LOCATION_X, location.x);
			prefs.putInt(LOCATION_Y, location.y);
			prefs.putInt(SIZE_WIDTH, size.width);
			prefs.putInt(SIZE_HEIGHT, size.height);
			super.save();
		}
		
		public Point getPosition() {
			return location;
		}
		
		public void setLocation(Point loaction) {
			this.location = loaction;
		}
		
		public Dimension getSize() {
			return size;
		}
		
		public void setSize(Dimension size) {
			this.size = size;
		}

		/**
		 * @return
		 */
		public boolean isEmpty() {
			return location.x <= 0 && location.y <= 0 && size.width <= 0 && size.height <= 0;
		}
	}
}
