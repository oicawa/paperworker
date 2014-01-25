/*
 *  $Id: BaseAction.java 2013/09/29 15:46:08 masamitsu $
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

package pw.action.basic;

import pw.core.PWAction;
import pw.core.PWError;
import pw.core.PWField;
import pw.core.PWUtilities;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
public abstract class AbstractBasicAction extends PWAction {
	
	protected Class<? extends PWItem> itemType;
	protected PWField.KeyType keyType;
	
	public AbstractBasicAction() {
		super();
	}

	public Class<? extends PWItem> getItemType() {
		return itemType;
	}
	
	public PWField.KeyType getKeyType() {
		return keyType;
	}

	/* (non-Javadoc)
	 * @see pw.core.PWAction#parseArguments(java.lang.String[])
	 */
	@Override
	protected void parseSettingParameters(String[] arguments) {
		if (arguments.length == 0) {
			throw new PWError("[%s#constructor] requires classpath and PWField.KeyType name(Optional. only 'Primary' or 'Unique').", getClass().getName());
		}

		// Get PWItem type
		try {
			@SuppressWarnings("unchecked")
			Class<? extends PWItem> tmpType = (Class<? extends PWItem>) PWUtilities.getClass(arguments[0]);
			itemType = tmpType;
		} catch (PWError e) {
			throw new PWError(e.getInnerException(), "[%s#constructor] classpath(%s) could not be parse as Class object", getClass().getName(), arguments[0]);
		}
		
		// Get KeyType
		if (arguments.length == 1) {
			keyType = PWField.KeyType.Primary;
			return;
		}
		PWField.KeyType tmpKeyType;
		try {
			tmpKeyType = PWField.KeyType.valueOf(arguments[1]);
		} catch (Exception e) {
			throw new PWError(e, "[%s#constructor] requires 'Primary' or 'Unique' or nothing(as Primary).", getClass().getName());
		}
		
		if (tmpKeyType == PWField.KeyType.None) {
			throw new PWError("[%s#constructor] requires 'Primary' or 'Unique' or nothing(as Primary).", getClass().getName());
		}
		keyType = tmpKeyType;
		
	}
}
