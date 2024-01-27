/*
 * abhoove - Andrew Hoover
 * gmgarla - Gavin Garland
 * omerwarth - Owen Merwarth
 * benUsimmons - Ben Simmons
 */

package cpsc2150.extendedConnectX.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import cpsc2150.extendedConnectX.models.IGameBoard;
import cpsc2150.extendedConnectX.models.BoardPosition;
import cpsc2150.extendedConnectX.models.GameBoard;

public class TestGameBoard
{
    /************************************************************************************
     *
     * GameBoard Factory and method to compare the expected board to the actual board
     *
     ************************************************************************************/

    // factory calls the GameBoard constructor
    private IGameBoard makeGameBoard(int rows, int cols, int tokens)
    {
        return new GameBoard(rows, cols, tokens);
    }

    // converts 2D array to a String to test expected output versus actual output
    private String expectedArrayToString(char[][] expected)
    {
        String expectedBoard = "|";
        int doubleDigits = 10;
        int rows = expected.length;
        int cols = expected[0].length;

        // adds column numbers to appropriate columns at the top of the game board string
        for (int i = 0; i < cols; i++)
        {
            // adds space before number if the number is a single digit
            if (i < doubleDigits)
            {
                expectedBoard += " ";
            }
            expectedBoard += i + "|";
        }
        expectedBoard += "\n";

        // adds the rest of the board stored in the array to the string
        for (int r = 0; r < rows; r++)
        {
            expectedBoard += "|";
            for (int c = 0; c < cols; c++)
            {
                expectedBoard += expected[r][c];
                expectedBoard += " |";
            }
            expectedBoard += "\n";
        }

        // returns the expected board array as a string
        return expectedBoard;
    }

    /************************************************************************************
     *
     * Constructor tests
     *
     ************************************************************************************/

     // tests constructor with a square 5 rows by 5 columns board
    @Test
    public void testGameBoard_Constructor_5Rows5Columns()
    {
        char[][] expectedGB;
        expectedGB = new char[][] {
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '}
        };
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        assertEquals(expectedArrayToString(expectedGB), gb.toString());
    }

    // tests constructor with smallest number of rows and a double digit number of columns
    @Test
    public void testGameBoard_Constructor_3Rows12Columns()
    {
        char[][] expectedGB;
        expectedGB = new char[][] {
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
        };
        int rows = 3;
        int cols = 12;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        assertEquals(expectedArrayToString(expectedGB), gb.toString());
    }

    // tests constructor with a double digit number of rows and the smallest number of columns
    @Test
    public void testGameBoard_Constructor_12Rows3Columns()
    {
        char[][] expectedGB;
        expectedGB = new char[][] {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
        };
        int rows = 12;
        int cols = 3;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        assertEquals(expectedArrayToString(expectedGB), gb.toString());
    }

    /************************************************************************************
     *
     * checkIfFree tests
     *
     ************************************************************************************/

    // tests that checkIfFree returns true if a column is completely empty
    @Test
    public void testGameBoard_checkIfFree_0_EmptyColumn_true()
    {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // asserts that checkIfFree returns true if the column is completely empty
        assertTrue(gb.checkIfFree(0));
    }

    // tests that checkIfFree returns true if a column is partially full
    @Test
    public void testGameBoard_checkIfFree_0_PartiallyFullColumn_true()
    {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // partially fill column 0 with tokens
        gb.dropToken('Z', 0);
        gb.dropToken('Y', 0);
        gb.dropToken('X', 0);

        // asserts that checkIfFree returns true if the column is partially full
        assertTrue(gb.checkIfFree(0));
    }

    // tests that checkIfFree returns false if a column is completely full
    @Test
    public void testGameBoard_checkIfFree_0_FullColumn_false()
    {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // fill column 0 with tokens
        gb.dropToken('Z', 0);
        gb.dropToken('Y', 0);
        gb.dropToken('X', 0);
        gb.dropToken('W', 0);
        gb.dropToken('V', 0);

        // asserts that checkIfFree returns false if the column is completely full
        assertFalse(gb.checkIfFree(0));
    }

    /************************************************************************************
     * 
     * checkHorizWin Tests
     * 
     ************************************************************************************/

