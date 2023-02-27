package model;

import java.util.List;

import static ui.User.*;


public class Player {
    private List<Card> playerCards;
    private int value;
    private double startingBalance;
    private double balance;

    // MODIFIES: this
    // EFFECTS: creates a new player with the given hand
    public Player(List<Card> playerHand) {
        this.playerCards = playerHand;
        this.startingBalance = 15;
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
    // MODIFIES: this
    // EFFECTS: if player wins normally, then they win double the amount they bet, if they lose, they don't get the
    //          amount they bet back, if they draw, they just get the amount they bet back, if player hit blackjack,
    //          they win back 1.5x the amount that they bet
    public double playerMoney(int n, double amountBetted, double current) {
        if (n == WIN) {
            balance = current + amountBetted * 2;
            return balance;
        } else if (n == LOSE || n == DBJ) {
            balance = current;
            return balance;
        } else if (n == DRAW) {
            balance = current + amountBetted;
            return balance;
        } else {
            balance = current + amountBetted * 1.5;
            return balance;
        }
    }

    // EFFECTS: returns the balance of the player
    public double getBalance() {
        return startingBalance;
    }

    // EFFECTS: returns the size of the player hand
    public int getSize() {
        return this.playerCards.size();
    }

    // EFFECTS: returns the player's hand
    public List<Card> getPlayerHand() {
        return playerCards;
    }
}
