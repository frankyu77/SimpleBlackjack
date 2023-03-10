package persistence;

import model.Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    void testWriterEmptyWorkroom() {
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
}
