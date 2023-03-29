package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

import static ui.User.*;

// Handles everything that has to do with the player (user):
// deals with when the player hits, handles the player's money when they win/lose/draw
public class Player implements Writable {
    private List<Card> playerCards;
    private int value;
    private double money;
    private double bet;

    // MODIFIES: this
    // EFFECTS: creates a new player with the given hand
    public Player() {
        this.playerCards = new ArrayList<>();
        this.money = 15;
    }

    // MODIFIES: this
    // EFFECTS: sets the playerCards with given cards
    public void setPlayerHand(List<Card> playerCards) {
        this.playerCards = playerCards;
    }

    // MODIFIES: this
    // EFFECTS: gets the total value of the player hand
    public int getValue() {
        value = 0;
        for (int i = 0; i < playerCards.size(); i++) {
            value += playerCards.get(i).getCardValue();
        }
        return value;
    }

    // REQUIRES: numberOfTimesHit >= 0, deck.size() > 4
    // MODIFIES: this
    // EFFECTS: adds the card in the deck at the given index to the hand of the player
    public List<Card> playerHits(int numberOfTimesHit, DeckOfCards deck) {
        this.playerCards.add(deck.getCard(numberOfTimesHit));
        return playerCards;
    }

    // REQUIRES: n >= 0, amountBetted >= 0, current >= 0
    // MODIFIES: this, money
    // EFFECTS: if player wins normally, then they win double the amount they bet, if they lose, they don't get the
    //          amount they bet back, if they draw, they just get the amount they bet back, if player hit blackjack,
    //          they win back 1.5x the amount that they bet
    public double playerMoney(int n, double amountBetted) {
        if (n == WIN) {
            this.money += amountBetted * 2;
            return this.money;
        } else if (n == LOSE || n == DBJ) {
            return this.money;
        } else if (n == DRAW) {
            this.money += amountBetted;
            return this.money;
        } else {
            this.money += amountBetted * 2.5;
            return this.money;
        }
    }

    // REQUIRES: bet <= currentMoney && bet > 0
    // EFFECTS: returns the current money minus the bet made
    public double betMade(double bet) {
        this.money -= bet;
        return this.money;
    }

    // EFFECTS: return the player money
    public double getBalance() {
        return this.money;
    }

    // MODIFIES: this
    // EFFECTS: sets the player money
    public double setMoney(double prevMoney) {
        this.money = prevMoney;
        return money;
    }

    // MODIFIES: this
    // EFFECTS: sets the player bet amount
    public void setBet(double bet) {
        this.bet = bet;
    }

    // EFFECTS: returns bet
    public double getBet() {
        return this.bet;
    }

    // MODIFIES: this
    // EFFECTS: set the value of the player hand
    public void setValue(int value) {
        this.value = value;
    }

    // EFFECTS: return card at given index
    public Card getCard(int index) {
        return this.playerCards.get(index);
    }

    // EFFECTS: returns card value at given index
    public int getCardValue(int index) {
        return this.playerCards.get(index).getCardValue();
    }

    // EFFECTS: returns the card name at given index
    public String getCardName(int index) {
        return this.playerCards.get(index).getCardName();
    }

    // EFFECTS: returns the size of the player hand
    public int getSize() {
        return this.playerCards.size();
    }

    // EFFECTS: returns the player's hand
    public List<Card> getPlayerHand() {
        return playerCards;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", this.value);
        json.put("money", this.money);
        json.put("playerCards", cardsToJson());
        json.put("bet", this.bet);
        return json;
    }

    // EFFECTS: returns playerCards as a JSON array
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card c : this.playerCards) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

}
