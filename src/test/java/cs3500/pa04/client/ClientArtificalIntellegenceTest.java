package cs3500.pa04.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.player.ArtificialIntelligence;
import cs3500.pa03.model.player.PlayerExtend;
import cs3500.pa03.model.ships.Ship;
import cs3500.pa03.model.ships.ShipType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the new artificial intelligence
 */
public class ClientArtificalIntellegenceTest {
  PlayerExtend ai;
  List<Coord> allShots;
  List<Ship> ships;

  /**
   * Initializes an AiPlayer.
   */
  @BeforeEach
  void setUp() {
    allShots = new ArrayList<>();

    ai = new ArtificialIntelligence("Test");
    ships = ai.setup(6, 6, getSpecs());
  }

  /**
   * Returns a Map with specifications on fleet size.
   *
   * @return a Map with specifications on fleet size
   */
  private Map<ShipType, Integer> getSpecs() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);

    return specifications;
  }

  /**
   * Tests the method takeShots. Checks that shots taken by AI
   * are all valid shots.
   */
  @Test
  void takeShots() {
    //shots w boats
    List<Coord> shots;

    try {
      shots = ai.takeShots();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    allShots.addAll(shots);

    assertEquals(4, shots.size());
    testShotIsInBound(shots);

    //sink one ship -- shots after taking damage
    List<Coord> damage = new ArrayList<>(ships.get(0).getPositions());

    ai.reportDamage(damage);
    int numBoatsLeft = 0;
    for (Ship ship : ships) {
      if (ship.shipInWater()) {
        numBoatsLeft++;
      }
    }
    assertEquals(3, numBoatsLeft);

    try {
      shots = ai.takeShots();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    allShots.addAll(shots);

    assertEquals(3, shots.size());
    testShotIsInBound(shots);
  }

  /**
   * Tests that the takeShots method learns from shots that result in a hit.
   */
  @Test
  void takeShotsAlgorithm() {
    List<Coord> damage = new ArrayList<>(ships.get(0).getPositions());
    ai.successfulHits(damage);

    try {
      ai.takeShots();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests that the list of coordinates shots, is in bounds of the board.
   *
   * @param shots the list of coordinates shots
   */
  private void testShotIsInBound(List<Coord> shots) {
    for (Coord shot : shots) {
      //confirming that they're all in range
      assertTrue(shot.getAxisX() >= 0);
      assertTrue(shot.getAxisX() < ai.getBoard().length);
      assertTrue(shot.getAxisY() >= 0);
      assertTrue(shot.getAxisY() < ai.getBoard()[0].length);
    }
  }
}
