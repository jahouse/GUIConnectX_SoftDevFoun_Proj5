/**
 * Jada Houser - CPSC2150 - Section 3 - Homework 3
 */

package cpsc2150.connectX;

/**
 * A board game containing characters.
 * A board game where two or more players take turns
 * placing a token in the board and try to
 * win based on a certain number of tokens in a row
 * This board game is bounded by MAX_ROW_SIZE and
 * MAX_COL_SIZE
 *
 * Initialization ensures the board game is empty
 * Defines: rows:r, columns:c. player token:p
 * Constraints: 2 < rows <= MAX_ROW_SIZE, 2 < columns <= MAX_COL_SIZE
 */

public interface IGameBoard {

    //primary methods
    /**
     * @param r the row of the last token
     * @param c the col of the last token
     * @return character at a specified place on the board
     * @pre r !>= ROW_SIZE && r !<0,
     * c !>= COL_SIZE && c !< 0
     * @post whatsAtPos = character at that index of the board
     */
    //Return a character from the specified index
    char whatsAtPos(int r, int c);

    /**
     * @pre ROW_SIZE != NULL
     * @post getNumRows = int of row size value
     * @return value of row size
     */
    //returns the number of rows
    int getNumRows();

    /**
     * @pre COL_SIZE != NULL
     * @post getNumCols = int of col size value
     * @return value of col size
     */
    //returns the number of columns
    int getNumColumns();

    /**
     * @pre NUM_TO_WIN != NULL
     * @post getNumToWin = int of num to win value
     * @return value of num to win
     */
    //returns the num in a row needed to win
    int getNumToWin();

    /**
     * @param p the player name & token name
     * @param c the column selected by the player
     * @pre p must == X || p must == O,
     * c !>= COL_SIZE && c !< 0
     * @post board will have a token set in the specified location
     */
    //Place token in specified col in board
    void placeToken(char p, int c);

    /**
     * @param c the column of the last token placement
     * @return boolean determining whether the column has space
     * @pre c !>= COL_SIZE
     * @post checkIfFree = true if the top row of given column is empty, else
     * false
     */
    boolean checkIfFree(int c);

    ///////////////////////////////
    //Secondary Methods
    /**
     * @return boolean determining whether the game resulted in a tie or not
     * @pre checkForWin has to have been previously called
     * @post checkTie = true if no positions on the board yield a win
     */
    default boolean checkTie()
    {
        int topCol = getNumColumns() - 1;

        while(topCol >= 0 && !checkIfFree(topCol))
        {
            topCol--;
        }

        if(topCol < 0)
        {
            return true;
        }

        return false;
    }

    /**
     * @param c the column of the last token placement
     * @return boolean determining whether the last move resulted in a win
     * @pre c !>= COL_SIZE && c !< 0
     * @post checkForWin = true if any of the other checks are true
     */
    default boolean checkForWin(int c)
    {
        int topRow = 0;
        char pTok;
        Character store = ' ';
        char checkStore;

        //Find the last token inserted
        while(topRow < getNumRows() && store.isLetter(whatsAtPos(topRow, c)))
        {
            checkStore = whatsAtPos(topRow,c);
            topRow++;
        }
        topRow--;
        pTok = whatsAtPos(topRow, c);

        if(checkHorizWin(topRow, c, pTok) || checkVertWin(topRow, c, pTok) || checkDiagWin(topRow, c, pTok))
        {
            return true;
        }
        return false;
    }

    /**
     * @param r the row of the last token
     * @param c the col of the last token
     * @param p the player name & token name
     * @return boolean determining whether the token placement resulted in
     * a horizontal win
     * @pre r !>= ROW_SIZE && r !<0,
     * c !>= COL_SIZE && c !< 0,
     * p must == a letter
     * @post checkHorizWin = true if an x amount of the same token are found
     * on the left and/or right
     */
    //Check if the last token placement resulted in a horizontal win
    default boolean checkHorizWin(int r, int c, char p)
    {
        //Starts at 1 in order to count the current index
        int count = 1;
        int ONE = 1;

        //Checking Left Side:
        //Check the bounds of the incoming column,
        //if subtracting by 1 does not result in a negative number,
        if (c - ONE > - 1) {
            //iterate through the board to the LEFT and increment count
            //if the same token is found next to it
            for (int col = c - ONE; col >= 0 && whatsAtPos(r, col) == p; col--) {
                count++;
                //If count >= NUM_TO_WIN, return true
                if (count >= getNumToWin()) {
                    return true;
                }
            }
        }
        //Checking Right Side:
        //Check the bounds of the incoming column -
        //if adding by 1 does not result in a number greater than the col size,
        if (c + ONE < getNumColumns()) {
            //iterate through the board to the RIGHT and increment count
            //if the same token is found next to it
            for (int col = c + ONE; col < getNumColumns() && whatsAtPos(r, col) == p; col++) {
                count++;
                //If count >= NUM_TO_WIN, return true
                if (count >= getNumToWin()) {
                    return true;
                }
            }
        }

        //Else return false
        return false;
    }


