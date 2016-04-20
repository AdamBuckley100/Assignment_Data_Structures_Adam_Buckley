import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Map;
import java.util.PriorityQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import java.util.HashMap;

/**
 * This is the class which you would click run for therefore
 * I have called it the ExecuteAndGui class. This class contains the main
 * method of the program.
 * 
 * A Simplistic Read-only GUI is included. in this class.
 * 
 * Module: Data Structures.
 * References: Moodle Data Structures notes.
 * 
 * @author Adam Buckley
 * @Date - Start Date: 19/03/2016
 * @Date - End Date: 20/04/2016
 */

public class ExecutionAndGui
{
	// singleLettersMappedToTheirHuffCode - This maps every letter in the word to it's huffman code which
	// I derived by traversing the tree.
	static HashMap<Character, String> singleLettersMappedToTheirHuffCode = new HashMap<>();

	static PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>();
	// (below) I am using sting for num of times the letter is in the word BECAUSE the phrase chosen could be
	// so large that int's won't suffice. I am using the Integer.valueOf() method at all points necessary though.
	static Map<Character, String> letterMappedToInstances = new HashMap<>();

	private static JFrame frame;
	private static JTextArea area;
	static CompresserAndDecompresser compressAndDecompress = new CompresserAndDecompresser();

	/**
	 * Create a graphical user interface for the 
	 * compression/decompression program.
	 */
	public static void startGUI()
	{
		makeFrame();
		frame.setVisible(true);
		//frame.setEditable(false);

		// Below: Close the application when X is hit in the top right corner.
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/**
	 * Make this interface visible again. (Has no effect if it is already
	 * visible.)
	 */
	public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}

	/**
	 * Make the frame for the user interface.
	 */
	private static void makeFrame()
	{
		frame = new JFrame("Showcasing Huffman code compression, then decompression");

		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setLayout(new BorderLayout(38, 38));
		contentPane.setBorder(new EmptyBorder( 40, 40, 40, 40));

		area = new JTextArea(40,60);
		// (below) Make it so that the program user cannot edit the text.
		area.setEditable(false);
		contentPane.add(area, BorderLayout.CENTER);

		frame.pack();
	}

	/**
	 * Using this method to add text onto a running total of text that will
	 * be outputted on my GUI frame.
	 * 
	 * @param: wordToAppend - A variable of type String is passed into this method,
	 * the string is appended onto the current total of string variables which the 
	 * "area" global variable String currently holds.
	 */
	public static void appendTo(String wordToAppend)
	{
		// (below) if the the area global variable is currently empty just add the wordToAppend, if not just add
		// the word to append onto what the area variable already holds.
		if (area.getText() == "")
		{
			area.setText(wordToAppend);
		}
		else
		{
			area.setText(area.getText() + wordToAppend);
		}
	}

