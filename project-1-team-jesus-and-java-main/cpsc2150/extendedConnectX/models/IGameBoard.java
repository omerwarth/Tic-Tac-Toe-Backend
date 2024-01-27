/*
 * abhoove - Andrew Hoover
 * gmgarla - Gavin Garland
 * omerwarth - Owen Merwarth
 * benUsimmons - Ben Simmons
 */

package cpsc2150.extendedConnectX.models;

/**
 * IGameBoard defines a game board of characters to play Connect X.
 * (0,0) represents the bottom left corner of the game board.
 *
 * @initialization ensures: GameBoard contains only blank space characters, is number_of_rows by number_of_columns, and requires winning_number_of_tokens for a player to win
 *
 * @defines: number_of_rows: Z - number of rows for the game board
 *           number_of_columns: Z - number of columns for the game board
 *           winning_number_of_tokens: Z - the number of tokens in a row required to win
 *
 * @constraints: MIN_ROWS <= number_of_rows <= MAX_ROWS AND
 *               MIN_COLUMNS <= number_of_columns <= MAX_COLUMNS AND
 *               MIN_NUM_TO_WIN <= winning_number_of_tokens <= MAX_NUM_TO_WIN AND
 *               winning_number_of_tokens <= number_of_rows AND
 *               winning_number_of_tokens <= number_of_columns AND
 *               No blank space character(s) (' ') can exist below a player's token or between players' tokens within the game board
 */
public interface IGameBoard
{
    public static final int MIN_ROWS = 3;
    public static final int MIN_COLUMNS = 3;
    public static final int MIN_NUM_TO_WIN = 3;
    public static final int MAX_ROWS = 100;
    public static final int MAX_COLUMNS = 100;
    public static final int MAX_NUM_TO_WIN = 25;
    
    /**
     * Returns the number of rows in the game board
     * 
     * @pre None
     * 
     * @post getNumRows = number_of_rows AND self = #self
     * 
     * @return integer indicating the number of rows in the game board
     */
    public int getNumRows();

    /**
     * Returns the number of columns in the game board
     * 
     * @pre None
     * 
     * @post getNumColumns = number_of_columns AND self = #self
     * 
     * @return integer indicating the number of columns in the game board
     */
    public int getNumColumns();

    /**
     * Returns the number of consecutive tokens needed to win
     * 
     * @pre None
     * 
     * @post getNumToWin = winning_number_of_tokens AND self = #self
     * 
     * @return integer indicating the number of consecutive tokens needed to win
     */
    public int getNumToWin();

    /**
     * Returns a boolean indicating whether a column is free or full of tokens
     * 
     * @param c integer of the column index of the board that will be checked
     * 
     * @pre 0 <= c < number_of_columns
     * 
     * @post checkIfFree = [true if the column c is not full, or false otherwise] AND self = #self
     * 
     * @return boolean indicating if the column is free
     */
    default boolean checkIfFree(int c)
    {
      // checks position at top of column to check; returns true if a blank space is found
      BoardPosition topOfCol = new BoardPosition(getNumRows() - 1, c);
      if(whatsAtPos(topOfCol) == ' ')
      { 
        return true;
      }

      // returns false if a player occupies the top spot in the column
      return false;
    }
   
    /**
     * Drops a players token into a column in the game board based on which player they are
     * 
     * @param p character representing the player's token to be dropped in the column
     * @param c integer represnting the column where the token will be dropped
     * 
     * @pre p == [One of the players' selected tokens] AND 0 <= c < number_of_columns AND checkIfFree(c) == true
     * 
     * @post [self = #self, but self is updated to include the newly placed token at the lowest possible row in column c]
     */
    public void dropToken(char p, int c);
   
    /**
     * Returns a boolean indicating whether the player who just placed a token has won or not
     * 
     * @param c integer representing the column to be checked for a win
     * 
     * @pre [c is the column that the last token was placed in]
     * 
     * @post checkForWin = [true IFF checkHorizWin == true OR checkVertWin == true OR checkDiagWin == true, and false otherwise] AND self = #self
     * 
     * @return boolean indicating if the player who just played has won the game
     */
    default boolean checkForWin(int c)
    {
      // finds the player at the highest occupied space within the column being checked
      int highestRow = getNumRows() - 1;
      BoardPosition currPosition = new BoardPosition(highestRow, c);
      char playerToCheck = whatsAtPos(currPosition);
      while(highestRow >= 0 && playerToCheck == ' ')
      {
        highestRow--;
        currPosition = new BoardPosition(highestRow, c);
        playerToCheck = whatsAtPos(currPosition);
      }

      // returns true if the player wins in any direction
      if(checkHorizWin(currPosition, playerToCheck) || checkVertWin(currPosition, playerToCheck) || checkDiagWin(currPosition, playerToCheck))
      {
        return true;
      }

      // returns false if no win is found
      return false;
    }
    
