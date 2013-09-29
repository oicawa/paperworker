/*
 *  $Id: CommandController.java 2013/09/29 19:04:28 masamitsu $
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

import pw.core.PWGeneralController;
import pw.core.PWSession;
import pw.core.action.BasicAddAction;
import pw.core.action.BasicDeleteAction;
import pw.core.action.BasicDetailAction;
import pw.core.action.BasicListAction;
import pw.core.action.BasicUpdateAction;

/**
 * @author masamitsu
 *
 */
public class OperationController extends PWGeneralController {

	public final static String ADD = "add";
	public final static String UPDATE = "update";
	public final static String DELETE = "delete";
	public final static String DETAIL = "detail";
	public final static String LIST = "list";
	/**
	 * @param session
	 */
	public OperationController(PWSession session) {
		super(session);
		
		String commandSettingClassPath = OperationSetting.class.getName();
		registAction(ADD, new BasicAddAction(commandSettingClassPath));
		registAction(UPDATE, new BasicUpdateAction(commandSettingClassPath));
		registAction(DELETE, new BasicDeleteAction(commandSettingClassPath));
		registAction(DETAIL, new BasicDetailAction(commandSettingClassPath));
		registAction(LIST, new BasicListAction(commandSettingClassPath));
		
		initializeTable(OperationSetting.class);
	}

}
