package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Handles all interface related outputs and inputs
public class User {
    static final String JSON_STORE = "./data/Game.json";
    private DeckOfCards deck1;
    private Game game;
    private int numberOfTimesHit = 3;
    private double currentMoney;
    private double betStatement;
    private Player player;
    private Dealer dealer;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public static final int WIN = 0;
    public static final int LOSE = 1;
    public static final int DRAW = 2;
    public static final int PBJ = 3;
    public static final int DBJ = 4;

    // EFFECTS: creates a new DeckOfCards
    public User() throws FileNotFoundException {
        deck1 = new DeckOfCards();
        currentMoney = 15;
        game = new Game();
        game.setDeckOfCards(deck1);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: displays the home screen to the user
    public void startingMessage() {
        while (true) {
            welcome();
            Scanner enter = new Scanner(System.in);
            String choice = enter.nextLine();

            if (choice.equals("instructions")) {
                instructions();
                break;
            } else if (choice.equals("begin")) {
                System.out.println("---------------------------------------------------------");
                System.out.println("Before we begin, you must first set up your deck to play!");
                System.out.println("---------------------------------------------------------");
                handlePresetDeck();
                break;
            } else if (choice.equals("load")) {
                loadWorkRoom();
                actualGame(deck1, player.getPlayerHand(), dealer.getDealerHand());
            } else if (choice.equals("quit")) {
                break;
            } else {
                System.out.println("Please enter a valid statement");
            }
        }

    }

    private void welcome() {
        System.out.println("--------------------------------------------------------------------------");

        System.out.println("Type 'instructions' to see the instructions.");
        System.out.println("Type 'begin' to start the game.");
        System.out.println("Type 'load' to load your previous game.");
        System.out.println("Type 'quit' to quit.");
    }

    // MODIFIES: this
    // EFFECTS: displays the instructions on how to play the game
    private void instructions() {
        System.out.println();
        System.out.println("This is a game of Black Jack, please read the README.md file for more detailed "
                + "information.");
        System.out.println("You start with an initial $15 and you must bet with a minimum of $5 each time.");
        System.out.println();
        while (true) {
            System.out.println("Type 'back' to go back to main screen");
            Scanner back = new Scanner(System.in);
            String goBack = back.nextLine();
            if (goBack.equals("back")) {
                startingMessage();
                break;
            } else {
                System.out.println("Please enter a valid statement");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to set up the deck by adding cards with given name and given value assigned to that card
    public void handlePresetDeck() {
        while (true) {
            System.out.println("What card do you want to add to the deck?");
            Scanner name = new Scanner(System.in);
            String cardName = name.nextLine();
            System.out.println("What value do you want to assign to that card?");
            Scanner value = new Scanner(System.in);
            int cardValue = value.nextInt();

            Card card = new Card(cardName, cardValue);
            deck1.addCard(card);
            System.out.println("--------------------------------------------------------------------------");
            System.out.print("Deck so far: ");
            for (int i = 0; i < deck1.getSize(); i++) {
                System.out.print(deck1.getCardName(i) + " ");
            }
            System.out.println("\n--------------------------------------------------------------------------");

            System.out.println("Do you want to add another card?");
            Scanner add = new Scanner(System.in);
            String another = add.nextLine();
            if (another.equals("no")) {
                break;
            }
        }
        beginGame(deck1);
    }

    // EFFECTS: prints out the final deck that is created by the user
    private void printFinalDeck() {
        System.out.println("==========================================================================");
        System.out.print("Deck: ");
        for (int i = 0; i < deck1.getSize(); i++) {
            System.out.print(deck1.getCardName(i) + " ");
        }

        System.out.print("\nValues: ");
        for (int i = 0; i < deck1.getSize(); i++) {
            System.out.print(deck1.getCardValue(i) + " ");
        }
        System.out.println("\n==========================================================================");
    }

    // REQUIRES: completedDeck must have more than 4 cards inside
    // MODIFIES: this
    // EFFECTS: asks if user wants to begin, if they say yes then begin game, if they say no then continue adding cards
    //          to the deck
    private void beginGame(DeckOfCards completedDeck) {
        printFinalDeck();
        System.out.println("Now that you have finished setting up the deck, would you like to begin?");
        Scanner scanner = new Scanner(System.in);
        String begin = scanner.nextLine();

        if (begin.equals("no")) {
            handlePresetDeck();
        } else {
            printFirstRoundOfCards(completedDeck);
        }
    }

    // REQUIRES: balance >= 5
    // MODIFIES: bet, this
    // EFFECTS: asks user how much they would like to bet, if they bet more than they have, have them try again, else
    //          return the bet
    private double printBetStatement(double balance) {
        System.out.println("-----------------------------");
        System.out.println("Current balance: " + balance);
        System.out.println("-----------------------------");
        double bet;

        while (true) {
            System.out.println("How much would you like to bet?");
            Scanner money = new Scanner(System.in);
            bet = money.nextDouble();

            if (balance - bet < 0) {
                System.out.println("You don't have that much balance");
            } else {
                break;
            }
        }
        return bet;
    }

    // REQUIRES: completedDeck must have more than 4 cards inside
    // MODIFIES: currentMoney, this
    // EFFECTS: prints out money you have after bet, then deal out the first round of cards, then ask user if they want
    //          to hit or stay. When game ends, ask if they want to play again
    private void printFirstRoundOfCards(DeckOfCards completedDeck) {
        player = new Player();
        dealer = new Dealer();
        game.setDeckOfCards(completedDeck);

        player.setPlayerHand(game.firstTwoPlayerCards());
        dealer.setDealerHand(game.firstTwoDealerCards());
        List<Card> playerCard1 = player.getPlayerHand();
        List<Card> dealerCard1 = dealer.getDealerHand();

        player.setMoney(currentMoney);
        betStatement = printBetStatement(player.getBalance());
        player.setBet(betStatement);
        currentMoney = player.betMade(betStatement);
        game.setPlayer(player);
        game.setDealer(dealer);
        printAfterBet(betStatement);

        actualGame(completedDeck, playerCard1, dealerCard1);
    }

    private void actualGame(DeckOfCards completedDeck, List<Card> playerCard1, List<Card> dealerCard1) {
        while (true) {
            if (printDealerAndPlayerHand(playerCard1, dealerCard1, player, dealer, betStatement)) {
                break;
            }

            String response = handleInputHitStaySave();

            try {
                int value = handleHitOrStay(response, player, dealer, playerCard1, dealerCard1,
                        completedDeck, betStatement);
                if (value == LOSE) {
                    playAgain();
                    break;
                } else if (value == 7) {
                    System.out.println("saved bruh...");
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String handleInputHitStaySave() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Do you want to 'hit' or 'stay' or 'save' and quit?");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();

        numberOfTimesHit++;
        return response;
    }

    // REQUIRES: betStatement <= currentMoney, betStatement > 0
    // EFFECTS: prints out the amount bet by the user and the current balance they have after the bet
    private void printAfterBet(double betStatement) {
        System.out.println("-----------------------------");
        System.out.println("Amount bet: " + betStatement);
        System.out.println("-----------------------------");
        System.out.println("Current balance: " + currentMoney);
    }

    // MODIFIES: this
    // EFFECTS: asks if user wants to play again, however if their balance is < 5, end game
    private void playAgain() {
        System.out.println("Do you want to play again?");
        Scanner response = new Scanner(System.in);
        String answer = response.nextLine();

        if (answer.equals("yes")) {
            if (currentMoney < 5) {
                System.out.println("You broke");
            } else {
                numberOfTimesHit = 3;
                this.deck1 = new DeckOfCards();
                startingMessage();
            }
        } else {
            System.out.println("Thank you for playing!");
        }
    }

    @SuppressWarnings("methodlength")
    // REQUIRES: completedDeck.size() >= 4, bet > 0
    // MODIFIES: this, currentMoney
    // EFFECTS: if there is not enough cards in the deck, end the game. If user hits, check if their value will be
    //          greater than 21, if so they lose, if not then continue. If user stays, let the dealer go and check
    //          who wins.
    private int handleHitOrStay(String response, Player p, Dealer d, List<Card> playerCard1, List<Card> dealerCard1,
                                    DeckOfCards completedDeck, double bet) {
        if (game.notEnoughCardsInDeck(numberOfTimesHit, completedDeck)) {
            System.out.println("Not enough cards in deck to continue the game :(");
            currentMoney = p.getBalance();
            return LOSE;
        } else if (response.equals("hit")) {
            p.playerHits(numberOfTimesHit, completedDeck);
            if (game.playerGreaterThan21(p)) {
                printDealerAndPlayerHand(playerCard1, dealerCard1, p, d, bet);

                lostStatementWithMoneyLost(p, bet);

                return LOSE;
            }
        } else if (response.equals("stay")) {
            if (dealersTurn(p, d, completedDeck, bet)) {
                return LOSE;
            }
            printDealerAndPlayerHand(playerCard1, dealerCard1, p, d, bet);
            handleMoney(p, d, bet);
            return LOSE;
        } else if (response.equals("save")) {
            saveWorkRoom();
            endGame();
            return 7;
        } else {
            return LOSE;
        }
        return WIN;
    }

    private void endGame() {
        System.out.println("Thank you for playing!");
    }

    // REQUIRES: completedDeck.size() >= 4
    // MODIFIES: this, numberOfTimesHit
    // EFFECTS: if dealers total card value is less than or equal to 17, then they will hit, if not enough cards in deck
    //          to continue, then end game
    private boolean dealersTurn(Player p, Dealer d, DeckOfCards completedDeck, double bet) {
        while (d.getValue() <= 17) {
            if (game.notEnoughCardsInDeck(numberOfTimesHit, completedDeck)) {
                System.out.println("Not enough cards in deck to continue the game :(");
                currentMoney = p.getBalance();
                return true;
            }
            d.dealerHits(numberOfTimesHit, completedDeck);
            numberOfTimesHit++;
        }
        return false;
    }

    // REQUIRES: bet > 0
    // MODIFIES: currentMoney, this
    // EFFECTS: print statements for when player loses, and prints out their money after their loss
    private void lostStatementWithMoneyLost(Player p, double bet) {
        System.out.println("You Lose!");
        currentMoney = p.playerMoney(LOSE, bet);
        System.out.println("-----------------------------");
        System.out.println("Current balance: " + currentMoney);
        System.out.println("-----------------------------");
    }

    // REQUIRES: bet > 0
    // EFFECTS: prints out the hands for the dealer and the player
    private boolean printDealerAndPlayerHand(List<Card> playerCards, List<Card> dealerCards, Player play, Dealer deal,
                                             double bet) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.print("Dealer: ");
        if (dealerCards.size() > 0) {
            for (Card dealerCard : dealerCards) {
                System.out.print(dealerCard.getCardName() + " ");
            }
        }
        int dealerValue = deal.getValue();
        System.out.println("\nDealer Total: " + dealerValue);

        System.out.print("\nPlayer: ");
        if (playerCards.size() > 0) {
            for (Card playerCard : playerCards) {
                System.out.print(playerCard.getCardName() + " ");
            }
        }
        int playerValue = play.getValue();
        System.out.println("\nPlayer Total: " + playerValue);

        return handleBlackJack(play, deal, playerValue, dealerValue, bet);
    }

    // REQUIRES: pval > 0, dval > 0, bet > 0
    // EFFECTS: handleMoney based on whether dealer or player hits blackjack, or both
    private boolean handleBlackJack(Player player, Dealer dealer, int pval, int dval, double bet) {
        if (player.getSize() == 2 && pval == 21 && dealer.getSize() == 2 && dval == 21) {
            System.out.println("--------------------------------------------------------------------------");
            handleMoney(player, dealer, bet);
            return true;
        } else if (player.getSize() == 2 && pval == 21) {
            System.out.println("--------------------------------------------------------------------------");
            handleMoney(player, dealer, bet);
            return true;
        } else if (dealer.getSize() == 2 && dval == 21) {
            System.out.println("--------------------------------------------------------------------------");
            handleMoney(player, dealer, bet);
            return true;
        }
        return false;
    }

    // REQUIRES: bet >0
    // EFFECTS: handles the money of the player, whether they win, lose, or draw, and print out their resulting money
    //          after
    private void handleMoney(Player player, Dealer dealer, double bet) {
        int w = game.whoWins(player, dealer);
        if (w == DRAW) {
            System.out.println("Draw!");
        } else if (w == PBJ) {
            System.out.println("You got a Black Jack!! You Win!");
        } else if (w == DBJ) {
            System.out.println("Dealer got a Black Jack. You Lose.");
        } else if (w == LOSE) {
            System.out.println("You Lose!");
        } else if (w == WIN) {
            System.out.println("You Win!");
        }
        currentMoney = player.playerMoney(w, bet);
        System.out.println("-----------------------------");
        System.out.println("Current balance: " + currentMoney);
        System.out.println("-----------------------------");
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved deck to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            game = jsonReader.read();
            deck1 = game.getDeck();
            player = game.getPlayer();
            betStatement = game.getPlayer().getBet();
            dealer = game.getDealer();
            numberOfTimesHit = player.getSize() + dealer.getSize() - 1;
            System.out.println("Loaded deck from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
