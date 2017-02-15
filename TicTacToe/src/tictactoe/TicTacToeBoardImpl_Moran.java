package tictactoe;

public class TicTacToeBoardImpl_Moran implements TicTacToeBoard {
	
	protected static final int NO_MOVE = -1;
	protected static final int NO_MATCH = -1;
	
	protected int[] movesArray;
	
	public TicTacToeBoardImpl_Moran() {
		final int CELL_COUNT = ROW_COUNT * COLUMN_COUNT;
		movesArray = new int[CELL_COUNT];
		for (int i = 0; i < CELL_COUNT; i++) {
			movesArray[i] = NO_MOVE;
		}
	}
	
	//part of pre: 0 <= row < ROW_COUNT && 0 <= column < COLUMN_COUNT
	//part of post: rv == null <==> the (row, column) spot on the board is empty
	public Mark getMark(int row, int column) {
	
		assert row < ROW_COUNT;
		assert column < COLUMN_COUNT;
		//Formula to calculate the index of the row and column in the array
		
		// This is array index
		// 0|1|2
		// 3|4|5
		// 6|7|8
		
		int gridPosition = column + (row * COLUMN_COUNT);
		
		/*
		 * Retrieves the position of a mark at the given array index
		 * 
		 * i.e : 
		 * 
		 * gridPosition = 2
		 * 
		 * _|_|gp
		 * _|_|_
		 *  | |
		 * 
		 * x,o,x,o,x,o,x,o,x
		 * array = {1,5,4,3,7,2,8,0,-1}
		 * 
		 * 
		 * indexOfMatch(array, arrayIndex) -> returns 5
		 * 
		 * _|_|o
		 * _|_|_
		 *  | |
		 *
		 * 
		 * 
		 */
		
		int markIndex = indexOfMatch(movesArray, gridPosition);
		
		//If it returns -1, there is no mark.
		if (markIndex == NO_MOVE) {
			return null;
		}
	
		// 'X' always goes first. Therefore, it's index is always even and divisible by 2
		if (markIndex % 2 == 0 ) {
			return Mark.X;
		}
		return Mark.O;
		
	}

	//part of pre: 0 <= row < ROW_COUNT && 0 <= column < COLUMN_COUNT
	//part of pre: getMark(row, column) == null
	//part of pre: !isGameOver
	public void setMark(int row, int column) {
		
		assert getMark(row,column) == null;
		assert row < ROW_COUNT;
		assert column < COLUMN_COUNT;
		
		assert isGameOver() == false;
		
		int markIndex = column + (row * COLUMN_COUNT);
				
		for (int i = 0; i < movesArray.length; i++) {
	
			if (movesArray[i] == NO_MOVE) {
				movesArray[i] = markIndex;
				break;
			}
		}
	}

	//part of post: rv == null <==> it is neither player's turn
	//part of pre: getMark(row,column) == null
	//part of pre: !isGameOver()
	//post: Mark
	public Mark getTurn() {
		
		if (isGameOver()) {
			return null;
		}
		
		for (int i = 0; i < movesArray.length; i++) {
			
			if (movesArray[i] == -1) {
				if (i % 2 == 0) {
					return Mark.X;
				}
				else {
					return Mark.O;
				}
			}
		}
		return null;
	}

	
	
	public boolean isGameOver() {
		
		int state = gameState();
		
		if (state == 0) {
			return false;
		}
		
		if (state > 0) {
			return true;
		}
		return false;
		
	}

	//part of pre: isGameOver()
	//part of post: rv == null <==> neither player won (game is a tie)
	public Mark getWinner() {
		
		assert isGameOver();
		
		if (gameState() == 1) {
			return Mark.X;
		}
		if (gameState() == 2) {
			return Mark.O;
		}
		if (gameState() == 3) {
			return null;
		}
		return null;
		
	}
	
	

