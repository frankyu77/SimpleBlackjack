package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ui.User.*;

class PlayerTest {
    private Player player;
    private List<Card> playerCards;
    private DeckOfCards deck;
    private int value;
    private double startingBalance;
    private double balance;

    private Card one = new Card("1", 1);
    private Card two = new Card("2", 2);
    private Card three = new Card("3", 3);
    private Card four = new Card("4", 4);
    private Card seven = new Card("7", 7);
    private Card king = new Card("K", 10);
    private Card queen = new Card("Q", 10);
    private Card ace = new Card("A", 11);

    @BeforeEach
    void setup() {
        deck = new DeckOfCards();
        deck.addCard(king);
        deck.addCard(seven);
        deck.addCard(two);
        deck.addCard(three);
        deck.addCard(one);
        deck.addCard(four);
        deck.addCard(ace);
        deck.addCard(queen);

        playerCards = new ArrayList<Card>();
        playerCards.add(king);
        playerCards.add(two);

        value = 0;
        startingBalance = 15;
        balance = 0;

        player = new Player();
        player.setPlayerHand(playerCards);
    }

    @Test
    void testConstructor() {
        assertEquals(player.getPlayerHand(), playerCards);
        assertEquals(player.getSize(), 2);
        assertEquals(player.getCard(0), king);
        assertEquals(player.getCard(1), two);
    }

    @Test
    void testGetValue() {
        assertEquals(player.getValue(), 12);
    }

    @Test
    void testPlayerHits() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(two);
        pc.add(one);
        assertEquals(player.playerHits(4, deck), pc);
        assertEquals(player.getValue(), 13);

        pc.add(four);
        assertEquals(player.playerHits(5, deck), pc);
        assertEquals(player.getValue(), 17);

        pc.add(queen);
        assertEquals(player.playerHits(7, deck), pc);
        assertEquals(player.getValue(), 27);
    }

    @Test
    void testPlayerWinMoney() {
        assertEquals(player.playerMoney(WIN, 10), 35);
        assertEquals(player.playerMoney(WIN, 1), 37);
        assertEquals(player.playerMoney(WIN, 1.25), 39.5);

        assertEquals(player.playerMoney(WIN, 1.25), 42);
    }

    @Test
    void testPlayerLoseMoney() {
        assertEquals(player.playerMoney(LOSE, 10), 15);
        assertEquals(player.playerMoney(DBJ, 2.5), 15);
    }

    @Test
    void testPlayerDraw() {
        assertEquals(player.playerMoney(DRAW, 10),25);
        assertEquals(player.playerMoney(DRAW, 2.3),27.3);
        assertEquals(player.playerMoney(DRAW, 20),47.3);

        assertEquals(player.playerMoney(DRAW, 2.45),49.75);
    }

    @Test
    void testPlayerBJ() {
        assertEquals(player.playerMoney(PBJ, 10), 40);
        assertEquals(player.playerMoney(PBJ, 24), 100);

        assertEquals(player.playerMoney(PBJ, 2.6), 106.5);
    }

    @Test
    void testBetMade() {
        assertEquals(player.betMade(2), 13);
        assertEquals(player.betMade(5), 8);
        assertEquals(player.betMade(5.42), 2.58);
    }


}
