/*
 *  $Id: PaperWorker.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

package paperworker.core.ui.command;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import paperworker.core.PWController;
import paperworker.core.PWError;
import paperworker.core.PWItem;
import paperworker.core.PWUtilities;
import paperworker.core.PWWarning;

public class PaperWorker implements Closeable {
	
	private HashMap<String, PWCommand<? extends PWItem, ? extends PWController>> commands = new HashMap<String, PWCommand<? extends PWItem, ? extends PWController>>();

	public static void main(String[] args) {
		PaperWorker paperworker = new PaperWorker();
		try {
			paperworker.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			paperworker.close();
		}
	}
	
	public void close() {
		for (PWCommand<? extends PWItem, ? extends PWController> command : commands.values()) {
			command.close();
		}
	}
	
	private void run(String[] args) throws PWError {
		registCommands();
		message("==================================================");
		message("PaperWorker");
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
				for (String name : commands.keySet()) {
					message(name);
				}
				message("");
				continue;
			}
			
			String[] commandLine = input.split(" ");
			String commandName = commandLine[0];
			
			PWCommand<? extends PWItem, ? extends PWController> command = commands.get(commandName);
			if (command == null) {
				continue;
			}
			
			int length = commandLine.length;
			if (length == 1) {
				command.printActionList();
				continue;
			}
			
			try {
				command.run(commandLine);
			} catch (PWError e) {
				error("*** ERROR *** %s", e.getMessage());
			} catch (PWWarning e) {
				message(e.getMessage());
			}
		}
	}
	
	private void registCommands() throws PWError {
		final String endsWith = ".ui.command/bin"; // TODO Confirm in case *.jar
		final String endTrim = "/bin"; // TODO Confirm in case *.jar
		File directory = (new File(".")).getAbsoluteFile().getParentFile().getParentFile();
		String startsWith = directory.getAbsolutePath();
		String[] pathes = System.getProperty("java.class.path").split(":");
		for (String path : pathes) {
			if (!path.startsWith(startsWith) ||
				!path.endsWith(endsWith)) {
				continue;
			}
			
			String packageName = path.substring(startsWith.length() + 1, path.length() - endTrim.length());
			PWCommand<? extends PWItem, ? extends PWController> command = createCommand(packageName);
			if (command == null) {
				continue;
			}
			String commandName = PWUtilities.getCommandName(packageName);
			commands.put(commandName, command);
		}
	}

	private PWCommand<? extends PWItem, ? extends PWController> createCommand(String packageName) throws PWError {
		String classPath = String.format("%s.Command", packageName);
		try {
			@SuppressWarnings("unchecked")
			Class<PWCommand<? extends PWItem, ? extends PWController>> commandClass = (Class<PWCommand<? extends PWItem, ? extends PWController>>)Class.forName(classPath);
			PWCommand<? extends PWItem, ? extends PWController> command = PWUtilities.createInstance(commandClass);
			return command;
		} catch (ClassNotFoundException e) {
			return null;
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
	
	public static boolean confirm(String message, String again, String ok, String cancel) {
		while (true) {
			String input = PaperWorker.prompt(message).toUpperCase();
			if (input.equals(ok.toUpperCase())) {
				return true;
			} else if (input.equals(cancel.toUpperCase())) {
				return false;
			} else {
				PaperWorker.message("");
				PaperWorker.message(again);
			}
		}
	}
	
	public static void flush() {
		System.out.flush();
	}
}