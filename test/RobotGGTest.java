import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.After;
import static org.junit.Assert.assertEquals;

public class RobotGGTest {
	private static RobotGG robotGG;
	private static TileBoard tileboard;
	private static TileBoardListener listener;
	
	@BeforeClass
	public static void setUpClass() {
		tileboard = new TileBoard(3, 3);
		listener = new TileBoardListener() {
			@Override
			public void tileChanged(TileBoardEvent e) {
				/*
				System.out.println("\n" + e.getRow() + " " + e.getColumn());
				System.out.println(e.getTileBoard().render());
				*/
			}
		};
		tileboard.addListener(listener);
		
		robotGG = new RobotGG(2, 2, tileboard);
	}
	
	@Test
	public void moveUpTest() {
		boolean oldResultFrom = tileboard.getTileStatus(2, 2);
		boolean oldResultTo = tileboard.getTileStatus(1, 2);
		
		boolean moveResult = robotGG.moveUp();
		assertEquals(moveResult, true);
		assertEquals(tileboard.getTileStatus(2, 2), oldResultFrom);
		assertEquals(tileboard.getTileStatus(1, 2), !oldResultTo);
		
		moveResult = robotGG.moveUp();
		assertEquals(moveResult, false);
		assertEquals(tileboard.getTileStatus(2, 2), oldResultFrom);
		assertEquals(tileboard.getTileStatus(1, 2), !oldResultTo);
	}
	
	@Test
	public void moveDownTest() {
		boolean oldResultFrom = tileboard.getTileStatus(2, 2);
		boolean oldResultTo = tileboard.getTileStatus(3, 2);
		
		boolean moveResult = robotGG.moveDown();
		assertEquals(moveResult, true);
		assertEquals(tileboard.getTileStatus(2, 2), oldResultFrom);
		assertEquals(tileboard.getTileStatus(3, 2), !oldResultTo);
		
		moveResult = robotGG.moveDown();
		assertEquals(moveResult, false);
		assertEquals(tileboard.getTileStatus(2, 2), oldResultFrom);
		assertEquals(tileboard.getTileStatus(3, 2), !oldResultTo);
	}
	
	@Test
	public void moveLeftTest() {
		boolean oldResultFrom = tileboard.getTileStatus(2, 2);
		boolean oldResultTo = tileboard.getTileStatus(2, 1);
		
		boolean moveResult = robotGG.moveLeft();
		assertEquals(moveResult, true);
		assertEquals(tileboard.getTileStatus(2, 2), oldResultFrom);
		assertEquals(tileboard.getTileStatus(2, 1), !oldResultTo);
		
		moveResult = robotGG.moveLeft();
		assertEquals(moveResult, false);
		assertEquals(tileboard.getTileStatus(2, 2), oldResultFrom);
		assertEquals(tileboard.getTileStatus(2, 1), !oldResultTo);
	}
	
	@Test
	public void moveRightTest() {
		boolean oldResultFrom = tileboard.getTileStatus(2, 2);
		boolean oldResultTo = tileboard.getTileStatus(2, 3);
		
		boolean moveResult = robotGG.moveRight();
		assertEquals(moveResult, true);
		assertEquals(tileboard.getTileStatus(2, 2), oldResultFrom);
		assertEquals(tileboard.getTileStatus(2, 3), !oldResultTo);
		
		moveResult = robotGG.moveRight();
		assertEquals(moveResult, false);
		assertEquals(tileboard.getTileStatus(2, 2), oldResultFrom);
		assertEquals(tileboard.getTileStatus(2, 3), !oldResultTo);
	}
	
	@Test
	public void roundAbout() {
		TileBoard tileboardCopy = new TileBoard(tileboard);
		robotGG.moveUp();
		robotGG.moveLeft();
		robotGG.moveDown();
		robotGG.moveDown();
		robotGG.moveRight();
		robotGG.moveRight();
		robotGG.moveUp();
		robotGG.moveUp();
		robotGG.moveLeft();
		robotGG.moveDown();
		assertEquals(tileboard.getTileStatus(1, 1), !tileboardCopy.getTileStatus(1, 1));
		assertEquals(tileboard.getTileStatus(1, 2), tileboardCopy.getTileStatus(1, 2));
		assertEquals(tileboard.getTileStatus(1, 3), !tileboardCopy.getTileStatus(1, 3));
		assertEquals(tileboard.getTileStatus(2, 1), !tileboardCopy.getTileStatus(2, 1));
		assertEquals(tileboard.getTileStatus(2, 2), !tileboardCopy.getTileStatus(2, 2));
		assertEquals(tileboard.getTileStatus(2, 3), !tileboardCopy.getTileStatus(2, 3));
		assertEquals(tileboard.getTileStatus(3, 1), !tileboardCopy.getTileStatus(3, 1));
		assertEquals(tileboard.getTileStatus(3, 2), !tileboardCopy.getTileStatus(3, 2));
		assertEquals(tileboard.getTileStatus(3, 3), !tileboardCopy.getTileStatus(3, 3));
	}
	
	@After
	public void goToInitialPosition() {
		if (robotGG.getCurrentColumn() < 2) {
			robotGG.moveRight();
		} else if (robotGG.getCurrentColumn() > 2) {
			robotGG.moveLeft();
		}
		
		if (robotGG.getCurrentRow() < 2) {
			robotGG.moveDown();
		} else if (robotGG.getCurrentRow() > 2) {
			robotGG.moveUp();
		}
		
		assertEquals(robotGG.getCurrentRow(), 2);
		assertEquals(robotGG.getCurrentColumn(), 2);
	}
	
}