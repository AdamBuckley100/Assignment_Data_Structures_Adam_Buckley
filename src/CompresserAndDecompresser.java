import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * This is the class that does:
 * 1) The main compression actions
 * 2) The main decompression actions
 * 
 * This class has Two methods of important:
 * 1) The compression method
 * 2) The decompression method
 * 
 * Note: this class does use some methods from my ExecutionAndGui class.
 * 
 * Module: Data Structures.
 * References: Moodle Data Structures notes.
 * 
 * @author Adam Buckley
 * @Date - Start Date: 19/03/2016
 * @Date - End Date: 20/04/2016
 */

public class CompresserAndDecompresser
{
	ExecutionAndGui execute;
	private String theTextInEnglishForm = "";

	/**
	 * This method is the one that carries out the compressing of the word .. this method returns the hashmap of single
	 * letters being mapped to the number of times that letter is in that word.
	 * 
	 * @return: HashMap - returns a hashmap which maps single letters the number of times that letter is in the word.
	 * @throws IOException
	 */
	public HashMap<Character, String> compression() throws IOException
	{
		HashMap<Character, String> mappingLetterToNumsOfLetterInWord = null;

		try {
			mappingLetterToNumsOfLetterInWord = new HashMap<Character, String>();

			File file = new File("resources/wordInEnglishFormat");

			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String textLine;

			// while there is still more characters in this line to be read and processed......
			while ((textLine = bufferedReader.readLine()) != null)
			{
				// add the 
				theTextInEnglishForm = theTextInEnglishForm + textLine;
				ExecutionAndGui.appendTo("The Word put in the file is: " + theTextInEnglishForm + "\n");
				for (int i = 0 ; i < textLine.length() ; i++)
				{
					// Derive the string version of the letter.
					Character charVersion = textLine.charAt(i);

					if (mappingLetterToNumsOfLetterInWord.containsKey(charVersion))
					{
						// Below line assigns to num the number which that was assigned
						// that letter previously.
						String numInString = mappingLetterToNumsOfLetterInWord.get(charVersion);
						// now plus that number by 1 and add it back to the wordMap as was
						// but with num incremented by 1.

						Integer changeToIntForASec = Integer.valueOf(numInString);
						changeToIntForASec++;

						mappingLetterToNumsOfLetterInWord.put(charVersion, String.valueOf(changeToIntForASec));
					}
					else
					{
						// This happens if the letter wasn't in the Map to begin with,
						// so put the new letter into the map and assign 1 to it's integer mapping.
						mappingLetterToNumsOfLetterInWord.put(charVersion,"1");
					}
				}
			}
			fileReader.close();
		}
		// IO Exception catch.. typical catch.
		catch (IOException e)
		{
			e.printStackTrace();
		}

		// Simply append to GUI text area every letter in the mappingLetterToNumsOfLetterInWord hashmap along with
		// it's corresponding weight (number of times that letter is in the word).
		for (Character theSingleLetter : mappingLetterToNumsOfLetterInWord.keySet())
		{
			ExecutionAndGui.appendTo("\n" + (String.valueOf(theSingleLetter)) + " is in the word: " + (String.valueOf(mappingLetterToNumsOfLetterInWord.get(theSingleLetter))) + " times.");
		}
		// return that mappingLetterToNumsOfLetterInWord hashmap.
		return mappingLetterToNumsOfLetterInWord;
	}

