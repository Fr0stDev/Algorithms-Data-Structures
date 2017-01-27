package tictactoe;

public interface TicTacToeBoard {
	
	public final static int ROW_COUNT = 3;
	public final static int COLUMN_COUNT = 3;
	
	public Mark getMark(int row, int column);
	
	public void setMark(int row, int column);
	
	public Mark getTurn();
	
	public boolean isGameOver();
	
	public Mark getWinner();
	
	
}