package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.coords.Coord;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "coord": {"x": 0, "y": 0},
 * "length": 6,
 * "direction": "VERTICAL"
 * }
 * </code>
 * </p>
 */
public record ShipJson(
    @JsonProperty("coord") Coord coord,
    @JsonProperty("length") int size,
    @JsonProperty("direction") String dir) {
}