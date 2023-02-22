package model;

import java.util.ArrayList;
import java.util.List;

import static ui.User.*;

public class Game {
    // Game class includes the WhoWins, Adds value of the cards, DealCards, etc.
    // all game related stuff
    private DeckOfCards deck;

    public Game(DeckOfCards cards) {
        this.deck = cards;
    }

    public List<Card> firstTwoPlayerCards() {
        List<Card> player1 = new ArrayList<>();

        if (this.deck.getSize() > 3) {
            for (int i = 0; i < 3; i += 2) {
                player1.add(this.deck.getCard(i));
            }
        }
        return player1;
    }

    public List<Card> firstTwoDealerCards() {
        List<Card> dealer1 = new ArrayList<>();

        if (this.deck.getSize() > 3) {
            for (int i = 1; i < 4; i += 2) {
                dealer1.add(this.deck.getCard(i));
            }
        }
        return dealer1;
    }

    public int whoWins(Player player, Dealer dealer) {
        if (dealer.getValue() == player.getValue() || player.getSize() == 2 && player.getValue() == 21
                && dealer.getSize() == 2 && dealer.getValue() == 21) {
            System.out.println("Draw!");
            return DRAW;
        } else if (player.getSize() == 2 && player.getValue() == 21) {
            System.out.println("You got a Black Jack!! You Win!");
            return PBJ;
        } else if (dealer.getSize() == 2 && dealer.getValue() == 21) {
            System.out.println("Dealer got a Black Jack. You Lose.");
            return DBJ;
        } else if (playerGreaterThan21(player) || (dealer.getValue() > player.getValue() && dealer.getValue() <= 21)) {
            System.out.println("You Lose!");
            return LOSE;
        } else if (dealerGreaterThan21(dealer) || player.getValue() > dealer.getValue()) {
            System.out.println("You Win!");
            return WIN;
        }
        return -1;
    }

    public boolean playerGreaterThan21(Player player) {
        if (player.getValue() > 21) {
            return true;
        }
        return false;
    }

    private boolean dealerGreaterThan21(Dealer dealer) {
        if (dealer.getValue() > 21) {
            return true;
        }
        return false;
    }

    public boolean notEnoughCardsInDeck(int n, DeckOfCards deck) {
        if (n >= deck.getSize()) {
            return true;
        }
        return false;
    }
}

