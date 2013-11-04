/*
 *  $Id: PWClobConverter.java 2013/10/27 20:30:48 masamitsu $
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

import java.sql.Clob;
import java.sql.SQLException;

import pw.core.PWConverter;
import pw.core.PWError;

/**
 * @author masamitsu
 *
 */
public class PWClobConverter implements PWConverter {

	/* (non-Javadoc)
	 * @see pw.core.PWConverter#toObject(java.lang.String)
	 */
	@Override
	public Object toObject(String value) {
		return value;
	}

	/* (non-Javadoc)
	 * @see pw.core.PWConverter#toString(java.lang.Object)
	 */
	@Override
	public String toString(Object object) {
		try {
			Clob clob = (Clob)object;
			String value = clob == null ? null : clob.getSubString(1, (int) clob.length());
			return value;
		} catch (SQLException e) {
			throw new PWError(e, e.getMessage());
		}
	}

}
