package cs3500.pa04.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.player.ArtificialIntelligence;
import cs3500.pa03.model.player.PlayerExtend;
import cs3500.pa03.model.ships.ShipType;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.SuccessfulHitsJson;
import cs3500.pa04.json.TakeShotsJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private PlayerExtend player;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    player = new ArtificialIntelligence("test");
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.SUBMARINE, 1);
    specs.put(ShipType.DESTROYER, 1);
    player.setup(9, 9, specs);
  }

  /**
   * Check that the server returns an end when given an end
   */
  @Test
  public void testVoidEndGame() {
    EndGameJson test = new EndGameJson("WIN", "Test");
    JsonNode sampleMessage = createSampleMessage("end-game", test);

    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.run();

    String expected = "{\"method-name\":\"end-game\",\"arguments\":\"void\"}";
    assertEquals(expected, logToString().trim());
  }

  /**
   * Check that the server returns a successful Hit
   */
  @Test
  public void testSuccessfulHits() {
    List<Coord> coords = List.of(new Coord(1, 1), new Coord(2, 2));
    SuccessfulHitsJson test = new SuccessfulHitsJson(coords);
    JsonNode jsonNode = createSampleMessage("successful-hits", test);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    assertEquals("{\"method-name\":\"successful-hits\",\"arguments\":"
        + "\"void\"}", logToString().trim());
    responseToClass(MessageJson.class);
  }

  /**
   * Check that the server returns a setup
   */
  @Test
  public void testSetups() {
    Map<String, Integer> specs = new HashMap<>();
    specs.put(ShipType.BATTLESHIP.toString(), 1);
    specs.put(ShipType.CARRIER.toString(), 1);
    specs.put(ShipType.SUBMARINE.toString(), 1);
    specs.put(ShipType.DESTROYER.toString(), 1);
    SetupJson test = new SetupJson(9, 9, specs);
    JsonNode jsonNode = createSampleMessage("setup", test);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    assertEquals("{\"method-name\":\"setup\",\"ar"
            + "guments\":{\"fleet\":[{\"coord\":{\"x\"",
        logToString().trim().substring(0, 58));
    responseToClass(MessageJson.class);
  }

  /**
   * Check that the server returns a report of the damage
   */
  @Test
  public void testReportDamage() {
    List<Coord> coords = List.of(new Coord(1, 1), new Coord(2, 2));
    ReportDamageJson test = new ReportDamageJson(coords);
    JsonNode jsonNode = createSampleMessage("report-damage", test);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    assertEquals("{\"method-name\":\"report-damage\",\"arguments\":"
        + "{\"coord", logToString().trim().substring(0, 50));
    responseToClass(MessageJson.class);
  }

  /**
   * Check that the server returns a join
   */
  @Test
  public void testJoin() {
    JoinJson test = new JoinJson("test", "SINGLE");
    JsonNode jsonNode = createSampleMessage("join", test);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    assertEquals("{\"method-name\":\"join\",\"arguments\":{\""
        + "name\":\"benjaminsmeyer\",\"game-type\":\"SINGLE\"}}", logToString().trim());
    responseToClass(MessageJson.class);
  }

  @Test
  public void testTakeShots() {
    List<Coord> coords = List.of(new Coord(1, 1), new Coord(2, 2));
    TakeShotsJson test = new TakeShotsJson(coords);
    JsonNode jsonNode = createSampleMessage("take-shots", test);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    assertEquals("{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\"",
        logToString().trim().substring(0, 60));
    responseToClass(MessageJson.class);
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
