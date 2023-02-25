package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card card1 = new Card("K", 10);
    Card card2 = new Card("2", 2);
    Card card3 = new Card("Ace", 11);
    Card card4 = new Card("two", 2);
    Card card5 = new Card("5", 15);
    Card card6 = new Card("fIVe", 5);
    Card card7 = new Card("1", 21);

    @Test
    void testConstructor() {
        assertEquals(card1.getCardName(), "K");
        assertEquals(card1.getCardValue(), 10);

        assertEquals(card2.getCardName(), "2");
        assertEquals(card2.getCardValue(), 2);

        assertEquals(card3.getCardName(), "Ace");
        assertEquals(card3.getCardValue(), 11);

        assertEquals(card4.getCardName(), "two");
        assertEquals(card4.getCardValue(), 2);

        assertEquals(card5.getCardName(), "5");
        assertEquals(card5.getCardValue(), 15);

        assertEquals(card6.getCardName(), "fIVe");
        assertEquals(card6.getCardValue(), 5);

        assertEquals(card7.getCardName(), "1");
        assertEquals(card7.getCardValue(), 21);
    }
}
