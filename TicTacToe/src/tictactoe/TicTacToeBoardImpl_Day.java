package tictactoe;

public class TicTacToeBoardImpl_Day implements TicTacToeBoard {

	protected static final int NO_MOVE = -1;
	protected static final int NO_MATCH = -1;

	protected int[] movesArray;

	public TicTacToeBoardImpl_Day() {
		final int CELL_COUNT = ROW_COUNT * COLUMN_COUNT;
		movesArray = new int[CELL_COUNT];

		for (int i = 0; i < CELL_COUNT; i++) {
			movesArray[i] = NO_MOVE;
		}
	}

	public Mark getMark(int row, int column) {
		if (indexOfMatch(movesArray, column) == NO_MOVE) {
			return null;
		}

		int index = column + (row * COLUMN_COUNT);

		if ((index % 2 == 0)) {
			return Mark.X;
		} else {
			return Mark.O;
		}

	}

	public void setMark(int row, int column) {
		int index = column + (row * COLUMN_COUNT);

		for (int i = 0; i < ROW_COUNT * COLUMN_COUNT; i++) {
			if (movesArray[i] == -1) {
				movesArray[i] = index;
				break;
			}
		}
	}

	public Mark getTurn() {
		Mark turn = null;
		if (isGameOver() == true) {
			return turn;
		}

		for (int i = 0; i < ROW_COUNT * COLUMN_COUNT; i++) {
			if (movesArray[i] == -1)
				if (i % 2 == 0) {
					turn = Mark.X;
					break;
				} else if (i % 2 != 0) {
					turn = Mark.O;
					break;
				}
		}
		return turn;
	}

	public boolean isGameOver() {
		for (int i = 0; i < 3; i++) {
			boolean horizontal = (getMark(i, 0) == getMark(i, 1)) && (getMark(i, 0) == getMark(i, 2));
			boolean vertical = (getMark(0, i) == getMark(1, i)) && (getMark(0, i) == getMark(2, i));
			
		}
		return false;

	}

	public Mark getWinner() {
		if (checkForWinner() == null) {
			return null;
		} else if (checkForWinner() == Mark.X) {
			return Mark.X;
		} else if (checkForWinner() == Mark.O) {
			return Mark.O;
		}

		return null;

	}

	public Mark checkForWinner() {
		String even = "";
		String odd = "";

		for (int k = 0; k < movesArray.length; k++) {
			if (movesArray[k] == -1) {
				break;
			}
			if (k % 2 == 0) {
				even += movesArray[k];
			} else if (k % 2 == 1) {
				odd += movesArray[k];
			}

		}

		if (even.contains("0") && even.contains("1") && even.contains("2")) {
			return Mark.X;
		}
		if (even.contains("0") && even.contains("3") && even.contains("6")) {
			return Mark.X;
		}
		if (even.contains("1") && even.contains("4") && even.contains("7")) {
			return Mark.X;
		}
		if (even.contains("2") && even.contains("5") && even.contains("8")) {
			return Mark.X;
		}
		if (even.contains("3") && even.contains("4") && even.contains("5")) {
			return Mark.X;
		}
		if (even.contains("6") && even.contains("7") && even.contains("8")) {
			return Mark.X;
		}
		if (even.contains("2") && even.contains("4") && even.contains("6")) {
			return Mark.X;
		}
		if (even.contains("0") && even.contains("4") && even.contains("8")) {
			return Mark.X;
		}
		if (odd.contains("0") && odd.contains("1") && odd.contains("2")) {
			return Mark.O;
		}
		if (odd.contains("0") && odd.contains("3") && odd.contains("6")) {
			return Mark.O;
		}
		if (odd.contains("1") && odd.contains("4") && odd.contains("7")) {
			return Mark.O;
		}
		if (odd.contains("2") && odd.contains("5") && odd.contains("8")) {
			return Mark.O;
		}
		if (odd.contains("3") && odd.contains("4") && odd.contains("5")) {
			return Mark.O;
		}
		if (odd.contains("6") && odd.contains("7") && odd.contains("8")) {
			return Mark.O;
		}
		if (odd.contains("2") && odd.contains("4") && odd.contains("6")) {
			return Mark.O;
		}
		if (odd.contains("0") && odd.contains("4") && odd.contains("8")) {
			return Mark.O;
		}
		return null;
	}

	private int indexOfMatch(int movesArray[], int index) {
		boolean foundMatch = false;
		index = 0;
		while (index < movesArray.length && (!foundMatch)) {
			foundMatch = (movesArray[index] == index);
			if (!foundMatch) {
				index++;
			}
		}
		int rv = (foundMatch ? index : NO_MATCH);
		return rv;
	}

	public String toString() {

		String stringToPrint = "";

		int row = 0;
		int column = 0;

		while (row < ROW_COUNT && column < COLUMN_COUNT) {
			Mark mark = getMark(row, column);

			if (mark == null) {
				stringToPrint += " ";
			} else {
				stringToPrint += mark;
			}

			column++;

			if (column != COLUMN_COUNT)
				stringToPrint += "|";

			if (column == COLUMN_COUNT) {
				stringToPrint += "\n";
				column = 0;
				row++;

				if (row != ROW_COUNT)
					stringToPrint += "-----" + "\n";

			}

		}

		return stringToPrint;
	}

	public static void main(String [ ] args)
	{
		
	      
		TicTacToeBoard board = new TicTacToeBoardImpl_Day();
		
		// Horizontal Test
		
		
		//board.setMark(0, 0); //X
		//board.setMark(1, 0); //O
		board.setMark(0, 1); //X
		board.setMark(1, 2); //O
		//board.setMark(0, 2); //X
		
		
		
		//board.setMark(1, 1);
		// Vertical Test 
		/*
		board.setMark(0, 0); //X
		board.setMark(0, 1); //O
		board.setMark(1, 0); //X
		board.setMark(0, 2); //O
		board.setMark(2, 0); //X
		*/
		
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
	    
	    //if (board.isGameOver()) {
	    	
	    	Mark winner = board.getWinner();
	    	System.out.println("GAME OVER:" + board.isGameOver());
		    System.out.println("WINNER: " + winner);
		    
	    //}
	 }
}
