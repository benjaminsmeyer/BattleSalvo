package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "method-name": "setup",
 * "arguments": {
 * "fleet": [
 * {
 * "coord": {"x": 0, "y": 0},
 * "length": 6,
 * "direction": "VERTICAL"
 * },
 * {
 * "coord": {"x": 1, "y": 0},
 * "length": 5,
 * "direction": "HORIZONTAL"
 * }
 * ]
 * }
 * }
 * </code>
 * </p>
 *
 * @param fleet the fleet
 */
public record FleetJson(
    @JsonProperty("fleet") ShipJson[] fleet) {
}