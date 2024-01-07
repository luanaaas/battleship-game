package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * JSON format of this record: Represents a coordinate
 * <p>
 * <code>
 * {
 *   "x": x,
 *   "y": y
 * }
 * </code>
 * </p>
 *
 * @param x x of coordinate
 * @param y y of coordinate
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {
}
