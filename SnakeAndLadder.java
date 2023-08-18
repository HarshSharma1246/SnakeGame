
import java.util.Random;
import java.util.Scanner;

public class SnakeAndLadder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int numSnakes = random.nextInt(10) + 5;
        int numLadders = random.nextInt(10) + 5;

        int[] snakes = new int[numSnakes];
        int[] ladders = new int[numLadders];

        for (int i = 0; i < numSnakes; i++) {
            snakes[i] = random.nextInt(90) + 10;
        }

        for (int i = 0; i < numLadders; i++) {
            ladders[i] = random.nextInt(90) + 10;
        }

        int currentPlayer = 1;
        int player1Position = 1;
        int player2Position = 1;

        updateBoard(player1Position, player2Position, snakes, ladders);

        while (player1Position < 100 && player2Position < 100) {
            System.out.println("Press Enter for Player " + currentPlayer + "'s turn...");
            scanner.nextLine();

            int diceRoll = random.nextInt(6) + 1;

            if (currentPlayer == 1) {
                player1Position += diceRoll;
                for (int snake : snakes) {
                    if (player1Position == snake) {
                        player1Position -= random.nextInt(10) + 1;
                        break;
                    }
                }
                for (int ladder : ladders) {
                    if (player1Position == ladder) {
                        player1Position += random.nextInt(10) + 1;
                        break;
                    }
                }
                currentPlayer = 2;
            } else {
                player2Position += diceRoll;
                for (int snake : snakes) {
                    if (player2Position == snake) {
                        player2Position -= random.nextInt(10) + 1;
                        break;
                    }
                }
                for (int ladder : ladders) {
                    if (player2Position == ladder) {
                        player2Position += random.nextInt(10) + 1;
                        break;
                    }
                }
                currentPlayer = 1;
            }

            updateBoard(player1Position, player2Position, snakes, ladders);

            if (player1Position >= 100) {
                System.out.println("Player 1 wins!");
                break;
            } else if (player2Position >= 100) {
                System.out.println("Player 2 wins!");
                break;
            }
        }
    }

    private static void updateBoard(int player1Position, int player2Position, int[] snakes, int[] ladders) {
        System.out.print("\033[H\033[2J"); // Clear terminal screen
        System.out.flush();

        System.out.println("===================================");
        int position = 1;
        for (int row = 9; row >= 0; row--) {
            for (int col = 1; col <= 10; col++) {
                boolean isSnakeOrLadder = false;
                String symbol = "   ";
                for (int snake : snakes) {
                    if (position == snake) {
                        symbol = " S ";
                        isSnakeOrLadder = true;
                        break;
                    }
                }
                if (!isSnakeOrLadder) {
                    for (int ladder : ladders) {
                        if (position == ladder) {
                            symbol = " L ";
                            isSnakeOrLadder = true;
                            break;
                        }
                    }
                }
                if (!isSnakeOrLadder) {
                    symbol = String.format("%3d", position);
                }
                if (position == player1Position) {
                    symbol = " P1 ";
                } else if (position == player2Position) {
                    symbol = " P2 ";
                }
                System.out.print(symbol);
                position++;
            }
            System.out.println();
        }
        System.out.println("===================================");
    }
}
