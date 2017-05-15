package test;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.Before;
import org.junit.Test;

import bowling.AssignmentMetaData;
import bowling.Mark;
import bowling.SinglePlayerBowlingScoreboard;
//import bowling.SinglePlayerBowlingScoreboardImpl_Kart;
import bowling.SinglePlayerBowlingScoreboardImpl_Moran;

/**
 * @author kart
 * 
 *
 */
public class SinglePlayerBowlingScoreboard_NoSparesStrikes_NoTenth_TestCasesSubsetForStudents {
	private static SinglePlayerBowlingScoreboard singlePlayerBowlingScoreboard_STUDENT;
	private static AssignmentMetaData assignmentMetaData_STUDENT;
	private static final String PLAYER_NAME = "Big Lebowski";
	
	@Retention(value=RUNTIME)
	@Target(value=METHOD)
	public @interface Points {
		int value();
	}
	
	@Before
	public void initBeforeEachTestMethod()
	{
		singlePlayerBowlingScoreboard_STUDENT = new SinglePlayerBowlingScoreboardImpl_Moran(PLAYER_NAME);
		assignmentMetaData_STUDENT = (AssignmentMetaData)singlePlayerBowlingScoreboard_STUDENT;
	}
	
	@Points(value=0)
	@Test(expected=AssertionError.class)
	public void assertionsEnabledTest()
	{
		assert false;
		throw new RuntimeException("ENABLE ASSERTIONS IN RUN CONFIGURATIONS!");
	}
	
	@Points(value=0)
	@Test
	public void hoursWorkedAndTestCasesScoreTest()
	{
		System.out.println("Hours Worked: " + assignmentMetaData_STUDENT.getHoursSpentWorkingOnThisAssignment());
		System.out.println("Score on TestCases_Subset: " + assignmentMetaData_STUDENT.getScoreAgainstTestCasesSubset());
	}
	
	@Points(value=5)
	@Test
	public void playerNameTest() {
		assertEquals(PLAYER_NAME, singlePlayerBowlingScoreboard_STUDENT.getPlayerName());
		System.out.println("playerNameTest:\n" + singlePlayerBowlingScoreboard_STUDENT);
	}
	
