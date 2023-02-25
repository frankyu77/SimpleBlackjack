package model;

public class Card {
    // set up a card with the name and the card value assigned to that name
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

}
