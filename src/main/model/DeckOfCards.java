package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DeckOfCards {
    private static List<String> cards = new ArrayList<>();

    public DeckOfCards() {
        cards = Arrays.asList("A", "9", "3", "5", "Q", "7", "6", "2", "8", "3", "A");
    }

    public String getCards() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.print(cards.get(i) + " ");
        }
        return "empty";
    }

    public static List<String> getDeck() {
        return cards;
    }
}
