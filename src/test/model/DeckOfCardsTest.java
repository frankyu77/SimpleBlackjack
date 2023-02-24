package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class DeckOfCardsTest {
    private DeckOfCards cards;
    private Card one = new Card("1", 1);
    private Card two = new Card("2", 2);
    private Card three = new Card("3", 3);
    private Card four = new Card("4", 4);
    private Card king = new Card("K", 10);

    @BeforeEach
    void setup() {
        cards = new DeckOfCards();
    }

    @Test
    void testConstructor() {
        assertEquals(0, cards.getSize());
    }

    @Test
    void testAddOneCard() {
        cards.addCard(one);

        assertEquals(1, cards.getSize());
        assertTrue(cards.getCard(0) == one);
        assertTrue(cards.getCardName(0) == "1");
        assertTrue(cards.getCardValue(0) == 1);
    }

    @Test
    void testAddMultipleCards() {
        cards.addCard(two);
        cards.addCard(three);
        cards.addCard(four);

        assertEquals(3, cards.getSize());

        assertTrue(cards.getCard(0) == two);
        assertTrue(cards.getCard(1) == three);
        assertTrue(cards.getCard(2) == four);

        assertTrue(cards.getCardName(0) == "2");
        assertTrue(cards.getCardName(1) == "3");
        assertTrue(cards.getCardName(2) == "4");
        assertTrue(cards.getCardValue(0) == 2);
        assertTrue(cards.getCardValue(1) == 3);
        assertTrue(cards.getCardValue(2) == 4);

    }

    @Test
    void testAddDuplicateCards() {
        cards.addCard(king);
        cards.addCard(king);
        cards.addCard(three);
        cards.addCard(king);

        assertEquals(4, cards.getSize());

        assertTrue(cards.getCard(0) == king);
        assertTrue(cards.getCard(1) == king);
        assertTrue(cards.getCard(2) == three);
        assertTrue(cards.getCard(3) == king);

        assertTrue(cards.getCardName(0) == "K");
        assertTrue(cards.getCardName(1) == "K");
        assertTrue(cards.getCardName(2) == "3");
        assertTrue(cards.getCardName(3) == "K");
        assertTrue(cards.getCardValue(0) == 10);
        assertTrue(cards.getCardValue(1) == 10);
        assertTrue(cards.getCardValue(2) == 3);
        assertTrue(cards.getCardValue(3) == 10);
    }

}