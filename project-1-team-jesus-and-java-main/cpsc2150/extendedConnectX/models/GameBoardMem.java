/*
 * abhoove - Andrew Hoover
 * gmgarla - Gavin Garland
 * omerwarth - Owen Merwarth
 * benUsimmons - Ben Simmons
 */

package cpsc2150.extendedConnectX.models;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Memory-efficient Map implementation for IGameBoard
 * 
 * @invariant MIN_ROWS <= numRows <= MAX_ROWS AND MIN_COLUMNS <= numCols <= MAX_COLUMNS
 *            MIN_NUM_TO_WIN <= numToWin <= MAX_NUM_TO_WIN AND numToWin <= numRows AND numToWin <= numCols AND
 *            No blank space character(s) (' ') can exist below a player's token or between players' tokens within the game board
 * 
 * @correspondence [self is a hashmap where the player's token is the key and the list of BoardPositions they occupy is the value] AND
 *                  self.number_of_rows = numRows AND self.number_of_columns = numCols AND self.winning_number_of_tokens = numToWin
 */
public class GameBoardMem extends AbsGameBoard
{
    private int numRows;
    private int numCols;
    private int numToWin;
    Map<Character, List<BoardPosition>> Board;

    /**
     * Constructs a GameBoard object that creates a new game board, sets the number of rows and columns on the board,
     * and sets the number of tokens in a rown needed to win
     *
     * @pre MIN_ROWS <= rows <= MAX_ROWS AND MIN_COLUMNS <= cols <= MAX_COLUMNS
     *      MIN_NUM_TO_WIN <= winSize <= MAX_NUM_TO_WIN AND winSize <= rows AND winSize <= cols
     *
     * @post numRows = rows AND numCols = cols AND numToWin = winSize
     *       AND Board = new HashMap<Character, List<BoardPosition>>();
     */
    public GameBoardMem(int rows, int cols, int winSize)
    {
        numRows = rows;
        numCols = cols;
        numToWin = winSize;
        Board = new HashMap<Character, List<BoardPosition>>();
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
        // loop to find the lowest row not containing a token
        int lowestRow = numRows - 1;
        BoardPosition curPos = new BoardPosition(lowestRow, c);
        while (lowestRow >= 0 && whatsAtPos(curPos) == ' ') 
        {
            lowestRow--;
            curPos = new BoardPosition(lowestRow, c);
        }
        lowestRow++;
        curPos = new BoardPosition(lowestRow, c);

        // adds p as a key with an empty List if the token has not previously been added as a key
        if (!Board.containsKey(p))
        {
            Board.put(p, new ArrayList<BoardPosition>());
        }

        // adds token in lowest row not containing a token
        Board.get(p).add(curPos);
    }

    public char whatsAtPos(BoardPosition pos)
    {
        // iterates through each key (player token) checking if the list of occupied BoardPositions contains pos
        // returns the player's token if pos if they occupy pos
        for (char player : Board.keySet())
        {   
            if (Board.get(player).contains(pos))
            {
                return player;
            }
        }

        // returns a blank space if no player occupies pos
        return ' ';
    }

    @Override public boolean isPlayerAtPos(BoardPosition pos, char player)
    {
        List<BoardPosition> playerPositions = Board.get(player);

        boolean result = playerPositions != null && playerPositions.contains(pos);

        return result;
    }
}
