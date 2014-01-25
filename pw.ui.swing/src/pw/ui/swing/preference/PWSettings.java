/*
 *  $Id: PWPreference.java 2013/11/09 14:21:08 masamitsu $
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

package pw.ui.swing.preference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.prefs.Preferences;

import pw.core.PWError;
import pw.core.PWUtilities;

/**
 * @author masamitsu
 *
 */
public class PWSettings {
	
	protected Preferences prefs;
	
	protected PWSettings(Class<?> type) {
		prefs = Preferences.userNodeForPackage(type);
	}
	
	public void save() {
		try{
			prefs.flush();
		}catch(java.util.prefs.BackingStoreException e) {
			throw new PWError(e, e.getMessage());
		}
	}
	
	protected Object getObject(String key) {
		// Get byte array from preferences
		byte[] value = prefs.getByteArray(key, null);
		if (value == null) {
			return null;
		}
		
		// Convert from byte array to object
		ByteArrayInputStream bytes = new ByteArrayInputStream(value);
		ObjectInputStream oos;
		try {
			oos = new ObjectInputStream(bytes);
			Object object;
			try {
				object = oos.readObject();
			} catch (ClassNotFoundException e) {
				return null;
			} finally {
				oos.close();
			}
			return object;
		} catch (IOException e) {
			return null;
		}
	}
	
	protected void setObject(String key, Object object) {
		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(bytes);
			try {
				stream.writeObject(object);
				stream.flush();
				prefs.putByteArray(key, bytes.toByteArray());
			} finally {
				stream.close();
			}
		} catch (IOException e) {
			throw new PWError(e, e.getMessage());
		}
	}
}