    //Test to check that checkHorizWin returns false when an empty board is created
    @Test
    public void testGameBoard_checkHorizWin_EmptyBoard_BottomRight_False()
    {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 4;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        BoardPosition pos = new BoardPosition(0, 4);

        // assert that checkHoriz returns false since the board is empty
        assertFalse(gb.checkHorizWin(pos, 'X'));
    }

    //This test is for when a win has occurred on the bottom row and the last token is placed in the bottom left corner
    @Test
    public void testGameBoard_checkHorizWin_LastPlacedOnLeft_BottomLeft_True()
    {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 4;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char p1 = 'X';
        gb.dropToken(p1, 0);
        gb.dropToken(p1, 1);
        gb.dropToken(p1, 2);
        gb.dropToken(p1, 3);
        BoardPosition pos = new BoardPosition(0, 0);

        // assert that checkHoriz returns true since the board has a horizontal win
        assertTrue(gb.checkHorizWin(pos, p1));
    }

    //This test is for when the gameboard has a horizontal win on a row in the middle of the board
    @Test
    public void testGameBoard_checkHorizWin_LastPlacedTokenRow1Col3_True(){
        int rows = 5;
        int cols = 5;
        int numToWin = 4;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char p1 = 'X';
        char p2 = 'O';
        gb.dropToken(p1, 0);
        gb.dropToken(p2, 0);
        gb.dropToken(p1, 1);
        gb.dropToken(p2, 1);
        gb.dropToken(p1, 2);
        gb.dropToken(p2, 2);
        gb.dropToken(p1, 3);
        gb.dropToken(p2, 3);
        BoardPosition pos = new BoardPosition(1, 3);

        // assert that checkHoriz returns true since the board has a horizontal win
        assertTrue(gb.checkHorizWin(pos, p2));
    }

    //This test fills the board with multiple characters and the last placed token makes 4 in a row with 'A' on the top row
    @Test
    public void testGameBoard_checkHorizWin_LastPlacedOnLeft_TopLeft_True()
    {
        int rows = 5;
        int cols = 5;
        int numToWin = 4;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char p1 = 'A';
        char p2 = 'B';
        char p3 = 'C';
        for (int r=0; r<2; r++){
            for(int i=0; i<5; i++){
                gb.dropToken(p2, i);
                gb.dropToken(p3, i);
            }
        }
        for(int i=0; i<5; i++) gb.dropToken(p1, i);
        BoardPosition pos = new BoardPosition(4, 0);

        // assert that checkHoriz returns true since the board has a horizontal win
        assertTrue(gb.checkHorizWin(pos, p1));

    }

    /************************************************************************************
     *
     * CheckVertWin tests
     *
     ************************************************************************************/

     //Test to check that checkVertWin returns false when an empty board is created
    @Test
    public void testGameBoard_checkVertWin_EmptyBoard_BottomRight_False()
    {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 4;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        BoardPosition pos = new BoardPosition(0, 4);

        // assert that checkVertWin returns false since the board is empty
        assertFalse(gb.checkVertWin(pos, 'X'));
    }

    //This test is for when the gameboard has a vertical win in the middle column
    @Test
    public void testGameBoard_checkVertWin_MiddleColumn_True()
    {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 4;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char p1 = 'X';
        gb.dropToken(p1, 2);
        gb.dropToken(p1, 2);
        gb.dropToken(p1, 2);
        gb.dropToken(p1, 2);
        BoardPosition pos = new BoardPosition(3, 2);

        // assert that checkVertWin returns true since the board has a vertical win
        assertTrue(gb.checkVertWin(pos, p1));
    }

    //This test is for when the gameboard has a vertical win in the far left column
    @Test
    public void testGameBoard_checkVertWin_FarLeftColumn_True(){
        int rows = 5;
        int cols = 5;
        int numToWin = 4;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char p1 = 'X';
        gb.dropToken(p1, 0);
        gb.dropToken(p1, 0);
        gb.dropToken(p1, 0);
        gb.dropToken(p1, 0);
        BoardPosition pos = new BoardPosition(3, 0);

        // assert that checkVertWin returns true since the board has a vertical win
        assertTrue(gb.checkVertWin(pos, p1));
    }

