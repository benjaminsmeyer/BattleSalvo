package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "method-name": "setup",
 * "arguments": {
 * "width": 10,
 * "height": 10,
 * "fleet-spec": {
 * "CARRIER": 2,
 * "BATTLESHIP": 4,
 * "DESTROYER": 1,
 * "SUBMARINE": 3
 * }
 * }
 * }
 * </code>
 * </p>
 *
 * @param width     the width
 * @param height    the height
 * @param specs     the fleet specs
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<String, Integer> specs) {
}