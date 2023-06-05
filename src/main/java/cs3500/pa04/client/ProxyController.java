package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.coords.Coord;
import cs3500.pa03.model.player.ArtificialIntelligence;
import cs3500.pa03.model.player.PlayerExtend;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SuccessfulHitsJson;
import cs3500.pa04.json.TakeShotsJson;
import cs3500.pa04.json.EndGameJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyController {

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final PlayerExtend player;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

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
    this.player = new ArtificialIntelligence("BEN");
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
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      // handleJoin(arguments);
    } else if ("setup".equals(name)) {
      // handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots(arguments);
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
   * Parses the given arguments as a EndGameJson, notifies the player whether they won, and provides a
   * void response to the server.
   *
   * @param arguments the Json representation of a EndGameJson
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGame = this.mapper.convertValue(arguments, EndGameJson.class);
    try {
      player.endGame(endGame.result(), endGame.reason());

      ObjectMapper mapper = new ObjectMapper();
      System.out.println(mapper.writeValueAsString(endGame));
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to send message.");
    }
    this.out.println(VOID_RESPONSE);
  }

  /**
   * Parses the given arguments as a SuccessfulHitsJson.
   *
   * @param arguments the Json representation of a SuccessfulHitsJson
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    SuccessfulHitsJson successfulHitsJson = this.mapper.convertValue(arguments, SuccessfulHitsJson.class);
    player.successfulHits(successfulHitsJson.volley());
    this.out.println(VOID_RESPONSE);
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
    this.out.println(jsonResponse);
  }

  /**
   * Parses the given arguments as a Take Shots.
   *
   * @param arguments the Json representation of a TakeShotsJson.
   */
  private void handleTakeShots(JsonNode arguments) {
    List<Coord> takeShots;
    try {
      takeShots = player.takeShots();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to get shots.");
    }

    TakeShotsJson response = new TakeShotsJson(takeShots);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }
}