    //This test is for when the gameboard has a vertical win in the far right column with another token below it
    @Test
    public void testGameBoard_checkVertWin_FarRightColumn_OpponentBelow_True()
    {
        int rows = 5;
        int cols = 5;
        int numToWin = 4;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char p1 = 'A';
        gb.dropToken('B', 4);
        gb.dropToken(p1, 4);
        gb.dropToken(p1, 4);
        gb.dropToken(p1, 4);
        gb.dropToken(p1, 4);
        BoardPosition pos = new BoardPosition(4, 4);

        // assert that checkVertWin returns true since the board has a vertical win
        assertTrue(gb.checkVertWin(pos, p1));
    }

    /************************************************************************************
     *
     * checkDiagWin tests
     *
     ************************************************************************************/

    // Tests checkDiagWin with a NESW diagonal beginning in the bottom left corner 
    // where the last token is placed on the far left of the diagonal; 
    // checkDiagWin is called on the last token placed, the furthest left token;
    // should return true
    @Test
    public void testGameBoard_checkDiagWin_NESW_LastPlacedOnLeft_BottomLeft_true()
    {
        // construct gameBoard
        int rows = 8;
        int cols = 8;
        int numToWin = 5;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // randomChar to fill space that is not the diagonal to check;
        char randomChar = 'A';

        // defines the last position where a token will be placed for the diagonal
        int lastTokenRow = 0;
        int lastTokenCol = 0;
        BoardPosition lastToken = new BoardPosition(lastTokenRow, lastTokenCol);

        // iterate through all relevant spaces
        for (int r = 0; r < numToWin; r++)
        {
            for (int c = 0; c < numToWin; c++)
            {
                // if the space falls on the diagonal, add 'Z' token
                if (r == c)
                {
                    gb.dropToken('Z', c);
                }

                // otherwise, add random character to non-diagonal space
                else
                {
                    gb.dropToken(randomChar, c);

                    // update the random character
                    if (randomChar < 'Y')
                    {
                        randomChar++;
                    } 
                    else 
                    {
                        randomChar = 'A';
                    }
                }
            }
        }

        // assert that checkDiagWin returns true for 'Z' since it has 5 tokens in a row on diagonal
        assertTrue(gb.checkDiagWin(lastToken, 'Z'));
    }

    // Tests checkDiagWin with a NESW diagonal beginning in the top right corner 
    // where the last token is placed on the far right of the diagonal; 
    // checkDiagWin is called on the last token placed, the furthest right token;
    // should return true
    @Test
    public void testGameBoard_checkDiagWin_NESW_LastPlacedOnRight_TopRight_true()
    {
        // construct gameBoard
        int rows = 8;
        int cols = 8;
        int numToWin = 5;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // randomChar to fill space that is not the diagonal to check;
        char randomChar = 'A';

        // defines the last position where a token will be placed for the diagonal
        int lastTokenRow = 7;
        int lastTokenCol = 7;
        BoardPosition lastToken = new BoardPosition(lastTokenRow, lastTokenCol);

        // iterate through all relevant spaces
        for (int r = 0; r < rows; r++)
        {
            for (int c = cols - numToWin; c < cols; c++)
            {
                // if the space falls on the diagonal, add 'Z' token
                if (r == c)
                {
                    gb.dropToken('Z', c);
                }

                // otherwise, add random character to non-diagonal space
                else
                {
                    gb.dropToken(randomChar, c);

                    // update the random character
                    if (randomChar < 'Y')
                    {
                        randomChar++;
                    } 
                    else 
                    {
                        randomChar = 'A';
                    }
                }
            }
        }

        // assert that checkDiagWin returns true for 'Z' since it has 5 tokens in a row on diagonal
        assertTrue(gb.checkDiagWin(lastToken, 'Z'));
    }

