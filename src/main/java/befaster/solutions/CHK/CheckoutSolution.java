package befaster.solutions.CHK;


import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {
    private Map<Character, Integer> priceMap;
    private Map<Character, Map<Integer, Integer>> offers;

    public CheckoutSolution() {
        priceMap = Map.of(
            'A', 50,
            'B', 30,
            'C', 20,
            'D',15
        );
        offers = Map.of(
            'A', Map.of()
        )
    }

    public Integer checkout(String skus) {


    }
}


