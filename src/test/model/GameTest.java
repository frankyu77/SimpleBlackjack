package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ui.User.*;

import java.util.ArrayList;
import java.util.List;

class GameTest {
    private DeckOfCards cards;
    private Game game;
    private Card one = new Card("1", 1);
    private Card two = new Card("2", 2);
    private Card three = new Card("3", 3);
    private Card four = new Card("4", 4);
    private Card seven = new Card("7", 7);
    private Card king = new Card("K", 10);
    private Card queen = new Card("Q", 10);
    private Card ace = new Card("A", 11);

    private Player player;
    private Dealer dealer;

    @BeforeEach
    void setup() {
        cards = new DeckOfCards();
        cards.addCard(king);
        cards.addCard(seven);
        cards.addCard(two);
        cards.addCard(three);
        cards.addCard(one);
        cards.addCard(four);
        cards.addCard(ace);
        cards.addCard(queen);

        game = new Game(cards);
    }

    @Test
    void testConstructor() {
        assertEquals(game.getDeck(), cards);
    }

    @Test
    void testFirstTwoPlayerCards() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(two);

        assertEquals(pc, game.firstTwoPlayerCards());
        assertEquals(2, game.firstTwoPlayerCards().size());
        assertTrue(game.firstTwoPlayerCards().get(0) == king);
        assertTrue(game.firstTwoPlayerCards().get(1) == two);
    }

    @Test
    void testFirstTwoDealerCards() {
        List<Card> dc = new ArrayList<Card>();
        dc.add(seven);
        dc.add(three);

        assertEquals(dc, game.firstTwoDealerCards());
        assertEquals(2, game.firstTwoDealerCards().size());
        assertTrue(game.firstTwoDealerCards().get(0) == seven);
        assertTrue(game.firstTwoDealerCards().get(1) == three);
    }

    @Test
    void testPlayerWin() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(seven);
        pc.add(three);
        player = new Player(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(seven);
        dealer = new Dealer(dc);

        assertTrue(game.whoWins(player, dealer) == WIN);
    }

    @Test
    void testPlayerWentOver21() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(seven);
        pc.add(three);
        pc.add(two);
        player = new Player(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(seven);
        dealer = new Dealer(dc);

        assertTrue(game.whoWins(player, dealer) == LOSE);
    }

    @Test
    void testPlayerHitBJ() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(ace);
        player = new Player(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(seven);
        dealer = new Dealer(dc);

        assertTrue(game.whoWins(player, dealer) == PBJ);
    }

    @Test
    void testDealerWin() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(seven);
        player = new Player(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(seven);
        dc.add(three);
        dealer = new Dealer(dc);

        assertTrue(game.whoWins(player, dealer) == LOSE);
    }

    @Test
    void testDealerWentOver21() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(seven);
        player = new Player(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(seven);
        dc.add(three);
        dc.add(two);
        dealer = new Dealer(dc);

        assertTrue(game.whoWins(player, dealer) == WIN);
    }

    @Test
    void testDealerHitBJ() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(seven);
        player = new Player(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(ace);
        dealer = new Dealer(dc);

        assertTrue(game.whoWins(player, dealer) == DBJ);
    }

    @Test
    void testDraw() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(four);
        pc.add(three);
        player = new Player(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(seven);
        dealer = new Dealer(dc);

        assertTrue(game.whoWins(player, dealer) == DRAW);
    }

    @Test
    void testPlayerGreaterThan21() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(four);
        pc.add(three);
        pc.add(seven);
        player = new Player(pc);

        assertTrue(game.playerGreaterThan21(player));
    }

    @Test
    void testPlayerNotGreaterThan21() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(four);
        pc.add(three);
        player = new Player(pc);

        assertFalse(game.playerGreaterThan21(player));
    }

    @Test
    void testNotEnoughCardsInDeck() {
        DeckOfCards deck = new DeckOfCards();
        deck.addCard(one);
        deck.addCard(two);
        Game game1 = new Game(deck);
        assertTrue(game1.notEnoughCardsInDeck(3, deck));
        assertTrue(game1.notEnoughCardsInDeck(2, deck));
        assertFalse(game1.notEnoughCardsInDeck(1, deck));

        DeckOfCards deck1 = new DeckOfCards();
        deck1.addCard(four);
        deck1.addCard(two);
        deck1.addCard(king);
        deck1.addCard(queen);
        deck1.addCard(ace);
        Game game2 = new Game(deck1);
        assertTrue(game2.notEnoughCardsInDeck(5, deck1));
        assertFalse(game2.notEnoughCardsInDeck(4, deck1));
        assertTrue(game2.notEnoughCardsInDeck(6, deck1));
    }
}
