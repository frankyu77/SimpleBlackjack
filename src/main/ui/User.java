package ui;

import model.*;

import java.util.List;
import java.util.Scanner;

public class User {
    private DeckOfCards deck1;
    private Card card;
    private Game game;
    private int numberOfTimesHit = 3;
    private double currentMoney = 15;

    public static final int WIN = 0;
    public static final int LOSE = 1;
    public static final int DRAW = 2;
    public static final int PBJ = 3;
    public static final int DBJ = 4;

    // EFFECTS: creates a new DeckOfCards
    public User() {
        deck1 = new DeckOfCards();
    }

    // MODIFIES: this
    // EFFECTS: displays the home screen to the user
    public void startingMessage() {
        while (true) {
            System.out.println("--------------------------------------------------------------------------");

            System.out.println("Type 'instructions' to see the instructions.");
            System.out.println("Type 'begin' to start the game.");
            Scanner enter = new Scanner(System.in);
            String choice = enter.nextLine();

            if (choice.equals("instructions")) {
                instructions(choice);
                break;
            } else if (choice.equals("begin")) {
                System.out.println("---------------------------------------------------------");
                System.out.println("Before we begin, you must first set up your deck to play!");
                System.out.println("---------------------------------------------------------");
                handlePresetDeck();
                break;
            } else {
                System.out.println("Please enter a valid statement");
            }

        }

    }

    // MODIFIES: this
    // EFFECTS: displays the instructions on how to play the game
    private void instructions(String choice) {
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

            card = new Card(cardName, cardValue);
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
        game = new Game(completedDeck);
        List<Card> playerCard1 = game.firstTwoPlayerCards();
        List<Card> dealerCard1 = game.firstTwoDealerCards();

        Player p = new Player(playerCard1);

        double betStatement = printBetStatement(currentMoney);
        currentMoney = currentMoney - betStatement;

        printAfterBet(betStatement);

        while (true) {
            Dealer d = new Dealer(dealerCard1);

            if (printDealerAndPlayerHand(playerCard1, dealerCard1, p, d, game, betStatement)) {
                break;
            }

            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Do you want to 'hit' or 'stay'?");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();

            numberOfTimesHit++;
            if (handleHitOrStay(response, p, d, playerCard1, dealerCard1, completedDeck, betStatement)) {
                break;
            }
        }
        playAgain();
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
                handlePresetDeck();
            }
        } else {
            System.out.println("Thank you for playing!");
        }
    }

    // REQUIRES: completedDeck.size() >= 4, bet > 0
    // MODIFIES: this, currentMoney
    // EFFECTS: if there is not enough cards in the deck, end the game. If user hits, check if their value will be
    //          greater than 21, if so they lose, if not then continue. If user stays, let the dealer go and check
    //          who wins.
    private boolean handleHitOrStay(String response, Player p, Dealer d, List<Card> playerCard1, List<Card> dealerCard1,
                                    DeckOfCards completedDeck, double bet) {
        if (game.notEnoughCardsInDeck(numberOfTimesHit, completedDeck)) {
            System.out.println("Not enough cards in deck to continue the game :(");
            currentMoney += bet;
            return true;
        } else if (response.equals("hit")) {
            p.playerHits(numberOfTimesHit, completedDeck);
            if (game.playerGreaterThan21(p)) {
                printDealerAndPlayerHand(playerCard1, dealerCard1, p, d, game, bet);

                lostStatementWithMoneyLost(p, bet);

                return true;
            }
        } else if (response.equals("stay")) {
            if (dealersTurn(d, completedDeck)) {
                return true;
            }
            printDealerAndPlayerHand(playerCard1, dealerCard1, p, d, game, bet);
            handleMoney(p, d, bet);
            return true;
        } else {
            return true;
        }
        return false;
    }

    // REQUIRES: completedDeck.size() >= 4
    // MODIFIES: this, numberOfTimesHit
    // EFFECTS: if dealers total card value is less than or equal to 17, then they will hit, if not enough cards in deck
    //          to continue, then end game
    private boolean dealersTurn(Dealer d, DeckOfCards completedDeck) {
        while (d.getValue() <= 17) {
            if (game.notEnoughCardsInDeck(numberOfTimesHit, completedDeck)) {
                System.out.println("Not enough cards in deck to continue the game :(");
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
        currentMoney = p.playerMoney(LOSE, bet, currentMoney);
        System.out.println("-----------------------------");
        System.out.println("Current balance: " + currentMoney);
        System.out.println("-----------------------------");
    }

    // REQUIRES: bet > 0
    // EFFECTS: prints out the hands for the dealer and the player
    private boolean printDealerAndPlayerHand(List<Card> playerCards, List<Card> dealerCards, Player play, Dealer deal,
                                          Game game, double bet) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.print("Dealer: ");
        if (dealerCards.size() > 0) {
            for (int i = 0; i < dealerCards.size(); i++) {
                System.out.print(dealerCards.get(i).getCardName() + " ");
            }
        }
        int dealerValue = deal.getValue();
        System.out.println("\nDealer Total: " + dealerValue);

        System.out.print("\nPlayer: ");
        if (playerCards.size() > 0) {
            for (int i = 0; i < playerCards.size(); i++) {
                System.out.print(playerCards.get(i).getCardName() + " ");
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
        currentMoney = player.playerMoney(w, bet, currentMoney);
        System.out.println("-----------------------------");
        System.out.println("Current balance: " + currentMoney);
        System.out.println("-----------------------------");
    }
}
