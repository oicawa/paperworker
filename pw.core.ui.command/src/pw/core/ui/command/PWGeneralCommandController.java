/*
 *  $Id: PWGeneralUIController.java 2013/10/02 6:58:55 masamitsu $
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

package pw.core.ui.command;

import java.util.Collection;
import java.util.HashMap;

import pw.core.PWAction;
import pw.core.PWError;
import pw.core.PWItem;
import pw.core.PWQuery;
import pw.core.PWSession;
import pw.core.ui.command.operation.PWBasicOperation;
import pw.core.ui.command.operation.PWOperation;

/**
 * @author masamitsu
 *
 */
public abstract class PWGeneralCommandController {

	protected HashMap<String, PWBasicOperation> operations = new HashMap<String, PWBasicOperation>();
	
	public PWGeneralCommandController() {
	}
	
	public void registOperation(String name, PWBasicOperation operation) {
		operations.put(name, operation);
	}
	
	public Object invoke(String operationName, Object... objects) {
		if (!operations.containsKey(operationName)) {
			throw new PWError("The specified operation '%s' is not found.", operationName);
		}
		//return operations.get(operationName).run(objects);
		return null;
	}

	/**
	 * @return 
	 * 
	 */
	public Collection<String> getActionNames() {
		return (Collection<String>)operations.keySet();
		
	}

	/**
	 * @param operationName
	 * @return
	 */
	public PWBasicOperation getOperation(String operationName) {
		if (!operations.containsKey(operationName)) {
			return null;
		}
		return operations.get(operationName);
	}

	/**
	 * @return
	 */
	public Collection<? extends String> getOperationNames() {
		return (Collection<String>)operations.keySet();
	}

}
