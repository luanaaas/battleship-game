package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents testing Message Json methods
 */
class MessageJsonTest {

  JoinJson joinJ;
  JsonNode joinNode;
  MessageJson messageJson;

  /**
   * Initializes records and jsonNode for testing this class
   */
  @BeforeEach
  void setUp() {
    joinJ = new JoinJson("LoganStanhope", GameType.SINGLE);
    joinNode = JsonUtils.serializeRecord(joinJ);
    messageJson = new MessageJson("join", joinNode);
  }

  /**
   * Test if properly changes to string
   */
  @Test
  void testToString() {
    assertEquals("{\"method-name\":\"join\",\"arguments\":"
            + "{\"name\":\"LoganStanhope\",\"game-type\":\"SINGLE\"}}",
        messageJson.toString());
  }
}