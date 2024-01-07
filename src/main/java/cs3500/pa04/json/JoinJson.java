package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * JSON format of this record: Represents joining game
 * <p>
 * <code>
 * {
 *   "name": "name",
 *   "game-type": gameType
 * }
 * </code>
 * </p>
 *
 * @param name name of player
 * @param gameType single or multi game type
 */
public record JoinJson(@JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType) {
}




