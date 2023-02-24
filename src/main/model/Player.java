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
        } else if (n == PBJ) {
            balance = current + amountBetted * 1.5;
            return balance;
        }
        balance = current + amountBetted;
        return balance;
    }

    public double getBalance() {
        return startingBalance;
    }

    public int getSize() {
        return this.playerCards.size();
    }

    public List<Card> getPlayerHand() {
        return playerCards;
    }
}