	/**
	 * This method is the one that carries out the decompressing of the word that has been turned to huffman code binary
	 * form by the compression method.
	 * 
	 * @return: - returns a String which is, the word decompressed to english form from binary form (huffman code binary form)
	 * @throws IOException
	 */
	public String decompression() throws IOException
	{
		// Write to gui text area that starting of decompression is happening...
		ExecutionAndGui.appendTo("\n..Lets start the decompression phase: \n");

		// The variable which holds the word in english format is set an empty String.
		String theEnglishWordDecompressedFromHuffman = "";

		// Initialize file, which is a variable of type File and pass into it the
		// location of the file of where I want the huffmanCode put..... "resources/wordInHuffmanCodingFormat".
		File file = new File("resources/wordInHuffmanCodingFormat");

		// Initialize fileReader which is a variable of type FileReader and pass into it the file.
		FileReader fileReader = new FileReader(file);

		//Initialize buffRead which is a variable of type BufferedReader and pass into it the variable fileReader.
		BufferedReader buffRead = new BufferedReader(fileReader);

		// (line below) A line is read using the BufferedReader and the line read is placed into the
		// String variable entitled theLineWhichWeAreReading.
		String theLineWhichWeAreReading = buffRead.readLine();

		// (below) Create a hash map which maps String variables to String variables and this map is going to hold 
		// single letters mapped to the number of times that letter is in the word.
		HashMap<String, String> decomMapLetterToHuff = new HashMap<>();

		String theWordInHuffmanFormat = "";

		// while there is still more characters in this line to be read and processed......
		while (theLineWhichWeAreReading != null)
		{
			// (3 lines below) i am simply splitting the hashmap and the actual
			// huffman code of the word. The String array will have 2 different variables
			// variable 1 is the LetterToHuffmanCode hashmap and variable 2 is the actual word in huffman code form.
			// (splitting with a ~).
			String[] hashMapThenHuffmanCode = theLineWhichWeAreReading.split("~");
			String letterFollowedByItsHuffCode = hashMapThenHuffmanCode[0];

			// running total needed.
			theWordInHuffmanFormat = theWordInHuffmanFormat + hashMapThenHuffmanCode[1];

			// (line below): I am spliting up each entry in the letterFollowedByItsHuffCode hash map
			// from the next entry..
			// .. and I am splitting it with a @.
			String[] letterAndItsHuffCode = letterFollowedByItsHuffCode.split("@");

			// Now, I am spliting up each SINGLE key entry in the letterFollowedByItsHuffCode hash map
			// from it's corresponding huffman code entry.
			// .. and I am splitting it with a ¬.
			for (String singleLetter : letterAndItsHuffCode)
			{
				// Spliting up the letter from it's instance... It's splited with a ¬
				// (remember this all words because the write class put these splitting characters in
				// in compression method).
				String[] aLetterThenItsInstance = singleLetter.split("¬"); 
				
				// the instance of the letter.. (which is the value of the letterAnditsHuffCode hashmap.
				String theInstance = aLetterThenItsInstance[0];
				
				// The Letter itself (which is the key of the hashmap entitled letterAnditsHuffCode.
				String theLetter = aLetterThenItsInstance[1];
				
				// Finally.. append to my J Text area ...
				ExecutionAndGui.appendTo(("\n" + (String.valueOf(theLetter))) + " ..paired with.. " + (String.valueOf(theInstance)));
				
				// now populate the decomMapLetterToHuff hashmap (maps single letters to the no. of times that letter
				// is in the word).
				decomMapLetterToHuff.put(theLetter, theInstance);
			}
			
			// o.k now keep reading.. time to work with the huffman code in right sequence..
			theLineWhichWeAreReading = buffRead.readLine();
			
		}

		// declare and initialise a variable which is A String variable which is to hold the word in english form
		// after it's decompressed from huffman to english... right now just assigned it an empty String.
		String theWordDecompressedToEnglish = "";

		// Now look at what was read in, in the line... now looking at the 2nd
		// half of the line that was read in from "wordInHuffmanCodingFormat"
		// which is the word in huffman code form.. (in right sequence)
		// While theres more than 0 characters to read in....
		while (theWordInHuffmanFormat.length() > 0)
		{
			// Declare and initialize A String called OneOrSeveralHuffmanCodeBits, this will
			// hold one or several huffman code bits and what it holds will be checked to see if its in the
			// hashmap and if it it isn't... add another huffman bit onto it, keep the running total and keep iterating.
			String OneOrSeveralHuffmanCodeBits = "";

			// This String variable is declared and initialized as 0, it will hold String reprensentation of how many
			// bit characters IN we are in the huffman code part of whats in "wordInHuffmanCodingFormat" file.
			String upToWhatBitWeAreLookingAt = "0";

			// while the map which maps single letters to their huffman code does NOT contain the key we are currently
			// at in the iteration then go inside the while loop...
			while (!decomMapLetterToHuff.containsKey(OneOrSeveralHuffmanCodeBits)) 
			{
				// (below) we should never get in that position but just inscase...
				if (Integer.valueOf(upToWhatBitWeAreLookingAt) > theWordInHuffmanFormat.length())
				{
				break;
				}

				// Convert the *special Bit we're looking up into* into an int so i can increment it...
				int upToWhatBitWeAreLookingAtInIntSoICanIncrement = Integer.valueOf(upToWhatBitWeAreLookingAt);
				upToWhatBitWeAreLookingAtInIntSoICanIncrement++;

				// turn it back to a String
				upToWhatBitWeAreLookingAt = String.valueOf(upToWhatBitWeAreLookingAtInIntSoICanIncrement);

				// OneOrSeveralHuffmanCodeBits variable is assigned huffman code bits,
				// the bits which it is assigned is a certain number of bits IN. (from 0 to the number of bit IN we are of the
				// huffman code itself in correct sequence).
				OneOrSeveralHuffmanCodeBits = theWordInHuffmanFormat.substring(0, Integer.valueOf(upToWhatBitWeAreLookingAt));
			}

			// (below) running total has been added to (huffman code bits have been added to it)
			theWordDecompressedToEnglish += decomMapLetterToHuff.get(OneOrSeveralHuffmanCodeBits);

			// (below) what is happening is I am now assigning theWordInHuffmanFormat (which the huffman code portion we are traversing through
			// looking for a match for a huffman code in the hashmap) to only the huffman code part that I have found no matches for in 
			// the hashmap. (repeating won't happen).
			theWordInHuffmanFormat = theWordInHuffmanFormat.substring(Integer.valueOf(upToWhatBitWeAreLookingAt), theWordInHuffmanFormat.length());
		}
		ExecutionAndGui.appendTo("\n\n" + "The word Decompressed is ...: " + (String.valueOf(theWordDecompressedToEnglish)));

		// closing out the buffer reader as I am finished with it.
		buffRead.close();

		// english word right sequence acquired.. ready to be returned...
		return theEnglishWordDecompressedFromHuffman;
	}

	/**
	 * A getter than returns the text in english format, I am
	 * calling this method from the ExecutionAndGui class.
	 * (A getter is needed because theTextInEnglishForm is a
	 * private global variable.
	 * 
	 * @return: String - returns a variable of type String,
	 * what is returned is the Text I want compressed, in normal
	 * english form.
	 */
	public String getTheTextInEnglishForm()
	{
		return theTextInEnglishForm;
	}
}