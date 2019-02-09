/**
 * Jada Houser - CPSC2150 - Section 3 - Homework 3
 */

package cpsc2150.connectX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @invariants
 * row !>= MAX_ROW_SIZE && row !< 3
 * col !>= MAX_COL_SIZE && col !< 3
 */

public class GameBoardMem implements IGameBoard {
    static int MAX_ROW_SIZE = 100;
    static int MAX_COL_SIZE = 100;
    static int MAX_WIN_SIZE = 25;
    static Map<Integer, ArrayList<Character>> mapConBoard;
    static int ROW_SIZE;
    static int COL_SIZE;
    static int NUM_TO_WIN;

    /**
     * @param playerRow the row size selected by the player
     * @param playerCol the column size selected by the player
     * @param playerWin the num of in-a-row to win
     * @pre playerWin >= 3 && playerWin <= 25
     * playerRow !>= MAX_ROW_SIZE && playerRow !< 3,
     * playerCol !>= MAX_COL_SIZE && playerCol !< 3
     * @post board will be constructed
     */
    //Regular constructor that takes in a row, col, and win number to
    //build the game board
    public GameBoardMem(int playerRow, int playerCol, int playerWin) {
        ROW_SIZE = playerRow;
        COL_SIZE = playerCol;
        mapConBoard = new HashMap<Integer, ArrayList<Character>>();
        NUM_TO_WIN = playerWin;
        for(int i = 0; i < COL_SIZE; i++)
        {
            mapConBoard.put(i, new ArrayList<Character>(ROW_SIZE));
        }
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

    public boolean checkIfFree(int c)
    {
        Integer IntCol = c;

        if(mapConBoard.get(IntCol).size() < ROW_SIZE)
        {
            return true;
        }
        return false;
    }

    //Return a character from the specified index
    public char whatsAtPos(int r, int c)
    {
        Integer IntRow = r;
        Integer IntCol = c;

        if(mapConBoard.get(IntCol).isEmpty())
        {
            return ' ';
        }

        if(IntRow >= mapConBoard.get(IntCol).size())
        {
            return ' ';
        }
        return mapConBoard.get(IntCol).get(IntRow);
    }

    //Place token in specified col in board
    public void placeToken(char p, int c)
    {
        Integer IntCol = c;

        if(checkIfFree(c))
        {
            mapConBoard.get(IntCol).add(p);
        }
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
