import java.util.List;

public class RobotGGSimulation {
	public static void main(String[] args) {
		TileBoard tileBoard = new TileBoard(3, 3);
		
		// -- Edit area
		// Switch tile whatever you want
		tileBoard.switchTile(3, 1);	
		tileBoard.switchTile(2, 3);

		// Choose your robot GG initial position
		RobotGG robotGG = new RobotGG(1, 2, tileBoard);
		// -- End of edit area
		
		ShortestRobotGGRoute solver = new ShortestRobotGGRouteWithAStar(robotGG);
		List<MoveDirection> solution = solver.getSolution();
		for (MoveDirection direction: solution) {
			System.out.println(direction);
		}
	}
}