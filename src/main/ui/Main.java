package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        try {
            User u = new User();
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("~~Welcome to Black Jack!!~~");
            u.startingMessage();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
