/*
 *  $Id: PWFieldTextEditor.java 2014/01/22 20:08:00 masamitsu $
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

package pw.ui.swing.basic;

import javax.swing.JTextField;

import pw.ui.swing.PWFieldPanel;

/**
 * @author masamitsu
 *
 */
public class PWFieldTextEditor extends PWFieldPanel<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1215287134800833221L;

	protected JTextField text;
	/**
	 * @param value
	 */
	public PWFieldTextEditor() {
		text = new JTextField();
		add(text);
	}
	
	/* (non-Javadoc)
	 * @see pw.ui.swing.PWFieldPanel#update()
	 */
	@Override
	public void setData(final String value) {
		text.setText(value);
	}

	/* (non-Javadoc)
	 * @see pw.ui.swing.PWFieldPanel#getData()
	 */
	@Override
	public String getData() {
		return text.getText();
	}
}
