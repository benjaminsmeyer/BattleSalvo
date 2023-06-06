package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.game.GameResult;
import cs3500.pa03.model.player.ArtificialIntelligence;
import cs3500.pa03.model.player.PlayerExtend;
import cs3500.pa03.model.ships.Ship;
import cs3500.pa03.model.ships.ShipType;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.SuccessfulHitsJson;
import cs3500.pa04.json.TakeShotsJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class uses the Proxy Pattern to talk to the
 * Server and dispatch methods to the Player.
 */
public class ProxyController {

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");
  private static final String GAME_TYPE_SINGLE = "SINGLE";
  private static final String GAME_TYPE_MULTI = "MULTI";
  private static final String name = "benjaminsmeyer";
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final PlayerExtend player;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @throws IOException if
   */
  public ProxyController(Socket server) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = new ArtificialIntelligence("benjaminsmeyer");
  }


  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }


  /**
   * Determines the type of request the server has sent ("guess" or "win")
   * and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }


  /**
   * Parses the given arguments as a EndGameJson,
   * notifies the player whether they won, and provides a
   * void response to the server.
   *
   * @param arguments the Json representation of a EndGameJson
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGame = this.mapper.convertValue(arguments, EndGameJson.class);
    try {
      player.endGame(GameResult.valueOf(endGame.result()), endGame.reason());

      ObjectMapper mapper = new ObjectMapper();
      System.out.println(mapper.writeValueAsString(endGame));
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to send message.");
    }
    MessageJson messageJson = new MessageJson("end-game", VOID_RESPONSE);
    JsonNode jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given arguments as a SuccessfulHitsJson.
   *
   * @param arguments the Json representation of a SuccessfulHitsJson
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    SuccessfulHitsJson successfulHitsJson = this.mapper.convertValue(arguments,
        SuccessfulHitsJson.class);
    player.successfulHits(successfulHitsJson.volley());
    MessageJson messageJson = new MessageJson("successful-hits", VOID_RESPONSE);
    JsonNode jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given arguments as a setup.
   *
   * @param arguments the Json representation of a SetupJson.
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson args = this.mapper.convertValue(arguments, SetupJson.class);
    Map<ShipType, Integer> fleet = new HashMap<>();
    for (String ship : args.specs().keySet()) {
      fleet.put(ShipType.valueOf(ship), args.specs().get(ship));
    }
    List<Ship> ships = player.setup(args.height(), args.width(), fleet);
    ShipJson[] shipCoords = getPlayerShips(ships);
    FleetJson response = new FleetJson(shipCoords);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageJson = new MessageJson("setup", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }

  /**
   * Gets the player ships.
   *
   * @param ships the list of ships
   */
  private ShipJson[] getPlayerShips(List<Ship> ships) {
    List<ShipJson> list = new ArrayList<>();
    for (Ship ship : ships) {
      ShipJson json = new ShipJson(ship.startingCoord(),
          ship.getOriginalSize(), ship.shipDirection());
      list.add(json);
    }
    return list.toArray(ShipJson[]::new);
  }

  /**
   * Parses the given arguments as a Report Damage.
   *
   * @param arguments the Json representation of a ReportDamageJson.
   */
  private void handleReportDamage(JsonNode arguments) {
    ReportDamageJson reportDamageJson = this.mapper.convertValue(arguments, ReportDamageJson.class);
    List<Coord> damageCoords = player.reportDamage(reportDamageJson.volley());
    ReportDamageJson response = new ReportDamageJson(damageCoords);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageJson = new MessageJson("report-damage", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given arguments as a Join.
   */
  private void handleJoin() {
    JoinJson response = new JoinJson(name, GAME_TYPE_SINGLE);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageJson = new MessageJson("join", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given arguments as a Take Shots.
   */
  private void handleTakeShots() {
    List<Coord> takeShots;
    try {
      takeShots = player.takeShots();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to get shots.");
    }

    TakeShotsJson response = new TakeShotsJson(takeShots);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageJson = new MessageJson("take-shots", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }
}
