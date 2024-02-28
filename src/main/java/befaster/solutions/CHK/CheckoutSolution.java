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
    private Map<Character, List<Offer>> offersDetails;

    private Map<Type, List<Character>> offers;

    public CheckoutSolution() {
        priceMap = Map.of(
            'A', 50,
            'B', 30,
            'C', 20,
            'D', 15,
            'E', 40,
            'F', 10
        );
        offersDetails = Map.of(
            'A', new ArrayList<>(),
            'B', new ArrayList<>(),
            'E', new ArrayList<>(),
            'F', new ArrayList<>()
        );

        offers = Map.of(
            Type.GET_ONE_FREE, List.of('E', 'F'),
            Type.DISCOUNT, List.of('A', 'B')
        );

        offersDetails.get('A').add(new Offer(Type.DISCOUNT, 5, 50, null));
        offersDetails.get('A').add(new Offer(Type.DISCOUNT, 3, 20, null));

        offersDetails.get('B').add(new Offer(Type.DISCOUNT, 2, 15, null));
        offersDetails.get('E').add(new Offer(Type.GET_ONE_FREE, 2, 30, 'B'));
        offersDetails.get('F').add(new Offer(Type.GET_ONE_FREE, 2, 10, 'F'));

    }

    public Integer checkout(String skus) {
        Map<Character, Integer> basket = new HashMap<>();
        Set<Character> offerableSkusInBasket = new HashSet<>();
        int price = 0;

        for (int i = 0; i < skus.length(); i++) {
            char current = skus.charAt(i);
            if (!priceMap.containsKey(current)) {
                return -1;
            }

            if (offersDetails.containsKey(current)) {
                offerableSkusInBasket.add(current);
            }

            basket.put(current, basket.getOrDefault(current, 0) + 1);

            price += priceMap.get(current);
        }

        List<Character> getOneFreeOffers = offers.getOrDefault(Type.GET_ONE_FREE, new ArrayList<>());

        for (Character offerSku: getOneFreeOffers) {
            if (offerableSkusInBasket.contains(offerSku)) {
               price -= getDeductionsFromOffer(
                   offerableSkusInBasket,
                   offerSku,
                   basket
               );
            }
        }

        List<Character> discountSkus = offers.getOrDefault(Type.DISCOUNT, new ArrayList<>());
        for (Character offerSku: discountSkus) {
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

    private int getDeductionsFromOffer(
        Set<Character> offerableSkusInBasket,
        Character offerSku,
        Map<Character, Integer> basket
    ) {
        int deductions = 0;
        while (offerableSkusInBasket.contains(offerSku)) {
            char sku = offerSku;
            int number = basket.getOrDefault(sku,0);
            List<Offer> offerList = offersDetails.get(sku);
            boolean matchingOffer = false;
            for (Offer offer: offerList) {
                while (offer.number <= number) {
                    matchingOffer = true;
                    number = number - offer.number;
                    basket.put(sku, number);
                    if (offer.type == Type.DISCOUNT) {
                        deductions += offer.price;
                    } else {
                        int numberOfRemovableItems = basket.getOrDefault(offer.freeItem, 0);
                        if (numberOfRemovableItems != 0) {
                            basket.put(offer.freeItem, numberOfRemovableItems - 1);
                            if (numberOfRemovableItems - 1 == 0) {
                                offerableSkusInBasket.remove(offer.freeItem);
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
