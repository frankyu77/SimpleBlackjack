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
        EventLog.getInstance().logEvent(new Event("First two Player cards dealt"));
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
        EventLog.getInstance().logEvent(new Event("First two Dealer cards dealt"));
        return dealer1;
    }

    // EFFECTS: handles who will win, whether player win, lose, draw, or player hit blackjack, or dealer hit blackjack
    public int whoWins(Player player, Dealer dealer) {
        if ((player.getSize() == 2 && player.getValue() == 21 && dealer.getSize() == 2 && dealer.getValue() == 21)
                || (dealer.getValue() == player.getValue())) {
            EventLog.getInstance().logEvent(new Event("Draw!"));
            return DRAW;
        } else if (player.getSize() == 2 && player.getValue() == 21) {
            EventLog.getInstance().logEvent(new Event("Player wins blackjack!"));
            return PBJ;
        } else if (dealer.getSize() == 2 && dealer.getValue() == 21) {
            EventLog.getInstance().logEvent(new Event("Dealer wins blackjack!"));
            return DBJ;
        } else if (playerGreaterThan21(player) || (dealer.getValue() > player.getValue() && dealer.getValue() <= 21)) {
            EventLog.getInstance().logEvent(new Event("Player loses!"));
            return LOSE;
        } else {
            EventLog.getInstance().logEvent(new Event("Player wins!"));
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
            EventLog.getInstance().logEvent(new Event("Not enough cards in deck"));
            return true;
        }
        return false;
    }

    // EFFECTS: returns the currents deck of the game
    public DeckOfCards getDeck() {
        return this.deck;
    }

    // MODIFIES: this
    // EFFECTS: sets player to given player
    public void setPlayer(Player player) {
        this.player = player;
    }

    // EFFECTS: returns player
    public Player getPlayer() {
        return this.player;
    }

    // EFFECTS: returns dealer
    public Dealer getDealer() {
        return this.dealer;
    }

    // MODIFIES: this
    // EFFECTS: sets dealer to given dealer
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    // MODIFIES: this
    // EFFECTS: sets deck to given deck
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

