import java.util.Scanner;

public class SnakeAndLadder {
    public static final String COLOR_RESET = "\u001B[0m";
    public static final String Player1_YELLOW = "\u001B[33m";
    public static final String Player2_BLUE = "\u001B[34m";
    public static final String SNAKE_RED = "\u001B[31m";
    public static final String LADDER_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numSnakes = (int) Math.floor(Math.random() * 5) + 7;
        int numLadders = (int) Math.floor(Math.random() * 5) + 7;

        int[][] snakes = new int[numSnakes][2]; // Zeroth index contains position of snake and at 1st index contains
                                                // length of snake
        int[][] ladders = new int[numLadders][2]; // Same as snake array

        for (int i = 0; i < numSnakes; i++) {
            snakes[i][0] = (int) Math.floor(Math.random() * 80) + 20; // Gives the snake position higher than 20 always
            snakes[i][1] = (int) Math.floor(Math.random() * (snakes[i][0] - 10)) + 10;
            // Snake length is always greater than 10 and is also dependent on the position
            // of the snake
            // Ex. if snake position is 30, snake length can be from 10 to 29
        }

        for (int i = 0; i < numLadders; i++) {
            ladders[i][0] = (int) Math.floor(Math.random() * 70) + 10; // Ladders only remain from 10th position to 80
            ladders[i][1] = (int) Math.floor(Math.random() * (90 - ladders[i][0])) + 10;
            // Ladder length is always greater than 10
            // If ladder position is 70, ladder length can be from 10 to 29
        }

        int currentPlayer = 1;
        int player1Position = 1;
        int player2Position = 1;

        updateBoard(player1Position, player2Position, snakes, ladders);
        boolean again = false; // Check wheather a player got 6 in last turn, if yes then player will get new
                               // chance
        while (player1Position < 100 && player2Position < 100) {

            if (!again)
                System.out.println("Press Enter for Player " + currentPlayer + "'s turn...");
            again = false;
            sc.nextLine();

            int diceRoll = (int) Math.floor(Math.random() * 6) + 1;

            if (currentPlayer == 1) {
                if (diceRoll + player1Position > 100) {
                    System.out.println("Player1 can't go beyond 100!! Try again in your next turn");
                    currentPlayer = 2;
                    updateBoard(player1Position, player2Position, snakes, ladders);
                    continue;
                }
                player1Position += diceRoll;
                System.out.println("Player1 got " + diceRoll + " on dice and went from " + (player1Position - diceRoll)
                        + " to " + player1Position);
                for (int[] snake : snakes) {
                    if (player1Position == snake[0]) {
                        int temp = player1Position;
                        player1Position -= snake[1];
                        System.out.println(
                                "Oops!! Player1 is eaten by the snakes and went from " + temp + " to "
                                        + player1Position);
                        break;
                    }
                }
                for (int ladder[] : ladders) {
                    if (player1Position == ladder[0]) {
                        int temp = player1Position;
                        player1Position += ladder[1];
                        System.out.println(
                                "Yeah!! Player1 has taken the ladder and went from " + temp + " to " + player1Position);
                        break;
                    }
                }
                if (diceRoll == 6) {
                    currentPlayer = 1;
                    again = true;
                }
                currentPlayer = 2;
            } else {
                if (diceRoll + player2Position > 100) {
                    System.out.println("Player2 can't go beyond 100!! Try again in your next turn");
                    updateBoard(player1Position, player2Position, snakes, ladders);
                    currentPlayer = 1;
                    continue;
                }
                player2Position += diceRoll;
                System.out.println("Player2 got " + diceRoll + " on dice and went from " + (player2Position - diceRoll)
                        + " to " + player2Position);
                for (int snake[] : snakes) {
                    if (player2Position == snake[0]) {
                        int temp = player2Position;
                        player2Position -= snake[1];
                        System.out.println(
                                "Oops!! Player2 has eaten by the snake and went from " + temp + " to "
                                        + player2Position);
                        break;
                    }
                }
                for (int ladder[] : ladders) {
                    if (player2Position == ladder[0]) {
                        int temp = player2Position;
                        player2Position += ladder[1];
                        System.out.println(
                                "Yeah!! Player2 has taken the ladder and went from " + temp + " to " + player2Position);
                        break;
                    }
                }
                if (diceRoll == 6) {
                    currentPlayer = 2;
                    again = true;
                } else
                    currentPlayer = 1;
            }
            sc.nextLine();
            if (!again)
                updateBoard(player1Position, player2Position, snakes, ladders);

            if (player1Position == 100) {
                System.out.println("Player 1 wins!");
                break;
            } else if (player2Position == 100) {
                System.out.println("Player 2 wins!");
                break;
            }
            if (again)
                System.out.println("Roll the dice again!! as you got 6");
        }
    }

    private static void updateBoard(int player1Position, int player2Position, int[][] snakes, int[][] ladders) {
        System.out.print("\033[H\033[2J"); // Clear terminal screen
        System.out.flush();

        System.out.println("=================================================");
        int position = 100;
        for (int row = 9; row >= 0; row--) {
            for (int col = 1; col <= 10; col++) {
                boolean isSnakeOrLadder = false;
                String symbol = "    ";
                for (int snake[] : snakes) {
                    if (position == snake[0]) {
                        symbol = SNAKE_RED + "  S  " + COLOR_RESET;
                        isSnakeOrLadder = true;
                        break;
                    }
                }
                if (!isSnakeOrLadder) {
                    for (int ladder[] : ladders) {
                        if (position == ladder[0]) {

                            symbol = LADDER_GREEN + "  L  " + COLOR_RESET;
                            isSnakeOrLadder = true;
                            break;
                        }
                    }
                }
                if (!isSnakeOrLadder) {
                    symbol = String.format(" %3d ", position);
                }
                if (position == player1Position) {
                    symbol = Player1_YELLOW + "  P1  " + COLOR_RESET;
                } else if (position == player2Position) {
                    symbol = Player2_BLUE + "  P2  " + COLOR_RESET;
                }
                System.out.print(symbol);
                position--;
            }
            System.out.println();
            System.out.println();

        }
