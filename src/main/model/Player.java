package model;

import java.util.List;


public class Player {
    private List<String> playerCards;

    public Player(List<String> playerHand) {
        this.playerCards = playerHand;
    }

    public int playerCount() {
        return 0;
    }

    public int playerMoney() {
        return 0;
    }

    public List<String> getPlayerHand() {
        return playerCards;
    }
}
