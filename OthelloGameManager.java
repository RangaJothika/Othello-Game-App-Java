// Code flow: 
// startGame -> hasValidMove (checks if at least one valid move exists) 
// -> isValidMove -> makeMove -> move (checks again if move is valid) -> flipPieces

// Use Windows cmd

import java.util.Scanner;

public class OthelloGameManager {

    public Player p1, p2;
    public Board b;
    private int whiteCount = 0;
    private int blackCount = 0;

    public static void main(String[] args) {
        OthelloGameManager o = new OthelloGameManager();
        o.startGame();
    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);

        p1 = takeip(1, sc);
        p2 = takeip(2, sc);

        while (p1.getColor() == p2.getColor()) {
            System.out.println("This color is already taken so please choose another color");
            p2.setColor(sc.next().charAt(0));
        }

        b = new Board(p1.getColor(), p2.getColor());

        boolean player1Turn = (p1.getColor() == 'B'); // Black plays first
        boolean consecutiveSkips = false;

        while (true) {
            b.print();// display board each time before player makes move

            char currentPlayerColor = player1Turn ? p1.getColor() : p2.getColor();
            Player currentPlayer = player1Turn ? p1 : p2;
            // check if there is valid moves before asking for entry
            boolean hasMove = b.hasValidMove(currentPlayerColor);

            if (!hasMove) {
                System.out.println("No valid moves for " + currentPlayer.getName() + " (" + currentPlayerColor
                        + "). Skipping turn...");
                if (consecutiveSkips) {
                    System.out.println("Both players have no valid moves. Game Over!");
                    break;
                }
                consecutiveSkips = true;
                player1Turn = !player1Turn;
                continue;
            }

            consecutiveSkips = false;

            // only if they have valid moves ask their entry
            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayerColor + ")");
            makeMove(currentPlayer, sc);

            if (b.getCount() >= 64)
                break;

            player1Turn = !player1Turn;
        }

        b.print();// print final board
        countAndDeclareWinner();
        sc.close();
    }

    private void countAndDeclareWinner() {
        blackCount = 0; // Reset counts before counting
        whiteCount = 0;

        char[][] board = b.getBoard();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'B')
                    blackCount++;
                else if (board[i][j] == 'W')
                    whiteCount++;
            }
        }

        System.out.println("Black Count: " + blackCount + " White Count: " + whiteCount);

        String result;
        if (blackCount > whiteCount) {
            result = (p1.getColor() == 'B') ? "Player 1 " + p1.getName() + " Wins!"
                    : "Player 2 " + p2.getName() + " Wins!";
        } else if (blackCount < whiteCount) {
            result = (p1.getColor() == 'W') ? "Player 1 " + p1.getName() + " Wins!"
                    : "Player 2 " + p2.getName() + " Wins!";
        } else {
            result = "The Match Draws";
        }
        System.out.println(result);
    }

    public boolean makeMove(Player player, Scanner sc) {
        while (true) {
            System.out.println("Enter x (0-7):");
            int x = sc.nextInt();
            System.out.println("Enter y (0-7):");
            int y = sc.nextInt();

            // check if the entered entry is possible move
            if (b.move(player.getColor(), x, y))
                return true;

            System.out.println("Invalid move, try again.");
        }
    }

    // take input from user
    public Player takeip(int num, Scanner sc) {
        System.out.println("Enter the player " + num + " name:");
        String name = sc.next();

        System.out.println("Enter the player " + num + " color ('B' for black and 'W' for white):");
        char symbol = Character.toUpperCase(sc.next().charAt(0));

        return new Player(name, symbol);
    }
}
