package cs131.pa1.filter.sequential;
import java.util.*;
import cs131.pa1.filter.Message;
/**
 * This class just adds the currentworking directory to the output queue
 * @author henryarvans
 *
 */
public class Pwd extends SequentialFilter {
	/**
	 * This constructor just instantiates the input and output queues 
	 */
	public Pwd() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}

	@Override
	/**
	 * The process line method isnt used for this class
	 * @return null
	 */
	protected String processLine(String line) {
		return null;
	}
	
	@Override
	/**
	 * Checks to see if there is anything in the input queue because pwd cannot take input
	 * Otherwise adds the currentworking directory to the output queue
	 */
	public void process() {
		if(input.size() > 0) {
			throw new RuntimeException(Message.CANNOT_HAVE_INPUT.with_parameter("pwd"));
		} else {
			output.add(SequentialREPL.currentWorkingDirectory);
		}
	}

}
