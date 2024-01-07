package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.GameType;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetUpJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.VolleyJson;
import cs3500.pa04.json.WinJson;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyDealer implements Controller {

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player aiPlayer;
  private final ObjectMapper mapper = new ObjectMapper();
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("");


  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if improper proxy dealer
   */
  public ProxyDealer(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.aiPlayer = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  @Override
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
    }
  }

  /**
   * Represents the given message arguments as a JoinJson type, and then serializes the end
   * game result and sends a void response to the server
   */
  private void handleJoin() {
    String username = "LoganStanhope";
    GameType gameType = GameType.SINGLE;

    JoinJson nameAndGameType = new JoinJson(username, gameType);
    JsonNode args = JsonUtils.serializeRecord(nameAndGameType);
    MessageJson joinMessage = new MessageJson("join", args);
    JsonNode joinSerialized = JsonUtils.serializeRecord(joinMessage);
    this.out.println(joinSerialized);
  }


  /**
   * Represents the given message arguments as a SetupJson type,
   * and then serializes the end game result and sends a void response to the server
   *
   * @param arguments are the Json representation of a messageJson
   */
  private void handleSetup(JsonNode arguments) {

    // converts the value of the arguments given from the server
    SetUpJson setupSpecifics = this.mapper.convertValue(arguments, SetUpJson.class);

    try {
      // calls setup on the player to get a list of ships
      List<Ship> playerShips = this.aiPlayer.setup(setupSpecifics.height(),
          setupSpecifics.width(),
          setupSpecifics.fleet());

      List<ShipJson> shipJsonList = convertToShipJson(playerShips);

      // creates a fleet Json with the converted ShipJson from the setup method
      FleetJson fleet = new FleetJson(shipJsonList);
      JsonNode fleetArgs = JsonUtils.serializeRecord(fleet);

      // creates a message Json as "fleet" as the message name the JsonNode as the fleet with
      // our list of ShipJson
      MessageJson fleetMessage = new MessageJson("setup", fleetArgs);

      JsonNode serializeSetup = JsonUtils.serializeRecord(fleetMessage);

      this.out.println(serializeSetup);

    } catch (Exception e) {
      System.out.println(e);
    }
  }


  /**
   * Represents the given message arguments as a takeShotsJson,
   * and then serializes the end game result and sends a void response to the server
   */
  private void handleTakeShots() {

    // calls take shots on the player
    List<Coord> playerShots = this.aiPlayer.takeShots();

    // converts our interpretation of Coord to a CoordJson
    List<CoordJson> regularCoordsToJsonCoords = convertToCoordJson(playerShots);

    // creates a VolleyJson with the list of CoordJson
    VolleyJson jsonCoordList = new VolleyJson(regularCoordsToJsonCoords);

    // serializes the VolleyJson
    JsonNode takeShotsResponse = JsonUtils.serializeRecord(jsonCoordList);

    // creates a MessageJson for the VolleyJson of take-shots
    MessageJson takeShotsMessage = new MessageJson("take-shots", takeShotsResponse);

    // serializes the MessageJson
    JsonNode serializeMsg = JsonUtils.serializeRecord(takeShotsMessage);

    // prints out the response for take shots
    this.out.println(serializeMsg);
  }


  /**
   * Represents the given message arguments as a JsonNode
   *
   * @param arguments are the Json representation of a messageJson
   */
  private void handleReportDamage(JsonNode arguments) {
    // translate from the server the damage report
    VolleyJson damage = this.mapper.convertValue(arguments, VolleyJson.class);

    // converts the CoordJson back to our interpretation of a Coord
    List<Coord> convertBackToCoords = convertToCoord(damage.shots());

    // calls report damage on this player to detect what shots hit our ships
    List<Coord> damages = this.aiPlayer.reportDamage(convertBackToCoords);

    // converts our interpretation of a Coord to a CoordJson
    List<CoordJson> convertBackToCoordJson = convertToCoordJson(damages);

    // creates a VolleyJson from the list of CoordJson
    VolleyJson reportedListOfDamages = new VolleyJson(convertBackToCoordJson);

    // serializes the VolleyJson
    JsonNode reportedDamage = JsonUtils.serializeRecord(reportedListOfDamages);

    // creates a MessageJson with the string name report-damage and the JsonNode
    MessageJson reportDamageMessage = new MessageJson("report-damage", reportedDamage);

    // serializes the MessageJson
    JsonNode serializedDamage = JsonUtils.serializeRecord(reportDamageMessage);

    // prints out the response for report damage
    this.out.println(serializedDamage);
  }


  /**
   * @param arguments are the Json representation of a message
   */
  private void handleSuccessfulHits(JsonNode arguments) {

    // converts the server's language into values we can interpret as successful
    // shots that hit the opponent
    VolleyJson givenSuccessfulHits = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> convertToCoord = convertToCoord(givenSuccessfulHits.shots());

    // updates given successful hits
    this.aiPlayer.successfulHits(convertToCoord);
    MessageJson message = new MessageJson("successful hits", VOID_RESPONSE);
    JsonNode emptyNode = JsonUtils.serializeRecord(message);

    this.out.println(emptyNode);
  }


  /**
   * Parses the given message arguments as a WinJson type,
   * and then serializes the end game result and sends a void response to the server.
   *
   * @param arguments are the Json representation of a WinJson
   */
  private void handleEndGame(JsonNode arguments) {

    // converts the servers given arguments to be interpreted
    WinJson winJson = this.mapper.convertValue(arguments, WinJson.class);

    // calls end game in player
    this.aiPlayer.endGame(winJson.result(), winJson.reason());
    MessageJson message = new MessageJson("end-game", VOID_RESPONSE);
    JsonNode serializeEndgame = JsonUtils.serializeRecord(message);

    // prints out void response
    this.out.println(serializeEndgame);
  }


  /**
   * Represents a conversion of a list of ships to a list of Json represented ships
   *
   * @param ships list of players ships after set up
   */
  private List<ShipJson> convertToShipJson(List<Ship> ships) {
    List<ShipJson> shipJsonList = new ArrayList<>();

    for (Ship s : ships) {
      ShipJson shipJson = new ShipJson(new CoordJson(s.getStart().getX(),
          s.getStart().getY()), s.getLength(), s.getDir().toString());
      shipJsonList.add(shipJson);
    }
    return shipJsonList;
  }

  /**
   * Converts a list of CoordJson to a list of Coords
   *
   * @param coords given list of CoordJsons
   */
  private List<Coord> convertToCoord(List<CoordJson> coords) {
    List<Coord> coordList = new ArrayList<>();
    for (CoordJson c : coords) {
      Coord convertedCoord = new Coord(c.x(), c.y());
      coordList.add(convertedCoord);
    }
    return coordList;
  }

  /**
   * Converts a list of Coord to a list of CoordJson
   *
   * @param coords given list of Coords
   */
  private List<CoordJson> convertToCoordJson(List<Coord> coords) {
    List<CoordJson> coordJsonList = new ArrayList<>();
    for (Coord c : coords) {
      CoordJson convertedCoord = new CoordJson(c.getX(), c.getY());
      coordJsonList.add(convertedCoord);
    }
    return coordJsonList;
  }
}
