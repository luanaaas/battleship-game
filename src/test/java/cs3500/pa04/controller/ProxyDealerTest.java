package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameBoard;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ProxyDealerTest {
  private ByteArrayOutputStream testLog;
  private ProxyDealer dealer;
  private GameBoard board;
  private Random rand;
  private Player aiPlayer;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    this.board = new GameBoard();
    this.rand = new Random(1);
    aiPlayer = new AiPlayer(board, rand);
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
   * Create a MessageJson for some name and arguments.
   *
   * @param methodName name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String methodName, Record messageObject) {
    MessageJson messageJson = new MessageJson(methodName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

  /**
   * Check that player joins properly with server
   */
  @Test
  public void testJoin() {
    JoinJson joinJ = new JoinJson("LoganStanhope", GameType.SINGLE);
    JsonNode sampleMessage = createSampleMessage("join", joinJ);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    try {
      this.dealer = new ProxyDealer(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.dealer.run();

    String expected = "{\"method-name\":\"join\",\"arguments\":"
        + "{\"name\":\"LoganStanhope\",\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Check that the server returns a game result when game ends.
   */
  @Test
  public void testForEnd() {
    // Prepare sample message
    WinJson winJson = new WinJson(GameResult.LOSE, "You loss");
    JsonNode sampleMessage = createSampleMessage("end-game", winJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    try {
      this.dealer = new ProxyDealer(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
      // socket.close();
    }

    // run the dealer and verify the response
    this.dealer.run();

    String expected = "{\"method-name\":\"end-game\",\"arguments\":\"\"}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Check that the server returns successful hits
   */
  @Test
  public void testForSuccessfulHits() {
    // Prepare sample message
    CoordJson c1 = new CoordJson(1, 0);
    CoordJson c2 = new CoordJson(2, 3);
    CoordJson c3 = new CoordJson(1, 1);
    List<CoordJson> jsonCoordList = new ArrayList<>();
    jsonCoordList.add(c1);
    jsonCoordList.add(c2);
    jsonCoordList.add(c3);
    VolleyJson volleyJson = new VolleyJson(jsonCoordList);
    JsonNode sampleMessage = createSampleMessage("successful-hits", volleyJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    try {
      this.dealer = new ProxyDealer(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.dealer.run();

    String expected = "{\"method-name\":\"successful hits\",\"arguments\":\"\"}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Check that the returns set up after server response
   */
  @Test
  public void testForSetUp() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);

    SetUpJson setupJson = new SetUpJson(10, 10, specifications);

    ////////////////////////////////////////////// helps with converting setup ships to shipJson
    List<Ship> ships = aiPlayer.setup(setupJson.height(), setupJson.width(),
        setupJson.fleet());
    List<ShipJson> shipJsonList = new ArrayList<>();

    for (Ship s : ships) {
      ShipJson shipJson = new ShipJson(new CoordJson(s.getStart().getX(),
          s.getStart().getY()), s.getLength(), s.getDir().toString());
      shipJsonList.add(shipJson);
    }
    ////////////////////////////////////////////////////////////

    FleetJson fleet = new FleetJson(shipJsonList);

    JsonNode fleetNode = createSampleMessage("fleet", fleet);

    JsonNode jsonNode = createSampleMessage("setup", setupJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(fleetNode.toString(), jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyDealer(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.dealer.run();
    responseToClass(SetUpJson.class);
  }



  /**
   * Check that the returns shots properly
   */
  @Test
  public void testForTakeShots() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);

    List<Ship> playerShips = aiPlayer.setup(15, 15, specifications);

    CoordJson c1 = new CoordJson(1, 0);
    CoordJson c2 = new CoordJson(2, 3);
    CoordJson c3 = new CoordJson(1, 1);
    List<CoordJson> jsonCoordList = new ArrayList<>();
    jsonCoordList.add(c1);
    jsonCoordList.add(c2);
    jsonCoordList.add(c3);

    List<Coord> playerShots = this.aiPlayer.takeShots();
    List<Coord> playerShots2 = this.aiPlayer.takeShots();
    List<CoordJson> coordJsonList = new ArrayList<>();
    for (Coord c : playerShots) {
      CoordJson convertedCoord = new CoordJson(c.getX(), c.getY());
      coordJsonList.add(convertedCoord);
    }
    VolleyJson volleyJson = new VolleyJson(jsonCoordList);

    JsonNode jsonNode = createSampleMessage("take-shots", volleyJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyDealer(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    // Run dealer and verify response.
    this.dealer.run();
    responseToClass(VolleyJson.class);
  }

  /**
   * Check that the reported damage response is proper
   */
  @Test
  public void testForReportDamage() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);

    List<Ship> playerShips = aiPlayer.setup(15, 15, specifications);
    CoordJson c1 = new CoordJson(1, 0);
    CoordJson c2 = new CoordJson(2, 3);
    CoordJson c3 = new CoordJson(1, 1);
    List<CoordJson> jsonCoordList = new ArrayList<>();
    jsonCoordList.add(c1);
    jsonCoordList.add(c2);
    jsonCoordList.add(c3);

    List<Coord> playerShots = this.aiPlayer.takeShots();

    List<Coord> damage = this.aiPlayer.reportDamage(playerShots);

    List<CoordJson> coordJsonList = new ArrayList<>();
    for (Coord c : damage) {
      CoordJson convertedCoord = new CoordJson(c.getX(), c.getY());
      coordJsonList.add(convertedCoord);
    }

    VolleyJson volleyJson = new VolleyJson(coordJsonList);

    JsonNode jsonNode = createSampleMessage("report-damage", volleyJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyDealer(socket, aiPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    // Run dealer and verify response.
    this.dealer.run();
    responseToClass(VolleyJson.class);
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
      System.out.println(e.getMessage());
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }
}