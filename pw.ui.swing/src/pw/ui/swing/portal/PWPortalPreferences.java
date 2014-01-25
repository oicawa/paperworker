/*
 *  $Id: InnerPreference.java 2013/11/17 9:05:00 masamitsu $
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

import pw.ui.swing.PWLayout;
import pw.ui.swing.preference.PWSettings;

class PWPortalPreferences extends PWSettings {
	
	private static final String LAYOUT = "PaperWorker.Portal.Layout";
	
	private PWLayout layout;
	
	/**
	 * @param pwPortalTab TODO
	 * @param type
	 */
	public PWPortalPreferences() {
		super(PWPortalPane.class);
		layout = (PWLayout)getObject(LAYOUT);
		if (layout == null) {
			layout = new PWLayout();
		}
	}
	
	public void save() {
		setObject(LAYOUT, layout);
		super.save();
	}
	
	public PWLayout getLayout() {
		return layout;
	}
	
	public void setLayout(PWLayout layout) {
		this.layout = layout;
	}
}