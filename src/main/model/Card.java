package model;

import org.json.JSONObject;
import persistence.Writable;

// set up a card with the name and the card value assigned to that name
public class Card implements Writable {
    private String cardName;
    private int cardValue;

    // REQUIRES: name.length() > 0, value >= 0
    // EFFECTS: creates a card with the given name and given value
    public Card(String name, int value) {
        this.cardName = name;
        this.cardValue = value;
    }

    // EFFECTS: returns the card name
    public String getCardName() {
        return this.cardName;
    }

    // EFFECTS: returns the card value
    public int getCardValue() {
        return this.cardValue;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cardName", cardName);
        json.put("cardValue", cardValue);
        return json;
    }

}
