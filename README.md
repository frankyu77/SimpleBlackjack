# 🃏 Black Jack 🃏

### Rules
Black Jack is a game where you and the dealer are dealt 2 cards. From there 
your goal is to get closer to 21 than the dealer when adding up all 
the cards you are dealt. If you get dealt a *Black Jack*, which is an ace and a 10 value card,
then you win automatically. You lose if your cards go over 21 ***or*** if the dealer
gets closer to 21 than you. The dealer will deal you two cards that will be face
up, and then deal themselves two cards where only one of their cards are facing up.
You will then be allowed to add cards to your hand until you want to stop, then the dealer
will start adding cards to their deck until they either 
beat you, lose, or draw. 

In this game of Black Jack, everyone will start with $20. You can bet any amount of money, as long as you have 
it and if you win, you get double the money back. If you lose, you lose the money that you bet. 
If you draw with the dealer, you keep the money you bet and the dealer will take back the money 
they bet as well. If you get a *Black Jack*, you get paid 150% of the value that you bet. One 
thing to keep in mind is that the dealer will never give themselves cards if their total card 
value is 17 or more.

### Who will use it?
Anyone who is interested in gaming or a little bit of gambling will enjoy this 
game. You don't have to be experienced in gambling to know how to play this game
and it is relatively simple to learn this game as well

### Why is this project of interest to you
This project interests me because I used to play black jack with my friends all
the time and I remember it always being fun. There was also a bot that you could 
add to your server in discord that you could also play blackjack with and that
essentially lead me to wanting to design a game similar to that. 

### User Stories
- As a user, I want to be able to build my own deck of cards and add assign different values and card names to the cards
- As a user, I want to be able to see the cards dealt to myself, as well as the dealer
- As a user, I want to be able to see the amount of money I have 
- As a user, I want to be able to choose whether I want to hit or stay
- As a user, I want to be able to see the instructions on how to play the game

- As a user, I want to be able to save my game whenever there is an option for me to do so or after each turn
- As a user, I want to be able to have a option to start from where I left off at the main menu 

### Instructions for Grader
- On the welcome page, you will find the begin game, load money, load game, and instructions button
  - The **begin game** button allows you to enter into the game where you have to setup your deck (more instructions on 
  that later)
  - The **load money** button lets you load the amount of money that you had when you pressed the save game button later 
  and 
  then you can press the begin game button to begin the game with the amount of money you had previously
  - The **load game** button takes you to the exact moment in the game where you left off
  - The **instructions** button takes you to the intructions page where you are able to press a back button to come back 
  to the welcome page
- On the setup page, you will be able to add X to Y, specifically you are able to add cards to a deck of cards
  - To do so, the user must enter in a name for the card they want to add and a value that the user assigns to the card
  (must be an integer)
  - After that you press the **add card** button to add the card to the deck (first of the two required actions)
  - You can also **undo** the card that you added which will remove the most recent card that you added into the deck of 
  cards (second of the two required actions)
  - You must add at least four cards in the deck (or a little message will show up), else you can press **begin game** 
  button to enter into the betting page
- On the betting page, you will be able to enter in the amount of money you want to bet
  - You can only enter in double values for the amount you want to bet (although there may be some rounding errors)
  - If you bet more money than you have, a small mesasge will appear
  - After you can press **begin game** to enter into the game
- On the game page the player is able to either hit, stay, or save the game and quit
  - If you press **hit**, the game will deal a card from the deck you made into your player hand and display an image of 
  a card with the name of the card you made in the center (this will be the visual component)
    - this will also add to your total value of your hand 
  - If you press **stay**, then the dealer will go do their turn and it will bring you to the end frame which will show 
  that you either won, lost, or drawed
    - if the first two cards dealt to you is a blackjack (10 value and 11 value card), then you will go straight to the
    end frame, which will show that either you won with blackjack or dealer won with blackjack
  - if you press **save**, then it will save the entire instance of the game at that moment and pressing load game from 
  the main page will bring you back to where you left off
- On the end frame page, you will be able to quit, save, or play again
  - pressing **quit** will quit you from the game
  - pressing **save** will save the amount of money you have, so you can just press load money at the beginning of the 
  game to start with the amount of money you had when you ended
    - one restriction to this is that you cannot press load game if you saved an instance of a game from the end frame
  - pressing **play again** will just bring you back to the setup page where you will be able to set up your deck again 
  but this time you will have the amount of money you ended with in the last round


### Phase 4: Task 2
/Users/frank/Library/Java/JavaVirtualMachines/corretto-11.0.17/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=59756:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/frank/Documents/CPSC 210/project_q2l0q/out/production/Project-Starter:/Users/frank/Documents/CPSC 210/project_q2l0q/lib/json-20210307.jar ui.Main

Sat Apr 08 14:39:19 PDT 2023 
Cards added to deck

Sat Apr 08 14:39:20 PDT 2023
Cards added to deck

Sat Apr 08 14:39:21 PDT 2023
Cards added to deck

Sat Apr 08 14:39:24 PDT 2023
Cards added to deck

Sat Apr 08 14:39:25 PDT 2023
Cards added to deck

Sat Apr 08 14:39:26 PDT 2023
Cards added to deck

Sat Apr 08 14:39:28 PDT 2023
Cards added to deck

Sat Apr 08 14:39:29 PDT 2023
Cards added to deck

Sat Apr 08 14:39:30 PDT 2023
Cards added to deck

Sat Apr 08 14:39:32 PDT 2023
Cards added to deck

Sat Apr 08 14:39:33 PDT 2023
Cards added to deck

Sat Apr 08 14:39:34 PDT 2023
Cards added to deck

Sat Apr 08 14:39:35 PDT 2023
Cards added to deck

Sat Apr 08 14:39:37 PDT 2023
Card removed from deck

Sat Apr 08 14:39:38 PDT 2023
Card removed from deck

Sat Apr 08 14:39:38 PDT 2023
Card removed from deck

Sat Apr 08 14:39:38 PDT 2023
Card removed from deck

Sat Apr 08 14:39:41 PDT 2023
Player bet made

Sat Apr 08 14:39:41 PDT 2023
First two Player cards dealt

Sat Apr 08 14:39:41 PDT 2023
First two Dealer cards dealt

Sat Apr 08 14:39:41 PDT 2023
Player loses!

Sat Apr 08 14:39:43 PDT 2023
Player hits

Sat Apr 08 14:39:43 PDT 2023
Player hits

Sat Apr 08 14:39:44 PDT 2023
Dealer hits

Sat Apr 08 14:39:44 PDT 2023
Player loses!

Process finished with exit code 0
