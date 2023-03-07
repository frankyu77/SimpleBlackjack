package model;

import java.util.List;

// Handles everything that has to do with the dealer:
// deals with when the dealer hits
public class Dealer {
    private List<Card> dealerCards;
    private int value;

    // MODIFIES: this
    // EFFECTS: creates a new dealer with the given hand
    public Dealer(List<Card> dealerHand) {
        this.dealerCards = dealerHand;
    }

    // MODIFIES: this
    // EFFECTS: gets the total value of the dealer hand
    public int getValue() {
        value = 0;
        for (int i = 0; i < dealerCards.size(); i++) {
            value += dealerCards.get(i).getCardValue();
        }

        return value;
    }

    // REQUIRES: numberOfTimesHit >= 0, deck.size() > 4
    // MODIFIES: this
    // EFFECTS: adds the card in the deck at the given index to the hand of the dealer
    public List<Card> dealerHits(int numberOfTimesHit, DeckOfCards deck) {
        this.dealerCards.add(deck.getCard(numberOfTimesHit));
        return dealerCards;
    }

    // EFFECTS: returns the size of the dealer hand
    public int getSize() {
        return this.dealerCards.size();
    }

    // EFFECTS: returns the dealer hand
    public List<Card> getDealerHand() {
        return dealerCards;
    }
}
