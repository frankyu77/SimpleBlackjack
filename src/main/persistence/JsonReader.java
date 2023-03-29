package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

// code taken from JsonSerializationDemo from Phase 2 by the 210 team!!
// Represents a reader that reads game from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        Game game = new Game();
        JSONObject player = jsonObject.getJSONObject("player");
        JSONObject dealer = jsonObject.getJSONObject("dealer");
        JSONObject deckOfCards = jsonObject.getJSONObject("deck");
        addPlayer(game, player);
        addDeckOfCards(game, deckOfCards);
        addDealer(game, dealer);

        return game;
    }

    // MODIFIES: game
    // EFFECTS: parses value, money, and bet from JSONObject and creates a new player to add to game
    private void addPlayer(Game game, JSONObject jsonObject) {
        int value = jsonObject.getInt("value");
        double playerMoney = jsonObject.getDouble("money");
        double playerBet = jsonObject.getDouble("bet");

        Player player = new Player();
        player.setBet(playerBet);
        player.setMoney(playerMoney);
        player.setValue(value);
        addPlayerCards(player, jsonObject);

        game.setPlayer(player);
    }

    // MODIFIES: player
    // EFFECTS: parses dealerCards as JSONArray from JSONObject and adds each card to player hand
    private void addPlayerCards(Player player, JSONObject jsonObject) {
        JSONArray playerCards = jsonObject.getJSONArray("playerCards");
        for (Object card : playerCards) {
            JSONObject nextCard = (JSONObject) card;
            addPlayerCard(player, nextCard);
        }
    }

    // MODIFIES: player
    // EFFECTS: parses cardName and cardValue from JSONObject and creates card to add to player hand
    private void addPlayerCard(Player player, JSONObject nextCard) {
        String cardName = nextCard.getString("cardName");
        int cardValue = nextCard.getInt("cardValue");
        Card card = new Card(cardName, cardValue);
        player.getPlayerHand().add(card);
    }

    // MODIFIES: game
    // EFFECTS: parses value, and bet from JSONObject and creates a new dealer to add to game
    private void addDealer(Game game, JSONObject jsonObject) {
        int dealerValue = jsonObject.getInt("valueD");

        Dealer dealer = new Dealer();
        dealer.setValue(dealerValue);
        addDealerCards(dealer, jsonObject);

        game.setDealer(dealer);
    }

    // MODIFIES: dealer
    // EFFECTS: parses dealerCards as JSONArray from JSONObject and adds each card to dealer hand
    private void addDealerCards(Dealer dealer, JSONObject jsonObject) {
        JSONArray dealerCards = jsonObject.getJSONArray("dealerCards");
        for (Object card : dealerCards) {
            JSONObject nextCard = (JSONObject) card;
            addDealerCard(dealer, nextCard);
        }
    }

    // MODIFIES: dealer
    // EFFECTS: parses cardName and cardValue from JSONObject and creates card to add to dealer hand
    private void addDealerCard(Dealer dealer, JSONObject nextCard) {
        String cardName = nextCard.getString("cardName");
        int cardValue = nextCard.getInt("cardValue");
        Card card = new Card(cardName, cardValue);
        dealer.getDealerHand().add(card);
    }

    // MODIFIES: game
    // EFFECTS: adds each card from deck of cards into game
    private void addDeckOfCards(Game game, JSONObject jsonObject) {
        DeckOfCards deckOfCards = new DeckOfCards();
        addCards(deckOfCards, jsonObject);
        game.setDeckOfCards(deckOfCards);
    }

    // MODIFIES: deckOfCards
    // EFFECTS: parses card from JSONObject and adds to deckOfCards
    private void addCards(DeckOfCards deckOfCards, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("deckOfCards");
        for (Object card : jsonArray) {
            JSONObject nextCard = (JSONObject) card;
            addCard(deckOfCards, nextCard);
        }
    }

    // MODIFIES: deck
    // EFFECTS: parses cardName and cardValue from JSONObject and creates card to add to deck
    private void addCard(DeckOfCards deck, JSONObject nextCard) {
        String cardName = nextCard.getString("cardName");
        int cardValue = nextCard.getInt("cardValue");
        Card card = new Card(cardName, cardValue);
        deck.addCard(card);
    }


}
