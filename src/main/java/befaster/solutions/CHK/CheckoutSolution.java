package befaster.solutions.CHK;


import befaster.solutions.CHK.CheckoutSolution.Offer.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class CheckoutSolution {

  static class Offer implements Comparable<Offer> {

    enum Type {
      DISCOUNT,
      GET_ONE_FREE,
      THREE_BUNDLED_ITEMS
    }

    private int number;
    private int price;
    private Set<Character> discountedItems;

    private Type type;

    public Offer(Type type, int number, int price, Set<Character> discountedItems) {
      this.number = number;
      this.price = price;
      this.discountedItems = discountedItems;
      this.type = type;
    }

    @Override
    public int compareTo(Offer o) {
      return o.number - this.number;
    }
  }

  private Map<Character, Integer> priceMap;
  private Map<Character, List<Offer>> offersDetails;

  private Map<Type, List<Character>> offers;

  public CheckoutSolution() {
    priceMap = new HashMap<>();
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

    offersDetails = new HashMap<>();
    offersDetails.put('A', new ArrayList<>());
    offersDetails.put('B', new ArrayList<>());
    offersDetails.put('E', new ArrayList<>());
    offersDetails.put('F', new ArrayList<>());
    offersDetails.put('H', new ArrayList<>());
    offersDetails.put('K', new ArrayList<>());
    offersDetails.put('N', new ArrayList<>());
    offersDetails.put('P', new ArrayList<>());
    offersDetails.put('Q', new ArrayList<>());
    offersDetails.put('R', new ArrayList<>());
    offersDetails.put('S', new ArrayList<>());
    offersDetails.put('T', new ArrayList<>());
    offersDetails.put('U', new ArrayList<>());
    offersDetails.put('V', new ArrayList<>());
    offersDetails.put('X', new ArrayList<>());
    offersDetails.put('Y', new ArrayList<>());
    offersDetails.put('Z', new ArrayList<>());

    offers = Map.of(
        Type.GET_ONE_FREE, List.of('E', 'F', 'N', 'R', 'U'),
        Type.DISCOUNT, List.of('A', 'B', 'H', 'K', 'P', 'Q', 'V'),
        Type.THREE_BUNDLED_ITEMS, List.of('S', 'T', 'X', 'Y', 'Z')
    );

    offersDetails.get('A').add(new Offer(Type.DISCOUNT, 5, priceMap.get('A') * 5 - 200, null));
    offersDetails.get('A').add(new Offer(Type.DISCOUNT, 3, priceMap.get('A') * 3 - 130, null));
    offersDetails.get('B').add(new Offer(Type.DISCOUNT, 2, priceMap.get('B') * 2 - 45, null));
    offersDetails.get('H').add(new Offer(Type.DISCOUNT, 10, priceMap.get('H') * 10 - 80, null));
    offersDetails.get('H').add(new Offer(Type.DISCOUNT, 5, priceMap.get('H') * 5 - 45, null));
    offersDetails.get('K').add(new Offer(Type.DISCOUNT, 2, priceMap.get('K') * 2 - 120, null));
    offersDetails.get('P').add(new Offer(Type.DISCOUNT, 5, priceMap.get('P') * 5 - 200, null));
    offersDetails.get('Q').add(new Offer(Type.DISCOUNT, 3, priceMap.get('Q') * 3 - 80, null));
    offersDetails.get('V').add(new Offer(Type.DISCOUNT, 3, priceMap.get('V') * 3 - 130, null));
    offersDetails.get('V').add(new Offer(Type.DISCOUNT, 2, priceMap.get('V') * 2 - 90, null));

    offersDetails.get('E').add(new Offer(Type.GET_ONE_FREE, 2, priceMap.get('B'), Set.of('B')));
    offersDetails.get('F').add(new Offer(Type.GET_ONE_FREE, 2, priceMap.get('F'), Set.of('F')));
    offersDetails.get('N').add(new Offer(Type.GET_ONE_FREE, 3, priceMap.get('M'), Set.of('M')));
    offersDetails.get('R').add(new Offer(Type.GET_ONE_FREE, 3, priceMap.get('Q'), Set.of('Q')));
    offersDetails.get('U').add(new Offer(Type.GET_ONE_FREE, 3, priceMap.get('U'), Set.of('U')));

    offersDetails.get('S')
        .add(new Offer(Type.THREE_BUNDLED_ITEMS, 3, 45, Set.of('S', 'T', 'X', 'Y', 'Z')));
    offersDetails.get('T')
        .add(new Offer(Type.THREE_BUNDLED_ITEMS, 3, 45, Set.of('S', 'T', 'X', 'Y', 'Z')));
    offersDetails.get('X')
        .add(new Offer(Type.THREE_BUNDLED_ITEMS, 3, 45, Set.of('S', 'T', 'X', 'Y', 'Z')));
    offersDetails.get('Y')
        .add(new Offer(Type.THREE_BUNDLED_ITEMS, 3, 45, Set.of('S', 'T', 'X', 'Y', 'Z')));
    offersDetails.get('Z')
        .add(new Offer(Type.THREE_BUNDLED_ITEMS, 3, 45, Set.of('S', 'T', 'X', 'Y', 'Z')));
  }

  public Integer checkout(String skus) {
    Map<Character, Integer> basket = new HashMap<>();
    Set<Character> offerableSkusInBasket = new HashSet<>();
    int price = 0;
    Queue<int[]> threeBundleRemovables = new PriorityQueue<>((a,b) -> b[1] - a[1]);
    for (int i = 0; i < skus.length(); i++) {
      char current = skus.charAt(i);
      if (!priceMap.containsKey(current)) {
        return -1;
      }

      if (offers.get(Type.THREE_BUNDLED_ITEMS).contains(current)) {
        threeBundleRemovables.add(new int[] { current - 'A', priceMap.get(current)});
      } else if (offersDetails.containsKey(current)) {
        offerableSkusInBasket.add(current);
      }

      basket.put(current, basket.getOrDefault(current, 0) + 1);

      price += priceMap.get(current);
    }

    List<Character> getOneFreeOffers = offers.getOrDefault(Type.GET_ONE_FREE, new ArrayList<>());

    price -= getBundlingDeductions(
        offerableSkusInBasket,
        basket,
        threeBundleRemovables
    );

    for (Character offerSku : getOneFreeOffers) {
      if (offerableSkusInBasket.contains(offerSku)) {
        price -= getDeductionsFromOffer(
            offerableSkusInBasket,
            offerSku,
            basket
        );
      }
    }

    List<Character> discountSkus = offers.getOrDefault(Type.DISCOUNT, new ArrayList<>());
    for (Character offerSku : discountSkus) {
      if (offerableSkusInBasket.contains(offerSku)) {
        price -= getDeductionsFromOffer(
            offerableSkusInBasket,
            offerSku,
            basket
        );
      }
    }

    return price;
  }

  private int getBundlingDeductions(
      Set<Character> offerableSkusInBasket,
      Map<Character, Integer> basket,
      Queue<int[]> threeBundleRemovables
  ) {
    int deductions = 0;
    while (threeBundleRemovables.size() >= 3) {
      for (int i = 0; i < 3; i++) {
        int[] skuAndPriceIntRep = threeBundleRemovables.poll();
        char sku = (char) ('A' + skuAndPriceIntRep[0]);
        int newNumber = basket.get(sku) - 1;

        if (newNumber == 0) {
          offerableSkusInBasket.remove(sku);
          basket.remove(sku);
        } else {
          basket.put(sku, newNumber);
        }

        deductions += skuAndPriceIntRep[1];
      }
      deductions -= 45;
    }
    return deductions;
  }

  private int getDeductionsFromOffer(
      Set<Character> offerableSkusInBasket,
      Character offerSku,
      Map<Character, Integer> basket
  ) {
    int deductions = 0;
    while (offerableSkusInBasket.contains(offerSku)) {
      char sku = offerSku;
      int number = basket.getOrDefault(sku, 0);
      List<Offer> offerList = offersDetails.get(sku);
      boolean matchingOffer = false;
      for (Offer offer : offerList) {
        while (offer.number <= number) {
          matchingOffer = true;
          number = number - offer.number;
          basket.put(sku, number);
          if (offer.type == Type.DISCOUNT) {
            deductions += offer.price;
          } else if (offer.type == Type.GET_ONE_FREE) {
            char removable = offer.discountedItems.stream().findFirst().orElse('~');
            int numberOfRemovableItems = basket.getOrDefault(removable, 0);
            if (numberOfRemovableItems != 0) {
              basket.put(removable, numberOfRemovableItems - 1);
              if (numberOfRemovableItems - 1 == 0) {
                offerableSkusInBasket.remove(removable);
              }
              deductions += offer.price;
            }
          }
        }
      }
      if (!matchingOffer) {
        offerableSkusInBasket.remove(sku);
      }
    }
    return deductions;
  }
}

