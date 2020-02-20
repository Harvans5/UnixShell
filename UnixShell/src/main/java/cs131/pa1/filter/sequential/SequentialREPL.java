package cs131.pa1.filter.sequential;
import java.io.File;
import java.util.*;
import java.util.Scanner;
import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

/**
 * The main implementation of the REPL loop (read-eval-print loop).
 * It reads commands from the user, parses them, executes them and displays the result.
 * @author cs131a
 *
 */
public class SequentialREPL {
	/**
	 * the path of the current working directory
	 */
	static String currentWorkingDirectory;
	/**
	 * The main method that will execute the REPL loop
	 * @param args not used
	 */
	@SuppressWarnings({ "static-access", "resource" })
	public static void main(String[] args){
		//First saves the current working directory field to the absolute path
		File f = new File(".");
		currentWorkingDirectory = f.getAbsolutePath();
		currentWorkingDirectory = currentWorkingDirectory.substring(0, currentWorkingDirectory.length() - 2);
		SequentialCommandBuilder scb = new SequentialCommandBuilder();
		//Creates a scanner that will grab input from the user in the console
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		String s;
		System.out.print(Message.WELCOME);
		while(exit == false) {
			System.out.print(Message.NEWCOMMAND);
			s = scanner.nextLine();
			//First checks if the input given by the user is exit
			if(s.equals("exit")) {
				//if the input is equal to exit it then makes sure that the user didnt include any pipes in that message
				//Because exit cannot take input or output
				int location = s.indexOf("exit");
				int locationPipe = s.indexOf("\\|");
				if(locationPipe != -1) {
					if(location != 0) {
						throw new RuntimeException(Message.CANNOT_HAVE_INPUT.with_parameter("exit"));
					} else if(location > locationPipe) {
						throw new RuntimeException(Message.CANNOT_HAVE_OUTPUT.with_parameter("exit"));
					}
				//If the user did not include any pipe then the exit boolean gets set to true and the loop ends
				} else if(location == 0){
					System.out.print(Message.GOODBYE);
					exit = true;
				}
			//The next input that is checked for is if the input contains any sort of cd command
			} else if(s.contains("cd ") || s.contains("cd .") || s.contains("cd ..") || s.equals("cd")){
				s = s.trim();
				int location = s.indexOf("cd");
				int locationPipe = s.indexOf("|");
				boolean notCD = false;
				//Checks if there is no paramters in which case an error will be thrown
				if(s.equals("cd")) {
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cd"));
					notCD = true;
				}
				//Just like in exit, there is first a check for any piping which will result in error because 
				//cd cannot take input or return output
				if(locationPipe != -1) {
					String [] cd = s.split("\\|",0);
					int temp = 0;
					for(int i = 0; i < cd.length; i++) {
						if(cd[i].contains("cd")) {
							temp = i;
						}
					}
					if(location != 0) {
						System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(cd[temp]));
					} else {
						System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(cd[temp]));
					}
				//If cd passed the tests for having no input or being dependend on for output and has a parameter
				//Then the next check is on what type of cd it is
				} else if(location == 0) {
					//If the command is equal to "cd ." then nothing is actually changed but it is also a valid command
					if(s.equals("cd .")) {
					
					//If the command is "cd .." then it finds the last location of a file seperator and removes off the last file 
					//and file seperator in the current working directory
					} else if(s.equals("cd ..")) {
						location = currentWorkingDirectory.indexOf(Filter.FILE_SEPARATOR);
						if(location != -1) {
							for(int i = 0; i < currentWorkingDirectory.length(); i++) {
								if(currentWorkingDirectory.charAt(i) == '\\' || currentWorkingDirectory.charAt(i) == '/') {
									location = i;
								}
							}
							currentWorkingDirectory = currentWorkingDirectory.substring(0,location);
						}
					//Makes sure that the cd has a parameter
					} else if(notCD == false){
						f = new File(currentWorkingDirectory);
						//Grabs everything after the "cd " which will be the new directory added to the current working directory
						s = s.substring(3);
						String x = "";
						boolean exists;
						location = s.indexOf(Filter.FILE_SEPARATOR);
						//creates an array of all the parameters
						String [] directories = s.split(Filter.FILE_SEPARATOR,0);
						//Checks if the parameters exist in the list of files in the current working directory and if so 
						//is added to the end of the current directory. Otherwise it informs the user that the directory wasn't found
						for(int i = 0; i < directories.length; i++) {
							x = directories[i];
							exists = exists(x);
							if(exists == false) {
								currentWorkingDirectory = currentWorkingDirectory + "/" +  x;
							} else {
								System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter("cd " +x.trim()));
							}
						}
					}
				}
			//If the user input wasn't a cd or an exit command it is then brought to sequential command builder to see
			//if it falls under the valid commands
			} else {
				try {
					//If the list of sequential filters is properly created and linked the filters can then be run
					List<SequentialFilter> sf = scb.createFiltersFromCommand(s);
					runFilters(sf);
				} catch(Exception e) {
					System.out.print(e.getMessage());
				}
			}
		}
	}
	/**
	 * simple while loop that calls the process method on all filters
	 * @param filters is the list of linked filters ready to be processed
	 */
	public static void runFilters(List<SequentialFilter> filters) {
		for(int i = 0; i < filters.size(); i++) {
			filters.get(i).process();
		}
	}
	/**
	 * Used for the cd class to see if the directory that is being attempted to be added to the current 
	 * working directory exists or not
	 * @param s the paramter on the cd input
	 * @return true or false depending on wether or not s is in the list of files
	 */
	public static boolean exists(String s) {
		File f = new File(SequentialREPL.currentWorkingDirectory);
		File[] files = f.listFiles();
		for(int i = 0; i < files.length; i++) {
			if(s.equals(files[i].getName())) {
				return false;
			}
		}
		return true;
	}
}