	private int gameState() {
		
		/*
		 * 0 = game not over 
		 * 1 = Winner is X
		 * 2 = Winner is O
		 * 3 = Game over with a tie
		 */
		
		int gameNotOver     = 0;
		int gameOverWinnerX = 1;
		int gameOverWinnerO = 2;
		int gameOverTie     = 3;
		
		
		
		//Win Vertically 
		// 0 3 6 , 1 4 7 , 2 5 8
				
		//Win Horizontally
		//0 1 2 , 3 4 5 , 6 7 8 
				
		//Win Diagonally
		// 0 4 8 , 2 4 6
				
		//Assuming it's an equal grid
				
		Mark winningMark = null;
				
		boolean hasFoundWinner = false;
				
		int row = 0;
		int column = 0;
				
		//Horizontal Winner Test
				
		while (!hasFoundWinner && row < ROW_COUNT) {
					
			Mark[] markArray = new Mark[ROW_COUNT];
					
			int index = 0;
			while (column < COLUMN_COUNT) {
					
				markArray[index] = getMark(row, column);
				column ++;
				index++;
						
			}
							
			if (allElementsEqual(markArray)) {
				hasFoundWinner = true;
				winningMark = markArray[0];
				break;
			}
			
			column = 0;
			row++;	
						
		}
				
		if (hasFoundWinner) {
			if (winningMark == Mark.X) {
				return gameOverWinnerX;
			}
			if (winningMark == Mark.O) {
				return gameOverWinnerO;
			}
		}
				
		// Vertical Winner Test
				
		row = 0;
		column = 0;

		while (!hasFoundWinner && column < COLUMN_COUNT) {
					
			Mark[] markArray = new Mark[ROW_COUNT];
					
			int index = 0;
			while (row < ROW_COUNT) {
					
				markArray[index] = getMark(row, column);
				row++;
				index++;
						
			}
							
			if (allElementsEqual(markArray)) {
				hasFoundWinner = true;
				winningMark = markArray[0];
				break;
			}
			
			row = 0;
			column++;	
						
		}
				
		if (hasFoundWinner) {
			if (winningMark == Mark.X) {
				return gameOverWinnerX;
			}
			if (winningMark == Mark.O) {
				return gameOverWinnerO;
			}
		}
				
		//Diagonal Winner Test "\"
				
		row = 0;
		column = 0;
				
		while (!hasFoundWinner && row < ROW_COUNT) {
					
			Mark[] markArray = new Mark[COLUMN_COUNT];
					
			int index = 0;
					
			while (row < ROW_COUNT) {
						
				markArray[index] = getMark(row, column);
				row++;
				column++;
				index++;
						
			}
					
			if (allElementsEqual(markArray)) {
				hasFoundWinner = true;
				winningMark = markArray[0];
				break;
			}
					
					
		}
				
		if (hasFoundWinner) {
			if (winningMark == Mark.X) {
				return gameOverWinnerX;
			}
			if (winningMark == Mark.O) {
				return gameOverWinnerO;
			}
		}
				
		//Diagonal Test "/"
				
		row = 0;
		column = COLUMN_COUNT - 1;
				
		while (!hasFoundWinner && row < ROW_COUNT) {
					
			Mark[] markArray = new Mark[COLUMN_COUNT];
					
			int index = 0;
					
			while (row < ROW_COUNT) {
						
				markArray[index] = getMark(row, column);
				row++;
				column--;
				index++;
						
			}
					
			if (allElementsEqual(markArray)) {
				hasFoundWinner = true;
				winningMark = markArray[0];
				break;
			}
					
		}
		
		if (hasFoundWinner) {
			if (winningMark == Mark.X) {
				return gameOverWinnerX;
			}
			if (winningMark == Mark.O) {
				return gameOverWinnerO;
			}
		}
		
		// If there are no moves left, and there is no winner, game ends inn a tie 
		
		boolean foundNoMove = false;
		for (int i = 0; i < movesArray.length; i++) {
			if (movesArray[i] == NO_MOVE) {
				foundNoMove = true;
				break;
			}
		}
		
		if (!foundNoMove && !hasFoundWinner) {
			return gameOverTie;
		}
		
		// If there is no winner and there are still moves left, game is not over
		
		return gameNotOver;
		
	}
	
