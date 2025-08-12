package Othello;

public class Board {
    private char[][] board;
    private char p1Color;
    private char p2Color;

    public Board(char p1Color, char p2Color) {
        this.board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '.';
            }
        }
        this.p1Color = p1Color;
        this.p2Color = p2Color;
        // Initial setup of the board with 4 pieces in the center
        board[3][3] = 'W';
        board[4][4] = 'W';
        board[3][4] = 'B';
        board[4][3] = 'B';
    }

    public char[][] getBoard() {
        return board;
    }

    public int getCount() {// having separate count metho because othello is more complex than tictactoe
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    count++;
                }
            }
        }
        return count;
    }

    // Function to make a move
    public boolean move(char playerColor, int x, int y) {
        // Check if the move is inside bounds and the space is empty
        if (x < 0 || x >= 8 || y < 0 || y >= 8 || board[x][y] != '.') {
            return false;
        }
        boolean isValid = false;// check if the cell where we want to place our clr is valid ie., flip atleast
                                // one opponent clr in any one direction
        // Directions to check for valid move (8 directions)
        int[] dx = { -1, -1, -1, 0, 1, 1, 1, 0 };
        int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };

        // Check all 8 directions
        for (int dir = 0; dir < 8; dir++) {
            int i = x + dx[dir];
            int j = y + dy[dir];

            boolean hasOpponent = false;// Becomes true once we see at least one opponent piece in this direction.
            while (i >= 0 && i < 8 && j >= 0 && j < 8 && board[i][j] != '.') {
                if (board[i][j] == playerColor) {// If we meet our own color
                    if (hasOpponent) {// we already saw opponents (hasOpponent=true)
                        isValid = true; // Valid move if there's an opponent piece to flip
                    }
                    break;// found no need to increment just break
                } else {// it is opponent color
                    hasOpponent = true;
                }
                i += dx[dir];// to move further in same direction if same clr is not yet found
                j += dy[dir];
            }
        }

        if (isValid) {// isvalid is true if alteast one direction has opponent (valid) because after
                      // it becomes true there is nothing to make it false
            board[x][y] = playerColor;
            flipPieces(playerColor, x, y);
            return true;
        }
        return false;
    }

    // Function to flip pieces after a valid move
    private void flipPieces(char playerColor, int x, int y) {// it would be too much confusing to have this flippieces
                                                             // in the same directions for loop of move method so we are
                                                             // havign it again in a separate method
        int[] dx = { -1, -1, -1, 0, 1, 1, 1, 0 };
        int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };

        for (int dir = 0; dir < 8; dir++) {
            int i = x + dx[dir];
            int j = y + dy[dir];

            // If there are opponent pieces to flip in the direction, flip them
            while (i >= 0 && i < 8 && j >= 0 && j < 8 && board[i][j] != '.') {
                if (board[i][j] == playerColor) {
                    // Flip all opponent pieces between the new piece and this piece
                    while (i != x || j != y) {
                        i -= dx[dir];
                        j -= dy[dir];
                        board[i][j] = playerColor;
                    }
                    break;
                }
                i += dx[dir];// if same clr is not found it keeps incrementing till
                j += dy[dir];
            }
        }
    }

    public boolean hasValidMove(char playerColor) {// checks for valid move throught out the board for all cells
                                                   // through isvalidmove method in for each cell in each direction
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == '.' && isValidMove(playerColor, i, j)) {// if any direction is valid it is returned
                                                                           // true and hasvalidmove will also become
                                                                           // true as atleast one direction of a cell
                                                                           // can have a valid move
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidMove(char playerColor, int x, int y) {// checks the legality of a move at a specified
                                                                 // location for all its directions
        if (board[x][y] != '.')
            return false;

        int[] dx = { -1, -1, -1, 0, 1, 1, 1, 0 };
        int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };

        for (int dir = 0; dir < 8; dir++) {
            int i = x + dx[dir], j = y + dy[dir];
            boolean hasOpponent = false;
            while (i >= 0 && i < 8 && j >= 0 && j < 8 && board[i][j] != '.') {
                if (board[i][j] == playerColor) {
                    if (hasOpponent)
                        return true;// if any direction has opponent clr bw it and same clr it returns true
                    break;
                } else {
                    hasOpponent = true;
                }
                i += dx[dir];
                j += dy[dir];
            }
        }
        return false;
    }

    // Print the current board
    public void print() {
        System.out.println("Current Board:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] == '\u0000' ? '.' : board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}
