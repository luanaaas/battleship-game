package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "method-name": "methodName",
 *   "arguments": {}
 * }
 * </code>
 * </p>
 *
 * @param methodName the name of the server method request
 * @param arguments   the arguments passed along with the message formatted as a Json object
 */
public record MessageJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") JsonNode arguments) {


  /**
   * Overrides to string for this record
   */
  @Override
  public String toString() {

    return JsonUtils.serializeRecord(this).toString();
  }


}