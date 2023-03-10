package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Handles everything that has to do with the dealer:
// deals with when the dealer hits
public class Dealer implements Writable {
    private List<Card> dealerCards;
    private int value;

    // MODIFIES: this
    // EFFECTS: creates a new dealer with the given hand
    public Dealer() {
        //this.dealerCards = dealerHand;
        this.dealerCards = new ArrayList<>();
    }

    public List<Card> setDealerHand(List<Card> dealerCards) {
        this.dealerCards = dealerCards;
        return this.dealerCards;
    }

    public int getCardValue(int index) {
        return this.dealerCards.get(index).getCardValue();
    }

    public String getCardName(int index) {
        return this.dealerCards.get(index).getCardName();
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

    public void setValue(int value) {
        this.value = value;
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

    public Card getCard(int index) {
        return this.dealerCards.get(index);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("valueD", value);
        json.put("dealerCards", cardsToJson());
        return json;
    }

    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card c : dealerCards) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

//    public List<Card> addCards(Card card) {
//        this.dealerCards.add(card);
//        return this.dealerCards;
//    }
}
