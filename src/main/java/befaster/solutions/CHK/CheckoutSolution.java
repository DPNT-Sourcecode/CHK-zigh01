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
        priceMap.put('K', 80);
        priceMap.put('L', 90);
        priceMap.put('M', 15);
        priceMap.put('N', 40);
        priceMap.put('O', 10);
        priceMap.put('P', 50);
        priceMap.put('Q', 30);
        priceMap.put('R', 50);
        priceMap.put('S', 30);
        priceMap.put('T', 20);
        priceMap.put('U', 40);
        priceMap.put('V', 50);
        priceMap.put('W', 20);
        priceMap.put('X', 90);
        priceMap.put('Y', 10);
        priceMap.put('Z', 50);

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
        offersDetails.put('U', new ArrayList<>());
        offersDetails.put('V', new ArrayList<>());

        offers = Map.of(
            Type.GET_ONE_FREE, List.of('E', 'F', 'N', 'R', 'U'),
            Type.DISCOUNT, List.of('A', 'B', 'H', 'K', 'P', 'Q', 'V')
        );

        offersDetails.get('A').add(new Offer(Type.DISCOUNT, 5,   priceMap.get('A') * 5 - 200, null));
        offersDetails.get('A').add(new Offer(Type.DISCOUNT, 3, priceMap.get('A') * 3 - 130, null));
        offersDetails.get('B').add(new Offer(Type.DISCOUNT, 2, priceMap.get('B') * 2 - 45, null));
        offersDetails.get('H').add(new Offer(Type.DISCOUNT, 10, priceMap.get('H') * 10 - 80, null));
        offersDetails.get('H').add(new Offer(Type.DISCOUNT, 5, priceMap.get('H') * 5 - 45, null));
        offersDetails.get('K').add(new Offer(Type.DISCOUNT, 2, priceMap.get('K') * 2 - 150, null));
        offersDetails.get('P').add(new Offer(Type.DISCOUNT, 5, priceMap.get('P') * 5 - 200, null));
        offersDetails.get('Q').add(new Offer(Type.DISCOUNT, 3, priceMap.get('Q') * 3 - 80, null));
        offersDetails.get('V').add(new Offer(Type.DISCOUNT, 3, priceMap.get('V') * 3 - 130, null));
        offersDetails.get('V').add(new Offer(Type.DISCOUNT, 2, priceMap.get('V') * 2 - 90, null));


        offersDetails.get('E').add(new Offer(Type.GET_ONE_FREE, 2, priceMap.get('B'), 'B'));
        offersDetails.get('F').add(new Offer(Type.GET_ONE_FREE, 2, priceMap.get('F'), 'F'));
        offersDetails.get('N').add(new Offer(Type.GET_ONE_FREE, 3, priceMap.get('M'), 'M'));
        offersDetails.get('R').add(new Offer(Type.GET_ONE_FREE, 3, priceMap.get('Q'), 'Q'));
        offersDetails.get('U').add(new Offer(Type.GET_ONE_FREE, 3, priceMap.get('U'), 'U'));
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


