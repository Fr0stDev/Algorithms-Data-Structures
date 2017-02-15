package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import tictactoe.Mark;
import tictactoe.TicTacToeBoard;
import tictactoe.TicTacToeBoardImpl_Moran;

public class TicTacToeTests_TestCasesSubsetForStudents {
	private TicTacToeBoard ticTacToeBoard_STUDENT;

	@Before public void setUp() {
		ticTacToeBoard_STUDENT = new TicTacToeBoardImpl_Moran();
	}

	@Test 
	public void emptyBoardTest()
	{
		for(int i = 0; i < TicTacToeBoard.ROW_COUNT; i++)
		{
			for(int j = 0; j < TicTacToeBoard.COLUMN_COUNT; j++)
			{
				assertEquals(null, ticTacToeBoard_STUDENT.getMark(i, j));
			}
		}
		assertEquals(Mark.X, ticTacToeBoard_STUDENT.getTurn());
		assertEquals(false, ticTacToeBoard_STUDENT.isGameOver());
	}

	@Test 
	public void oneSymbolTest()
	{		
		final int ROW = 0; 
		final int COLUMN = 2;
		final Mark MARK = Mark.X;

		ticTacToeBoard_STUDENT.setMark(ROW, COLUMN);

		for(int i = 0; i < TicTacToeBoard.ROW_COUNT; i++)
		{
			for(int j = 0; j < TicTacToeBoard.COLUMN_COUNT; j++)
			{
				if(i == ROW && j == COLUMN)
				{
					assertEquals(MARK, ticTacToeBoard_STUDENT.getMark(i, j));
				}
				else
				{
					assertEquals(null, ticTacToeBoard_STUDENT.getMark(i, j));
				}
			}
		}
		assertEquals(Mark.O, ticTacToeBoard_STUDENT.getTurn());
		assertEquals(false, ticTacToeBoard_STUDENT.isGameOver());
	}

	@Test(expected=AssertionError.class)
	public void setMark_OutOfRange()
	{
		final int ROW1 = 4;
		final int COLUMN1 = 4;
		ticTacToeBoard_STUDENT.setMark(ROW1, COLUMN1);
	}
}
