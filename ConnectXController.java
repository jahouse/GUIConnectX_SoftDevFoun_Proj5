package cpsc2150.connectX;

/**
 * The controller class will handle communication between our View and our Model (IGameBoard)
 *
 * This is where you will write code
 *
 * You will need to include your IGameBoard interface
 * and both of the IGameBoard implementations from Homework 3
 * If your code was correct you will not need to make any changes to your IGameBoard implementation class
 */

public class ConnectXController {
    //our current game that is being played
    private IGameBoard curGame;


    //The screen that provides our view
    private ConnectXView screen;



    public static final int MAX_PLAYERS = 10;
    //our play tokens are hard coded. We could make a screen to get those from the user, but
    //I want to keep this example simple
    private char[] players = {'X', 'O', 'Y', 'Z', 'A', 'K', 'E', 'J', 'N', 'H'};

    int numPlayers;

    boolean state = false;
    boolean stateTie = false;
    int iter = 0;

    /**
     *
     * @param model the board implementation
     * @param view the screen that is shown
     * @post the controller will respond to actions on the view using the model.
     */
    ConnectXController(IGameBoard model, ConnectXView view, int np){
        this.curGame = model;
        this.screen = view;
        numPlayers = np;

    }

    /**
     * @param c the column of the last token placement
     * @return row of that last token placed
     * @pre c !>= COL_SIZE
     * @post  findRow = int of row of last token placed
     */
    public int findRow(int c) {
        //Start at top of the board
        int row = 0;
        Character store = ' ';

        while(row < curGame.getNumRows() && store.isLetter(curGame.whatsAtPos(row, c)))
        {
            //screen.setMessage("Curr" + curGame.whatsAtPos(row,c));
            row++;
        }
        row--;

        //At that index, set the token
        return row;
    }

    /**
     * @param col the column of the activated button
     * @post will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button
     */
    public void processButtonClick(int col)
    {
        String errorMsg = "";

        //First, check if the column is free
        if(!curGame.checkIfFree(col))
        {
            //if not, print an error message
            errorMsg += "Column is full";
        }

        if(!errorMsg.equals(""))
        {
            screen.setMessage(errorMsg);
        }
        //If no error, check if current state is true
        else if(state)
        {
            //Make new game
            newGame();
        }
        else
        {
            //Have player place token and set token
            curGame.placeToken(players[iter], col);
            screen.setMarker(findRow(col), col, players[iter]);

            //If a win occurs, change state to true & print message
            if(curGame.checkForWin(col))
            {
                state = true;
                screen.setMessage("Player " + players[iter] + " won!" +
                        "\nPlease click any button to start another game");
            }

            //If a tie occurs, change state to true & print a message
            if(curGame.checkTie())
            {
                state = true;
                screen.setMessage("The game is a tie.");
            }

            //If the state is false, change players and print message for who's turn
            if(!state)
            {
                iter = (iter + 1) % numPlayers;
                screen.setMessage("It is " + players[iter] + "'s turn. ");
            }
        }
    }

    /**
     * This method will start a new game by returning to the setup screen and controller
     */
    private void newGame()
    {
        //close the current screen
        screen.dispose();
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}
