/*
 * abhoove - Andrew Hoover
 * gmgarla - Gavin Garland
 * omerwarth - Owen Merwarth
 * benUsimmons - Ben Simmons
 */

package cpsc2150.extendedConnectX.models;

/**
 * Fast 2D array implementation for IGameBoard
 * 
 * @invariant MIN_ROWS <= numRows <= MAX_ROWS AND MIN_COLUMNS <= numCols <= MAX_COLUMNS
 *            MIN_NUM_TO_WIN <= numToWin <= MAX_NUM_TO_WIN AND numToWin <= numRows AND numToWin <= numCols AND
 *            No blank space character(s) (' ') can exist below a player's token or between players' tokens within the game board
 * 
 * @correspondence [self is a 2D array of characters] AND self.number_of_rows = numRows AND self.number_of_columns = numCols AND 
 *                  self.winning_number_of_tokens = numToWin
 */
public class GameBoard extends AbsGameBoard
{
    private int numRows;
    private int numCols;
    private int numToWin;
    private char[][] Board;

    /**
     * Constructs a GameBoard object that creates a new game board, sets the number of rows and columns on the board,
     * and sets the number of tokens in a rown needed to win
     * 
     * @pre MIN_ROWS <= rows <= MAX_ROWS AND MIN_COLUMNS <= cols <= MAX_COLUMNS
     *      MIN_NUM_TO_WIN <= winSize <= MAX_NUM_TO_WIN AND winSize <= rows AND winSize <= cols
     * 
     * @post numRows = rows AND numCols = cols AND numToWin = winSize
     *       AND [Board is a new 2D character array of size rows by cols, with each position containing a single blank space character (' ')]
     */
    public GameBoard(int rows, int cols, int winSize)
    {
        numRows = rows;
        numCols = cols;
        numToWin = winSize;
        
        // initializes game board to contain only blank space characters
        Board = new char[numRows][numCols];
        for (int r = 0; r < numRows; r++)
        {
            for (int c = 0; c < numCols; c++)
            {
                Board[r][c] = ' ';
            }
        }

    }

    public int getNumRows()
    {
        return numRows;
    }

    public int getNumColumns()
    {
        return numCols;
    }

    public int getNumToWin()
    {
        return numToWin;
    }
   
    public void dropToken(char p, int c)
    {
        // places the character p in column c. The token will be placed in the lowest available row in column c.
        // iterator to find the lowest row not containing a token
        int i = numRows - 1;
        while(i >= 0 && Board[i][c] == ' ')
        {
            i--;
        }
            i++;
        Board[i][c] = p;
    }
   
    public char whatsAtPos(BoardPosition pos)
    {
        //returns what is in the GameBoard at position pos If no marker is there, it returns a blank space char.
        return Board[pos.getRow()][pos.getColumn()];
    }
}