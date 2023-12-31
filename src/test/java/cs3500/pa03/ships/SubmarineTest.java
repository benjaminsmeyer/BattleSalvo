package cs3500.pa03.ships;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.ships.Ship;
import cs3500.pa03.model.ships.ShipType;
import cs3500.pa03.model.ships.Submarine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the submarine class
 */
class SubmarineTest {

  private Ship ship;

  /**
   * Setups each test
   */
  @BeforeEach
  void setup() {
    ship = new Submarine();
  }


  /**
   * Tests if the name is returned correctly
   */
  @Test
  void getNameTest() {
    assertEquals("SUBMARINE", ship.getName());
  }

  /**
   * Tests if the original size is returned correctly
   */
  @Test
  void getOriginalSizeTest() {
    assertEquals(3, ship.getOriginalSize());
  }

  /**
   * Tests if the current size is returned correctly
   */
  @Test
  void getCurrentSizeTest() {
    assertEquals(3, ship.getCurrentSize());
    ship.shipHit();
    assertEquals(2, ship.getCurrentSize());
  }

  /**
   * Tests if the first letter is returned correctly
   */
  @Test
  void getFirstLetterTest() {
    assertEquals("S", ship.getFirstLetter());
  }

  /**
   * Tests the shipHit and if it works as expected
   */
  @Test
  void shipHitTest() {
    assertEquals(3, ship.getCurrentSize());
    ship.shipHit();
    assertEquals(2, ship.getCurrentSize());
  }

  /**
   * Tests if the check ship hit works as expected
   */
  @Test
  void checkShipHitTest() {
    assertFalse(ship.checkShipHit());
    ship.shipHit();
    assertTrue(ship.checkShipHit());
  }

  /**
   * Tests if the ship in water works as expected
   */
  @Test
  void shipInWaterTest() {
    assertTrue(ship.shipInWater());
    ship.shipHit();
    assertTrue(ship.shipInWater());
    for (int i = 0; i < 2; i++) {
      ship.shipHit();
    }
    assertFalse(ship.shipInWater());
  }

  /**
   * Tests if the ship type getter works as expected
   */
  @Test
  void getShipTypeTest() {
    assertEquals(ShipType.SUBMARINE, ship.getShipType());
  }

  /**
   * Tests if getPositions returns correctly
   */
  @Test
  void getPositionsTest() {
    Coord coord = new Coord(1, 1, ship);
    ship.addCoord(coord);
    assertTrue(ship.getPositions().contains(coord));
    coord = new Coord(2, 1, ship);
    assertFalse(ship.getPositions().contains(coord));
  }

  /**
   * Tests if addCoord returns correctly
   */
  @Test
  void addCoordTest() {
    Coord coord = new Coord(1, 1, ship);
    ship.addCoord(coord);
    assertTrue(ship.getPositions().contains(coord));
    coord = new Coord(2, 1, ship);
    assertFalse(ship.getPositions().contains(coord));
  }

  /**
   * Tests if vertical direction returns correctly
   */
  @Test
  void shipDirectionVerticalTest() {
    ship.setShipDirection("VERTICAL");
    Coord coord = new Coord(1, 1, ship);
    ship.addCoord(coord);
    coord = new Coord(1, 2, ship);
    ship.addCoord(coord);
    assertEquals("VERTICAL", ship.shipDirection());
  }

  /**
   * Tests if horizontal direction returns correctly
   */
  @Test
  void shipDirectionHorizontalTest() {
    ship.setShipDirection("HORIZONTAL");
    Coord coord = new Coord(1, 2, ship);
    ship.addCoord(coord);
    coord = new Coord(2, 2, ship);
    ship.addCoord(coord);
    assertEquals("HORIZONTAL", ship.shipDirection());
  }

  /**
   * Tests if the starting coord returns correctly
   */
  @Test
  void startingCoordHorizontalTest() {
    ship.setShipDirection("HORIZONTAL");
    Coord coord = new Coord(1, 2, ship);
    ship.addCoord(coord);
    coord = new Coord(2, 2, ship);
    ship.addCoord(coord);
    assertEquals("(1, 2)", ship.startingCoord().toString());
  }

  /**
   * Tests if the starting coord returns correctly
   */
  @Test
  void startingCoordVerticalTest() {
    ship.setShipDirection("VERTICAL");
    Coord coord = new Coord(1, 1, ship);
    ship.addCoord(coord);
    coord = new Coord(1, 2, ship);
    ship.addCoord(coord);
    assertEquals("(1, 1)", ship.startingCoord().toString());
  }
}