    // Tests checkDiagWin with a NESW diagonal beginning in the bottom left corner 
    // where the last token is placed in the middle of the diagonal; 
    // checkDiagWin is called on the last token placed, the middle token;
    // should return true
    @Test
    public void testGameBoard_checkDiagWin_NESW_LastPlacedInMiddle_BottomLeft_true()
    {
        // construct gameBoard
        int rows = 8;
        int cols = 8;
        int numToWin = 5;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // randomChar to fill space that is not the diagonal to check;
        char randomChar = 'A';

        // defines the last position where a token will be placed for the diagonal
        int lastTokenRow = 2;
        int lastTokenCol = 2;
        BoardPosition lastToken = new BoardPosition(lastTokenRow, lastTokenCol);

        // iterate through all relevant spaces
        for (int r = 0; r < numToWin; r++)
        {
            for (int c = 0; c < numToWin; c++) 
            {
                // if the space falls on the diagonal, add 'Z' token
                if (r == c) 
                {
                    gb.dropToken('Z', c);
                }

                // otherwise, add random character to non-diagonal space
                else 
                {
                    gb.dropToken(randomChar, c);

                    // update the random character
                    if (randomChar < 'Y') 
                    {
                        randomChar++;
                    } 
                    else 
                    {
                        randomChar = 'A';
                    }
                }
            }
        }

        // assert that checkDiagWin returns true for 'Z' since it has 5 tokens in a row on diagonal
        assertTrue(gb.checkDiagWin(lastToken, 'Z'));
    }

    // Tests checkDiagWin with a NWSE diagonal beginning in the bottom right corner 
    // where the last token is placed on the far right of the diagonal; 
    // checkDiagWin is called on the last token placed, the far right token;
    // should return true
    @Test
    public void testGameBoard_checkDiagWin_NWSE_LastPlacedOnRight_BottomRight_true() 
    {
        // construct gameBoard
        int rows = 8;
        int cols = 8;
        int numToWin = 5;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // randomChar to fill space that is not the diagonal to check;
        char randomChar = 'A';

        // defines the last position where a token will be placed for the diagonal
        int lastTokenRow = 0;
        int lastTokenCol = 7;
        BoardPosition lastToken = new BoardPosition(lastTokenRow, lastTokenCol);

        // iterate through all relevant spaces
        for (int r = 0; r < numToWin; r++) {
            for (int c = cols - numToWin; c < cols; c++) 
            {
                // if the space falls on the diagonal, add 'Z' token
                if (r + c == rows - 1) 
                {
                    gb.dropToken('Z', c);
                }

                // otherwise, add random character to non-diagonal space
                else 
                {
                    gb.dropToken(randomChar, c);

                    // update the random character
                    if (randomChar < 'Y') 
                    {
                        randomChar++;
                    } 
                    else 
                    {
                        randomChar = 'A';
                    }
                }
            }
        }

        // assert that checkDiagWin returns true for 'Z' since it has 5 tokens in a row on diagonal
        assertTrue(gb.checkDiagWin(lastToken, 'Z'));
    }

    // Tests checkDiagWin with a NWSE diagonal beginning in the top left corner 
    // where the last token is placed on the far left of the diagonal; 
    // checkDiagWin is called on the last token placed, the far left token;
    // should return true
    @Test
    public void testGameBoard_checkDiagWin_NWSE_LastPlacedOnLeft_TopLeft_true() 
    {
        // construct gameBoard
        int rows = 8;
        int cols = 8;
        int numToWin = 5;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // randomChar to fill space that is not the diagonal to check;
        char randomChar = 'A';

        // defines the last position where a token will be placed for the diagonal
        int lastTokenRow = 7;
        int lastTokenCol = 0;
        BoardPosition lastToken = new BoardPosition(lastTokenRow, lastTokenCol);

        // iterate through all relevant spaces
        for (int r = 0; r < rows; r++) 
        {
            for (int c = 0; c < numToWin; c++) 
            {
                // if the space falls on the diagonal, add 'Z' token
                if (r + c == rows - 1) 
                {
                    gb.dropToken('Z', c);
                }

                // otherwise, add random character to non-diagonal space
                else 
                {
                    gb.dropToken(randomChar, c);

                    // update the random character
                    if (randomChar < 'Y') 
                    {
                        randomChar++;
                    } 
                    else 
                    {
                        randomChar = 'A';
                    }
                }
            }
        }

        // assert that checkDiagWin returns true for 'Z' since it has 5 tokens in a row on diagonal
        assertTrue(gb.checkDiagWin(lastToken, 'Z'));
    }

