package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.coords.Coord;
import java.util.List;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "method-name": "join",
 * "arguments": {
 * "name": "github_username",
 * "game-type": "SINGLE"
 * }
 * }
 * </code>
 * </p>
 *
 * @param name the name
 * @param gameType the gameType
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType) {
}