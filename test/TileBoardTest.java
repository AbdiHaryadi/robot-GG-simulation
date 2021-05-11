import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TileBoardTest {
	private static TileBoard tileboard;
	private static TileBoardListener listener;
	private static int numberOfChangedTile;
	
	@BeforeClass
	public static void setUpClass() {
		tileboard = new TileBoard(2, 2);
		listener = new TileBoardListener() {
			@Override
			public void tileChanged(TileBoardEvent e) {
				assertEquals(tileboard.getTileStatus(e.getRow(), e.getColumn()), e.getNewTileStatus());
				numberOfChangedTile++;
			}
		};
		boolean addResult = tileboard.addListener(listener);
		assertEquals(addResult, true);
		addResult = tileboard.addListener(listener); // try again
		assertEquals(addResult, false);
	}
	
	@Test
	public void testSwitchLeftUpCorner() {
		boolean oldStatus = tileboard.getTileStatus(1, 1);
		tileboard.switchTile(1, 1);
		assertEquals(!oldStatus, tileboard.getTileStatus(1, 1));
	}
	
	@Test
	public void testSwitchRightDownCorner() {
		boolean oldStatus = tileboard.getTileStatus(2, 2);
		tileboard.switchTile(2, 2);
		assertEquals(!oldStatus, tileboard.getTileStatus(2, 2));
	}
	
	@Test
	public void testEquality() {
		TileBoard tileboardCopy = new TileBoard(tileboard);
		assertEquals(tileboard, tileboardCopy);
		tileboardCopy.switchTile(2, 2);
		assertNotEquals(tileboard, tileboardCopy);
	}
	
	@AfterClass
	public static void tearDownClass() {
		boolean removeResult = tileboard.removeListener(listener);
		assertEquals(removeResult, true);
		tileboard.switchTile(1, 1);
		assertEquals(numberOfChangedTile, 2);
		removeResult = tileboard.removeListener(listener); // try again
		assertEquals(removeResult, false);
	}

}
