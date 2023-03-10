package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// creates List<Card> which is the DeckOfCards that the game will work on
public class DeckOfCards implements Writable {
    private List<Card> cards;

    // EFFECTS: creates a new DeckOfCards
    public DeckOfCards() {
        cards = new ArrayList<Card>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given card to the end of the deck
    public List<Card> addCard(Card card) {
        cards.add(card);
        return this.cards;
    }

    // EFFECTS: returns the size of the deck
    public int getSize() {
        return cards.size();
    }

    // EFFECTS: returns the card at the given index
    public Card getCard(int n) {
        return cards.get(n);
    }

    // EFFECTS: returns the card name at the given index
    public String getCardName(int n) {
        return cards.get(n).getCardName();
    }

    // EFFECTS: returns the card value at the given index
    public int getCardValue(int n) {
        return cards.get(n).getCardValue();
    }



//    private List<String> getDeckNames() {
//        for (Card card: cards) {
//            cardNames.add(card.getCardName());
//        }
//        return cardNames;
//    }
//
//    private List<Integer> getDeckValues() {
//        for (Card card: cards) {
//            cardValues.add(card.getCardValue());
//        }
//        return cardValues;
//    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("deckOfCards", cardsToJson());
        return json;
    }

    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card c : cards) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }
}