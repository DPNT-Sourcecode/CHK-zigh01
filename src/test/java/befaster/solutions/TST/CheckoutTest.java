package befaster.solutions.TST;

import static org.hamcrest.MatcherAssert.assertThat;

import befaster.solutions.CHK.CheckoutSolution;
import org.junit.jupiter.api.Test;

public class CheckoutTest {

  @Test
  public void testRegular() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("abcd");
    assertThat(price, 115);
  }
}

