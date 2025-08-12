package Othello;

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
        boolean player1Turn = (p1.getColor() == 'B');// to get who plays first as who has black clr
        boolean consecutiveSkips = false;// to find both players does not have valid moves so both of their turn is
                                         // skipped consecutively leading to end of game
        while (true) {
            b.print();
            boolean validMoveMade = false;
            char currentPlayerColor = player1Turn ? p1.getColor() : p2.getColor();
            Player currentPlayer = player1Turn ? p1 : p2;
            boolean hasMove = player1Turn ? b.hasValidMove(p1.getColor()) : b.hasValidMove(p2.getColor());// checks does
                                                                                                          // it has
                                                                                                          // valid move
                                                                                                          // and then
                                                                                                          // initialize
                                                                                                          // flag of
                                                                                                          // hasmove
                                                                                                          // according
                                                                                                          // to it ,it
                                                                                                          // is used if
                                                                                                          // it doesnot
                                                                                                          // have valid
                                                                                                          // move at all
                                                                                                          // to skip the
                                                                                                          // turn

            if (!hasMove) {// has not even a single valid move in any cell in any direciton
                System.out.println("No valid moves for current player. Skipping turn...");
                if (consecutiveSkips) {
                    System.out.println("Both players have no valid moves. Game Over!");
                    break; // End the game if both players have skipped consecutively
                }
                consecutiveSkips = true;// once a player has no move the consecutive skips become true so again if the
                                        // second player too has no move and consecutive skip is true game end
                player1Turn = !player1Turn;
                continue;// skips the remaining becauseit is for when we have a valid move
            }

            consecutiveSkips = false; // Reset the flag if a valid move is made because it may be change to true if
                                      // prev turn has no valid move thus it helps in not endign games if the skips
                                      // are not consecutive if the next is also invalid then it would be go inside
                                      // the above if block and not reach here

            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayerColor + ")");
            makeMove(currentPlayer, sc);

            if (b.getCount() >= 64)// if board is full
                break;

            player1Turn = !player1Turn;
        }
        b.print();
        countAndDeclareWinner();
        sc.close();
    }

    private void countAndDeclareWinner() {
        char[][] board = b.getBoard();
        String result;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'B')
                    blackCount++;
                else if (board[i][j] == 'W')
                    whiteCount++;
            }
        }
        System.out.println("Black Count: " + blackCount + " White Count: " + whiteCount);
        if (blackCount > whiteCount) {
            result = p1.getColor() == 'B' ? "Player 1 " + p1.getName() + " Wins!"
                    : "Player 2 " + p2.getName() + " Wins!";
        } else if (blackCount < whiteCount) {
            result = p1.getColor() == 'W' ? "Player 1 " + p1.getName() + " Wins!"
                    : "Player 2 " + p2.getName() + " Wins!";
        } else {
            result = "The Match Draws";
        }

        System.out.println(result);
    }

    public boolean makeMove(Player player, Scanner sc) {
        // int attempts = 3; // limit retries to 3
        while (true) {
            System.out.println("Enter x:");
            int x = sc.nextInt();
            System.out.println("Enter y:");
            int y = sc.nextInt();
            if (b.move(player.getColor(), x, y))// if there is a valid move for this clr and turn wont be skipped and
                                                // actual players move happen via this move method
                return true;// this loop runs till we get a true because we know that it has a valid move so
                            // it goes on asking u to try again withotu any attempt restrictions
            System.out.println("Invalid move, try again.");
        }
        // return false;
    }

    public Player takeip(int num, Scanner sc) {
        System.out.println("Enter the player " + num + " name:");
        String name = sc.next();
        System.out.println("Enter the player " + num + " color('B' for black and 'W' for white):");
        char symbol = sc.next().charAt(0);
        return new Player(name, symbol);
    }
}
