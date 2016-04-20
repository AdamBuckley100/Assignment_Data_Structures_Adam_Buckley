import java.util.HashMap;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

/** This class is the class that writes out the word in huffman code form to
 * the file in the resources folder entitle "wordInHuffmanCodingFormat".
 */
public class FileWrite
{
	/**
	 * This method is what writes to the file "wordInHuffmanCodingFormat", the result for the program user.
	 * 
	 * @param: map - A hashmap is taken in (i'll be taking in single letter to huffman code mapping)
	 * @param code
	 * @throws IOException
	 */
	public void writeToHuffmanFile(HashMap<Character, String> hashMapIUseForDecomWordSequence, String huffmanVersionOfWord) throws IOException
	{
		File file = new File("resources/wordInHuffmanCodingFormat");

		file.createNewFile();
		FileWriter fileWrite = new FileWriter(file); 

		// (4 lines below): o.k go through every key in the hashmap passed in (going to be the single letters)
		// and create a variable that will have be assigned thw following: the key and then a ¬ delimiter then
		// value of that key THEN a final delimiter "@" character.
		for(Character eachSingleChar: hashMapIUseForDecomWordSequence.keySet() )
		{
			String combinedLetterMappedToItsInstance = eachSingleChar + "¬" + hashMapIUseForDecomWordSequence.get(eachSingleChar) + "@";
			fileWrite.write(combinedLetterMappedToItsInstance);
			// The flush method flushes the output stream and forces any buffered output bytes to be written out, which I want.
			fileWrite.flush();
		}
		
		// (line below) I place a final delimiter before I write the actual huffman code itself in it's totality.
		fileWrite.write('~');

		// Once again, flush() writes the content of the buffer to the destination
		// and makes the buffer empty for further data to store.
		fileWrite.flush();

		// Huffman form of the word is written (binary)
		fileWrite.write(huffmanVersionOfWord); 

		// Once again, The flush method flushes the output stream and forces any
		// buffered output bytes to be written out, which I want.
		fileWrite.flush();

		// need to close: the close() method flushes the data 
		// and indicates that there isn't any more data (I flushed right before anyway).
		fileWrite.close();
	}
}