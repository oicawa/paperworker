package paperworker.core.ui.command;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import paperworker.core.PWController;
import paperworker.core.PWError;
import paperworker.core.PWWarning;

public class PaperWorker implements Closeable {
	
	private HashMap<String, Command<? extends PWController>> commands = new HashMap<String, Command<? extends PWController>>();

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
		for (Command<? extends PWController> command : commands.values()) {
			command.close();
		}
	}
	
	private void run(String[] args) {
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
				message("<< Command List >>");
				message(" --------------------------------------------------");
				// TODO: Must implement the logic that gets all libraries including "Command" class dynamically.
				message(" quit");
				message(" member");
				message(" group");
				message(" --------------------------------------------------");
				message("");
				continue;
			}
			
			String[] commandLine = input.split(" ");
			String commandName = commandLine[0];
			
			Command<? extends PWController> command = getCommand(commandName);
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
	
	private Command<? extends PWController> getCommand(String commandName) {
		if (!commands.containsKey(commandName)) {
			String classPath = String.format("paperworker.%s.ui.command.Command", commandName);
			try {
				@SuppressWarnings("unchecked")
				Class<Command<? extends PWController>> commandClass = (Class<Command<? extends PWController>>)Class.forName(classPath);
				Command<? extends PWController> command = commandClass.newInstance();
				commands.put(commandName, command);
			} catch (ClassNotFoundException e) {
				error("*** ERROR *** paperworker has no command. [name: %s, classpath: %s]", commandName, classPath);
				error(e.getMessage());
				return null;
			} catch (InstantiationException e) {
				error("*** ERROR *** paperworker command couldn't instatiate. [name: %s, classpath: %s]", commandName, classPath);
				error(e.getMessage());
				return null;
			} catch (IllegalAccessException e) {
				error("*** ERROR *** paperworker command [%s] couldn't access to the constructor(?). [name: %s, classpath: %s]", commandName, classPath);
				error(e.getMessage());
				return null;
			} catch (Exception e) {
				error(e.getMessage());
				return null;
			}
		}
		return commands.get(commandName); 
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