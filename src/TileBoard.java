import java.util.ArrayList;
import java.util.stream.IntStream;

public class TileBoard {
	private final int nRow;
	private final int nCol;
	private boolean[][] tileStatus;
	private ArrayList<TileBoardListener> listeners;
	
	public TileBoard(int nRow, int nCol) throws IllegalArgumentException {
		if (nRow <= 0 || nCol <= 0) {
			throw new IllegalArgumentException();
		} else {
			this.nRow = nRow;
			this.nCol = nCol;
			tileStatus = new boolean[nRow][nCol];
			IntStream.range(0, nRow).forEach(i -> 
				IntStream.range(0, nCol).forEach(j ->
					tileStatus[i][j] = false));
			listeners = new ArrayList<TileBoardListener>();
		}
	}
	
	public TileBoard(TileBoard oldTileBoard) {
		nRow = oldTileBoard.nRow;
		nCol = oldTileBoard.nCol;
		tileStatus = new boolean[nRow][nCol];
		IntStream.range(0, nRow).forEach(i -> 
			IntStream.range(0, nCol).forEach(j ->
				tileStatus[i][j] = oldTileBoard.tileStatus[i][j]));
		listeners = new ArrayList<TileBoardListener>();
		
	}
	
	public void switchTile(int row, int col) throws IllegalArgumentException {
		if (isValidRowCol(row, col)) {
			boolean newStatus = !tileStatus[row - 1][col - 1];
			tileStatus[row - 1][col - 1] = newStatus;
			fireTileChanged(new TileBoardEvent(this, row, col, newStatus));
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public boolean getTileStatus(int row, int col) throws IllegalArgumentException {
		if (isValidRowCol(row, col)) {
			return tileStatus[row - 1][col - 1];
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public boolean addListener(TileBoardListener newListener) {
		boolean alreadyExists = listeners.contains(newListener);
		if (!alreadyExists) {
			listeners.add(newListener);
		}
		return !alreadyExists;
	}
	
	public boolean removeListener(TileBoardListener oldListener) {
		boolean alreadyExists = listeners.contains(oldListener);
		if (alreadyExists) {
			listeners.remove(oldListener);
		}
		return alreadyExists;
	}
	
	public int getNumberOfRows() {
		return nRow;
	}
	
	public int getNumberOfColumns() {
		return nCol;
	}
	
	public boolean isValidRowCol(int row, int col) {
		return row >= 1 && row <= nRow && col >= 1 && col <= nCol;
	}
	
	public String render() {
		StringBuilder builder = new StringBuilder();
		IntStream.range(0, nRow).forEach(i -> {
			IntStream.range(0, nCol).forEach(j ->
					builder.append(
						tileStatus[i][j]? 'X' : '_'
					)
				);
			builder.append('\n');
			}
		);
		return builder.toString();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof TileBoard) {
			TileBoard tileBoard = (TileBoard) obj;
			if (nRow != tileBoard.getNumberOfRows() || nCol != tileBoard.getNumberOfColumns()) {
				return false;
			} else {
				int i = 1;
				boolean mismatch = false;
				while (i <= nRow && !mismatch) {
					int j = 1;
					while (j <= nCol && !mismatch) {
						if (getTileStatus(i, j) != tileBoard.getTileStatus(i, j)) {
							mismatch = true;
						} else {
							j++;
						}
					}
					if (!mismatch) {
						i++;
					}
				}
				return !mismatch;
			}
		} else {
			return false;
		}
	}
	
	
	private void fireTileChanged(TileBoardEvent e) {
		listeners.stream()
			.forEach(listener -> listener.tileChanged(e));
	}
	
}