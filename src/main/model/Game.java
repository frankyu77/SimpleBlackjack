package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

import static ui.User.*;

// Game class includes the who wins in a game, adds the value of the cards, deals the cards out, and checks if there are
// not enough cards in the deck
// also checks when player/dealer value is greater than 21
public class Game implements Writable {
    private DeckOfCards deck;
    private Player player;
    private Dealer dealer;

    // REQUIRES: cards.size() >= 4
    // EFFECTS: creates game with the DeckOfCards inputted
//    public Game(DeckOfCards cards, Player player, Dealer dealer) {
//        this.deck = cards;
//        this.player = player;
//        this.dealer = dealer;
//    }

    // EFFECTS: creates a new game
    public Game() {
        this.player = new Player();
        this.dealer = new Dealer();
        this.deck = new DeckOfCards();
    }

    // EFFECTS: prints out the first two cards for the player
    public List<Card> firstTwoPlayerCards() {
        List<Card> player1 = new ArrayList<>();
        if (this.deck.getSize() > 3) {
            for (int i = 0; i < 3; i += 2) {
                player1.add(this.deck.getCard(i));
            }
        }
        this.player.setPlayerHand(player1);
        //return this.player;
        return player1;
    }

    // EFFECTS: prints out the first two cards for the dealer
    public List<Card> firstTwoDealerCards() {
        List<Card> dealer1 = new ArrayList<>();
        if (this.deck.getSize() > 3) {
            for (int i = 1; i < 4; i += 2) {
                dealer1.add(this.deck.getCard(i));
            }
        }
        this.dealer.setDealerHand(dealer1);
        //return dealer;
        return dealer1;
    }

    // EFFECTS: handles who will win, whether player win, lose, draw, or player hit blackjack, or dealer hit blackjack
    public int whoWins(Player player, Dealer dealer) {
        if ((player.getSize() == 2 && player.getValue() == 21 && dealer.getSize() == 2 && dealer.getValue() == 21)
                || (dealer.getValue() == player.getValue())) {
            return DRAW;
        } else if (player.getSize() == 2 && player.getValue() == 21) {
            return PBJ;
        } else if (dealer.getSize() == 2 && dealer.getValue() == 21) {
            return DBJ;
        } else if (playerGreaterThan21(player) || (dealer.getValue() > player.getValue() && dealer.getValue() <= 21)) {
            return LOSE;
        } else {
            return WIN;
        }
    }

    // EFFECTS: return true if player card values are greater than 21, else false
    public boolean playerGreaterThan21(Player player) {
        if (player.getValue() > 21) {
            return true;
        }
        return false;
    }

    // EFFECTS: return true if dealer card values are greater than 21, else false
    public boolean dealerGreaterThan21(Dealer dealer) {
        if (dealer.getValue() > 21) {
            return true;
        }
        return false;
    }

    // REQUIRES: n >= 0, deck.size() > 4
    // EFFECTS: if the next card to be drawn is greater than the length of the deck (deck is too small), then return
    //          true, else false
    public boolean notEnoughCardsInDeck(int n, DeckOfCards deck) {
        if (n >= deck.getSize()) {
            return true;
        }
        return false;
    }

    // EFFECTS: returns the currents deck of the game
    public DeckOfCards getDeck() {
        return this.deck;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public void setDeckOfCards(DeckOfCards deck1) {
        this.deck = deck1;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("deck", deck.toJson());
        json.put("player", player.toJson());
        json.put("dealer", dealer.toJson());
        return json;
    }



}

