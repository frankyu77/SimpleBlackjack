package model;

public class Card {
    // set up a card with the name and the card value assigned to that name
    private String cardName;
    private int cardValue;

    public Card(String name, int value) {
        this.cardName = name;
        this.cardValue = value;
    }

    public String getCardName() {
        return this.cardName;
    }

    public int getCardValue() {
        return this.cardValue;
    }

}
