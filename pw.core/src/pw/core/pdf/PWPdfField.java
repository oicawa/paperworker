/*
 *  $Id: PWPdfField.java 2014/02/26 23:08:49 masamitsu $
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

package pw.core.pdf;

import pw.core.pdf.PWPdfFile.Encoding;
import pw.core.pdf.PWPdfFile.Font;

/**
 * @author  masamitsu
 */
public class PWPdfField {
	/**
	 * 
	 */
	public float x;
	/**
	 * 
	 */
	public float y;
	/**
	 * 
	 */
	public String text;
	/**
	 * 
	 */
	public float fontSize;
	/**
	 * 
	 */
	public Font font;
	/**
	 * 
	 */
	public Encoding encoding;
	/**
	 * 
	 */
	public boolean isEmbedded;

	/**
	 * 
	 */
	public PWPdfField(float x, float y, String text, float fontSize, Font font, Encoding encoding, boolean isEmbedded) {
		this.x = x;
		this.y = y;
		if (text != null) {
			String tmpText = text;
			if (encoding == Encoding.UNIJIS_UCS2_V || encoding == Encoding.UNIJIS_UCS2_HW_V) {
				final char[] SRC = { '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
				final char[] DST = { 'ー', '０', '１', '２', '３', '４', '５', '６', '７', '８', '９' };
				for (int i = 0; i < SRC.length; i++) {
					tmpText = tmpText.replace(SRC[i], DST[i]);
				}
			}
			this.text = tmpText;
		}
		this.fontSize = fontSize;
		this.font = font;
		this.encoding = encoding;
		this.isEmbedded = isEmbedded;
	}
}