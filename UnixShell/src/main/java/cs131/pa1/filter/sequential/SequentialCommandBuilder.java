package cs131.pa1.filter.sequential;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;
/**
 * This class manages the parsing and execution of a command.
 * It splits the raw input into separated subcommands, 
 * creates subcommand filters, and links them into a list. 
 * @author cs131a
 *
 */
public class SequentialCommandBuilder {
	/**
	 * Creates and returns a list of filters from the specified command
	 * @param command the command to create filters from
	 * @return the list of SequentialFilter that represent the specified command
	 */
	public static List<SequentialFilter> createFiltersFromCommand(String command){
		List<SequentialFilter> sf = new ArrayList<SequentialFilter>(); 
		//Checks if there is a redirect in the command string, if there isnt than an output should be added to the end 
		//to display the input to the console
		if(!(command.contains(">"))){
			command = command + "|" + "output";
		//Otherwise it replaces the redirect with a pipe than a redirect because once the command string is split by pipes
		//redirect should be seen as a command after a pipe because it requires piped input
		} else {
			command = command.replace(">", "|>");
		}
		if(command.charAt(0) == '|') {
			command = command.substring(1,command.length());
		}
		String [] commands = command.split("\\|",0);
		//Checks right away to make sure there is no filters occuring after a redirect and will throw an error if so
		for(int i = 0; i < commands.length; i++) {
			if(commands[i].contains(">") && i != commands.length - 1) {
				throw new RuntimeException(Message.CANNOT_HAVE_OUTPUT.with_parameter(commands[i]));
			}
		}
		//Loops over all the filter commands and calls construct filter on each command
		for(int i = 0; i < commands.length; i++) {
			SequentialFilter sq = constructFilterFromSubCommand(commands[i]);
			sf.add(sq);
		}
		//Once sequential filter has been filled with every filter in the command string it can then link the filters
		linkFilters(sf);
		return sf;
	}
	
	/**
	 * Returns the filter that appears last in the specified command
	 * @param command the command to search from
	 * @return the SequentialFilter that appears last in the specified command
	 */
	@SuppressWarnings("unused")
	private static SequentialFilter determineFinalFilter(String command){
		return null;
	}
	
	/**
	 * Returns a string that contains the specified command without the final filter
	 * @param command the command to parse and remove the final filter from 
	 * @return the adjusted command that does not contain the final filter
	 */
	@SuppressWarnings("unused")
	private static String adjustCommandToRemoveFinalFilter(String command){
		return null;
	}
	
	/**
	 * Creates a single filter from the specified subCommand
	 * @param subCommand the command to create a filter from
	 * @return the SequentialFilter created from the given subCommand
	 */
	private static SequentialFilter constructFilterFromSubCommand(String subCommand){
		subCommand = subCommand.trim();
		//Splits the filter into the actual filter command and any parameters it may have
		String[]command = subCommand.split(" ", 0);
		//Saves the first element as the filter command
		String c = command[0];
		//saves the rest of the elements as any of the parameters that the filter may have
		//Only passes the parameters to the filters that require them
		String [] input = Arrays.copyOfRange(command, 1, command.length);
		if(c.equals("cat")) {
			Cat cat = new Cat(input);
			return cat;
		} else if(c.equals("pwd")) {
			Pwd pwd = new Pwd();
			return pwd;
		} else if(c.equals("ls")) {
			Ls ls = new Ls();
			return ls;
		} else if(c.equals("grep")) {
			Grep grep = new Grep(input);
			return grep;
		}else if(c.equals("wc")) {
			Wc wc = new Wc();
			return wc;
		} else if(c.equals("uniq")) {
			Uniq uniq = new Uniq();
			return uniq;
		} else if(c.equals(">")) {
			Redirect redirect = new Redirect(input);
			return redirect;
		} else if(c.equals("output")){
			Output output = new Output();
			return output;
		} else {
			String s = Arrays.deepToString(input);
			s = s.replace("[", "");
			s = s.replace("]", "");
			s = s.replace(",", "");
			throw new RuntimeException(Message.COMMAND_NOT_FOUND.with_parameter(c + " " + s));
		}
	}
	
	/**
	 * links the given filters with the order they appear in the list
	 * @param filters the given filters to link
	 * @return true if the link was successful, false if there were errors encountered.
	 * Any error should be displayed by using the Message enum.
	 */
	private static boolean linkFilters(List<SequentialFilter> filters){
		if(filters.size() == 0) {
			return false;
		} else {
			//Creates a pointer for the head of the filters
			Filter head = filters.get(0);
			Filter curr;
			//Loops starting at the second element and just points heads next to the current pointer and
			//the current pointer gets its prev set to the head then the head gets set to the current 
			//and the process continues
			for(int i = 1; i < filters.size(); i++) {
				curr = filters.get(i);
				head.setNextFilter(curr);
				curr.setPrevFilter(head);
				head = curr;
			}
			return true;
		}
	}
}
