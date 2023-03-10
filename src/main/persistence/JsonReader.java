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

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
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

    // EFFECTS: parses workroom from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        /*JSONArray deckOfCards = jsonObject.getJSONArray("deckOfCards");
        JSONObject player = jsonObject.getJSONObject("player");
        JSONObject dealer = jsonObject.getJSONObject("dealer");
        Game game = new Game(deckOfCards, player, dealer);
        addCard(deckOfCards, jsonObject);
        return Game; */
//        DeckOfCards deck = new DeckOfCards();
//        Player p = new Player();
//        Dealer d = new Dealer();
//        JSONObject player = jsonObject.getJSONObject("player");
//        JSONObject dealer = jsonObject.getJSONObject("dealer");
//        JSONObject deckOfCards = jsonObject.getJSONObject("deck");
//        Player player = parsePlayer(jsonObject);
//        Dealer dealer = parseDealer(jsonObject);
//        DeckOfCards deck = parseDeckOfCards(jsonObject);

        Game game = new Game();
        JSONObject player = jsonObject.getJSONObject("player");
        JSONObject dealer = jsonObject.getJSONObject("dealer");
        JSONObject deckOfCards = jsonObject.getJSONObject("deck");
        addPlayer(game, player);
        addDeckOfCards(game, deckOfCards);
        addDealer(game, dealer);

        return game;
    }

//    private void addPlayer(Game game, JSONObject jsonPlayer) {
//        JSONArray playerCards = jsonPlayer.getJSONArray("playerCards");
//        for (Object card : playerCards) {
//            JSONObject nextCard = (JSONObject) card;
//            addPlayerCard(player, nextCard);
//        }
//        addPlayerValue(player, jsonPlayer);
//    }

//    private Player parsePlayer(JSONObject jsonObject) {
//
//        Player player = new Player();
//
//    }

    private void addPlayer(Game game, JSONObject jsonObject) {
        int value = jsonObject.getInt("value");
        double playerMoney = jsonObject.getDouble("money");

        Player player = new Player();
        player.setMoney(playerMoney);
        player.setValue(value);
        addPlayerCards(player, jsonObject);

        game.setPlayer(player);
    }

    private void addPlayerCards(Player player, JSONObject jsonObject) {
        JSONArray playerCards = jsonObject.getJSONArray("playerCards");
        for (Object card : playerCards) {
            JSONObject nextCard = (JSONObject) card;
            addPlayerCard(player, nextCard);
        }
    }

    private void addPlayerCard(Player player, JSONObject nextCard) {
        String cardName = nextCard.getString("cardName");
        int cardValue = nextCard.getInt("cardValue");
        Card card = new Card(cardName, cardValue);
        player.getPlayerHand().add(card);
    }

    private void addDealer(Game game, JSONObject jsonObject) {
        int dealerValue = jsonObject.getInt("valueD");

        Dealer dealer = new Dealer();
        dealer.setValue(dealerValue);
        addDealerCards(dealer, jsonObject);

        game.setDealer(dealer);
    }

//    private void addDealer(Dealer dealer, JSONObject jsonDealer) {
//        JSONArray dealerCards = jsonDealer.getJSONArray("dealerCards");
//        for (Object card : dealerCards) {
//            JSONObject nextCard = (JSONObject) card;
//            addDealerCard(dealer, nextCard);
//        }
//    }

    private void addDealerCards(Dealer dealer, JSONObject jsonObject) {
        JSONArray dealerCards = jsonObject.getJSONArray("dealerCards");
        for (Object card : dealerCards) {
            JSONObject nextCard = (JSONObject) card;
            addDealerCard(dealer, nextCard);
        }
    }

    private void addDealerCard(Dealer dealer, JSONObject nextCard) {
        String cardName = nextCard.getString("cardName");
        int cardValue = nextCard.getInt("cardValue");
        Card card = new Card(cardName, cardValue);
        dealer.getDealerHand().add(card);
    }


    private void addDeckOfCards(Game game, JSONObject jsonObject) {
        DeckOfCards deckOfCards = new DeckOfCards();
        addCards(deckOfCards, jsonObject);
        game.setDeckOfCards(deckOfCards);
    }

    private void addCards(DeckOfCards deckOfCards, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("deckOfCards");
        for (Object card : jsonArray) {
            JSONObject nextCard = (JSONObject) card;
            addCard(deckOfCards, nextCard);
        }
    }

    private void addCard(DeckOfCards deck, JSONObject nextCard) {
        String cardName = nextCard.getString("cardName");
        int cardValue = nextCard.getInt("cardValue");
        Card card = new Card(cardName, cardValue);
        deck.addCard(card);
    }


}
