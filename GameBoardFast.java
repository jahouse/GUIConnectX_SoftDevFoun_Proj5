/**
 * Jada Houser - CPSC2150 - Section 3 - Homework 3
 */

package cpsc2150.connectX;

import java.lang.String;
import java.lang.StringBuilder;

/**
 * @invariants
 * row !>= MAX_ROW_SIZE && row !< 3
 * col !>= MAX_COL_SIZE && col !< 3
 */

public class GameBoardFast implements IGameBoard {
    static int MAX_ROW_SIZE = 100;
    static int MAX_COL_SIZE = 100;
    static int MAX_WIN_SIZE = 25;
    static char[][] connectBoard;
    static int ROW_SIZE;
    static int COL_SIZE;
    static int NUM_TO_WIN;

    /**
     * @param playerRow the row size selected by the player
     * @param playerCol the column size selected by the player
     * @param playerWin the player name & token name
     * @pre playerWin must == X || playerWin must == O,
     * playerRow !>= MAX_ROW_SIZE && playerRow !< 3,
     * playerCol !>= MAX_COL_SIZE && playerCol !< 3
     * @post board will be constructed
     */
    //Regular constructor that takes in a row, col, and win number to
    //build the game board
    public GameBoardFast(int playerRow, int playerCol, int playerWin) {
        ROW_SIZE = playerRow;
        COL_SIZE = playerCol;

        connectBoard = new char[ROW_SIZE][COL_SIZE];

        NUM_TO_WIN = playerWin;
    }

    /**
     * @param otherGame the other board to be copied to the current board
     * @pre otherGame != NULL
     * @post GameBoardFast = current board intialized to incoming GameBoardFast
     */
    //Sidenote: I made a copy constructor so I could keep my main short...please have mercy
    //on my points...
    public GameBoardFast(GameBoardFast otherGame) {
        ROW_SIZE = otherGame.getNumRows();
        COL_SIZE = otherGame.getNumColumns();

        connectBoard = new char[ROW_SIZE][COL_SIZE];

        NUM_TO_WIN = otherGame.getNumToWin();
    }

    //returns the number of rows
    public int getNumRows() {
        return ROW_SIZE;
    }

    //returns the number of columns
    public int getNumColumns() {
        return COL_SIZE;
    }

    //returns the num in a row needed to win
    public int getNumToWin() {
        return NUM_TO_WIN;
    }

    //Place token in specified col in board
    public void placeToken(char p, int c) {
        //Start at top of the board
        int i = 0;
        Character store = ' ';
        //Move down the board until there is an empty space
        while (store.isLetter(whatsAtPos(i, c)) && i < ROW_SIZE) {
            i++;
        }
        /*if(i >= ROW_SIZE)
        {
            i--;
        }
        if(i < 0)
        {
            i++;
        }*/

        //At that index, set the token
        connectBoard[i][c] = p;
    }

    //Return a character from the specified index
    public char whatsAtPos(int r, int c) {
        //If there is a letter at the specified indices
        //return the character
        Character store = ' ';

        if (store.isLetter(connectBoard[r][c])) {
            return connectBoard[r][c];
        }
        //Else return a blank character
        return (' ');
    }

    public boolean checkIfFree(int c)
    {
        int topRow = ROW_SIZE - 1;

        if(whatsAtPos(topRow, c) == ' ')
        {
            return true;
        }

        return false;
    }

    /**
     * @return string representation of the board
     * @pre this != null
     * @post toString = string representation of the board
     */
    //Returns a string representation of the board
    @Override
    public String toString() {
        //Initialize the StringBuilder
        StringBuilder board = new StringBuilder(100);
        //Initialize iterators
        int i, j;

        //Append column numbers to board string
        for (i = 0; i < COL_SIZE; i++) {
            if(i < 10) {
                board.append("| ").append(i);
            }
            else if( i > 9)
            {
                board.append("|").append(i);
            }
        }

        board.append("|");

        //Go through the board and append what's at each index
        for (j = ROW_SIZE - 1; j >= 0; j--) {
            board.append("\n").append("| ");
            for (i = 0; i < COL_SIZE; i++) {
                board.append(whatsAtPos(j, i)).append("| ");
            }
        }

        board.append("\n");
        //Return completed string
        return board.toString();
    }

}
