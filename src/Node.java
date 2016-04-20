/**
 * NOTE: I borrowed this class from The person below and made alterations
 * for it to better suit my program's implementation.
 * 
 * reads bits-at-a-time where the number of bits is between 1 and 32.
 * Updated for version 2.0 to extend java.io.InputStream
 * 
 * <P>
 * @author Owen Astrachan
 * @version 1.0, July 2000
 * @version 2.0, October 2004 
 */
public class Node implements Comparable<Node>

{
	String theSpecificLetter; // the actually String variable which that node holds, (can be a letter OR a phrase of letters).
	Node leftChild; // the tree's left child node (down left..)
	Node rightChild; // the tree's right child node (down right..)
	String instancesOfThatLetter; // i.e. the number of times that letter OR phrase of letters is in that word. I made it String instead
	// int to keep is scalable (the word/sentence chosen to be compressed could be very large)

	/**
	 * The constructor method for the single tree nodes.
	 * 
	 * @param: theSpecificLetter - Pretty self explanatory.. The letter or phrase of letters string variable the node will have.
	 * @param: leftChild - the binary tree node's left node.
	 * @param: rightChild - the binary tree node's right node.
	 * @param: instanceOfThatLetter - number of times that letter or phrase of letters in in the word.
	 */
	public Node(String theSpecificLetter, Node leftChild, Node rightChild, String instanceOfThatLetter)
	{
		this.theSpecificLetter = theSpecificLetter;
		this.instancesOfThatLetter = instanceOfThatLetter;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	/**
	 * The getter needed to get the left node of the node
	 * we are currently concerned with.
	 * 
	 * @return: - Returns the actual binary tree node OBJECT itself to the person who calls this method.
	 */
	public Node getLeftChild()
	{
		return leftChild;
	}

	/**
	 * This method is a getter, it simply "gets" the right 
	 * node OF the node we are currently dealing with so to speak.
	 * 
	 * @return: - Returns the actual binary tree node OBJECT itself to whoever calls this method.
	 */
	public Node getRightChild()
	{
		return rightChild;
	}

	/**
	 * In this method, I'm comparing "THIS" node to the node that was passed in
	 * to the parameter by the person who called this method. This method must be called
	 * ON an object of type Binary Tree Node AND have in it's parameter a node object
	 * as two objects of different types cannot be compared.
	 * 
	 * @param: - The node that is not "THIS" node, it is the other node, i'm calling this parameter
	 * "aTreeNodeObject" and it's a binary tree node object.
	 * 
	 * @return: - What is returned is an integer variable. 1, 0 or -1 is returned depending on whether the
	 * two node objects are the same alphabetically (see String global variable) or is one node's string
	 * alphabetically lower than the other and vice versa.
	 */
	public int compareTo(Node aTreeNodeObject)
	{
		if (Integer.valueOf((this.instancesOfThatLetter)) > Integer.valueOf(aTreeNodeObject.instancesOfThatLetter))
		{
			return 1;
		}
		else if (this.instancesOfThatLetter == aTreeNodeObject.instancesOfThatLetter)
		{
			return 0;
		}
		else // [if(this.instancesOfThatLetter < aTreeNodeObject.instancesOfThatLetter)]
		{
			return -1;
		}
	}

	/**
	 * This getter method is getting the number of times the letter String which THIS node has
	 * as a global variable is actually in the word typed into "wordInEnglishFormat".
	 * 
	 * @return: - An invariable is returned stating the number of times the letter or phrase of letters
	 * is in the word.
	 */
	public String getInstancesOfThatLetter()
	{
		return instancesOfThatLetter;
	}

	/**
	 * This getters gets the String variable of THIS node (it is a single letter or several consecutive letters).
	 * 
	 * @return: - A string variable is returned, what is returned is the actually letter String variable which
	 * this node has as it's global variable.
	 */
	public String getTheSpecificLetter()
	{
		return theSpecificLetter;
	}
}