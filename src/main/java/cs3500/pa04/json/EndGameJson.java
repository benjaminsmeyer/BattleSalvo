package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.game.GameResult;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "method-name": "end-game",
 * "arguments": {
 *      "result": "WIN",
 *      "reason": "Player 1 sank all of Player 2's ships"
 *  }
 * }
 * </code>
 * </p>
 *
 * @param result the result of the game
 * @param reason the reason of the game
 */
public record EndGameJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason) {
}
