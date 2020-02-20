package cs131.pa1.filter.sequential;
import java.util.*;
/**
 * This class is used if the input isnt redirected into a text file at the end at which case it prints 
 * out the remaining input to the console
 * @author henryarvans
 *
 */
public class Output extends SequentialFilter {
	/**
	 * Constructor for output which just instantiates the input and output queue 
	 */
	public Output() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**
	 * Process line is not used in this class
	 * @return null
	 */
	@Override
	protected String processLine(String line) {
		return null;
	}
	/**
	 * will just print any input left in the queue to the console
	 */
	public void process() {
		while(input.isEmpty() == false) {
			System.out.println(input.poll());
		}
	}
}
