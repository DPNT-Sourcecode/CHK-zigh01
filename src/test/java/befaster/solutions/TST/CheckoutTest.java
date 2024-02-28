package befaster.solutions.TST;

import static org.hamcrest.MatcherAssert.assertThat;

import befaster.solutions.CHK.CheckoutSolution;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutTest {

  @Test
  public void testRegularCheckout() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("ABCD");
    assertThat(price, equalTo(115));
  }

  @Test
  public void testCheckoutWithOffer() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("AAABCD");
    assertThat(price, equalTo(195));
  }
}


