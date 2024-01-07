package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


/**
 * JSON format of this record: Represents a fleet or list of ships (in json format)
 * <p>
 * <code>
 * {
 *   "fleet": ships,
 * }
 * </code>
 * </p>
 *
 * @param ships ths list of ship jsons for the fleet

 */
public record FleetJson(@JsonProperty("fleet") List<ShipJson> ships) { }
