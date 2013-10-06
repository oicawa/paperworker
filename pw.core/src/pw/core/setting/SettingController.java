/*
 *  $Id: JobController.java 2013/09/28 21:14:47 masamitsu $
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

package pw.core.setting;

import pw.core.PWField;
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
public class SettingController extends PWGeneralController {

	public static final String ADDJOB = "addjob";
	public static final String UPDATEJOB = "updatejob";
	public static final String DELETEJOB = "deletejob";
	public static final String DETAILJOB = "detailjob";
	public static final String LISTJOB = "listjob";
	public static final String ADDACTION = "addaction";
	public static final String UPDATEACTION = "updateaction";
	public static final String DELETEACTION = "deleteaction";
	public static final String DETAILACTION = "detailaction";
	public static final String LISTACTION = "listaction";
	
	/**
	 * @param session
	 */
	public SettingController(PWSession session) {
		super(session);

		String jobSettingClassPath = JobSetting.class.getName();
		String actionSettingClassPath = ActionSetting.class.getName();
		String keyType = PWField.KeyType.Primary.toString();
		registAction(ADDJOB, new BasicAddAction(), jobSettingClassPath, keyType);
		registAction(UPDATEJOB, new BasicUpdateAction(), jobSettingClassPath, keyType);
		registAction(DELETEJOB, new BasicDeleteAction(), jobSettingClassPath, keyType);
		registAction(DETAILJOB, new BasicDetailAction(), jobSettingClassPath, keyType);
		registAction(LISTJOB, new BasicListAction(), jobSettingClassPath, keyType);
		registAction(ADDACTION, new BasicAddAction(), actionSettingClassPath, keyType);
		registAction(UPDATEACTION, new BasicUpdateAction(), actionSettingClassPath, keyType);
		registAction(DELETEACTION, new BasicDeleteAction(), actionSettingClassPath, keyType);
		registAction(DETAILACTION, new BasicDetailAction(), actionSettingClassPath, keyType);
		registAction(LISTACTION, new BasicListAction(), actionSettingClassPath, keyType);
	}
}