	/**
	 * My main method, this method is where everything is run from start to finish in my program.
	 * 
	 * @param args (arguments).
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception
	{
		// call the method to pop up the GUI.
		startGUI();

		constructTree();

		// poll form the queue which takes the smallest node out... (node with smallest frequency associate with it)
		Node root = nodePriorityQueue.poll();

		appendTo("\n\n The following is how the tree is traversing through the binary tree to get the huffman code for each letter:\n\n");
		// Calling the pre-order traversal of the tree method...  passing in an empty String and the root which I polled just previously.
		traverseTheTree(root, "");

		// Append to J Text area's output String all the letters in the singleLettersMappedToTheirHuffCode hash
		// map and the corresponding huffman coding binary code.
		for (Character key : singleLettersMappedToTheirHuffCode.keySet())
		{
			appendTo("\n" + (String.valueOf(key)) + "..paired to.." + (String.valueOf(singleLettersMappedToTheirHuffCode.get(key))));
		}

		appendTo("\n\n The word has now been compressed to: ");

		String theTextInEnglishForm = compressAndDecompress.getTheTextInEnglishForm();

		// declare and initialise a string variable called theHuffmanCodeOfTheWord and assign it an 
		// empty String, it will hold the word in the correct sequence except it'll be in huffman coding format.
		String theHuffmanCodeOfTheWord = "";

		// Traverse through the String variable called theTextInEnglishForm and extract the character at each
		// location, now append the huffman code which that letter is mapped to in
		// the hashmap entitled singleLettersMappedToTheirHuffCode to the String variable "theHuffmanCodeOfTheWord".
		for(int i = 0; i < theTextInEnglishForm.length(); i++)
		{
			Character c = theTextInEnglishForm.charAt(i);
			theHuffmanCodeOfTheWord += singleLettersMappedToTheirHuffCode.get(c);
		}

		appendTo(theHuffmanCodeOfTheWord + "\n");

		// Call the method in my FileWrite class which writes the String I pass in to the "wordInHuffmanCodingFormat"
		// file in the resources folder.
		new FileWrite().writeToHuffmanFile(singleLettersMappedToTheirHuffCode, theHuffmanCodeOfTheWord);
		
		// Call the decompression method in on the compressAndDecompress object from the
		// CompressAndDecompress Class.
		compressAndDecompress.decompression();
	}

	/**
	 * This method constructs the binary tree for me and populates my priority queue,
	 * this method is called from main.
	 * 
	 * @throws IOException
	 */
	public static void constructTree() throws IOException
	{		
		// Assign the letterMappedToInstances global variable (which is a hashmap) the hashmap that
		// is returned when the compression method is called
		letterMappedToInstances = compressAndDecompress.compression();

		for ( Character key:letterMappedToInstances.keySet() )
		{
			// get string representation of the letter.... (changes from Character to String)
			String theLetterItselfInStringForm = String.valueOf(key);

			// .get(key) returns the VALUE side of the hash map so the no. of times that letter (above)
			// is in the english word is returned.
			String numOfInstances = letterMappedToInstances.get(key);

			Node node = new Node(theLetterItselfInStringForm, null, null, String.valueOf(numOfInstances)); // gets no children: right child or left
			// child because I know that it is a leaf node because it's in the populated hashmap of letter to instances.

			// add the binary tree node to the queue.
			nodePriorityQueue.add(node);
		}

		// while queue is not only the root on it's own..
		while (nodePriorityQueue.size() > 1)
		{
			// first node "polled" is the smallest.. (based on instances of the letter/letter phrase)
			Node smallestNode = nodePriorityQueue.poll();
			// and of course when I poll again i'm getting the smallest one AFTER the first smallest one was polled out.
			Node smallestNodeAfter1stTaken = nodePriorityQueue.poll();

			// add the letter phrase to the other letter phrase ie. add "io" to "klo" (concatenate).
			String letterPhrasesAdded = smallestNode.getTheSpecificLetter() + smallestNodeAfter1stTaken.getTheSpecificLetter();

			// The newly created node is going to need its instance value to be the two smallest added together.
			int NewInstanceValueForNewNode = (Integer.valueOf(smallestNode.instancesOfThatLetter)) + (Integer.valueOf(smallestNodeAfter1stTaken.instancesOfThatLetter));

			// The new bigger node is declared.
			Node newDoubleNode;

			// Now organize which child is which:.. first determine which child goes where..
			// (line below) asking if the node first polled (smallest) is in the word MORE times than the node polled after first one
			// and if it is then add the first polled node as left child and the second polled node as right child
			if (smallestNode.compareTo(smallestNodeAfter1stTaken) == 1)
			{
				// The smallest node (after 1st smallest was taken) which is "smallestNodeAfter1stTaken" must be the left child...
				newDoubleNode = new Node(letterPhrasesAdded, smallestNodeAfter1stTaken, smallestNode, String.valueOf(NewInstanceValueForNewNode));
			}
			else // if (n1.compareTo(n2) != 1)
			{
				// The smallest node (node polled first) which is "smallestNode" must be the left child...
				newDoubleNode = new Node(letterPhrasesAdded, smallestNode, smallestNodeAfter1stTaken, String.valueOf(NewInstanceValueForNewNode));
			}
			nodePriorityQueue.add(newDoubleNode);
		}
	}

	/**
	 * This method traverses down a tree, you pass in a node (usually root) and it'll identify the huffman code for each
	 * single character in the word I choose to pass into the program via my "wordInEnglishFormat" .txt file.
	 * (pre-order traversal).
	 * 
	 * @param currentNode - the node which you pass into the method - which is usually root
	 * @param RunningTotalTraverseHuffman - (because of recursion) this is the current amount of traversals (left or right)
	 * done so far to reach a certain letter (1 is right... 0 is left)
	 */
	public static void traverseTheTree(Node currentNode, String RunningTotalTraverseHuffman)
	{
		if (currentNode.getTheSpecificLetter().length() == 1)
		{
			appendTo("\n" + String.valueOf(currentNode.getTheSpecificLetter()) + " is the leaf node that was discovered through traversal \n");;
			singleLettersMappedToTheirHuffCode.put(currentNode.getTheSpecificLetter().charAt(0), RunningTotalTraverseHuffman);
		}
		else // if ((currentNode.getTheSpecificLetter().length() > 1)..... If its a non-lead node we will recursive this method....
		{
			appendTo("\n" + "Tree with root of " + (String.valueOf(currentNode.getTheSpecificLetter())) + " is passed into be traversed and ");

			Node leftChild = currentNode.getLeftChild();
			Node rightChild = currentNode.getRightChild();

			// if this node, which we already know is non leaf has A left child add 0 to the running total (WHY?
			// because left down in huffman coding is 0.
			if (leftChild != null)
			{
				RunningTotalTraverseHuffman += "0";

				// (line below) call the method again but this time pass in the running total and the left child 
				traverseTheTree(leftChild, RunningTotalTraverseHuffman);

				RunningTotalTraverseHuffman = RunningTotalTraverseHuffman.substring(0, RunningTotalTraverseHuffman.length() - 1);
			}

			// a non-leaf node will only reach this far in the methid IF it has a right child and NO left child.
			// Add 1 to the running total (WHY? because right down in huffman is 1.
			if (rightChild != null)
			{
				RunningTotalTraverseHuffman += "1";
				// (line below) call the method again but this time pass in the running total and the right child
				traverseTheTree(rightChild, RunningTotalTraverseHuffman);
				RunningTotalTraverseHuffman = RunningTotalTraverseHuffman.substring(0, RunningTotalTraverseHuffman.length() - 1);
			}
		}
	}
}