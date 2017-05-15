package bowling;

import org.junit.runners.model.FrameworkField;
import org.omg.CosNaming._BindingIteratorImplBase;

/**
 * @author Guillermo Moran
 * NOTE: You can "strip this skeleton for parts"
 * Don't assume that all of the preconditions are included
 * Be sure to check this code for correctness
 */
public class SinglePlayerBowlingScoreboardImpl_Moran implements SinglePlayerBowlingScoreboard, AssignmentMetaData
{
	private static final int MAXIMUM_ROLLS = 21;	//Maximum rolls in a one player game
	private String playerName;
	private int[] pinsKnockedDownArray = new int[MAXIMUM_ROLLS];
	private int rollCount = 0;

	public SinglePlayerBowlingScoreboardImpl_Moran(String playerName)
	{
		assert playerName != null : "playerName is null!";
		this.playerName = playerName;
		
		
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public int getCurrentFrame() 
	{
		
		int index = 0;
		int frame = 1;
		int boxIndex = 1;
		while (index < rollCount) {
			
			if (boxIndex == 2) {
				frame += 1;
				boxIndex -= 1;
			}
			
			else if (pinsKnockedDownArray[index] == 10) {
				frame += 1;
				
			}
			else {
				boxIndex += 1;
			}
			index++;
			
		}
		return frame;
		
	}

	public Mark getMark(int frameNumber, int boxIndex) 
	{	
		assert 1 <= frameNumber : "frameNumber = " + frameNumber + " < 1!";
		assert frameNumber <= 10 : "frameNumber = " + frameNumber + " > 10!";
		assert 1 <= boxIndex : "boxIndex = " + boxIndex + " < 1!";
		assert boxIndex <= 3 : "boxIndex = " + boxIndex + " > 3!";
		//		Exercise for student: Fix
		//		assert (boxIndex != 2) || (!isStrike(frameNumber) && !isSpare(frameNumber)) : "boxIndex = " + boxIndex + ", but there was a Strike! : frameNumber = " + frameNumber;
		//		assert (boxIndex != 2) || (!isSpare(frameNumber)) : "boxIndex = " + boxIndex + ", but there was a Spare! : frameNumber = " + frameNumber;
		Mark mark = null;
		if(frameNumber < 10) mark = getMarkSingleDigitFrameNumber(frameNumber, boxIndex);
		else mark = getMarkTenthFrame(boxIndex);
		assert mark != null : "mark is null!";
		return mark;
	}

	private Mark getMarkSingleDigitFrameNumber(int frameNumber, int boxIndex)
	{
		assert 1 <= frameNumber : "frameNumber = " + frameNumber + " < 1!";
		assert frameNumber <= 9 : "frameNumber = " + frameNumber + " > 9!";
		assert 1 <= boxIndex : "boxIndex = " + boxIndex + " < 1!";
		assert boxIndex <= 2 : "boxIndex = " + boxIndex + " > 2!";
		
		int firstMarkInFrame = getRollIndexOfFirstBall(frameNumber);
		int markIndexInFrameAtBoxIndex = firstMarkInFrame + (boxIndex - 1);
		
		boolean isEmptyMarkAtLocation = (markIndexInFrameAtBoxIndex >= rollCount);
		
		if (isStrike(frameNumber) && boxIndex == 1) {
			return Mark.EMPTY;
		}
		if (isStrike(frameNumber) && boxIndex == 2) {
			return Mark.STRIKE;
		}
		
		if (isSpare(frameNumber) && boxIndex == 2) {
			return Mark.SPARE;
		}
		
		if (isEmptyMarkAtLocation) {
			
			return Mark.EMPTY;
		}
		
		
		Mark markAtBoxIndexAtFrameNumber = Mark.translate(pinsKnockedDownArray[markIndexInFrameAtBoxIndex]);

		return markAtBoxIndexAtFrameNumber;
		
	}

	private Mark getMarkTenthFrame(int boxIndex)
	{
		assert 1 <= boxIndex : "boxIndex = " + boxIndex + " < 1!";
		assert boxIndex <= 3 : "boxIndex = " + boxIndex + " > 3!";
		
		int locationOfMarkInPinsArray = getRollIndexOfFirstBall(10) + (boxIndex - 1);

		boolean locationOfMarkInPinsArrayHasNoRoll = (locationOfMarkInPinsArray >= rollCount);
		if(locationOfMarkInPinsArrayHasNoRoll)
		{
			return Mark.EMPTY;
		}
		else
		{
			return Mark.translate(pinsKnockedDownArray[locationOfMarkInPinsArray]);
		}

		
	}

	public int getScore(int frameNumber) {
		assert 1 <= frameNumber : "frameNumber = " + frameNumber + " < 1!";
		assert frameNumber <= 10 : "frameNumber = " + frameNumber + " > 10!";
		
		//int totalScore = 0;
		//int lastRollIndex = rollCount;
		
		/*
		int totalScore = 0;

		for (int frame = 1; frame <= frameNumber; frame++)
		{
			int frameScore = 0;
			int ballCountInFrame = 0;
			int rollIndexOfFirstBall = getRollIndexOfFirstBall(frame);

			while (frameScore <= 10 && ballCountInFrame < 2)
			{
				
				frameScore += pinsKnockedDownArray[rollIndexOfFirstBall + ballCountInFrame];
				ballCountInFrame++;
				
			}
			totalScore += frameScore;
		}
		
		if (isStrike(frameNumber)) {
			totalScore = getSingleFrameScore(frameNumber + 1) + getSingleFrameScore(frameNumber + 2);
		}
			
		return totalScore;	
		*/
		
		int total = 0;
		int index = 0;
		int frame = 1;
		int boxIndex = 1;
		while (frame <= frameNumber) {
			
			System.out.println("frame: " + frame);
			
			if (boxIndex == 2 && !isSpare(frame)) {
				total += getSingleFrameScore(frame);
				frame += 1;
				boxIndex -= 1;
			}
			
			else if (isStrike(frame)) {
				total = total + 10 + getSingleFrameScore(frame + 1) + getSingleFrameScore(frame + 2);
				frame += 1;
				
			}
			
			else if (isSpare(frame)) {
				
				System.out.println("FRAMEE: + " + frame);
				total = total + 10 + getSingleFrameScore(frame + 1);
				frame += 1;
				
				boxIndex = 1;
				
			}
			else {
				boxIndex += 1;
			}
			index++;
			
		}
		return total;
		
		
	}
	
	private int getSingleFrameScore(int frameNumber) {
		if (isStrike(frameNumber) || isSpare(frameNumber)) {
			return 10;
		}
		else {
			int firstBallIndex = getRollIndexOfFirstBall(frameNumber);
			int secondBallIndex = firstBallIndex + 1;
			return pinsKnockedDownArray[firstBallIndex] + pinsKnockedDownArray[secondBallIndex];
		}
	}

	public boolean isGameOver() {
		return getCurrentFrame() == 11;
	}
	
	// UNCOMMENT ASSERTS
	
	public void recordRoll(int pinsKnockedDown) 
	{
		assert 0 <= pinsKnockedDown : "pinsKnockedDown = " + pinsKnockedDown + " < 0!";
		assert pinsKnockedDown <= 10 : "pinsKnockedDown = " + pinsKnockedDown + " > 10!";
		assert (getCurrentBall() == 1) || 
		((getCurrentBall() == 2) && (((getCurrentFrame() == 10) && isStrikeOrSpare(getMark(10, 1))) || ((pinsKnockedDownArray[rollCount - 1] + pinsKnockedDown) <= 10))) || 
		((getCurrentBall() == 3) && (((getCurrentFrame() == 10) && isStrikeOrSpare(getMark(10, 2))) || ((pinsKnockedDownArray[rollCount - 1] + pinsKnockedDown) <= 10)));
		assert !isGameOver() : "Game is over!";
		
		pinsKnockedDownArray[rollCount] = pinsKnockedDown;
		rollCount++;
	}

	public int getCurrentBall() {
		assert !isGameOver() : "Game is over!";

		//if (rollCount % 2 == 0) {
			//return 1;
		//}
		//else {
			//return 2;
		//}
		
		
		int currentFrame = getCurrentFrame();
		int firstBallIndexAtCurrentFrame = getRollIndexOfFirstBall(currentFrame);
		int currentBall = (rollCount - firstBallIndexAtCurrentFrame) + 1;
		
		if (currentBall == 0) {
			currentBall = 1;
		}
		
		return currentBall;
	}

	private static final String VERTICAL_SEPARATOR = "#";
	private static final String HORIZONTAL_SEPARATOR = "#";
	private static final String LEFT_EDGE_OF_SMALL_SQUARE = "[";
	private static final String RIGHT_EDGE_OF_SMALL_SQUARE = "]";
	private String getScoreboardDisplay()
	{
		StringBuffer frameNumberLineBuffer = new StringBuffer();
		StringBuffer markLineBuffer = new StringBuffer();
		StringBuffer horizontalRuleBuffer = new StringBuffer();
		StringBuffer scoreLineBuffer = new StringBuffer();
		frameNumberLineBuffer.append(VERTICAL_SEPARATOR);

		markLineBuffer.append(VERTICAL_SEPARATOR);
		horizontalRuleBuffer.append(VERTICAL_SEPARATOR);
		scoreLineBuffer.append(VERTICAL_SEPARATOR);

		for(int frameNumber = 1; frameNumber <= 9; frameNumber++)
		{
			frameNumberLineBuffer.append("  " + frameNumber + "  ");
			markLineBuffer.append(" ");
			markLineBuffer.append(getMark(frameNumber, 1));
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(getMark(frameNumber, 2));
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);

			final int CHARACTER_WIDTH_SCORE_AREA = 5;
			for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) horizontalRuleBuffer.append(HORIZONTAL_SEPARATOR);
			if(isGameOver() || frameNumber < getCurrentFrame())
			{
				int score = getScore(frameNumber);
				final int PADDING_NEEDED_BEHIND_SCORE = 1;
				final int PADDING_NEEDED_IN_FRONT_OF_SCORE = CHARACTER_WIDTH_SCORE_AREA - ("" + score).length() - PADDING_NEEDED_BEHIND_SCORE;
				for(int i = 0; i < PADDING_NEEDED_IN_FRONT_OF_SCORE; i++) scoreLineBuffer.append(" ");
				scoreLineBuffer.append(score);
				for(int i = 0; i < PADDING_NEEDED_BEHIND_SCORE; i++) scoreLineBuffer.append(" ");
			}
			else
			{
				for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) scoreLineBuffer.append(" ");
			}