    /**
     * Returns a boolean indicating whether or not the game has ended in a tie
     * 
     * @pre None
     * 
     * @post checkTie = [true if all of the columns are full, and false otherwise] AND self = #self
     * 
     * @return boolean indicating whether or not a tie has occurred, which results when all columns are full
     */
    default boolean checkTie()
    {
      // check if all columns are full; if one is free to hold tokens,
      // a tie has not occurred and the method returns false
      for(int col = 0; col < getNumColumns(); col++)
      {
        if(checkIfFree(col))
        {
          return false;
        }
      }

      // returns true if all columns are full
      return true;
    }
    
    /**
     * Returns a boolean indicating whether a player has won in the horizontal direction
     * 
     * @param pos BoardPosition object that is represents the position to check for the win
     * @param p a character that shows which player's tokens to look for
     *  
     * @pre p == [One of the players' selected tokens]
     * 
     * @post checkHorizWin = [true if there are winning_number_of_tokens in a row horizontally including pos, or false otherwise] AND self = #self
     * 
     * @return true or false depending on if there is winning_number_of_tokens in a row horizontally
     */
    default boolean checkHorizWin(BoardPosition pos, char p)
    {
      // local variables for method
      int numTokensInRow = 0;
      int col = pos.getColumn();
      int row = pos.getRow();
      BoardPosition curPos = new BoardPosition(row, col);

      // checks column at current position and columns ahead of it
      // the loop stops when the last column is reached, the number of tokens to win has
      // been reached, or the character retreived is not the player's token
      while (col < getNumColumns() && numTokensInRow < getNumToWin() && isPlayerAtPos(curPos, p))
      {
        numTokensInRow++;
        col++;
        curPos = new BoardPosition(row, col);
      }

      // checks columns behind the current position
      // the loop stops when the first column is reached, the number of tokens to win has
      // been reached, or the character retreived is not the player's token
      col = pos.getColumn() - 1;
      curPos = new BoardPosition(row, col);
      while (col >= 0 && numTokensInRow < getNumToWin() && isPlayerAtPos(curPos, p))
      {
        numTokensInRow++;
        col--;
        curPos = new BoardPosition(row, col);
      }

      // checks if winning number of tokens in a row is reached
      // returns true if yes, false otherwise
      if (numTokensInRow == getNumToWin())
      {
        return true;
      }
      
      return false;
    }
    
    /**
     * Returns a boolean indicating whether a player has won in the vertical direction
     * 
     * @param pos BoardPosition object that is represents the position to check for the win
     * @param p a character that shows which player's tokens to look for
     * 
     * @pre p == [One of the players' selected tokens]
     * 
     * @post checkVertWin = [true if there are winning_number_of_tokens in a row vertically including pos, or false otherwise] AND self = #self
     * 
     * @return true or false depending on if there is winning_number_of_tokens in a row vertically
     */
    default boolean checkVertWin(BoardPosition pos, char p)
    {
      // local variables for method
      int numTokensInRow = 0;
      int col = pos.getColumn();
      int row = pos.getRow();
      BoardPosition curPos = new BoardPosition(row, col);

      // checks row at current position and rows ahead of it
      // the loop stops when the last row is reached, the number of tokens to win has
      // been reached, or the character retreived is not the player's token
      while (row < getNumRows() && numTokensInRow < getNumToWin() && isPlayerAtPos(curPos, p))
      {    
        numTokensInRow++;
        row++;
        curPos = new BoardPosition(row, col);
      }

      // checks rows behind the current position
      // the loop stops when the first row is reached, the number of tokens to win has
      // been reached, or the character retreived is not the player's token
      row = pos.getRow() - 1;
      curPos = new BoardPosition(row, col);
      while (row >= 0 && numTokensInRow < getNumToWin() && isPlayerAtPos(curPos, p))
      {
        numTokensInRow++;
        row--;
        curPos = new BoardPosition(row, col);
      }

      // checks if winning number of tokens in a row is reached
      // returns true if yes, false otherwise
      if (numTokensInRow == getNumToWin())
      {
          return true;
      }
      
      return false;
    }
    
