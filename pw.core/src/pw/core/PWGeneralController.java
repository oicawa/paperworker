/*
 *  $Id: PWJob.java 2013/09/28 15:58:04 masamitsu $
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * @author masamitsu
 *
 */
public class PWGeneralController {

	protected PWSession session;
	
	protected HashMap<String, PWAction> actions = new HashMap<String, PWAction>();
	
	public PWGeneralController(PWSession session) {
		this.session = session;
	}
	
	public void registAction(String name, PWAction action) {
		assert(session != null);
		action.setSession(session);
		actions.put(name, action);
	}

	protected void initializeTable(Class<? extends PWItem> itemType) {
		if (session.getAccesser().existTable(PWItem.getTableName(itemType))) {
			return;
		}
		
    	PWQuery query = PWQuery.getCreateTableQuery(itemType);
    	session.getAccesser().execute(query);
	}
	
	public Object invoke(String actionName, Object... objects) {
		if (!actions.containsKey(actionName)) {
			throw new PWError("The specified action '%s' is not found.", actionName);
		}
		return actions.get(actionName).run(objects);
	}

	/**
	 * @return 
	 * 
	 */
	public Collection<String> getActionNames() {
		return (Collection<String>)actions.keySet();
		
	}

	/**
	 * @param actionName
	 * @return
	 */
	public PWAction getAction(String actionName) {
		if (!actions.containsKey(actionName)) {
			return null;
		}
		return actions.get(actionName);
	}
}
