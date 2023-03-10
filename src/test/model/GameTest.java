package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ui.User.*;

import java.util.ArrayList;
import java.util.List;

class GameTest {
    DeckOfCards cards;
    Game game;
    DeckOfCards cards2;
    Game game2;
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
        game = new Game();
    }

    @Test
    void testConstructor() {
        game.setDeckOfCards(cards);
        assertEquals(game.getDeck(), cards);
    }
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void testFirstTwoPlayerCards() {
        DeckOfCards pc = new DeckOfCards();
        pc.addCard(king);
        pc.addCard(four);
        pc.addCard(two);
        pc.addCard(seven);
        game2 = new Game();
        game2.setDeckOfCards(pc);

        List<Card> cards = new ArrayList<>();
        cards.add(king);
        cards.add(two);
        Player player = new Player();
        player.setPlayerHand(cards);

        assertEquals(player.getPlayerHand(), game2.firstTwoPlayerCards());
        assertEquals(game2.firstTwoPlayerCards().size(), 2);
        assertEquals(2, player.getSize());
        assertTrue(game2.firstTwoPlayerCards().get(0) == king);
        assertTrue(game2.firstTwoPlayerCards().get(1) == two);
    }

    @Test
    void testNotEnoughCardsForPlayerHand() {
        cards2 = new DeckOfCards();
        cards2.addCard(two);
        cards2.addCard(one);
        game2 = new Game();

        List<Card> cards = new ArrayList<>();
        Player player = new Player();
        player.setPlayerHand(cards);

        assertEquals(player.getPlayerHand(), game2.firstTwoPlayerCards());
        assertEquals(0, game2.firstTwoPlayerCards().size());
    }
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void testFirstTwoDealerCards() {
        game2 = new Game();
        game2.setDeckOfCards(cards);

        List<Card> cards = new ArrayList<>();
        cards.add(seven);
        cards.add(three);
        Dealer dealer1 = new Dealer();
        dealer1.setDealerHand(cards);

        assertEquals(dealer1.getDealerHand(), game2.firstTwoDealerCards());
        assertEquals(2, game2.firstTwoDealerCards().size());
        assertTrue(game2.firstTwoDealerCards().get(0) == seven);
        assertTrue(game2.firstTwoDealerCards().get(1) == three);
    }

    @Test
    void testNotEnoughCardsForDealerHand() {
        cards2 = new DeckOfCards();
        cards2.addCard(two);
        cards2.addCard(one);
        game2 = new Game();

        List<Card> cards = new ArrayList<>();
        Dealer dealer = new Dealer();
        dealer.setDealerHand(cards);

        assertEquals(dealer.getDealerHand(), game.firstTwoDealerCards());
        assertEquals(0, game2.firstTwoDealerCards().size());
    }
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void testPlayerWin() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(seven);
        pc.add(three);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(seven);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == WIN);
    }

    @Test
    void testPlayerWinByGetting21() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(seven);
        pc.add(three);
        pc.add(one);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(seven);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == WIN);
    }

    @Test
    void testPlayerWentOver21ByOne() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(seven);
        pc.add(three);
        pc.add(two);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(seven);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == LOSE);
    }

    @Test
    void testPlayerWentOver21ByALot() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(seven);
        pc.add(three);
        pc.add(two);
        pc.add(queen);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(seven);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == LOSE);
    }

    @Test
    void testPlayerHitBJ() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(king);
        pc.add(ace);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(seven);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == PBJ);
    }
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void testDealerWin() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(seven);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(seven);
        dc.add(three);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == LOSE);
    }

    @Test
    void testDealerWinByGetting21() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(seven);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(seven);
        dc.add(three);
        dc.add(one);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == LOSE);
    }

    @Test
    void testDealerWentOver21ByOne() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(seven);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(seven);
        dc.add(three);
        dc.add(two);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == WIN);
    }

    @Test
    void testDealerWentOver21ByALot() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(seven);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(four);
        dc.add(two);
        dc.add(queen);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == WIN);
    }

    @Test
    void testDealerHitBJ() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(seven);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(ace);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == DBJ);
    }
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void testDraw() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(four);
        pc.add(three);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(seven);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == DRAW);
    }

    @Test
    void testDrawBothHit21() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(four);
        pc.add(three);
        pc.add(four);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(four);
        dc.add(seven);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == DRAW);
    }

    @Test
    void testDrawBothHaveTwoCards() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(two);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(two);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == DRAW);
    }

    @Test
    void testDrawBothHitBJ() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(ace);
        player = new Player();
        player.setPlayerHand(pc);

        List<Card> dc = new ArrayList<Card>();
        dc.add(king);
        dc.add(ace);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.whoWins(player, dealer) == DRAW);
    }
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void testPlayerGreaterThan21ByOne() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(four);
        pc.add(three);
        pc.add(seven);
        player = new Player();
        player.setPlayerHand(pc);

        assertTrue(game.playerGreaterThan21(player));
    }

    @Test
    void testPlayerGreaterThan21ByALot() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(four);
        pc.add(three);
        pc.add(four);
        pc.add(one);
        player = new Player();
        player.setPlayerHand(pc);

        assertTrue(game.playerGreaterThan21(player));
    }

    @Test
    void testPlayerNotGreaterThan21ByOne() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(four);
        pc.add(three);
        pc.add(three);
        player = new Player();
        player.setPlayerHand(pc);

        assertFalse(game.playerGreaterThan21(player));
    }

    @Test
    void testPlayerNotGreaterThan21ByALot() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(two);
        player = new Player();
        player.setPlayerHand(pc);

        assertFalse(game.playerGreaterThan21(player));
    }

    @Test
    void testPlayerAt21() {
        List<Card> pc = new ArrayList<Card>();
        pc.add(queen);
        pc.add(four);
        pc.add(three);
        pc.add(four);
        player = new Player();
        player.setPlayerHand(pc);

        assertFalse(game.playerGreaterThan21(player));
    }
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void testDealerGreaterThan21ByOne() {
        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(four);
        dc.add(three);
        dc.add(seven);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.dealerGreaterThan21(dealer));
    }

    @Test
    void testDealerGreaterThan21ByALot() {
        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(four);
        dc.add(three);
        dc.add(four);
        dc.add(one);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertTrue(game.dealerGreaterThan21(dealer));
    }

    @Test
    void testDealerNotGreaterThan21ByOne() {
        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(four);
        dc.add(three);
        dc.add(three);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertFalse(game.dealerGreaterThan21(dealer));
    }

    @Test
    void testDealerNotGreaterThan21ByALot() {
        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(two);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertFalse(game.dealerGreaterThan21(dealer));
    }

    @Test
    void testDealerAt21() {
        List<Card> dc = new ArrayList<Card>();
        dc.add(queen);
        dc.add(four);
        dc.add(three);
        dc.add(four);
        dealer = new Dealer();
        dealer.setDealerHand(dc);

        assertFalse(game.dealerGreaterThan21(dealer));
    }
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void testNotEnoughCardsInDeck() {
        DeckOfCards deck = new DeckOfCards();
        deck.addCard(one);
        deck.addCard(two);
        Game game1 = new Game();
        assertTrue(game1.notEnoughCardsInDeck(3, deck));
        assertTrue(game1.notEnoughCardsInDeck(2, deck));
        assertFalse(game1.notEnoughCardsInDeck(1, deck));

        DeckOfCards deck1 = new DeckOfCards();
        deck1.addCard(four);
        deck1.addCard(two);
        deck1.addCard(king);
        deck1.addCard(queen);
        deck1.addCard(ace);
        Game game2 = new Game();
        assertTrue(game2.notEnoughCardsInDeck(5, deck1));
        assertFalse(game2.notEnoughCardsInDeck(4, deck1));
        assertTrue(game2.notEnoughCardsInDeck(6, deck1));
    }
}
