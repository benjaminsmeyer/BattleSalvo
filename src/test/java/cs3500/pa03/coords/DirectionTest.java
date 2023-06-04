package cs3500.pa03.coords;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.coords.Direction;
import org.junit.jupiter.api.Test;

/**
 * Tests the direction class
 */
class DirectionTest {

  /**
   * Tests if the getDirection returns correctly
   */
  @Test
  void getDirectionTest() {
    assertEquals(0, Direction.UP.getDirection());
    assertEquals(1, Direction.DOWN.getDirection());
    assertEquals(2, Direction.RIGHT.getDirection());
    assertEquals(3, Direction.LEFT.getDirection());
  }
}