	/*
	 * Checks if all the elemnnts in an array are the same
	 */
	private static boolean allElementsEqual(Mark[] array) {
		
		boolean areEqual = true;
		
        for(int i=0 ; i < array.length; i++) {
        	
        	
        	if (array[i] == null) {
        		areEqual = false;
        		break;
        	}
        	
            if(!array[0].equals(array[i])) {
                areEqual = false;
                break;
            }
        }

        return areEqual;
    }
	
	public String toString() { 
		
		String stringToPrint = "";
		
		int row = 0;
		int column = 0;
		
		while (row < ROW_COUNT && column < COLUMN_COUNT) {
			Mark mark = getMark(row,column);
		
			if (mark == null) {
				stringToPrint += " ";
			}
			else {
				stringToPrint += mark;
			}
			
			column++;
			
			if (column != COLUMN_COUNT) 
				stringToPrint += "|";
			
			if (column == COLUMN_COUNT) {
				stringToPrint += "\n";
				column = 0;
				row ++;
				
				if (row != ROW_COUNT) 
					stringToPrint += "-----" + "\n";
				
			}
			
		}
	
        return stringToPrint;
     } 
	
	private static int indexOfMatch(int[] array, int target) {
		boolean foundMatch = false;;
		int index = 0;
		while ((index < array.length) && (!foundMatch)) {
			foundMatch = (array[index] == target);
			if (!foundMatch) index++;
			
		}
		int rv = (foundMatch ? index : NO_MATCH);
		return rv;
	}
	
	public static void main(String [ ] args)
	{
		
	      
		TicTacToeBoard board = new TicTacToeBoardImpl_Moran();
		
		// Horizontal Test
		
		/*
		board.setMark(0, 0); //X
		board.setMark(1, 0); //O
		board.setMark(0, 1); //X
		board.setMark(1, 2); //O
		board.setMark(0, 2); //X
		*/
		
		
		//board.setMark(1, 1);
		// Vertical Test 
		
		board.setMark(0, 0); //X
		board.setMark(0, 1); //O
		board.setMark(1, 0); //X
		board.setMark(0, 2); //O
		board.setMark(2, 0); //X
		
		
		// Diagonal Test "\"
		
		/*
		board.setMark(0, 0); //X
		board.setMark(0, 1); //O
		board.setMark(1, 1); //X
		board.setMark(1, 0); //O
		board.setMark(2, 2); //X
		*/
		
		// Diagonal Test "/"
		
		/*
		board.setMark(0, 2); //X
		board.setMark(0, 1); //O
		board.setMark(1, 1); //X
		board.setMark(1, 0); //O
		board.setMark(2, 0); //X
		*/
		
		// 9 x 9 test
		
		/*
		board.setMark(0, 0);
		board.setMark(1, 0);
		board.setMark(1, 1);
		board.setMark(2, 0);
		board.setMark(2, 2);
		board.setMark(3, 0);
		board.setMark(3, 3);
		board.setMark(4, 0);
		board.setMark(4, 4);
		board.setMark(5, 0);
		board.setMark(5, 5);
		board.setMark(6, 0);
		board.setMark(6, 6);
		board.setMark(7, 0);
		board.setMark(7, 7);
		board.setMark(8, 0);
		board.setMark(8, 8);
		*/
		
		
		      
	    String s = board.toString();
	    System.out.println(s);
	    
	    if (board.isGameOver()) {
	    	
	    	Mark winner = board.getWinner();
	    	System.out.println("GAME OVER:" + board.isGameOver());
		    System.out.println("WINNER: " + winner);
		    
	    }
	 }
}