    // Tests checkDiagWin with a NWSE diagonal beginning in the bottom right corner 
    // where the last token is placed in the middle of the diagonal; 
    // checkDiagWin is called on the last token placed, the middle token;
    // should return true
    @Test
    public void testGameBoard_checkDiagWin_NWSE_LastPlacedInMiddle_BottomRight_true()
    {
        // construct gameBoard
        int rows = 8;
        int cols = 8;
        int numToWin = 5;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // randomChar to fill space that is not the diagonal to check;
        char randomChar = 'A';

        // defines the last position where a token will be placed for the diagonal
        int lastTokenRow = 2;
        int lastTokenCol = 5;
        BoardPosition lastToken = new BoardPosition(lastTokenRow, lastTokenCol);

        // iterate through all relevant spaces
        for (int r = 0; r < numToWin; r++)
        {
            for (int c = cols - numToWin; c < cols; c++) 
            {
                // if the space falls on the diagonal, add 'Z' token
                if (r + c == rows - 1) 
                {
                    gb.dropToken('Z', c);
                }

                // otherwise, add random character to non-diagonal space
                else 
                {
                    gb.dropToken(randomChar, c);

                    // update the random character
                    if (randomChar < 'Y') {
                        randomChar++;
                    } 
                    else 
                    {
                        randomChar = 'A';
                    }
                }
            }
        }

        // assert that checkDiagWin returns true for 'Z' since it has 5 tokens in a row on diagonal
        assertTrue(gb.checkDiagWin(lastToken, 'Z'));
    }

    // Tests checkDiagWin with a NESW diagonal with no token on an edge 
    // where the last token is placed in the third row (row = 2), third column (col = 2); 
    // checkDiagWin is called on the last token placed, the far left token;
    // should return true
    @Test
    public void testGameBoard_checkDiagWin_NESW_LastPlacedInRow2Col2_true() 
    {
        // construct gameBoard
        int rows = 8;
        int cols = 8;
        int numToWin = 5;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // randomChar to fill space that is not the diagonal to check;
        char randomChar = 'A';

        // defines the last position where a token will be placed for the diagonal
        int lastTokenRow = 2;
        int lastTokenCol = 2;
        BoardPosition lastToken = new BoardPosition(lastTokenRow, lastTokenCol);

        // iterate through all relevant spaces
        for (int r = 0; r < lastTokenRow + numToWin; r++) 
        {
            for (int c = lastTokenCol; c < lastTokenCol + numToWin; c++) 
            {
                // if the space falls on the diagonal, add 'Z' token
                if (r == c) 
                {
                    gb.dropToken('Z', c);
                }

                // otherwise, add random character to non-diagonal space
                else 
                {
                    gb.dropToken(randomChar, c);

                    // update the random character
                    if (randomChar < 'Y') 
                    {
                        randomChar++;
                    } 
                    else 
                    {
                        randomChar = 'A';
                    }
                }
            }
        }

        // assert that checkDiagWin returns true for 'Z' since it has 5 tokens in a row on diagonal
        assertTrue(gb.checkDiagWin(lastToken, 'Z'));
    }

    /************************************************************************************
     *
     * checkTie tests
     *
     ************************************************************************************/
    
