package cs3500.pa04.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coord {
  private final int x;
  private final int y;

  @JsonCreator
  public Coord(@JsonProperty("x") int x,
               @JsonProperty("y") int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
