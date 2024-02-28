package befaster.solutions.CHK;


import befaster.solutions.CHK.CheckoutSolution.Offer.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class CheckoutSolution {
    static class Offer implements Comparable<Offer> {
        enum Type {
            DISCOUNT,
            GET_ONE_FREE
        }
        private int number;
        private int price;
        private Character freeItem;

        private Type type;

        public Offer(Type type, int number, int price, Character freeItem) {
            this.number = number;
            this.price = price;
            this.freeItem = freeItem;
            this.type = type;
        }

        @Override
        public int compareTo(Offer o) {
            return o.number - this.number;
        }
    }

    private Map<Character, Integer> priceMap;
    private Map<Character, List<Offer>> offers;

    public CheckoutSolution() {
        priceMap = Map.of(
            'A', 50,
            'B', 30,
            'C', 20,
            'D', 15,
            'E', 40
        );
        offers = Map.of(
            'A', new ArrayList<>(),
            'B', new ArrayList<>(),
            'E', new ArrayList<>()
        );

        offers.get('A').add(new Offer(Type.DISCOUNT, 5, 50, null));
        offers.get('A').add(new Offer(Type.DISCOUNT, 3, 20, null));

        offers.get('B').add(new Offer(Type.DISCOUNT, 2, 15, null));
        offers.get('E').add(new Offer(Type.GET_ONE_FREE, 2, 30, 'B'));
    }

    public Integer checkout(String skus) {
        Map<Character, Integer> maybeOfferCount = new HashMap<>();
        Map<Character, Integer> basket = new HashMap<>();
        Set<Character> offerableSkusInBasket = new HashSet<>();
        int price = 0;
        for (int i = 0; i < skus.length(); i++) {
            char current = skus.charAt(i);
            if (!priceMap.containsKey(current)) {
                return -1;
            }

            if (offers.containsKey(current)) {
                maybeOfferCount.put(current, maybeOfferCount.getOrDefault(current, 0) + 1);
                offerableSkusInBasket.add(current);
            }

            basket.put(current, basket.getOrDefault(current, 0) + 1);

            price += priceMap.get(current);
        }

        while (offerableSkusInBasket.size() > 0) {
            for (Entry<Character, Integer> skuAndNum: maybeOfferCount.entrySet()) {
                    char sku = skuAndNum.getKey();
                    List<Offer> offerList = offers.get(sku);
                    boolean matchingOffer = false;
                    for (Offer offer: offerList) {
                        if (offer.number <= skuAndNum.getValue()) {
                            matchingOffer = true;
                            maybeOfferCount.put(sku, skuAndNum.getValue() - offer.number);
                            if (offer.type == Type.DISCOUNT) {
                                price -= offer.price;
                            } else {
                                int numberOfRemovableItems = basket.getOrDefault(offer.freeItem, 0);
                                if (numberOfRemovableItems!= 0) {
                                    basket.put(offer.freeItem, numberOfRemovableItems - 1);
                                    price -= offer.price;
                                }
                            }
                        }
                    }
                    if (!matchingOffer) {
                        offerableSkusInBasket.remove(sku);
                    }
            }
        }

        return price;
    }
}

