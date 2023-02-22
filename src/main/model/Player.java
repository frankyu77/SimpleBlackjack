package model;

import java.util.List;

import static ui.User.*;


public class Player {
    private List<Card> playerCards;
    private int value;
    private double startingBalance;
    private double balance;

    public Player(List<Card> playerHand) {
        this.playerCards = playerHand;
        this.startingBalance = 15;
    }

    public int getValue() {
        value = 0;
        for (int i = 0; i < playerCards.size(); i++) {
            value += playerCards.get(i).getCardValue();
        }
        return value;
    }

    public List<Card> playerHits(int numberOfTimesHit, DeckOfCards deck) {
        this.playerCards.add(deck.getCard(numberOfTimesHit));
        return playerCards;
    }

    public int getSize() {
        return this.playerCards.size();
    }

    public double playerMoney(int n, double amountBetted, double current) {
        if (n == WIN) {
            return current + amountBetted * 2;
        } else if (n == LOSE || n == DBJ) {
            return current;
        } else if (n == DRAW) {
            return current + amountBetted;
        } else if (n == PBJ) {
            return current + amountBetted + amountBetted * 1.5;
        }
        return current + amountBetted;
    }

    public double getBalance() {
        return startingBalance;
    }

    public List<Card> getPlayerHand() {
        return playerCards;
    }
}