     //This is an empty board test and checkTie should return false
    @Test
    public void testGameBoard_checkTie_EmptyBoard_false() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // assert that checkTie returns false since the board is empty
        assertFalse(gb.checkTie());
    }

    //This test fills the board with only a few characters; checkTie should return false
    @Test
    public void testGameBoard_checkTie_PartiallyFullBoard_false() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        // partially fill the gameBoard with tokens
        gb.dropToken('C', 1);
        gb.dropToken('B', 0);
        gb.dropToken('A', 0);

        // assert that checkTie returns false since the board is not full
        assertFalse(gb.checkTie());
    }

    //This test fills the gameboard except for one space; checkTie should return false since there is space for one more token
    @Test
    public void testGameBoard_checkTie_AlmostFullBoard_false()
    {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char randomChar = 'A';

        // fill the gameBoard with tokens
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols-1; j++)
            {
                gb.dropToken(randomChar, j);
                if (randomChar < 'Y')
                {
                    randomChar++;
                }
                else
                {
                    randomChar = 'A';
                }
            }
        }
        gb.dropToken('A', 4);
        gb.dropToken('B', 4);
        gb.dropToken('C', 4);
        gb.dropToken('D', 4);

        //assert that checkTie returns false since there is one more space for a token
        assertFalse(gb.checkTie());
    }

    //This test completely fills the board with characters and checkTie should return true
    @Test
    public void testGameBoard_checkTie_FullBoard_true()
    {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char randomChar = 'A';

        // fill the gameBoard with tokens
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                gb.dropToken(randomChar, j);
                if (randomChar < 'Y')
                {
                    randomChar++;
                }
                else
                {
                    randomChar = 'A';
                }
            }
        }

        //assert that checkTie returns true since the board is full
        assertTrue(gb.checkTie());
    }

    /************************************************************************************
     *
     * whatsAtPos tests
     *
     ************************************************************************************/

     // tests that whatsAtPos returns a blank space character for a random position when the board is empty
    @Test
    public void testGameBoard_whatsAtPos_emptyBoard()
    {
        // construct empty gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        int testTokenRow = 1;
        int testTokenCol = 1;
        BoardPosition testPos = new BoardPosition(testTokenRow, testTokenCol);
        char expectedEmptyPos = ' ';

        assertEquals(expectedEmptyPos, gb.whatsAtPos(testPos));
    }

    // tests that whatsAtPos returns the correct token for a token in the bottom left corner
    @Test
    public void testGameBoard_whatsAtPos_singleToken_tokenInBottomLeft()
    {
        // construct empty gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char expectedToken = 'G';
        gb.dropToken(expectedToken, 0);

        int testTokenRow = 0;
        int testTokenCol = 0;
        BoardPosition testPos = new BoardPosition(testTokenRow, testTokenCol);
        
        assertEquals(expectedToken, gb.whatsAtPos(testPos));
    }

    // tests that whatsAtPos returns the correct token for a token in the middle of a full board
    @Test
    public void testGameBoard_whatsAtPos_FullBoard_tokenInMiddle()
    {
        // construct empty gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char expectedToken = 'G';
        char otherToken = 'A';
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (i == 2 && j == 2)
                {
                    gb.dropToken(expectedToken, j);
                }
                else
                {
                    gb.dropToken(otherToken, j);
                    if (otherToken < 'Y')
                    {
                        otherToken++;
                    }
                    else
                    {
                        otherToken = 'A';
                    }
                }
            }
        }

        int testTokenRow = 2;
        int testTokenCol = 2;
        BoardPosition testPos = new BoardPosition(testTokenRow, testTokenCol);
        
        assertEquals(expectedToken, gb.whatsAtPos(testPos));
    }

    // tests that whatsAtPos returns the correct token for a token in the top right of a full board
    @Test
    public void testGameBoard_whatsAtPos_FullBoard_topRight()
    {
        // construct empty gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char expectedToken = 'G';
        char otherToken = 'A';
        
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (i == 4 && j == 4)
                {
                    gb.dropToken(expectedToken, j);
                }
                else
                {
                    gb.dropToken(otherToken, j);
                    if (otherToken < 'Y')
                    {
                        otherToken++;
                    }
                    else
                    {
                        otherToken = 'A';
                    }
                }
            }
        }
        
        int testTokenRow = 4;
        int testTokenCol = 4;
        BoardPosition testPos = new BoardPosition(testTokenRow, testTokenCol);
        
        assertEquals(expectedToken, gb.whatsAtPos(testPos));
    }

    // tests that whatsAtPos returns a blank space for the top right of a board containing some tokens
    @Test
    public void testGameBoard_whatsAtPos_tokensPlaced_topRight_emptyPos()
    {
        // construct empty gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;
        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char expectedToken = ' ';
        char otherToken = 'A';
        
        for (int i = 0; i < cols; i++)
        {
            gb.dropToken(otherToken, i);
            otherToken++;
        }
        
        int testTokenRow = 4;
        int testTokenCol = 4;
        BoardPosition testPos = new BoardPosition(testTokenRow, testTokenCol);
        
        assertEquals(expectedToken, gb.whatsAtPos(testPos));
    }

     /************************************************************************************
     *
     * isPlayerAtPos tests
     *
     ************************************************************************************/

    //Test to check that isPlayerAtPos returns true for a token in the bottom right corner
    @Test
    public void testGameBoard_isPlayerAtPos_BottomRight_True() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        BoardPosition pos = new BoardPosition(0, 4);

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        gb.dropToken('X', 4);

        assertTrue(gb.isPlayerAtPos(pos, 'X'));
    }

    //Test to check that isPlayerAtPos returns false for a position above a token placed at the bottom right corner
    @Test
    public void testGameBoard_isPlayerAtPos_AboveToken_BottomRight_False() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        BoardPosition pos = new BoardPosition(1, 4);

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        gb.dropToken('X', 4);

        assertFalse(gb.isPlayerAtPos(pos, 'X'));
    }

    //Test to check that isPlayerAtPos returns false for a position with another player's token occupying it
    @Test
    public void testGameBoard_isPlayerAtPos_BottomRight_OtherPlayer_False() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        BoardPosition pos = new BoardPosition(0, 4);

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        gb.dropToken('X', 4);

        assertFalse(gb.isPlayerAtPos(pos, 'O'));
    }

    //Test to check that isPlayerAtPos returns true for a token placed in the middle column
    @Test
    public void testGameBoard_isPlayerAtPos_MiddleColumn_True() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        BoardPosition pos = new BoardPosition(2, 2);

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        gb.dropToken('X', 2);
        gb.dropToken('X', 2);
        gb.dropToken('X', 2);

        assertTrue(gb.isPlayerAtPos(pos, 'X'));
    }

    //Test to check that isPlayerAtPos returns false for an empty game board
    @Test
    public void testGameBoard_isPlayerAtPos_EmptyBoard_False() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        BoardPosition pos = new BoardPosition(0, 0);

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);

        assertFalse(gb.isPlayerAtPos(pos, 'X'));
    }

    /************************************************************************************
     *
     * dropToken tests
     *
     ************************************************************************************/
    
    //Test to check that dropToken places the correct token at the bottom right corner of the gameboard
    @Test
    public void testGameBoard_dropToken_BottomRight() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char[][] expectedGB;
        expectedGB = new char[][] {
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'X'}
        };
        gb.dropToken('X', 4);

        assertEquals(expectedArrayToString(expectedGB), gb.toString());
    }
    //Test to check that dropToken places the correct token at the rightmost column of the gameboard if there is already a token in that column
    @Test
    public void testGameBoard_dropToken_RightmostColumn_PartiallyFullColumn() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char[][] expectedGB;
        expectedGB = new char[][] {
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', ' ', 'X'}
        };
        gb.dropToken('X', 4);
        gb.dropToken('X', 4);

        assertEquals(expectedArrayToString(expectedGB), gb.toString());
    }

    //Test to check that dropToken places the correct tokens at the rightmost column of the gameboard to fill a column
    @Test
    public void testGameBoard_dropToken_RightmostColumn_FullColumn() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char[][] expectedGB;
        expectedGB = new char[][] {
                {' ', ' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', ' ', 'X'}
        };
        gb.dropToken('X', 4);
        gb.dropToken('X', 4);
        gb.dropToken('X', 4);
        gb.dropToken('X', 4);
        gb.dropToken('X', 4);

        assertEquals(expectedArrayToString(expectedGB), gb.toString());
    }

    //Test to check that dropToken places the correct tokens in the middle column of the gameboard with alternating tokens
    @Test
    public void testGameBoard_dropToken_MiddleColumn_AlternatingTokensStacked() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char[][] expectedGB;
        expectedGB = new char[][] {
                {' ', ' ', 'X', ' ', ' '},
                {' ', ' ', 'O', ' ', ' '},
                {' ', ' ', 'X', ' ', ' '},
                {' ', ' ', 'O', ' ', ' '},
                {' ', ' ', 'X', ' ', ' '}
        };
        gb.dropToken('X', 2);
        gb.dropToken('O', 2);
        gb.dropToken('X', 2);
        gb.dropToken('O', 2);
        gb.dropToken('X', 2);

        assertEquals(expectedArrayToString(expectedGB), gb.toString());
    }

    //Test to check that dropToken places the correct tokens across the first row of the board
    @Test
    public void testGameBoard_dropToken_FillFirstRow() {
        // construct gameBoard
        int rows = 5;
        int cols = 5;
        int numToWin = 3;

        IGameBoard gb = makeGameBoard(rows, cols, numToWin);
        char[][] expectedGB;
        expectedGB = new char[][] {
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {'X', 'X', 'X', 'X', 'X'}
        };
        gb.dropToken('X', 0);
        gb.dropToken('X', 1);
        gb.dropToken('X', 2);
        gb.dropToken('X', 3);
        gb.dropToken('X', 4);

        assertEquals(expectedArrayToString(expectedGB), gb.toString());
    }
}