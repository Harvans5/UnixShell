package cs131.pa1.filter.sequential;
import java.util.*;
import cs131.pa1.filter.Message;
/**
 * This class takes in a piped input and will output the input minus any duplicate lines
 * utilizing a hashset to keep track of duplicates
 * @author henryarvans
 *
 */
public class Uniq extends SequentialFilter {
	/**
	 * Constuctor just instantiates the input and output queues
	 */
	public Uniq() {
		input = new LinkedList<String>();
		output = new LinkedList<String>();
	}
	/**
	 * The process method overwrites the parent method to first check that there is input 
	 * if there is then it proceeds to call to the parent class
	 */
	public void process() {
		if(input.size() == 0) {
			throw new RuntimeException(Message.REQUIRES_INPUT.with_parameter("uniq"));
		} else {
			super.process();
		}
	}

	@Override
	/**
	 * Creates a hashset and array list and then proceeds to run over the piped input and only add lines to array list 
	 * that are not contained in the hashset 
	 * @param the piped input
	 * @return the string of only unique lines 
	 */
	protected String processLine(String line) {
		HashSet<String> h = new HashSet<String>();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(line);
		String x = "";
		String s = "";
		ArrayList<String> isUniq = new ArrayList<String>();
		while(scanner.hasNextLine()) {
			x = scanner.nextLine();
			if(!(h.contains(x))) {
				isUniq.add(x);
				h.add(x);
			}
		}
		//Used to prevent a trailing blank line at the end of the string
		for(int i = 0; i < isUniq.size(); i++) {
			if(i == isUniq.size() - 1) {
				s = s + isUniq.get(i);
			} else {
				s = s + isUniq.get(i) + "\n";
			}
		}
		return s;
	}

}
