package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Dir;
import cs3500.pa04.model.Ship;
import java.util.List;

/**
 * JSON format of this record: A singluar ship
 * <p>
 * <code>
 * {
 *   "coord": startCoord,
 *   "length": length,
 *   "direction": dir
 * }
 * </code>
 * </p>
 *
 * @param startCoord the starting coordinate of the ship
 * @param length length of the ship
 * @param dir the horizontal or vertical direction of a ship
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson startCoord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String dir) {
}
