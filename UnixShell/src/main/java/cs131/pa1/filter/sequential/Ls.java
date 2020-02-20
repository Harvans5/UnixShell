package cs131.pa1.filter.sequential;
import java.io.File;
import java.util.*;
import cs131.pa1.filter.Message;
/**
 * LS is one of the simpler filters, and takes no parameter or piped input
 * This class simply creates a string and concatnates every file in the current working directory
 * @author henryarvans
 */
public class Ls extends SequentialFilter {
	/**
	 * Constructor for ls which just instantiates the input and output queues
	 */
	public Ls() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}

	@Override
	/**
	 * Process line is not used for ls
	 * @return null
	 */
	protected String processLine(String line) {
		return null;
	}
	/**
	 * first checks to make sure nothing is in the input queue because ls cannot take input
	 * Then adds to the output queue a list of all the files in the current working directory
	 */
	public void process() {
		if(input.size() > 0) {
			throw new RuntimeException(Message.CANNOT_HAVE_INPUT.with_parameter("ls"));
		} else {
			File f = new File(SequentialREPL.currentWorkingDirectory);
			File[] files = f.listFiles();
			String file = "";
			for(int i = 0; i < files.length; i++) {
				if(i == files.length - 1) {
					file = file + files[i].getName();
				} else {
					file = file + files[i].getName() + "\n";
				}
			}
			output.add(file);
		}
	}

}
