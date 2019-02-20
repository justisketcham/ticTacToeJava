package sample;

import java.util.Random;

// an object of this class stores all raw data about the game (who's turn it currently is, the spaces on the board) and
// the methods for deciding a computer opponent's next move (either random, or intelligent), and deciding the winner of
// the game
public class GameState {
    //private char playerTurn = 'x'; // who's turn is it, either X (true) or O (false)
    public char[][] boardSpaces = new char[][]{{0, 0, 0}, {0, 0, 0},{0, 0, 0}};
    private boolean gameOver = false;

    public GameState() {
    }

    public GameState(String ply1, String ply2, char firstMover) {
        //playerTurn = 'x';

    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean isOver() {
        return gameOver;
    }

    public char checkForWin() {
        for (int x = 0; x < 3; x++) // check all horizontal groups
        {
            // if there is a horizontal match AND that match is not empty spaces (empty space = 0)
            if (boardSpaces[0][x] != '\0' && matching(boardSpaces[0][x], boardSpaces[1][x], boardSpaces[2][x])) {
                setGameOver();
                return boardSpaces[0][x]; // then return the winning token
            }
        }

        for (int y = 0; y < 3; y++) // check all vertical groups
        {
            // if there is a vertical match AND that match is not empty spaces
            if (boardSpaces[y][0] != '\0' && matching(boardSpaces[y][0], boardSpaces[y][1], boardSpaces[y][2])) {
                setGameOver();
                return boardSpaces[y][0]; // then return the winning token
            }
        }

        if (boardSpaces[0][0] != '\0' && matching(boardSpaces[0][0], boardSpaces[1][1], boardSpaces[2][2])) // check both diagonal groups
        {
            setGameOver();
            return boardSpaces[0][0];
        } else if (boardSpaces[2][0] != '\0' && matching(boardSpaces[2][0], boardSpaces[1][1], boardSpaces[0][2])) {
            setGameOver();
            return boardSpaces[2][0];
        } else // if no winner is found, then determine if there is still a playable/empty space
        {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (boardSpaces[y][x] == '\0') // if an empty space is found return empty token
                    {
                        return boardSpaces[y][x]; // returning the empty token to show that the game is not over
                    }
                }
            }
        }

        return '0'; // if all other cases fail (no winner, and no empty spaces), then this game must be a draw
    }

    public void set(char player,   // the player's symbol
                    int x, int y) // position where to place player's symbol
    {
        boardSpaces[x][y] = player;
    }

    public int get(int x, int y) {
        return boardSpaces[x][y];
    }

   /* public char getTurn() {
        return 0;//playerTurn;
    }*/


    boolean occupied(int i, int j){
        return boardSpaces[i][j] != '\0';
    }

    private boolean matching(int a, int b, int c) {
        return a == b && b == c;
    }

    //public char [][] getBoard(){ return boardSpaces;}


}