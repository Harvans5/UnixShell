package cs131.pa1.filter.sequential;
import java.util.*;
import cs131.pa1.filter.Message;
/**
 * This class takes in a piped input and a parameter and will run through the piped input and add any line to the 
 * output that contains the paramter which will be the search string
 * @author henryarvans
 *
 */
public class Grep extends SequentialFilter {
	//String grep which will be set equal to the paramter which is the search string
	String grep;
	/**
	 * Constructor for grep that first makes sure that a paramter was passed, and that only one paramter was 
	 * passed because grep only accepts one and only one paramter
	 * Then sets grep field equal to that paramter if it exists
	 * @param input the search string that will be used on the inout fed to grep
	 */
	public Grep(String [] input) {
		if(input.length == 0) {
			throw new RuntimeException(Message.REQUIRES_PARAMETER.with_parameter("grep"));
		} else if(input.length > 1) {
			throw new RuntimeException(Message.INVALID_PARAMETER.with_parameter(Arrays.deepToString(input)));
		} else {
			grep = input[0];
			this.input = new LinkedList<String>();
			output = new LinkedList<String>();
		}
	}

	@Override
	/**
	 * Runs over the input line and checks for any line that contains the field grep in it
	 * if the line contains it, it is then added to an arraylist
	 * @param the piped input
	 * @return will be a string which is everyline in the array list
	 */
	protected String processLine(String line) {
		String s = "";
		String x = "";
		ArrayList<String> containGrep = new ArrayList<String>();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(line);
		while(scanner.hasNextLine()) {
			x = scanner.nextLine();
			if(x.contains(grep)) {
				containGrep.add(x);
			}
		}
		//Used to make sure there is no trailing line of space in the return 
		for(int i = 0; i < containGrep.size(); i++) {
			if(i == containGrep.size() - 1) {
				s = s + containGrep.get(i);
			} else {
				s = s + containGrep.get(i) + "\n";
			}
		}
		return s;
	}
	/**
	 * Overrides process to first make sure that there is at least one item in the input queue 
	 * if so then calls the parent process
	 */
	public void process() {
		if(this.input.size() == 0) {
			throw new RuntimeException(Message.REQUIRES_INPUT.with_parameter("grep " + grep));
		} else {
			super.process();
		}
	}

}
