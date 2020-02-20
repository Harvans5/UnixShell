package cs131.pa1.filter.sequential;
import java.util.*;
import cs131.pa1.filter.Message;
/**
 * This class takes in a piped input and then creates a string representing the amount of lines, words and characters 
 * that exist in that piped input
 * @author henryarvans
 *
 */
public class Wc extends SequentialFilter {
	/**
	 * constructor for wc which just instantiates the input and output queues
	 */
	public Wc() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}

	@SuppressWarnings("resource")
	@Override
	/**
	 * Takes in the piped input and utilizes scanners to add up how many lines, words and characters are in the input
	 * and then returns that string of the three as output
	 * @return the piped output of a string of the lines, words and characters
	 * @param the piped input
	 */
	protected String processLine(String line) {
		String s = "";
		String x = "";
		int lineCount = 0;
		int wordCount = 0;
		int charCount = 0;
		//Scanner is used on the piped input
		Scanner scanner = new Scanner(line);
		while(scanner.hasNextLine()) {
			x = scanner.nextLine();
			lineCount++;
			//Scanner2 is used for every individual line to calculate the word and character count
			Scanner scanner2 = new Scanner(x);
			charCount += x.length();
			while(scanner2.hasNext()){
				scanner2.next();
				wordCount++;
			}
		}
		s = lineCount + " " + wordCount + " " + charCount;
		return s;
	}
	/**
	 * Overrides the process method to make sure that there is input
	 * if so calls back to the parent method
	 */
	public void process() {
		if(input.size() == 0) {
			throw new RuntimeException(Message.REQUIRES_INPUT.with_parameter("wc"));
		} else {
			super.process();
		}
	}

}
