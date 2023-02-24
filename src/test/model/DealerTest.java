package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    private Dealer dealer;
    private List<Card> dealerCards;
    private DeckOfCards deck;
    private int value;

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

        dealerCards = new ArrayList<Card>();
        dealerCards.add(seven);
        dealerCards.add(three);

        value = 0;

        dealer = new Dealer(dealerCards);
    }

    @Test
    void testConstructor() {
        assertEquals(dealer.getDealerHand(), dealerCards);
        assertEquals(dealer.getSize(), 2);
    }

    @Test
    void testGetValue() {
        assertEquals(dealer.getValue(), 10);
    }

    @Test
    void testDealerHits() {
        List<Card> dc = new ArrayList<Card>();
        dc.add(seven);
        dc.add(three);
        dc.add(one);
        assertEquals(dealer.dealerHits(4, deck), dc);

        dc.add(four);
        assertEquals(dealer.dealerHits(5, deck), dc);

        dc.add(queen);
        assertEquals(dealer.dealerHits(7, deck), dc);
    }
}
