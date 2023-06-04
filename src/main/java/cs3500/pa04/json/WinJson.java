package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "win": True
 * }
 * </code>
 * </p>
 *
 * @param isWinner true if this client is the winner
 */
public record WinJson(
    @JsonProperty("win") boolean win) {

    public boolean isWinner() {
      return win;
    }
}
