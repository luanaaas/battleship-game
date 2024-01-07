package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.ShipType;
import java.util.Map;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "height": height,
 *   "width": width,
 *   "fleet-spec" fleet
 * }
 * </code>
 * </p>
 *
 * @param height   the height of the board
 * @param width   the width of the board
 * @param fleet   the fleet information including ship type and amount of each
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SetUpJson(@JsonProperty("height") int height,
                        @JsonProperty("width") int width,
                        @JsonProperty("fleet-spec") Map<ShipType, Integer> fleet)  {}





