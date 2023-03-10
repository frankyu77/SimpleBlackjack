package persistence;

import model.Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGame() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGame.json");
        try {
            Game game = reader.read();
            assertEquals(0, game.getPlayer().getPlayerHand().size());
            assertEquals(15, game.getPlayer().getBalance());
            assertEquals(0, game.getPlayer().getValue());

            assertEquals(0, game.getDealer().getDealerHand().size());
            assertEquals(0, game.getDealer().getValue());

            assertEquals(0, game.getDeck().getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderRegularGame.json");
        try {
            Game game = reader.read();
            assertEquals(2, game.getPlayer().getPlayerHand().size());
            assertEquals(4, game.getPlayer().getCardValue(0));
            assertEquals("4", game.getPlayer().getCardName(0));
            assertEquals(6, game.getPlayer().getCardValue(1));
            assertEquals("6", game.getPlayer().getCardName(1));
            assertEquals(15, game.getPlayer().getBalance());
            assertEquals(10, game.getPlayer().getValue());

            assertEquals(2, game.getDealer().getDealerHand().size());
            assertEquals(5, game.getDealer().getCardValue(0));
            assertEquals("5", game.getDealer().getCardName(0));
            assertEquals(7, game.getDealer().getCardValue(1));
            assertEquals("7", game.getDealer().getCardName(1));
            assertEquals(12, game.getDealer().getValue());

            assertEquals(7, game.getDeck().getSize());
            assertEquals(4, game.getDeck().getCardValue(0));
            assertEquals(5, game.getDeck().getCardValue(1));
            assertEquals(6, game.getDeck().getCardValue(2));
            assertEquals(7, game.getDeck().getCardValue(3));
            assertEquals(8, game.getDeck().getCardValue(4));
            assertEquals(2, game.getDeck().getCardValue(5));
            assertEquals(3, game.getDeck().getCardValue(6));
            assertEquals("4", game.getDeck().getCardName(0));
            assertEquals("5", game.getDeck().getCardName(1));
            assertEquals("6", game.getDeck().getCardName(2));
            assertEquals("7", game.getDeck().getCardName(3));
            assertEquals("8", game.getDeck().getCardName(4));
            assertEquals("2", game.getDeck().getCardName(5));
            assertEquals("3", game.getDeck().getCardName(6));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
