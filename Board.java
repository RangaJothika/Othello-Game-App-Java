public class Board {

    private char[][] board;
    private char p1Color;
    private char p2Color;

    public Board(char p1Color, char p2Color) {
        // create an empty board initially with '.' in all cells(except the initial
        // setup)
        this.board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '.';
            }
        }
        // get colors
        this.p1Color = Character.toUpperCase(p1Color);
        this.p2Color = Character.toUpperCase(p2Color);

        // Initial setup
        board[3][3] = 'W';
        board[4][4] = 'W';
        board[3][4] = 'B';
        board[4][3] = 'B';
    }

    public char[][] getBoard() {
        return board;
    }

    public int getCount() {
        int count = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                if (board[i][j] != '.')
                    count++;
        return count;
    }

    public boolean move(char playerColor, int x, int y) {
        playerColor = Character.toUpperCase(playerColor);
        if (x < 0 || x >= 8 || y < 0 || y >= 8 || board[x][y] != '.') {
            return false;
        }
        if (!isValidMove(playerColor, x, y)) {// check if current entry coordinates are valid or not
            return false;
        }

        board[x][y] = playerColor;//place the color at respective entry cooordinate
        flipPieces(playerColor, x, y);
        return true;
    }

    private void flipPieces(char playerColor, int x, int y) {
        int[] dx = { -1, -1, -1, 0, 1, 1, 1, 0 };
        int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };

        for (int dir = 0; dir < 8; dir++) {
            int i = x + dx[dir];
            int j = y + dy[dir];
            boolean hasOpponent = false;

            //  list to store pieces to be flipped [dynamic size] as we dont know how many
            // pieces need to  flip initially
            java.util.List<int[]> toFlip = new java.util.ArrayList<>();

            while (i >= 0 && i < 8 && j >= 0 && j < 8 && board[i][j] != '.') {
                if (board[i][j] == playerColor) {
                    if (hasOpponent) {
                        for (int[] pos : toFlip) {
                            board[pos[0]][pos[1]] = playerColor;
                        }
                    }
                    break;
                }
                // till same color is found keep on adding the opponent color to list
                else {
                    hasOpponent = true;// without any opponent color in mid we find same color again,flip will not
                                       // happen 
                    toFlip.add(new int[] { i, j });
                }
                i += dx[dir];
                j += dy[dir];
            }
        }
    }

    public boolean hasValidMove(char playerColor) {
        playerColor = Character.toUpperCase(playerColor);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j] == '.' && isValidMove(playerColor, i, j))
                    return true;
        return false;
    }

    private boolean isValidMove(char playerColor, int x, int y) {
        if (board[x][y] != '.')
            return false;

        //  represent the 8 directions around a cell on 2D board.
        int[] dx = { -1, -1, -1, 0, 1, 1, 1, 0 };
        // x is the left(neg) and right(pos) side
        int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };
        // y is the top(neg) and bottom(pos) side

        char opponentColor = (playerColor == 'B') ? 'W' : 'B';

        // for all directions
        for (int dir = 0; dir < 8; dir++) {
            int i = x + dx[dir];// x and y + to move from current x and y
            int j = y + dy[dir];
            // atleast one direction should have opponent color to be valid move
            boolean hasOpponent = false;

            // for one direction ,checks all cells in it
            while (i >= 0 && i < 8 && j >= 0 && j < 8) {
                if (board[i][j] == opponentColor) {
                    hasOpponent = true;
                    // if opponent color is found it keeps checking
                } else if (board[i][j] == playerColor) {
                    // until same color found only middle can be flipped
                    if (hasOpponent)
                        return true;
                    else
                        break;
                } else {
                    break;
                }
                i += dx[dir];
                j += dy[dir];
            }
        }
        return false;
    }

    public void print() {

        System.out.println("Current Board:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
