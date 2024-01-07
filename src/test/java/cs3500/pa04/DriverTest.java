package cs3500.pa04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for main driver
 */
class DriverTest {
  Driver driver;
  String[] args;

  /**
   * Initializes driver
   */
  @BeforeEach
  public void setup() {
    this.driver = new Driver();
    this.args = new String[2];
  }

  /**
   * Tests main args
   */
  @Test
  public void testMain() {
    try {
      args[0] = "0.0.0.0";
      args[1] = "35001";
      Driver.main(args);
    } catch (Exception e) {
      System.out.print(e.getMessage());
    }
  }
}