/*
 * abhoove - Andrew Hoover
 * gmgarla - Gavin Garland
 * omerwarth - Owen Merwarth
 * benUsimmons - Ben Simmons
 */

package cpsc2150.extendedConnectX.models;

/**
 * AbsGameBoard provides an overridden version of toString that will convert the entire board to a single string.
 * This method will be inherited by both implementing classes.
 */
public abstract class AbsGameBoard implements IGameBoard
{
    /**
     * Returns a string that contains the entire game board
     * 
     * @pre None
     * 
     * @post toString = [a string that contains the entire game board as a single string] AND self = #self
     * 
     * @return string that contains the entire game board
     */
    @Override public String toString()
    {
        String gameBoard = "|";
        int doubleDigits = 10;

        // adds column numbers to appropriate columns at the top of the game board string
        for(int i = 0; i < getNumColumns(); i++)
        {
            // adds space before number if the number is a single digit
            if(i < doubleDigits)
            {
                gameBoard += " ";
            }
            gameBoard += i + "|";
        }
        gameBoard += "\n";

        // adds the remaining game board, including all tokens and spaces, to the string
        for(int r = getNumRows() - 1; r >= 0; r--)
        {
            gameBoard += "|";
            for(int c = 0; c < getNumColumns(); c++)
            {
                BoardPosition currPosition = new BoardPosition(r, c);
                gameBoard += whatsAtPos(currPosition);
                gameBoard += " |";
            }
            gameBoard += "\n";
        }

        // returns the entire game board
        return gameBoard;
    }
}
