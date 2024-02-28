package befaster.solutions.CHK;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CheckoutSolution {
    class Offer {
        private int number;
        private int price;

        public Offer(int number, int price) {
            this.number = number;
            this.price = price;
        }
    }

    private Map<Character, Integer> priceMap;
    private Map<Character, Offer> offers;

    public CheckoutSolution() {
        priceMap = Map.of(
            'A', 50,
            'B', 30,
            'C', 20,
            'D',15
        );
        offers = Map.of(
            'A', new Offer(3, 130),
            'B', new Offer(2, 45)
        );
    }

    public Integer checkout(String skus) {
        Map<Character, Integer> maybeOfferCount = new HashMap<>();
        Set<Character> offerableSkusInBasket = new HashSet<>();
        int price = 0;
        for (int i = 0; i < skus.length(); i++) {
            char current = skus.charAt(i);
            if (!priceMap.containsKey(current)) {
                return -1;
            }

            if (offers.containsKey(current)) {
                maybeOfferCount.put(current, maybeOfferCount.getOrDefault(current, 0) + 1);
            }
            price += priceMap.get(current);
        }

        while (offerableSkusInBasket.size() > 0) {
            for (Entry<Character, Integer> skuAndPrice: maybeOfferCount.entrySet()) {
                    char sku = skuAndPrice.getKey();
                    Offer offer = offers.get(sku);
                    if (offer.number > skuAndPrice.getValue()) {
                        offerableSkusInBasket.remove(skuAndPrice.getKey());
                        continue;
                    }
                    maybeOfferCount.put(sku, skuAndPrice.getValue() - offer.number);
                    price -= priceMap.get(sku) * offer.number;
                    price += offer.price;
            }
        }

        return price;
    }
}