			frameNumberLineBuffer.append(VERTICAL_SEPARATOR);
			markLineBuffer.append(VERTICAL_SEPARATOR);
			horizontalRuleBuffer.append(VERTICAL_SEPARATOR);
			scoreLineBuffer.append(VERTICAL_SEPARATOR);
		}
		//Frame 10:
		{
			final String THREE_SPACES = "   ";
			frameNumberLineBuffer.append(THREE_SPACES + 10 + THREE_SPACES);

			markLineBuffer.append(" ");
			markLineBuffer.append(getMark(10, 1));
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(getMark(10, 2));
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(getMark(10, 3));
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);

			final int CHARACTER_WIDTH_SCORE_AREA = 8;
			for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) horizontalRuleBuffer.append(HORIZONTAL_SEPARATOR);
			if(isGameOver())
			{
				int score = getScore(10);
				final int PADDING_NEEDED_BEHIND_SCORE = 1;
				final int PADDING_NEEDED_IN_FRONT_OF_SCORE = CHARACTER_WIDTH_SCORE_AREA - ("" + score).length() - PADDING_NEEDED_BEHIND_SCORE;
				for(int i = 0; i < PADDING_NEEDED_IN_FRONT_OF_SCORE; i++) scoreLineBuffer.append(" ");
				scoreLineBuffer.append(score);
				for(int i = 0; i < PADDING_NEEDED_BEHIND_SCORE; i++) scoreLineBuffer.append(" ");
			}
			else
			{
				for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) scoreLineBuffer.append(" ");
			}

			frameNumberLineBuffer.append(VERTICAL_SEPARATOR);
			markLineBuffer.append(VERTICAL_SEPARATOR);
			horizontalRuleBuffer.append(VERTICAL_SEPARATOR);
			scoreLineBuffer.append(VERTICAL_SEPARATOR);
		}

		return 	getPlayerName() + "\n" +
		horizontalRuleBuffer.toString() + "\n" +
		frameNumberLineBuffer.toString() + "\n" +
		horizontalRuleBuffer.toString() + "\n" +
		markLineBuffer.toString() + "\n" +
		scoreLineBuffer.toString() + "\n" +
		horizontalRuleBuffer.toString();
	}

	public String toString()
	{
		return getScoreboardDisplay();
	}

	private int getRollIndexOfFirstBall(int frameNumber)
	{
		int index = 0;
		int frame = 1;
		int boxIndex = 1;
		while (frame < frameNumber) {
			
			if (boxIndex == 2) {
				frame += 1;
				boxIndex -= 1;
			}
			
			else if (pinsKnockedDownArray[index] == 10) {
				frame += 1;
				
			}
			else {
				boxIndex += 1;
			}
			index++;
			
		}
		return index;
		//return (2 * (frameNumber - 1));
	}

	private boolean isStrike(int frameNumber)
	{
		int firstBallRollIndex = getRollIndexOfFirstBall(frameNumber);
		int pinCountAtRollIndex = pinsKnockedDownArray[firstBallRollIndex];
		
		return pinCountAtRollIndex == 10;
	}

	private boolean isSpare(int frameNumber)
	{
		int firstBallRollIndex = getRollIndexOfFirstBall(frameNumber);
		int secondBallRollIndex = firstBallRollIndex + 1;
		//System.out.println(pinsKnockedDownArray[firstBallRollIndex] + pinsKnockedDownArray[secondBallRollIndex]);
		return pinsKnockedDownArray[firstBallRollIndex] + pinsKnockedDownArray[secondBallRollIndex] == 10;
	}

	private boolean isStrikeOrSpare(Mark mark)
	{
		return ((mark == Mark.STRIKE) || (mark == Mark.SPARE));
	}

	//*************************************************
	//*************************************************
	//*************************************************
	//*********ASSIGNMENT METADATA STUFF***************
	public String getFirstNameOfSubmitter() 
	{
		return "Guillermo";
	}

	public String getLastNameOfSubmitter() 
	{
		return "Moran";
	}

	public double getHoursSpentWorkingOnThisAssignment()
	{
		return 0.0;
	}

	public int getScoreAgainstTestCasesSubset()
	{
		return 100;
	}
}