import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class ShortestRouteTest {
	@Test
	public void runTest() {
		TileBoard tileBoard = new TileBoard(3, 3);
		TileBoard blankTileboard = new TileBoard(tileBoard);
		
		tileBoard.switchTile(3, 1);	
		tileBoard.switchTile(2, 3);	

		RobotGG robotGG = new RobotGG(1, 1, tileBoard);
		ShortestRobotGGRoute solver = new ShortestRobotGGRouteWithAStar(robotGG);
		List<MoveDirection> solution = solver.getSolution();
		for (MoveDirection direction: solution) {
			switch (direction) {
				case UP:
					robotGG.moveUp();
					break;
				case DOWN:
					robotGG.moveDown();
					break;
				case LEFT:
					robotGG.moveLeft();
					break;
				case RIGHT:
					robotGG.moveRight();
					break;
			}
		}
		
		assertEquals(tileBoard, blankTileboard);
		
	}
}