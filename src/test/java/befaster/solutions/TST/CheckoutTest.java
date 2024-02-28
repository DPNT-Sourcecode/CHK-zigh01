package befaster.solutions.TST;

import static org.hamcrest.MatcherAssert.assertThat;

import befaster.solutions.CHK.CheckoutSolution;
import java.util.HashMap;
import java.util.Map;
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
  public void testCheckoutWithSoManyA() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("AAAAAAAAAA");
    assertThat(price, equalTo(400));
  }

  @Test
  public void testCheckoutWithAFreeItemTypeWithFreeItemAndTheItemIsTheSameNotPresent() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("FF");
    assertThat(price, equalTo(20));
  }

  @Test
  public void testCheckoutWithAFreeItemTypeWithFreeItemAndTheItemIsTheSamePresent() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("FFF");
    assertThat(price, equalTo(20));
  }

  @Test
  public void testCheckoutWithAFreeItemTypeWithFreeItemTwiceAndTheItemIsTheSamePresent() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("FFFF");
    assertThat(price, equalTo(30));
  }

  @Test
  public void testCheckoutWithAFreeItemTypeWithFreeItem() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("EEB");
    assertThat(price, equalTo(80));
  }

  @Test
  public void testCheckoutWithAFreeItemTypeWithoutFreeItem() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("EE");
    assertThat(price, equalTo(80));
  }

  @Test
  public void testCheckoutWithAFreeItemTypeWithNotEnoughFreeItems() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("EEEEB");
    assertThat(price, equalTo(160));
  }

  @Test
  public void testCheckoutWithAFreeItemTypeWithEnoughFreeItems() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("EEEEBB");
    assertThat(price, equalTo(160));
  }

  @Test
  public void testCheckoutWithAFreeItemTypeWithEnoughFreeItemsOrderIndependent() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("BBEEEE");
    assertThat(price, equalTo(160));
  }

  @Test
  public void allSkusWork() {
    CheckoutSolution solution = new CheckoutSolution();
    Map<Character, Integer> priceMap = new HashMap<>();
    priceMap.put('A', 50);
    priceMap.put('B', 30);
    priceMap.put('C', 20);
    priceMap.put('D', 15);
    priceMap.put('E', 40);
    priceMap.put('F', 10);
    priceMap.put('G', 20);
    priceMap.put('H', 10);
    priceMap.put('I', 35);
    priceMap.put('J', 60);
    priceMap.put('K', 70);
    priceMap.put('L', 90);
    priceMap.put('M', 15);
    priceMap.put('N', 40);
    priceMap.put('O', 10);
    priceMap.put('P', 50);
    priceMap.put('Q', 30);
    priceMap.put('R', 50);
    priceMap.put('S', 20);
    priceMap.put('T', 20);
    priceMap.put('U', 40);
    priceMap.put('V', 50);
    priceMap.put('W', 20);
    priceMap.put('X', 17);
    priceMap.put('Y', 20);
    priceMap.put('Z', 21);

    for (Character sku: priceMap.keySet()) {
      assertThat(solution.checkout(sku + ""), equalTo(priceMap.get(sku)));
    }
  }

  @Test
  public void testCheckoutWithInvalid() {
    CheckoutSolution solution = new CheckoutSolution();
    int price = solution.checkout("aaabcd");
    assertThat(price, equalTo(-1));
  }
}


