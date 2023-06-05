package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.client.Coord;
import java.util.List;

public record VolleyJSON(
    @JsonProperty("shots") List<Coord> shots) {
}
