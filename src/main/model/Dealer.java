package model;

import java.util.List;

public class Dealer {
    private List<Card> dealerCards;
    private int value;

    public Dealer(List<Card> dealerHand) {
        this.dealerCards = dealerHand;
    }

    public int getValue() {
        value = 0;
        for (int i = 0; i < dealerCards.size(); i++) {
            value += dealerCards.get(i).getCardValue();
        }

        return value;
    }

    public List<Card> dealerHits(int numberOfTimesHit, DeckOfCards deck) {
        this.dealerCards.add(deck.getCard(numberOfTimesHit));
        return dealerCards;
    }

    public int getSize() {
        return this.dealerCards.size();
    }

    public List<Card> getDealerHand() {
        return dealerCards;
    }
}
