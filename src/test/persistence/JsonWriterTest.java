package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Game game = new Game();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGame() {
        try {
            Game game = new Game();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGame.json");
            game = reader.read();
            assertEquals(0, game.getPlayer().getPlayerHand().size());
            assertEquals(15, game.getPlayer().getBalance());
            assertEquals(0, game.getPlayer().getValue());

            assertEquals(0, game.getDealer().getDealerHand().size());
            assertEquals(0, game.getDealer().getValue());

            assertEquals(0, game.getDeck().getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            DeckOfCards cards = new DeckOfCards();
            Card one = new Card("1", 1);
            Card two = new Card("2", 2);
            Card three = new Card("3", 3);
            Card four = new Card("4", 4);
            Card king = new Card("K", 10);
            cards.addCard(one);
            cards.addCard(two);
            cards.addCard(three);
            cards.addCard(four);
            cards.addCard(king);

            List<Card> pcard = new ArrayList<>();
            List<Card> dcard = new ArrayList<>();
            pcard.add(one);
            pcard.add(three);
            pcard.add(king);
            dcard.add(two);
            dcard.add(four);
            Player player = new Player();
            Dealer dealer = new Dealer();
            player.setPlayerHand(pcard);
            player.setMoney(35);
            player.setValue(14);
            dealer.setDealerHand(dcard);
            dealer.setValue(6);

            Game game = new Game();
            game.setDeckOfCards(cards);
            game.setPlayer(player);
            game.setDealer(dealer);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            game = reader.read();
            assertEquals(3, game.getPlayer().getPlayerHand().size());
            assertEquals(35, game.getPlayer().getBalance());
            assertEquals(14, game.getPlayer().getValue());

            assertEquals(2, game.getDealer().getDealerHand().size());
            assertEquals(6, game.getDealer().getValue());

            assertEquals(5, game.getDeck().getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
