/*
 *  $Id: InnerPreference.java 2013/11/17 9:55:52 masamitsu $
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

import java.awt.Point;
import java.util.HashMap;
import java.util.prefs.BackingStoreException;

import pw.core.PWError;
import pw.ui.swing.PWLayout;
import pw.ui.swing.preference.PWSettings;

public class PWMenuPreferences extends PWSettings {
	
	private static final String LAYOUT = "PaperWorker.Menu.Layout";
	private static final String BUTTONS = "PaperWorker.Menu.Buttons";
	
	private PWLayout layout;
	private HashMap<Point, PWMenuButtonSetting> buttons;
	
	/**
	 * @param type
	 */
	@SuppressWarnings("unchecked")
	public PWMenuPreferences() {
		super(PWMenuPane.class);
		layout = (PWLayout)getObject(LAYOUT);
		if (layout == null) {
			layout = new PWLayout();
		}
		buttons = (HashMap<Point, PWMenuButtonSetting>)getObject(BUTTONS);
		if (buttons == null) {
			buttons = new HashMap<Point, PWMenuButtonSetting>();
		}
	}
	
	public void save() {
		setObject(LAYOUT, layout);
		setObject(BUTTONS, buttons);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			throw new PWError(e, e.getMessage());
		}
	}
	
	public PWMenuButtonSetting getButtonSetting(Point key) {
		if (!buttons.containsKey(key))
			return null;
		return buttons.get(key);
	}
	
	public PWLayout getLayout() {
		return layout;
	}
	
	public void setLayout(PWLayout layout) {
		this.layout = layout;
	}

	/**
	 * @return the buttons
	 */
	public HashMap<Point, PWMenuButtonSetting> getButtons() {
		return buttons;
	}

	/**
	 * @param buttons the buttons to set
	 */
	public void setButtons(HashMap<Point, PWMenuButtonSetting> buttons) {
		this.buttons = buttons;
	}
}