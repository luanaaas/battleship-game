package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * JSON format of this record: Represents a shot
 * <p>
 * <code>
 * {
 *   "coordinates": shots,
 * }
 * </code>
 * </p>
 *
 * @param shots the coordinates of shots of the player
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VolleyJson(@JsonProperty("coordinates") List<CoordJson> shots) {
}