	@Points(value=5)
	@Test(expected=AssertionError.class)
	public void tooManyPinsTest() {
		int[] pinsKnockedDownArray = new int[]{19};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);
	}
	
	@Points(value=5)
	@Test(expected=AssertionError.class)
	public void tooManyPinsTest2() {
		int[] pinsKnockedDownArray = new int[]{7, 7};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);
	}
	
	@Points(value=5)
	@Test
	public void noRollsCurrentFrameCurrentBallTest() {
		int[] pinsKnockedDownArray = new int[]{};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);		
		assertEquals(1, singlePlayerBowlingScoreboard_STUDENT.getCurrentFrame());
		assertEquals(1, singlePlayerBowlingScoreboard_STUDENT.getCurrentBall());
	}
	
	@Points(value=5)
	@Test
	public void twoGutterBallMarkTest_OnlyNineFrames()
	{
		int[] pinsKnockedDownArray = new int[]{0, 0};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);
		assertEquals(Mark.ZERO, singlePlayerBowlingScoreboard_STUDENT.getMark(1, 1));
		assertEquals(Mark.ZERO, singlePlayerBowlingScoreboard_STUDENT.getMark(1, 2));

		for(int frameNumber = 2; frameNumber < 10; frameNumber++)
		{
			assertEquals(Mark.EMPTY, singlePlayerBowlingScoreboard_STUDENT.getMark(frameNumber, 1));
			assertEquals(Mark.EMPTY, singlePlayerBowlingScoreboard_STUDENT.getMark(frameNumber, 2));
		}
	}
	
	@Points(value=5)
	@Test
	public void twoGutterBallScoreTest()
	{
		int[] pinsKnockedDownArray = new int[]{0, 0};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);
		assertEquals(0, singlePlayerBowlingScoreboard_STUDENT.getScore(1));
	}
	
	@Points(value=5)
	@Test
	public void twoRollsMarkTest()
	{
		int[] pinsKnockedDownArray = new int[]{7, 2};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);

		assertEquals(Mark.SEVEN, singlePlayerBowlingScoreboard_STUDENT.getMark(1, 1));
		assertEquals(Mark.TWO, singlePlayerBowlingScoreboard_STUDENT.getMark(1, 2));	
	}
	
	@Points(value=5)
	@Test
	public void twoRollsMark2Test()
	{
		int[] pinsKnockedDownArray = new int[]{3, 4};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);

		assertEquals(Mark.THREE, singlePlayerBowlingScoreboard_STUDENT.getMark(1, 1));
		assertEquals(Mark.FOUR, singlePlayerBowlingScoreboard_STUDENT.getMark(1, 2));
	}

	@Points(value=5)
	@Test
	public void twoRollsScoreTest()
	{
		int[] pinsKnockedDownArray = new int[]{3, 4};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);

		assertEquals(sum(pinsKnockedDownArray, 0, pinsKnockedDownArray.length), singlePlayerBowlingScoreboard_STUDENT.getScore(1));
	}

	@Points(value=5)
	@Test
	public void fourRollsMarkTest()
	{
		int[] pinsKnockedDownArray = new int[]{2, 0, 4, 5};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);

		assertEquals(Mark.TWO, singlePlayerBowlingScoreboard_STUDENT.getMark(1, 1));
		assertEquals(Mark.ZERO, singlePlayerBowlingScoreboard_STUDENT.getMark(1, 2));

		assertEquals(Mark.FOUR, singlePlayerBowlingScoreboard_STUDENT.getMark(2, 1));
		assertEquals(Mark.FIVE, singlePlayerBowlingScoreboard_STUDENT.getMark(2, 2));
	}

	@Points(value=5)
	@Test
	public void fourThreesScoreTest()
	{
		int[] pinsKnockedDownArray = new int[]{3, 3, 3, 3};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);
		
		for(int frameNumber = 1; frameNumber <= 2; frameNumber++)
		{
			assertEquals(sum(pinsKnockedDownArray, 0, 2*frameNumber), singlePlayerBowlingScoreboard_STUDENT.getScore(frameNumber));
		}
	}

	@Points(value=5)
	@Test
	public void fiveRollsScoreTest()
	{
		int[] pinsKnockedDownArray = new int[]{2, 3, 4, 5, 6};
		recordPins(singlePlayerBowlingScoreboard_STUDENT, pinsKnockedDownArray);
		
		for(int frameNumber = 1; frameNumber <= 2; frameNumber++)
		{
			assertEquals(sum(pinsKnockedDownArray, 0, 2*frameNumber), singlePlayerBowlingScoreboard_STUDENT.getScore(frameNumber));
		}
	}

	@Points(value=5)
	@Test
	public void noSparesOrStrikesNineFrame_CurrentFrameCurrentBallTest()
	{
		int[] pinsKnockedDownArray = new int[]{1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2};

		for(int frameNumber = 1; frameNumber <= 9; frameNumber++)
		{
			assertEquals(frameNumber, singlePlayerBowlingScoreboard_STUDENT.getCurrentFrame());
			assertEquals(1, singlePlayerBowlingScoreboard_STUDENT.getCurrentBall());
			
			int firstRollInFrame = pinsKnockedDownArray[2*(frameNumber - 1)];
			assert firstRollInFrame < 10 : "firstRollInFrame = " + firstRollInFrame + " <> 10!";

			singlePlayerBowlingScoreboard_STUDENT.recordRoll(firstRollInFrame);

			assertEquals(frameNumber, singlePlayerBowlingScoreboard_STUDENT.getCurrentFrame());
			assertEquals(2, singlePlayerBowlingScoreboard_STUDENT.getCurrentBall());
			
			int secondRollInFrame = pinsKnockedDownArray[2*(frameNumber - 1) + 1];
			assert secondRollInFrame < 10 : "secondRollInFrame = " + secondRollInFrame + " <> 10!";
			singlePlayerBowlingScoreboard_STUDENT.recordRoll(secondRollInFrame);

			assertEquals(frameNumber + 1, singlePlayerBowlingScoreboard_STUDENT.getCurrentFrame());
			assertEquals(1, singlePlayerBowlingScoreboard_STUDENT.getCurrentBall());
		}
	}

	@Points(value=5)
	@Test
	public void noSparesOrStrikesNineFrame_MarkTest()
	{
		int[] pinsKnockedDownArray = new int[]{1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2};

		for(int frameNumber = 1; frameNumber <= 9; frameNumber++)
		{
			int firstRollInFrame = pinsKnockedDownArray[2*(frameNumber - 1)];
			assert firstRollInFrame < 10 : "firstRollInFrame = " + firstRollInFrame + " <> 10!";

			assertEquals(Mark.EMPTY, singlePlayerBowlingScoreboard_STUDENT.getMark(frameNumber, 1));
			assertEquals(Mark.EMPTY, singlePlayerBowlingScoreboard_STUDENT.getMark(frameNumber, 2));
			
			singlePlayerBowlingScoreboard_STUDENT.recordRoll(firstRollInFrame);
			
			assertEquals(Mark.translate(firstRollInFrame), singlePlayerBowlingScoreboard_STUDENT.getMark(frameNumber, 1));
			assertEquals(Mark.EMPTY, singlePlayerBowlingScoreboard_STUDENT.getMark(frameNumber, 2));
			
			int secondRollInFrame = pinsKnockedDownArray[2*(frameNumber - 1) + 1];
			assert secondRollInFrame < 10 : "secondRollInFrame = " + secondRollInFrame + " <> 10!";

			singlePlayerBowlingScoreboard_STUDENT.recordRoll(secondRollInFrame);

			assertEquals(Mark.translate(firstRollInFrame), singlePlayerBowlingScoreboard_STUDENT.getMark(frameNumber, 1));
			assertEquals(Mark.translate(secondRollInFrame), singlePlayerBowlingScoreboard_STUDENT.getMark(frameNumber, 2));
		}
	}

	@Points(value=5)
	@Test
	public void noSparesOrStrikesNineFrameScoreTest()
	{
		int[] pinsKnockedDownArray = new int[]{1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2};

		for(int frameNumber = 1; frameNumber <= 9; frameNumber++)
		{
			int firstRollInFrame = pinsKnockedDownArray[2*(frameNumber - 1)];
			assert firstRollInFrame < 10 : "firstRollInFrame = " + firstRollInFrame + " <> 10!";
			
			singlePlayerBowlingScoreboard_STUDENT.recordRoll(firstRollInFrame);
			
			int secondRollInFrame = pinsKnockedDownArray[2*(frameNumber - 1) + 1];
			assert secondRollInFrame < 10 : "secondRollInFrame = " + secondRollInFrame + " <> 10!";

			singlePlayerBowlingScoreboard_STUDENT.recordRoll(secondRollInFrame);

			assertEquals(sum(pinsKnockedDownArray, 0, 2*frameNumber), singlePlayerBowlingScoreboard_STUDENT.getScore(frameNumber));
		}
	}
	
	@Points(value=5)
	@Test
	public void noSparesOrStrikesNineFrameScore2Test()
	{
		int[] pinsKnockedDownArray = new int[]{1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8, 0, 9, 0};

		for(int frameNumber = 1; frameNumber <= 9; frameNumber++)
		{
			int firstRollInFrame = pinsKnockedDownArray[2*(frameNumber - 1)];
			assert firstRollInFrame < 10 : "firstRollInFrame = " + firstRollInFrame + " <> 10!";
			
			singlePlayerBowlingScoreboard_STUDENT.recordRoll(firstRollInFrame);
			
			int secondRollInFrame = pinsKnockedDownArray[2*(frameNumber - 1) + 1];
			assert secondRollInFrame < 10 : "secondRollInFrame = " + secondRollInFrame + " <> 10!";

			singlePlayerBowlingScoreboard_STUDENT.recordRoll(secondRollInFrame);

			assertEquals(sum(pinsKnockedDownArray, 0, 2*frameNumber), singlePlayerBowlingScoreboard_STUDENT.getScore(frameNumber));
		}
	}

	/**********************************************************************************************************************/
	
	private void recordPins(SinglePlayerBowlingScoreboard bowlingScoreboard, int[] pinsKnockedDownArray)
	{
		for(int i = 0; i < pinsKnockedDownArray.length; i++)
		{
			bowlingScoreboard.recordRoll(pinsKnockedDownArray[i]);
		}
	}
	
	//post: rv = intArray[beginIndex] + intArray[beginIndex + 1] + ... + intArray[endIndex - 1]
	private static int sum(int[] intArray, int beginIndex, int endIndex)
	{
		assert beginIndex >= 0 : "beginIndex = " + beginIndex + " < 0!";
		assert endIndex <= (intArray.length) : "endIndex = " + endIndex + " > " + (intArray.length) + " = (intArray.length)!";
		assert beginIndex < endIndex : "beginIndex = " + beginIndex + " > " + endIndex + " = endIndex!";
		int sum = 0;
		for(int i = beginIndex; i < endIndex; i++)
		{
			sum = sum + intArray[i];
		}
		return sum;
	}
}
