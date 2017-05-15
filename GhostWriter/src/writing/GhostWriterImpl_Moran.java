package writing;

import java.util.ArrayList;
import java.util.List;

public class GhostWriterImpl_Moran implements GhostWriter
{	
	
	private String input;
	
	public GhostWriterImpl_Moran(String inputText)
	{
		this.input = inputText;
	}
	
	public String getInputText()
	{
		return this.input;
	}
	
	public String generate(String seed, List<Integer> selectionList)
	{
		assert seed != null : "seed is null!";
		assert seed.length() <= 10 : "seed.length() = " + seed.length() + " > 10!";
		assert selectionList != null : "selectionList is null!";
		assert getInputText().indexOf(seed) != -1 : "getInputText().indexOf(" + seed + ") is -1!";

		//selection list is a list of randomly generated integers
		//length of selection list is length of the output 
		
		
		int expectedLength = selectionList.size();
		
		int outputLevel  = seed.length(); //is this right lmao
		
		String generatedText = seed;
		
		int currentLength = seed.length(); //Start at the seed length 
		
		String currentSeed = seed;

		
		List<Character> suffixList = new ArrayList<Character>();	
		
		while (currentLength < expectedLength) {
			
			//Build the array
			String currentInput = this.input;
			
			int indexOfFirstCharacterOfMatch = getStartIndexOfFirstSubstringOccurence(currentInput, currentSeed);
			
			while (indexOfFirstCharacterOfMatch != -1) {
						
				//System.out.println(currentSeed);
				//System.out.println(currentInput);
				
				int indexOfLastCharacterOfMatch = indexOfFirstCharacterOfMatch + outputLevel;
				int indexOfCharacterAfterMatch = indexOfLastCharacterOfMatch;
				
				suffixList.add(currentInput.charAt(indexOfCharacterAfterMatch));
				suffixList.sort(null);
				currentInput = currentInput.substring(indexOfLastCharacterOfMatch);
				//System.out.println(currentInput);
				
				
				//System.out.println(suffixList);
				
				indexOfFirstCharacterOfMatch = getStartIndexOfFirstSubstringOccurence(currentInput, currentSeed);

			}
			
			int randomNumber = selectionList.get(currentLength);
			randomNumber = randomNumber % suffixList.size();
			char newChar = suffixList.get(randomNumber);
			
			generatedText += newChar;
			
			currentLength++;
			
			String newUnformattedSeed = currentSeed += newChar;
			
			String newSeed = getNewSeed(newUnformattedSeed, outputLevel);
			currentSeed = newSeed;
			
			currentInput = this.input;
			suffixList.clear();
			
		}
		return generatedText;
	}
	
	
	private int getStartIndexOfFirstSubstringOccurence(String string, String target) {
		
		return string.indexOf(target);
		
		
	}
	
	private String getNewSeed(String unformattedSeed, int level) {
		return unformattedSeed.substring(unformattedSeed.length() - level);
	}


	@Override
	public String getFirstNameOfSubmitter() {
		return "Guillermo";
	}

	@Override
	public String getLastNameOfSubmitter() {
		return "Moran";
	}
	
	@Override
	public double getHoursSpentWorkingOnThisAssignment() {
		return 0;
	}

	@Override
	public int getScoreAgainstTestCasesSubset() {
		return 193;
	}
}