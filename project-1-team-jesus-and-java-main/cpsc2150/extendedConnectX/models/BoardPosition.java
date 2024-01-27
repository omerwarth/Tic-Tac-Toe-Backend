/*
 * abhoove - Andrew Hoover
 * gmgarla - Gavin Garland
 * omerwarth - Owen Merwarth
 * benUsimmons - Ben Simmons
 */

package cpsc2150.extendedConnectX.models;

/**
 * BoardPosition class to represent a specific position on the game board
 */
public class BoardPosition
{
    private int Row;
    private int Column;

    /**
     * Constructs a BoardPosition object with specific row and column indices to represent a single position on the game board
     * 
     * @param aRow integer for the row index of the BoardPosition object to be created
     * @param aColumn integer for the column index of the BoardPosition object to be created
     *
     * @pre None
     * 
     * @post Row = aRow AND Column = aColumn
     */
    public BoardPosition(int aRow, int aColumn)
    {
        Row = aRow;
        Column = aColumn;
    }

    /**
     * Returns the row index of the BoardPosition object
     * 
     * @return integer indicating the row index for this BoardPosition object
     * 
     * @pre None
     * 
     * @post getRow = Row AND Row = #Row AND Column = #Column
     */
    public int getRow()
    {
        return Row;
    }

    /**
     * Returns the column index of the BoardPosition object
     * 
     * @return integer indicating the column index for this BoardPosition object
     * 
     * @pre None
     * 
     * @post getColumn = Column AND Row = #Row AND Column = #Column
     */
    public int getColumn()
    {
        return Column;
    }

    /**
     * Returns a boolean indicating whether a BoardPosition object is equal to another BoardPosition object
     * 
     * @param obj another BoardPosition object to be compared with this BoardPosition object
     * 
     * @return true if this BoardPosition object is equal to another BoardPosition object, and false otherwise
     * 
     * @pre None
     * 
     * @post [equals = true if this BoardPosition object is equivalent to the BoardPosition object passed in via parameter, or equals = false otherwise] AND Row = #Row AND Column = #Column
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof BoardPosition)) {
            return false;
        } else {
            BoardPosition board = (BoardPosition) obj;
            return ((Row == board.getRow()) && (Column == board.getColumn()));
        }
    }

    /**
     * Returns a string of the row and column indices of this BoardPosition
     * 
     * @return string containing the row and column indices of this BoardPosition object
     * 
     * @pre None
     * 
     * @post [toString is string in the format "<row>,<column>"] AND Row = #Row AND Column = #Column
     */
    @Override
    public String toString()
    {
        return Integer.toString(Row)+ "," + Integer.toString(Column);
    }
}