    /**
     * @param r the row of the last token
     * @param c the col of the last token
     * @param p the player name & token name
     * @return boolean determining whether the token placement resulted in
     * a vertical win
     * @pre r !>= ROW_SIZE && r !<0,
     * c !>= 7 && COL_SIZE !< 0,
     * p must == a letter
     * @post checkVertWin = true if an x amount of the same token are found
     * below
     */
    //check for vertical win
    default boolean checkVertWin(int r, int c, char p)
    {
        int count = 1;

        //Checking Above (because both boards are upside down):
        //Check the bounds of the incoming row,
        //if subtracting by 1 does not result in a less than or equal to 0,
        if (r - 1 > 0) {
            //iterate through the board to the TOP and decrement count
            //if the same token is found below it
            for (int row = r - 1; row >= 0 && whatsAtPos(row, c) == p; row--) {
                count++;
                //If count >= NUM_TO_WIN, return true
                if (count >= getNumToWin()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param r the row of the last token
     * @param c the col of the last token
     * @param p the player name & token name
     * @return boolean determining whether the token placement resulted in
     * a diagonal win
     * @pre r !>= ROW_SIZE && r !<0,
     * c !>= 7 && COL_SIZE !< 0,
     * p must == a letter
     * @post checkDiagWin = true if an x amount of the same token are found
     * on the left diagonal (upper/lower) or right diagonal (upper/lower)
     */
    //check for diagonal win
    default boolean checkDiagWin(int r, int c, char p)
    {
        int ZERO = 0;
        int ONE = 1;
        int count = 1;

        //Checking Upper Left:
        //Check the bounds of the incoming row and col,
        //if subtracting row or col by 1 does not result in a negative number,
        if (r - ONE > ZERO && c - ONE > ZERO) {
            //iterate through the board to the TOP and LEFT and increment count
            //if the same token is found next to it
            for (int row = r - ONE, col = c - ONE; row >= ZERO && col >= ZERO &&
                    whatsAtPos(row, col) == p; row--, col--) {
                count++;
                //If count >= NUM_TO_WIN, return true
                if (count >= getNumToWin()) {
                    return true;
                }
            }
        }

        //Checking Lower Left:
        //Check the bounds of the incoming row and col,
        //if adding 1 to row does not result in a number greater than row size,
        //and subtracting col by 1 does not result in a negative number,
        if (r + ONE > ZERO && c - ONE > ZERO) {
            //iterate through the board to the BOTTOM and LEFT and increment count
            //if the same token is found next to it
            for (int row = r + ONE, col = c - ONE; row < getNumRows() && col >= ZERO &&
                    whatsAtPos(row, col) == p; row++, col--) {
                count++;
                //If count >= 4, return true
                if (count >= getNumToWin()) {
                    return true;
                }
            }
        }

        //Checking Upper Right:
        //Check the bounds of the incoming row and col,
        //if adding 1 to col does not result in a number greater than row size,
        //and subtracting row by 1 does not result in a negative number,
        if (r - ONE > ZERO && c + ONE > ZERO) {
            //iterate through the board to the TOP and RIGHT and increment count
            //if the same token is found next to it
            for (int row = r - ONE, col = c + ONE; row >= ZERO && col < getNumColumns() &&
                    whatsAtPos(row, col) == p; row--, col++) {
                count++;
                //If count >= 4, return true
                if (count >= getNumToWin()) {
                    return true;
                }
            }
        }

        //Checking Lower Right:
        //Check the bounds of the incoming row and col,
        //if adding 1 to row does not result in a number greater than row size,
        //and adding 1 to col does not result in a number greater than col size,
        if (r + ONE > ZERO && c + ONE > ZERO) {
            //iterate through the board to the BOTTOM and RIGHT and increment count
            //if the same token is found next to it
            for (int row = r + ONE, col = c + ONE; row < getNumRows() && col < getNumColumns() &&
                    whatsAtPos(row, col) == p; row++, col++) {
                count++;
                //If count >= 4, return true
                if (count >= getNumToWin()) {
                    return true;
                }
            }
        }

        //Else return false
        return false;
    }

}