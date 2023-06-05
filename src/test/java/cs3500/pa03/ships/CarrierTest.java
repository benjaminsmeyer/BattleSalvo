package cs3500.pa03.ships;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.ships.Carrier;
import cs3500.pa03.model.ships.Ship;
import cs3500.pa03.model.ships.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the carrier test
 */
class CarrierTest {

  private Ship ship;

  /**
   * Setups each test
   */
  @BeforeEach
  void setup() {
    ship = new Carrier();
  }


  /**
   * Tests if the name is returned correctly
   */
  @Test
  void getNameTest() {
    assertEquals("CARRIER", ship.getName());
  }

  /**
   * Tests if the original size is returned correctly
   */
  @Test
  void getOriginalSizeTest() {
    assertEquals(6, ship.getOriginalSize());
  }

  /**
   * Tests if the current size is returned correctly
   */
  @Test
  void getCurrentSizeTest() {
    assertEquals(6, ship.getCurrentSize());
    ship.shipHit();
    assertEquals(5, ship.getCurrentSize());
  }

  /**
   * Tests if the first letter is returned correctly
   */
  @Test
  void getFirstLetterTest() {
    assertEquals("C", ship.getFirstLetter());
  }

  /**
   * Tests the shipHit and if it works as expected
   */
  @Test
  void shipHitTest() {
    assertEquals(6, ship.getCurrentSize());
    ship.shipHit();
    assertEquals(5, ship.getCurrentSize());
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
    for (int i = 0; i < 5; i++) {
      ship.shipHit();
    }
    assertFalse(ship.shipInWater());
  }

  /**
   * Tests if the ship type getter works as expected
   */
  @Test
  void getShipTypeTest() {
    assertEquals(ShipType.CARRIER, ship.getShipType());
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

}