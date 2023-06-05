package cs3500.pa03.coords;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.ships.Destroyer;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Coords class
 */
class CoordTest {

  private Coord coordWithNoBoat;
  private Coord coordWithBoat;

  /**
   * Setups each test
   */
  @BeforeEach
  void setup() {
    coordWithNoBoat = new Coord(5, 5);
    coordWithBoat = new Coord(5, 5, new Destroyer());
  }

  /**
   * Tests if the getX function returns correctly
   */
  @Test
  void getXtest() {
    assertEquals(5, coordWithBoat.getX());
    assertEquals(5, coordWithNoBoat.getX());
  }

  /**
   * Tests if the getY function returns correctly
   */
  @Test
  void getYtest() {
    assertEquals(5, coordWithBoat.getY());
    assertEquals(5, coordWithNoBoat.getY());
  }

  /**
   * Tests if the checkHit function returns correctly
   */
  @Test
  void checkHitTest() {
    assertFalse(coordWithBoat.checkHit());
    assertFalse(coordWithNoBoat.checkHit());
    coordWithBoat.spotHit();
    coordWithNoBoat.spotHit();
    assertTrue(coordWithBoat.checkHit());
    assertTrue(coordWithNoBoat.checkHit());
  }

  /**
   * Tests if the spotHit function works correctly
   */
  @Test
  void spotHitTest() {
    assertFalse(coordWithBoat.checkHit());
    assertFalse(coordWithNoBoat.checkHit());
    coordWithBoat.spotHit();
    coordWithNoBoat.spotHit();
    assertTrue(coordWithBoat.checkHit());
    assertTrue(coordWithNoBoat.checkHit());
  }

  /**
   * Tests if the checkSpotInUse function returns correctly
   */
  @Test
  void checkSpotInUseTest() {
    assertTrue(coordWithBoat.checkSpotInUse());
    assertFalse(coordWithNoBoat.checkSpotInUse());
  }

  /**
   * Tests if the spotTaken function returns correctly
   */
  @Test
  void spotTakenReturnsTest() {
    assertEquals("DESTROYER", coordWithBoat.spotTaken().getName());
  }

  /**
   * Tests if the spotTaken function throws correctly
   */
  @Test
  void spotTakenThrowsTest() {
    assertThrows(NoSuchElementException.class,
        () -> coordWithNoBoat.spotTaken());
  }

  /**
   * Tests if the toString returns correctly
   */
  @Test
  void testToString() {
    assertEquals("(5, 5)", coordWithBoat.toString());
    assertEquals("(5, 5)", coordWithNoBoat.toString());
  }
}