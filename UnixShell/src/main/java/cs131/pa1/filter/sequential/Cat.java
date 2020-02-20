package cs131.pa1.filter.sequential;
import java.util.*;

import cs131.pa1.filter.Message;
import java.io.*;

 /**
  * This class takes in a parameter(s) and then creates a string of all the data in the parameters 
  * and adds it to the output queue 
  * @author henryarvans
  *
  */
public class Cat extends SequentialFilter {
	//Create string array cat that will hold all paramters when a cat objected is created
	String[]cat;
	/**
	 * Constructor for cat object and instantiates an input and output queue as a linked list
	 * Makes sure that there is at least one paramter and otherwise throws an error
	 * @param input which represents all text files passed as a paramter to cat
	 */
	public Cat(String[] input) {
		if(input == null || input.length == 0) {
			throw new RuntimeException(Message.REQUIRES_PARAMETER.with_parameter("cat"));
		} else {
			this.cat = input;
			this.input = new LinkedList<String>();
			this.output = new LinkedList<String>();
		}
	}

	@Override
	/**
	 * Not used for cat object
	 */
	protected String processLine(String line) {
		return null;
	}
	/**
	 * Overrides the super process method and first creates a string of all the paramters that can be passed
	 * Then checks to make sure that their is no input because cat cannot receive input
	 * then runs over all txt files passed and concatanates them together into a string and adds that string to the 
	 * output list. Also checks to make sure that the text files its trying to read exist
	 */
	public void process() {
		String allFiles = Arrays.deepToString(cat);
		allFiles = allFiles.replace("[", "");
		allFiles = allFiles.replace("]", "");
		allFiles = allFiles.replace(",", "");
		if(this.input.size() > 0) {
			throw new RuntimeException(Message.CANNOT_HAVE_INPUT.with_parameter("cat" + " " + allFiles));
		} else {
			String s = "";
			String x = "";
			try {
				for(int i = 0; i < cat.length; i++) {
					@SuppressWarnings("resource")
					Scanner scanner = new Scanner(new File(cat[i]));
					while(scanner.hasNextLine()) {
						x = scanner.nextLine();
						//Used to make sure there are no trailing line of space in the output string
						if(!scanner.hasNextLine()) {
							s = s + x;
						} else {
							s = s + x + "\n";	
						}
					}
				}
			}catch(Exception e) {
				throw new RuntimeException(Message.FILE_NOT_FOUND.with_parameter("cat" + " " + allFiles));
			}
			output.add(s);
		}
	}
}
