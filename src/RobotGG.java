import java.util.ArrayList;

public class RobotGG {
	private int currentRow;
	private int currentCol;
	private TileBoard tileBoard;
	private ArrayList<RobotGGListener> listeners;
	
	public RobotGG(int row, int col, TileBoard tileBoard) throws IllegalArgumentException {
		if (tileBoard.isValidRowCol(row, col)) {
			currentRow = row;
			currentCol = col;
			this.tileBoard = tileBoard;
			listeners = new ArrayList<RobotGGListener>();
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public RobotGG(RobotGG oldRobot, TileBoard newTileBoard) {
		this(oldRobot.currentRow, oldRobot.currentCol, newTileBoard);
	}
	
	public int getCurrentRow() {
		return currentRow;
	}
	
	public int getCurrentColumn() {
		return currentCol;
	}
	
	public TileBoard getTileBoard() {
		return tileBoard;
	}
	
	public boolean moveUp() {
		boolean canMove = currentRow > 1;
		if (canMove) {
			currentRow--;
			tileBoard.switchTile(currentRow, currentCol);
		}
		return canMove;
	}
	
	public boolean moveDown() {
		boolean canMove = currentRow < tileBoard.getNumberOfRows();
		if (canMove) {
			currentRow++;
			tileBoard.switchTile(currentRow, currentCol);
		}
		return canMove;
	}
	
	public boolean moveLeft() {
		boolean canMove = currentCol > 1;
		if (canMove) {
			currentCol--;
			tileBoard.switchTile(currentRow, currentCol);
		}
		return canMove;
	}
	
	public boolean moveRight() {
		boolean canMove = currentCol < tileBoard.getNumberOfColumns();
		if (canMove) {
			currentCol++;
			tileBoard.switchTile(currentRow, currentCol);
		}
		return canMove;
	}
	
}