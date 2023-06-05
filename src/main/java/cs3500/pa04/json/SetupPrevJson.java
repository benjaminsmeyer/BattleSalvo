package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ships.ShipType;
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
public record SetupPrevJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> specs) {
}