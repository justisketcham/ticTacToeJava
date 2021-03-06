package sample;

import java.util.Random;
import java.io.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.text.FontWeight;

// an object of this class stores all raw data about the game (who's turn it currently is, the spaces on the board) and
// the methods for deciding a computer opponent's next move (either random, or intelligent), and deciding the winner of
// the game
public class GameState implements Serializable {

    boolean isNewGame = true;

    static final File savedGame = new File("savedGame.bin");


    //private char playerTurn = 'x'; // who's turn is it, either X (true) or O (false)
    public char[][] boardSpaces = new char[][]{{0, 0, 0}, {0, 0, 0},{0, 0, 0}};
    private boolean gameOver = false;

    transient GameController controller;

    // indeces for current mover and winner   can only be 0 or 1 in a two player game
    public  int currentMover = 0;
    public int winnerIndex = -1;

    // parallel arrays   -  index with currentMover or winnerIndex above
    static public char [] pIcon  = new char[]{'x','o'};
    public Player [] p = new Player[2];
    // lets OpponentAI access desired board space  - used in the AI's move methods
    transient public StackPane [][] location = new StackPane[3][3];

    public GameState() { }



    public GameState(String humanName, char playerChoice, String easyMode, char firstmover)  // Single Player Mode
    {

        currentMover = firstmover == 'X' ? 0 : 1;
        if(playerChoice == 'X'){
            p[0] = new Human(humanName);
            p[1] = new OpponentAI(easyMode);
        }
        else{
            p[1] = new Human(humanName);
            p[0] = new OpponentAI(easyMode);
        }
    }

    public GameState(String xPlayer, String oPlayer, char firstmover) {

        currentMover = firstmover == 'X' ? 0 : 1;
        p[0] = new Human(xPlayer);
        p[1] = new Human(oPlayer);

        //init();
    }

    void setUI(GameController c){
        if(location == null)
            location = new StackPane[3][3];

        controller = c;
        controller.game = this;
        controller.init(this);
        firstMove();
    }

    void firstMove(){
        if(p[currentMover] instanceof OpponentAI)           // if mover is CPU, call it's nextMove()
            ((OpponentAI) p[currentMover]).nextMove(this);
    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean isOver() {
        return gameOver;
    }


    public void set(char player,   // the player's symbol
                    int x, int y) // position where to place player's symbol
    {
        boardSpaces[x][y] = player;
    }

    public int get(int x, int y) {
        return boardSpaces[x][y];
    }



    boolean occupied(int i, int j){
        return boardSpaces[i][j] != '\0';
    }

    private boolean matching(int a, int b, int c) {
        return a == b && b == c;
    }

    public void updateWinner() {
        for (int x = 0; x < 3; x++) // check all horizontal groups
        {
            // if there is a horizontal match AND that match is not empty spaces (empty space = 0)
            if (boardSpaces[0][x] != 0 && boardSpaces[0][x] == boardSpaces[1][x] && boardSpaces[1][x] == boardSpaces[2][x]) {
                winnerIndex = playerOf(boardSpaces[0][x]); // then return the winning token
                controller.outputWinner();
                return;
            }
        }

        for (int y = 0; y < 3; y++) // check all vertical groups
        {
            // if there is a vertical match AND that match is not empty spaces
            if (boardSpaces[y][0] != 0 && boardSpaces[y][0] == boardSpaces[y][1] && boardSpaces[y][1] == boardSpaces[y][2]) {
                winnerIndex = playerOf(boardSpaces[y][0]); // then return the winning token
                controller.outputWinner();
                return;
            }
        }

        if (boardSpaces[0][0] != 0 && boardSpaces[0][0] == boardSpaces[1][1] && boardSpaces[1][1] == boardSpaces[2][2]) // check both diagonal groups
        {
            winnerIndex = playerOf(boardSpaces[0][0]);
            controller.outputWinner();
            return;
        } else if (boardSpaces[2][0] != 0 && boardSpaces[2][0] == boardSpaces[1][1] && boardSpaces[1][1] == boardSpaces[0][2]) {
            winnerIndex = playerOf(boardSpaces[2][0]);
            controller.outputWinner();
            return;
        }

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                if (!occupied(i, j))
                    return;
        controller.outputWinner();

    }
        public StackPane atLocation(int x, int y){   //used in OpponentAI.randomMove()

            return location[x][y];

        }


        public void  changeTurn(){     // called each time a player has made a move
            updateWinner();
            currentMover = (++currentMover % 2);
            if(!gameOver){
                try{    save(this);     }
                catch(Exception e){  e.printStackTrace(); }
            }
            if(p[currentMover] instanceof OpponentAI)
                ((OpponentAI)p[currentMover]).nextMove(this);
        }


        public int playerOf(char c){ //returns index associated with the a players symbol;  used by updateWinner()

            return  c == 'x' ? 0 : 1;

        }
    public static GameState restore() throws Exception{
        FileInputStream fileInput = new FileInputStream(savedGame);
        ObjectInputStream objInput = new ObjectInputStream((fileInput));
        return (GameState)objInput.readObject();


    }

    public static void save(GameState game) throws Exception{
        //if(savedGame.)
        game.isNewGame = false;
        FileOutputStream fileOutput = new FileOutputStream(savedGame, false);
        ObjectOutputStream objOutput = new ObjectOutputStream((fileOutput));
        objOutput.writeObject(game);
    }
}