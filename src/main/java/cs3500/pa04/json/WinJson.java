package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameResult;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "result": result,
 *   "reason": reason,
 * }
 * </code>
 * </p>
 *
 * @param result win, lose, or draw result of the game
 * @param reason why the game end occurred
 */
public record WinJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason) {
}
