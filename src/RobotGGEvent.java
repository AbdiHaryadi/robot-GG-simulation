public class RobotGGEvent {
	private RobotGG robotGG;
	private int oldRow;
	private int oldColumn;
	private int newRow;
	private int newColumn;
	private MoveDirection direction;
	
	public RobotGGEvent(RobotGG robotGG, int oldRow, int oldColumn, int newRow, int newColumn, MoveDirection direction) {
		this.robotGG = robotGG;
		this.oldRow = oldRow;
		this.oldColumn = oldColumn;
		this.newRow = newRow;
		this.newColumn = newColumn;
		this.direction = direction;
	}
	
	public RobotGG getRobotGG() {
		return robotGG;
	}
	
	public int getOldRow() {
		return oldRow;
	}
	
	public int getOldColumn() {
		return oldColumn;
	}
	
	public int getNewRow() {
		return newRow;
	}
	
	public int getNewColumn() {
		return newColumn;
	}
	
	public MoveDirection getMoveDirection() {
		return direction;
	}
	
}