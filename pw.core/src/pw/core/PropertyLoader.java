/*
 *  $Id: PropertyLoader.java 2013/10/06 16:02:02 masamitsu $
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

package pw.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author masamitsu
 *
 */
public class PropertyLoader {
	
	private static HashMap<String, Properties> propertyMap = new HashMap<String, Properties>();
	
	public static String getValue(String propertyName) {
		return getValue("core", propertyName);
	}
	
	public static String getValue(String categoryName, String propertyName) {
		if (!propertyMap.containsKey(categoryName)) {
			Properties conf = new Properties();
			try {
				File current = new File(System.getProperty("user.dir"));
				String format = "/resources/%s.properties";
				String relationalPath = String.format(format, categoryName);
				String fullPath = current.getParentFile().getAbsolutePath() + relationalPath;
				conf.load(new FileInputStream(fullPath));
				propertyMap.put(categoryName, conf);
			} catch (FileNotFoundException e) {
				throw new PWError(e, e.getMessage());
			} catch (IOException e) {
				throw new PWError(e, e.getMessage());
			}
		}
		return propertyMap.get(categoryName).getProperty(propertyName);
	}

}
