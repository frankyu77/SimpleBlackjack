package model;

import java.util.List;

public class Dealer {
    private List<String> dealerCards;

    public Dealer(List<String> dealerHand) {
        this.dealerCards = dealerHand;
    }

    public int dealerCount() {
        return 0;
    }

    public List<String> getDealerHand() {
        return dealerCards;
    }
}
