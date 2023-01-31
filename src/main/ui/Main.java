package ui;

import model.DealCards;
import model.DeckOfCards;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DeckOfCards decks = new DeckOfCards();
        // another way of printing out the deck of cards:
        //decks.getCards();
        //System.out.println(" ");

        //printing out entire deck
        List<String> x = DeckOfCards.getDeck();
        for (int i = 0; i < x.size(); i++) {
            System.out.print(x.get(i) + " ");
        }
        System.out.println(" ");

        //player hand
        for (int i = 0; i < 3; i += 2) {
            System.out.print(x.get(i) + " ");
            x.remove(i);
        }
        System.out.println(" ");

        //dealer hand
        for (int i = 1; i < 4; i += 2) {
            System.out.print(x.get(i) + " ");
            x.remove(i);
        }
        System.out.println(" ");

        //what's left of the deck after dealing
        for (int i = 0; i < x.size(); i++) {
            System.out.print(x.get(i) + " ");
        }
    }
}