    /**
     * Returns a boolean indicating whether a player has won in the diagonal direction
     * 
     * @param pos BoardPosition object that is represents the position to check for the win
     * @param p a character that shows which player's tokens to look for
     * 
     * @pre p == [One of the players' selected tokens]
     * 
     * @post checkDiagWin = [true if there are winning_number_of_tokens in a row horizontally including pos, or false otherwise] AND self = #self
     * 
     * @return true or false depending on if there is winning_number_of_tokens in a row diagonally
     */
    default boolean checkDiagWin(BoardPosition pos, char p)
    {
      // local variables for method
      int numTokensInRow = 0;
      int row = pos.getRow();
      int col = pos.getColumn();
      BoardPosition curPos = new BoardPosition(row, col);
  
      // check for left to right diagonal
      // the method checks up and right, and then down and left until
      // the boundary of the game board is reached, the number of tokens to win
      // is attained, or the player's token is no longer found in a successive spot
      while(row < getNumRows() && col < getNumColumns() && numTokensInRow < getNumToWin() && isPlayerAtPos(curPos, p))
      {
        numTokensInRow++;
        row++;
        col++;
        curPos = new BoardPosition(row, col);
      }
      row = pos.getRow() - 1;
      col = pos.getColumn() - 1;
      curPos = new BoardPosition(row, col);
      while(row >= 0 && col >= 0 && numTokensInRow < getNumToWin() && isPlayerAtPos(curPos, p))
      {
        numTokensInRow++;
        row--;
        col--;
        curPos = new BoardPosition(row, col);
      }

      // returns true if winning number of tokens in a row is reached for right to left diagonal
      if (numTokensInRow == getNumToWin())
      {
          return true;
      }

      // resets local variables to check opposite diagonal
      numTokensInRow = 0;
      row = pos.getRow();
      col = pos.getColumn();
      curPos = new BoardPosition(row, col);

      // check for right to left diagonal
      // the method checks up and left, and then down and right until
      // the boundary of the game board is reached, the number of tokens to win
      // is attained, or the player's token is no longer found in a successive spot
      while(row < getNumRows() && col >= 0 && numTokensInRow < getNumToWin() && isPlayerAtPos(curPos, p))
      {
        numTokensInRow++;
        row++;
        col--;
        curPos = new BoardPosition(row, col);
      }
      row = pos.getRow() - 1;
      col = pos.getColumn() + 1;
      curPos = new BoardPosition(row, col);
      while(row >= 0 && col < getNumColumns() && numTokensInRow < getNumToWin() && isPlayerAtPos(curPos, p))
      {
        numTokensInRow++;
        row--;
        col++;
        curPos = new BoardPosition(row, col);
      }

      // returns true if winning number of tokens in a row is reached for left to right diagonal
      if (numTokensInRow == getNumToWin())
      {
          return true;
      }
      
      // returns false if no diagonal win is found
      return false;
    }
   
    /**
     * checks the GameBoard at an specifc position to see what is contained
     * 
     * @param pos BoardPosition object that indicates the position of the board to check
     * 
     * @pre None
     * 
     * @post whatsAtPos = [One of the players' selected tokens OR ' '] AND self = #self
     * 
     * @return One of the players' selected tokens, or a blank space
     */
    public char whatsAtPos(BoardPosition pos);
    
    /**
     * checks the Gameboard at a specific position to see if a player has a token at this board position
     * 
     * @param pos BoardPosition object that is used to locate the position that is being checked
     * @param player char that specifies which player the board will be checked for
     * 
     * @pre player == [One of the players' selected tokens]
     * 
     * @post isPlayerAtPos = [true IFF the player is at pos, or false if the player is not at pos] AND self = #self
     * 
     * @return true if the specified player token is at the position, and false otherwise
     */
    default boolean isPlayerAtPos(BoardPosition pos, char player)
    {
      // checks if character at the position to check is the player's character
      if(whatsAtPos(pos) == player)
      {
        return true;
      }

      // returns false if character at the position to check is not the player's character
      return false;
    }
}