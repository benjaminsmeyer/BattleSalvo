package cs3500.pa03.ships;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.ships.ShipType;
import org.junit.jupiter.api.Test;

/**
 * Tests the ship type enum
 */
class ShipTypeTest {

  /**
   * Tests if the getName returns correctly
   */
  @Test
  void getNameTest() {
    assertEquals("CARRIER", ShipType.CARRIER.getName());
  }

  /**
   * Tests if the getSize returns correctly
   */
  @Test
  void getSizeTest() {
    assertEquals(6, ShipType.CARRIER.getSize());
  }

  /**
   * Tests if the getFirstLetter returns correctly
   */
  @Test
  void getFirstLetterTest() {
    assertEquals("C", ShipType.CARRIER.getFirstLetter());
  }
}