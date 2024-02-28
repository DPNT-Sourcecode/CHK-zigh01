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

  @Test
  public void testCheckoutWithOfferAndMore() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("AAAAABCD");
    assertThat(price, equalTo(265));
  }


  @Test
  public void testCheckoutWithSameOfferTwice() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("AAAAAABCD");
    assertThat(price, equalTo(315));
  }

  @Test
  public void testCheckoutWithTwoDifferent() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("AAAAAABBCD");
    assertThat(price, equalTo(330));
  }

  @Test
  public void testCheckoutWithAFreeItemTypeWithFreeItem() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("EEB");
    assertThat(price, equalTo(80));
  }
  @Test
  public void testCheckoutWithInvalid() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("aaabcd");
    assertThat(price, equalTo(-1));
  }
}

