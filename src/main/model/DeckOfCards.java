package model;

import java.util.ArrayList;
import java.util.List;

public class DeckOfCards {
    private static List<Card> cards;

    public DeckOfCards() {
        cards = new ArrayList<Card>();
    }

    public List<Card> addCard(Card card) {
        cards.add(card);
        return this.cards;
    }

    public int getSize() {
        return cards.size();
    }

    public Card getCard(int n) {
        return cards.get(n);
    }

    public String getCardName(int n) {
        return cards.get(n).getCardName();
    }

    public int getCardValue(int n) {
        return cards.get(n).getCardValue();
    }

}