public class TileBoardEvent {
	private final TileBoard tileBoard;
	private final int row;
	private final int col;
	private final boolean newTileStatus;
	
	public TileBoardEvent(TileBoard tileBoard, int row, int col, boolean newTileStatus) {
		this.tileBoard = tileBoard;
		this.row = row;
		this.col = col;
		this.newTileStatus = newTileStatus;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return col;
	}
	
	public boolean getNewTileStatus() {
		return newTileStatus;
	}
	
	public TileBoard getTileBoard() {
		return tileBoard;
	}
	
}