package model;

import java.util.List;

import static ui.User.*;

// Handles everything that has to do with the player (user):
// deals with when the player hits, handles the player's money when they win/lose/draw
public class Player {
    private List<Card> playerCards;
    private int value;

    // MODIFIES: this
    // EFFECTS: creates a new player with the given hand
    public Player(List<Card> playerHand) {
        this.playerCards = playerHand;
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
            current = current + amountBetted * 2;
            return current;
        } else if (n == LOSE || n == DBJ) {
            return current;
        } else if (n == DRAW) {
            current = current + amountBetted;
            return current;
        } else {
            current = current + amountBetted * 2.5;
            return current;
        }
    }

    // EFFECTS: returns the current money minus the bet made
    public double betMade(double bet, double currentMoney) {
        return currentMoney - bet;
    }

    // EFFECTS: returns the original money before the bet was made
    public double originalMoney(double bet, double currentMouney) {
        return currentMouney + bet;
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
