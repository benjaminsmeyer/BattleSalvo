package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.coords.Coord;
import java.util.List;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "method-name": "report-damage",
 * "arguments": {
 * "volley": [
 * {"x": 0, "y": 1},
 * {"x": 3, "y": 2}
 * ]
 * }
 * }
 * </code>
 * </p>
 *
 * @param volley the damage
 */
public record ReportDamageJson(
    @JsonProperty("coordinates") List<Coord> volley) {
}