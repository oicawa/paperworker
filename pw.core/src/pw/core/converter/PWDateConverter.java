/*
 *  $Id: PWDateConverter.java 2013/10/26 21:03:26 masamitsu $
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

package pw.core.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pw.core.PWConverter;
import pw.core.PWError;

/**
 * @author masamitsu
 *
 */
public class PWDateConverter implements PWConverter {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	/* (non-Javadoc)
	 * @see pw.core.view.PWConverter#convert(java.lang.String)
	 */
	@Override
	public Object toObject(String value) {
		try {
			return format.parse(value);
		} catch (ParseException e) {
			throw new PWError(e, "Invalid format for Date. [%s]", value);
		}
	}

	/* (non-Javadoc)
	 * @see pw.core.PWConverter#toString(java.lang.Object)
	 */
	@Override
	public String toString(Object object) {
		return format.format((Date)object);
	}

}
