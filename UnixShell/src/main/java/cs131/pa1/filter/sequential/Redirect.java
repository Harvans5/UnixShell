package cs131.pa1.filter.sequential;
import java.util.*;
import java.io.*;
import cs131.pa1.filter.Message;
/**
 * This class will take in a piped input and a parameter and transfer the piped input into a text
 * file which will either be created or replaced by the name of the paramter
 * @author henryarvans
 */
public class Redirect extends SequentialFilter {
	//Field redirect which will be set equal to the name of the new text file that the input will be transfered to
	String redirect;
	/**
	 * Constructor for this class checks to make sure that there is a paramter and only one parameter
	 * @param input the name of the new text file
	 */
	public Redirect(String [] input) {
		if(input == null || input.length == 0) {
			throw new RuntimeException(Message.REQUIRES_PARAMETER.with_parameter(">"));
		} else if(input.length > 1) {
			throw new RuntimeException(Message.INVALID_PARAMETER.with_parameter(">" + Arrays.deepToString(input)));
		} else {
			//Since it passed the other two cases to get here you can safley assume that there is one and only one element in the parameter array
			redirect = input[0];
			this.input = new LinkedList<String>();
			output = new LinkedList<String>();
		}
	}
	/**
	 * Makes sure that there is an input before calling back to the parent process method
	 */
	public void process() {
		if(input.size() == 0 || input == null) {
			throw new RuntimeException(Message.REQUIRES_INPUT.with_parameter(">" + " " + redirect));
		} else {
			super.process();
		}
	}

	@Override
	/**
	 * Creates a new file with the name given by the paramter and then checks to see if a file with 
	 * that name already exists in which case it replaces it otherwise it creates one.
	 * Then transfers over the content of the piped input into the file
	 * @return null because nothing can occur after redirect
	 * @param line the piped input
	 */
	protected String processLine(String line){
		File f = new File(SequentialREPL.currentWorkingDirectory, redirect);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(line);
		String x = "";
		try {
			if(!(f.exists())) {
				f.createNewFile();
			} else {
				//If a file with the same name as the paramter exists it deletes that file and then creates a new one with the that name
				f.delete();
				f.createNewFile();
			}
			@SuppressWarnings("resource")
			PrintStream output = new PrintStream(redirect);
			while(scanner.hasNextLine()) {
				x = scanner.nextLine();
				//Used to make sure that there is no trailing white space at the end of the file
				if(!(scanner.hasNextLine())) {
					output.print(x);
				} else {
					output.println(x);
				}
			}
		//Will not hit the catch statment because both cases of wether or not the file exists are handled in the try statment
		}catch(Exception e) {
			
		}
		//Returns null because nothing can occur after the redirect
		return null;
	}

}
