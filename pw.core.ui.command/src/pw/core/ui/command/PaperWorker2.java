/*
 *  $Id: PaperWorker2.java 2013/09/28 15:16:12 masamitsu $
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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import pw.core.PWAction;
import pw.core.PWError;
import pw.core.PWGeneralController;
import pw.core.PWSession;
import pw.core.PWUtilities;
import pw.core.SqlAccesser;
import pw.core.action.BasicAddAction;
import pw.core.action.BasicDeleteAction;
import pw.core.action.BasicDetailAction;
import pw.core.action.BasicListAction;
import pw.core.action.BasicUpdateAction;
import pw.core.setting.JobSetting;
import pw.core.setting.SettingController;
import pw.core.setting.ActionSetting;
import pw.core.ui.command.operation.BasicAddOperation;
import pw.core.ui.command.operation.BasicDeleteOperation;
import pw.core.ui.command.operation.BasicDetailOperation;
import pw.core.ui.command.operation.BasicListOperation;
import pw.core.ui.command.operation.BasicUpdateOperation;

/**
 * @author masamitsu
 *
 */
public class PaperWorker2 implements Closeable {
	
	private PWSession session = new PWSession();
	
	private HashMap<String, PWGeneralController> jobs = new HashMap<String, PWGeneralController>();
	
	private OperationController operationController;
	
	public PaperWorker2(String userId) {
		session.setAccesser(SqlAccesser.getAccesser(userId));
		session.setUserId(userId);
	}
	
	public static void main(String[] args) {
		
		String userId = "";
		if (args.length == 1) {
			userId = args[1];
		}

		try {
			PaperWorker2 paperworker = new PaperWorker2(userId);
			paperworker.initialize();
			try {
				paperworker.run();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				paperworker.close();
			}
		} catch (PWError e) {
			error(e.getMessage());
			error(e.getInnerException());
		}
	}
	
	public void close() {
		session.getAccesser().close();
	}
	
	public void initialize() {
		// Load jobs
		loadJobs();
	}

	/**
	 * @throws PWError 
	 * 
	 */
	private void loadJobs() {
		SettingController settingController = new SettingController(session);
		jobs.put("job", settingController);
		operationController = new OperationController(session);
		jobs.put("command", operationController);
		
		// Job
		@SuppressWarnings("unchecked")
		List<Object> jobSettingObjects = (List<Object>)settingController.invoke(SettingController.LISTJOB, JobSetting.class);
		for (Object jobSettingObject : jobSettingObjects) {
			JobSetting jobSetting = (JobSetting)jobSettingObject;
			Class<?> controllerType = PWUtilities.getClass(jobSetting.getClassPath());
			PWGeneralController controller = (PWGeneralController)PWUtilities.createInstance(controllerType);
			jobs.put(jobSetting.getName(), controller);
			
			// Action
			@SuppressWarnings("unchecked")
			List<Object> actionSettingObjects = (List<Object>)settingController.invoke(SettingController.LISTACTION, ActionSetting.class);
			for (Object actionSettingObject : actionSettingObjects) {
				ActionSetting actionSetting = (ActionSetting)actionSettingObject;
				Class<?> actionnType = PWUtilities.getClass(actionSetting.getActionClassPath());
				Object[] arguments = (Object[])actionSetting.getArgumentArray();
				PWAction action = (PWAction)PWUtilities.createInstance(actionnType, arguments);
				controller.registAction(actionSetting.getActionName(), action);
			}
		}
	}

	private void run() {
		message("==================================================");
		message("PaperWorker2");
		message("==================================================");
		flush();
		while (true) {
			String input = prompt("> ");
			if (input.equals("quit")) {
				message("Bye.");
				return;
			}
			
			if (input.equals("")) {
				continue;
			}
			
			if (input.equals("help")) {
				message("quit");
				for (String name : jobs.keySet()) {
					message(name);
				}
				continue;
			}
			
			String[] commandLine = input.split(" ");
			String jobName = commandLine[0];
			
			PWGeneralController controller = jobs.get(jobName);
			if (controller == null) {
				continue;
			}
			
			int length = commandLine.length;
			if (length == 1) {
				printActionList(controller);
				continue;
			}
			
			String actionName = commandLine[1];

			try {
				PWBasicOperation operation = getOperation(controller, jobName, actionName);
				operation.run(commandLine);
			} catch (PWError e) {
				error("*** ERROR *** %s", e.getMessage());
			}
		}
	}
	
	/**
	 * @param jobName
	 * @param actionName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private PWBasicOperation getOperation(PWGeneralController controller, String jobName, String actionName) {
		PWAction action = controller.getAction(actionName);
		if (action == null) {
			throw new PWError("Not found action.[%s]", actionName);
		}
		OperationSetting operationSetting = new OperationSetting();
		operationSetting.setJobName(jobName);
		operationSetting.setActionName(actionName);
		operationSetting = (OperationSetting) operationController.invoke("detail", operationSetting);
		if (operationSetting == null) {
			return getDefaultOperation(controller, action);
		}
		
		try {
			String classPath = operationSetting.getOperationClassPath();
			Class<? extends PWBasicOperation> operationType;
			operationType = (Class<? extends PWBasicOperation>)PWUtilities.getClass(classPath);
			PWBasicOperation operation = PWUtilities.createInstance(operationType, controller);
			return operation;
		} catch (PWError e) {
			return getDefaultOperation(controller, action);
		}
	}

	/**
	 * @param name
	 * @return
	 */
	private PWBasicOperation getDefaultOperation(PWGeneralController controller, PWAction action) {
		Class<?> class_ = action.getClass();
		while (class_ != null) {
			if (class_ == BasicAddAction.class) {
				return new BasicAddOperation(controller, ((BasicAddAction)action).getItemType());
			} else if (class_ == BasicDeleteAction.class) {
				return new BasicDeleteOperation(controller, ((BasicDeleteAction)action).getItemType());
			} else if (class_ == BasicDetailAction.class) {
				return new BasicDetailOperation(controller, ((BasicDetailAction)action).getItemType());
			} else if (class_ == BasicListAction.class) {
				return new BasicListOperation(controller, ((BasicListAction)action).getItemType());
			} else if (class_ == BasicUpdateAction.class) {
				return new BasicUpdateOperation(controller, ((BasicUpdateAction)action).getItemType());
			} else {
				class_ = class_.getSuperclass();
			}
		}
		return null;
	}

	/**
	 * @param controller
	 */
	private void printActionList(PWGeneralController controller) {
		List<String> names = new ArrayList<String>(controller.getActionNames());
		Collections.sort(names);
		
		for (String name : names) {
			message(name);
		}
	}

	public static void message(String format, Object... args) {
		System.out.println(String.format(format, args));
	}
	
	public static void error(String format, Object... args) {
		System.err.println(String.format(format, args));
		System.err.println("");
		System.err.flush();
	}
	
	public static void error(Exception e) {
		e.printStackTrace();
	}
	
	public static void flush() {
		System.out.flush();
	}
	
	public static String prompt(String format, Object... args) {
		System.out.print(String.format(format, args));
		flush();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in), 1);
		String input = "";
		try {
			input = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}

}
