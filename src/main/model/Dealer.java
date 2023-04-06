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
        this.dealerCards = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: sets dealer hand to list of cards given
    public List<Card> setDealerHand(List<Card> dealerCards) {
        this.dealerCards = dealerCards;
        return this.dealerCards;
    }

    // EFFECTS: returns card value at given index
    public int getCardValue(int index) {
        return this.dealerCards.get(index).getCardValue();
    }

    // EFFECTS: returns card name at given index
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

    // MODIFIES: this
    // EFFECTS: sets value to given value
    public void setValue(int value) {
        this.value = value;
    }

    // REQUIRES: numberOfTimesHit >= 0, deck.size() > 4
    // MODIFIES: this
    // EFFECTS: adds the card in the deck at the given index to the hand of the dealer
    public List<Card> dealerHits(int numberOfTimesHit, DeckOfCards deck) {
        this.dealerCards.add(deck.getCard(numberOfTimesHit));
        EventLog.getInstance().logEvent(new Event("Dealer hits"));
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

    // EFFECTS: returns card at given index
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

    // EFFECTS: returns dealerCards as a jsonArray
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card c : dealerCards) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}
