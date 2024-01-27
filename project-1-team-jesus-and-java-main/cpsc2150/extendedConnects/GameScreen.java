/*
 * abhoove - Andrew Hoover
 * gmgarla - Gavin Garland
 * omerwarth - Owen Merwarth
 * benUsimmons - Ben Simmons
 */

package cpsc2150.extendedConnects;

import cpsc2150.extendedConnectX.models.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class GameScreen
{   
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 10;

    public static void main(String[] args)
    {
        // creates scanner and local variables to hold user input and game information such as the number of players, game board size, and the current turn
        Scanner input = new Scanner(System.in);
        char playGame = 'Y';
        int numOfPlayers = 0;
        List<Character> playerTokens;
        char currentPlayer = ' ';
        int currentPlayerIndex = 0;
        int numColumns = 0;
        int numRows = 0;
        int tokensToWin = 0;
        char gameType = ' ';
        IGameBoard board;
        int userChoice = 0;
        

        // takes in the number of players that will play the game;
        // validates that the number of players is within the range allowed
        System.out.println("How many players?");
        numOfPlayers = Integer.parseInt(input.nextLine());
        while (numOfPlayers < MIN_PLAYERS || numOfPlayers > MAX_PLAYERS)
        {
            if (numOfPlayers < MIN_PLAYERS)
            {
                System.out.println("Must be at least " + MIN_PLAYERS + " players");
            }
            else if (numOfPlayers > MAX_PLAYERS)
            {
                System.out.println("Must be " + MAX_PLAYERS + " players or fewer");
            }
            System.out.println("How many players?");
            numOfPlayers = Integer.parseInt(input.nextLine());
        }

        // intializes ArrayList to hold players' tokens
        playerTokens = new ArrayList<Character>();

        // takes in characters for players' tokens
        for (int i = 1; i <= numOfPlayers; i++)
        {
            System.out.println("Enter the character to represent player " + i);
            char newToken = input.nextLine().charAt(0);
            // ensures player's token is uppercase
            newToken = Character.toUpperCase(newToken);

            // ensures that selected token is not already being used by another player
            while (playerTokens.contains(newToken))
            {
                System.out.println(newToken + " is already taken as a player token!");
                System.out.println("Enter the character to represent player " + i);
                newToken = input.nextLine().charAt(0);
                // ensures player's token is uppercase
                newToken = Character.toUpperCase(newToken);
            }

            // adds player's token to list
            playerTokens.add(newToken);
        }
        
        // sets currentPlayer to player 1's token
        currentPlayer = playerTokens.get(currentPlayerIndex);

        //takes the number of rows for boardsize
        System.out.println("How many rows should be on the board?");
        numRows = Integer.parseInt(input.nextLine());

        //validates numRows input
        while(numRows > IGameBoard.MAX_ROWS || numRows < IGameBoard.MIN_ROWS)
        {
            if(numRows > IGameBoard.MAX_ROWS){
                System.out.println("Rows cannot exceed " + IGameBoard.MAX_ROWS);
                numRows = Integer.parseInt(input.nextLine());
            }
            else if(numRows < IGameBoard.MIN_ROWS){
                System.out.println("Rows cannot be less than " + IGameBoard.MIN_ROWS);
                numRows = Integer.parseInt(input.nextLine());
            }
        }

        //takes the number of columns for boardsize
        System.out.println("How many columns should be on the board?");
        numColumns = Integer.parseInt(input.nextLine());

        //validates numColumns input
        while(numColumns > IGameBoard.MAX_COLUMNS || numColumns < IGameBoard.MIN_COLUMNS)
        {
            if(numColumns > IGameBoard.MAX_COLUMNS){
                System.out.println("Columns cannot exceed " + IGameBoard.MAX_COLUMNS);
                numColumns = Integer.parseInt(input.nextLine());
            }
            else if(numColumns < IGameBoard.MIN_COLUMNS){
                System.out.println("Columns cannot be less than " + IGameBoard.MIN_COLUMNS);
                numColumns = Integer.parseInt(input.nextLine());
            }
        }

        //takes input for the number of win tokens (tokensToWIn)
        System.out.println("How many in a row to win?");
        tokensToWin = Integer.parseInt(input.nextLine());

        //validates tokensToWin input
        while(tokensToWin > IGameBoard.MAX_NUM_TO_WIN || tokensToWin < IGameBoard.MIN_NUM_TO_WIN || tokensToWin > numRows || tokensToWin > numColumns)
        {
            if(tokensToWin > IGameBoard.MAX_NUM_TO_WIN){
                System.out.println("The number in a row to win cannot exceed " + IGameBoard.MAX_NUM_TO_WIN);
                tokensToWin = Integer.parseInt(input.nextLine());
            }
            else if(tokensToWin < IGameBoard.MIN_NUM_TO_WIN){
                System.out.println("The number in a row to win cannot be less than " + IGameBoard.MIN_NUM_TO_WIN);
                tokensToWin = Integer.parseInt(input.nextLine());
            }
            else {
                System.out.println("The number in a row to win cannot be less than the number of rows or columns");
                tokensToWin = Integer.parseInt(input.nextLine());
            }
        }

        //takes input for the type of gameboard to be played on
        System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)");
        gameType = input.nextLine().charAt(0);

        //validates gameType input
        while(gameType != 'F' && gameType != 'f' && gameType != 'M' && gameType != 'm')
        {
            System.out.println("The game must be of either type Fast (F or f) or of type Memory Efficient (M or m)");
            gameType = input.nextLine().charAt(0);
        }

        if (gameType == 'F' || gameType == 'f')
        {
            // construct game board(GameBoard) and print empty board
            board = new GameBoard(numRows, numColumns, tokensToWin);
            System.out.println(board.toString());

        }

        else
        {
            // construct game board(GameBoardMem) and print empty board
            board = new GameBoardMem(numRows, numColumns, tokensToWin);
            System.out.println(board.toString());
        }

        // do-while runs game for however many games user wants to play
        do
        {
            // takes in user input
            System.out.println("Player " + currentPlayer + ", what column do you want to place your marker in?");
            userChoice = Integer.parseInt(input.nextLine());
            
            // validates if user entered an exisiting and available column
            while (userChoice < 0 || userChoice >= board.getNumColumns() || !board.checkIfFree(userChoice))
            {
                if (userChoice < 0)
                {
                    System.out.println("Column cannot be less than 0");
                }
                else if (userChoice >= board.getNumColumns())
                {
                    System.out.println("Column cannot be greater than " + (board.getNumColumns() - 1));
                }
                else if (!board.checkIfFree(userChoice))
                {
                    System.out.println("Column is full");
                }

                // takes in another column selection from user until valid column is entered
                System.out.println("Player " + currentPlayer + ", what column do you want to place your marker in?");
                userChoice = Integer.parseInt(input.nextLine());
            }
        
            // drops token and prints resulting board
            board.dropToken(currentPlayer, userChoice);
            System.out.println(board.toString());

            // checks for win or tie; if one has occurred, user is asked if they
            // want to play again
            if (board.checkForWin(userChoice))
            {
                // prints winning message if player wins
                System.out.println("Player " + currentPlayer + " Won!");
                System.out.println("Would you like to play again? Y/N");
                playGame = input.nextLine().charAt(0);
                while (playGame != 'Y' && playGame != 'y' && playGame != 'N' && playGame != 'n')
                {
                    System.out.println("Would you like to play again? Y/N");
                    playGame = input.nextLine().charAt(0);
                }

                // if the player wants to play again, reset game board and print empty board
                if (playGame == 'Y' || playGame == 'y')
                {
                    // takes in the number of players that will play the game;
                    // validates that the number of players is within the range allowed
                    System.out.println("How many players?");
                    numOfPlayers = Integer.parseInt(input.nextLine());
                    while (numOfPlayers < MIN_PLAYERS || numOfPlayers > MAX_PLAYERS)
                    {
                        if (numOfPlayers < MIN_PLAYERS)
                        {
                            System.out.println("Must be at least " + MIN_PLAYERS + " players");
                        }
                        else if (numOfPlayers > MAX_PLAYERS)
                        {
                            System.out.println("Must be " + MAX_PLAYERS + " players or fewer");
                        }
                        System.out.println("How many players?");
                        numOfPlayers = Integer.parseInt(input.nextLine());
                    }

                    // intializes ArrayList to hold players' tokens
                    playerTokens = new ArrayList<Character>();

                    // takes in characters for players' tokens
                    for (int i = 1; i <= numOfPlayers; i++)
                    {
                        System.out.println("Enter the character to represent player " + i);
                        char newToken = input.nextLine().charAt(0);
                        // ensures player's token is uppercase
                        newToken = Character.toUpperCase(newToken);

                        // ensures that selected token is not already being used by another player
                        while (playerTokens.contains(newToken))
                        {
                            System.out.println(newToken + " is already taken as a player token!");
                            System.out.println("Enter the character to represent player " + i);
                            newToken = input.nextLine().charAt(0);
                            // ensures player's token is uppercase
                            newToken = Character.toUpperCase(newToken);
                        }

                        // adds player's token to list
                        playerTokens.add(newToken);
                    }
        
                    // sets currentPlayer to player 1's token
                    currentPlayer = playerTokens.get(currentPlayerIndex);

                    //takes the number of rows for boardsize
                    System.out.println("How many rows should be on the board?");
                    numRows = Integer.parseInt(input.nextLine());

                    //validates numRows input
                    while(numRows > IGameBoard.MAX_ROWS || numRows < IGameBoard.MIN_ROWS)
                    {
                        if(numRows > IGameBoard.MAX_ROWS){
                            System.out.println("Rows cannot exceed " + IGameBoard.MAX_ROWS);
                            numRows = Integer.parseInt(input.nextLine());
                        }
                        else if(numRows < IGameBoard.MIN_ROWS){
                            System.out.println("Rows cannot be less than " + IGameBoard.MIN_ROWS);
                            numRows = Integer.parseInt(input.nextLine());
                        }
                    }

                    //takes the number of columns for boardsize
                    System.out.println("How many columns should be on the board?");
                    numColumns = Integer.parseInt(input.nextLine());

                    //validates numColumns input
                    while(numColumns > IGameBoard.MAX_COLUMNS || numColumns < IGameBoard.MIN_COLUMNS)
                    {
                        if(numColumns > IGameBoard.MAX_COLUMNS){
                            System.out.println("Columns cannot exceed " + IGameBoard.MAX_COLUMNS);
                            numColumns = Integer.parseInt(input.nextLine());
                        }
                        else if(numColumns < IGameBoard.MIN_COLUMNS){
                            System.out.println("Columns cannot be less than " + IGameBoard.MIN_COLUMNS);
                            numColumns = Integer.parseInt(input.nextLine());
                        }
                    }

                    //takes input for the number of win tokens (tokensToWIn)
                    System.out.println("How many in a row to win?");
                    tokensToWin = Integer.parseInt(input.nextLine());

                    //validates tokensToWin input
                    while(tokensToWin > IGameBoard.MAX_NUM_TO_WIN || tokensToWin < IGameBoard.MIN_NUM_TO_WIN || tokensToWin > numRows || tokensToWin > numColumns)
                    {
                        if(tokensToWin > IGameBoard.MAX_NUM_TO_WIN){
                            System.out.println("The number in a row to win cannot exceed " + IGameBoard.MAX_NUM_TO_WIN);
                            tokensToWin = Integer.parseInt(input.nextLine());
                        }
                        else if(tokensToWin < IGameBoard.MIN_NUM_TO_WIN){
                            System.out.println("The number in a row to win cannot be less than " + IGameBoard.MIN_NUM_TO_WIN);
                            tokensToWin = Integer.parseInt(input.nextLine());
                        }
                        else {
                            System.out.println("The number in a row to win cannot be less than the number of rows or columns");
                            tokensToWin = Integer.parseInt(input.nextLine());
                        }
                    }

                    //takes input for the type of gameboard to be played on
                    System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)");
                    gameType = input.nextLine().charAt(0);

                    //validates gameType input
                    while(gameType != 'F' && gameType != 'f' && gameType != 'M' && gameType != 'm')
                    {
                        System.out.println("The game must be of either type Fast (F or f) or of type Memory Efficient (M or m)");
                        gameType = input.nextLine().charAt(0);
                    }

                    if (gameType == 'F' || gameType == 'f')
                    {
                        // construct game board(GameBoard) and print empty board
                        board = new GameBoard(numRows, numColumns, tokensToWin);
                        System.out.println(board.toString());

                    }

                    else
                    {
                        // construct game board(GameBoardMem) and print empty board
                        board = new GameBoardMem(numRows, numColumns, tokensToWin);
                        System.out.println(board.toString());
                    }
                }
            }
            else if (board.checkTie())
            {
                // prints tie message if tie occurs
                System.out.println("Tie!");
                System.out.println("Would you like to play again? Y/N");
                playGame = input.nextLine().charAt(0);
                while (playGame != 'Y' && playGame != 'y' && playGame != 'N' && playGame != 'n')
                {
                    System.out.println("Would you like to play again? Y/N");
                    playGame = input.nextLine().charAt(0);
                }

                // if the player wants to play again, reset game board and print empty board
                if (playGame == 'Y' || playGame == 'y')
                {
                    // takes in the number of players that will play the game;
                    // validates that the number of players is within the range allowed
                    System.out.println("How many players?");
                    numOfPlayers = Integer.parseInt(input.nextLine());
                    while (numOfPlayers < MIN_PLAYERS || numOfPlayers > MAX_PLAYERS)
                    {
                        if (numOfPlayers < MIN_PLAYERS)
                        {
                            System.out.println("Must be at least " + MIN_PLAYERS + " players");
                        }
                        else if (numOfPlayers > MAX_PLAYERS)
                        {
                            System.out.println("Must be " + MAX_PLAYERS + " players or fewer");
                        }
                        System.out.println("How many players?");
                        numOfPlayers = Integer.parseInt(input.nextLine());
                    }

                    // intializes ArrayList to hold players' tokens
                    playerTokens = new ArrayList<Character>();

                    // takes in characters for players' tokens
                    for (int i = 1; i <= numOfPlayers; i++)
                    {
                        System.out.println("Enter the character to represent player " + i);
                        char newToken = input.nextLine().charAt(0);
                        // ensures player's token is uppercase
                        newToken = Character.toUpperCase(newToken);

                        // ensures that selected token is not already being used by another player
                        while (playerTokens.contains(newToken))
                        {
                            System.out.println(newToken + " is already taken as a player token!");
                            System.out.println("Enter the character to represent player " + i);
                            newToken = input.nextLine().charAt(0);
                            // ensures player's token is uppercase
                            newToken = Character.toUpperCase(newToken);
                        }

                        // adds player's token to list
                        playerTokens.add(newToken);
                    }
        
                    // sets currentPlayer to player 1's token
                    currentPlayer = playerTokens.get(currentPlayerIndex);

                    //takes the number of rows for boardsize
                    System.out.println("How many rows should be on the board?");
                    numRows = Integer.parseInt(input.nextLine());

                    //validates numRows input
                    while(numRows > IGameBoard.MAX_ROWS || numRows < IGameBoard.MIN_ROWS)
                    {
                        if(numRows > IGameBoard.MAX_ROWS){
                            System.out.println("Rows cannot exceed " + IGameBoard.MAX_ROWS);
                            numRows = Integer.parseInt(input.nextLine());
                        }
                        else if(numRows < IGameBoard.MIN_ROWS){
                            System.out.println("Rows cannot be less than " + IGameBoard.MIN_ROWS);
                            numRows = Integer.parseInt(input.nextLine());
                        }
                    }

                    //takes the number of columns for boardsize
                    System.out.println("How many columns should be on the board?");
                    numColumns = Integer.parseInt(input.nextLine());

                    //validates numColumns input
                    while(numColumns > IGameBoard.MAX_COLUMNS || numColumns < IGameBoard.MIN_COLUMNS)
                    {
                        if(numColumns > IGameBoard.MAX_COLUMNS){
                            System.out.println("Columns cannot exceed " + IGameBoard.MAX_COLUMNS);
                            numColumns = Integer.parseInt(input.nextLine());
                        }
                        else if(numColumns < IGameBoard.MIN_COLUMNS){
                            System.out.println("Columns cannot be less than " + IGameBoard.MIN_COLUMNS);
                            numColumns = Integer.parseInt(input.nextLine());
                        }
                    }

                    //takes input for the number of win tokens (tokensToWIn)
                    System.out.println("How many in a row to win?");
                    tokensToWin = Integer.parseInt(input.nextLine());

                    //validates tokensToWin input
                    while(tokensToWin > IGameBoard.MAX_NUM_TO_WIN || tokensToWin < IGameBoard.MIN_NUM_TO_WIN || tokensToWin > numRows || tokensToWin > numColumns)
                    {
                        if(tokensToWin > IGameBoard.MAX_NUM_TO_WIN){
                            System.out.println("The number in a row to win cannot exceed " + IGameBoard.MAX_NUM_TO_WIN);
                            tokensToWin = Integer.parseInt(input.nextLine());
                        }
                        else if(tokensToWin < IGameBoard.MIN_NUM_TO_WIN){
                            System.out.println("The number in a row to win cannot be less than " + IGameBoard.MIN_NUM_TO_WIN);
                            tokensToWin = Integer.parseInt(input.nextLine());
                        }
                        else {
                            System.out.println("The number in a row to win cannot be less than the number of rows or columns");
                            tokensToWin = Integer.parseInt(input.nextLine());
                        }
                    }

                    //takes input for the type of gameboard to be played on
                    System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)");
                    gameType = input.nextLine().charAt(0);

                    //validates gameType input
                    while(gameType != 'F' && gameType != 'f' && gameType != 'M' && gameType != 'm')
                    {
                        System.out.println("The game must be of either type Fast (F or f) or of type Memory Efficient (M or m)");
                        gameType = input.nextLine().charAt(0);
                    }

                    if (gameType == 'F' || gameType == 'f')
                    {
                        // construct game board(GameBoard) and print empty board
                        board = new GameBoard(numRows, numColumns, tokensToWin);
                        System.out.println(board.toString());

                    }

                    else
                    {
                        // construct game board(GameBoardMem) and print empty board
                        board = new GameBoardMem(numRows, numColumns, tokensToWin);
                        System.out.println(board.toString());
                    }
                }
            }

            // if there is no win or tie, the next player is given a turn
            else
            {
                // increments currentPlayerIndex and ensures that it is within the number of players playing
                currentPlayerIndex++;
                if (currentPlayerIndex >= numOfPlayers)
                {
                    // sets currentPlayerIndex to first player's index if incrementation results in
                    // currentPlayerIndex moving past the index for the last player
                    currentPlayerIndex = 0;
                }

                // sets currentPlayer to token of next player to play
                currentPlayer = playerTokens.get(currentPlayerIndex);

            }
        } while (playGame == 'Y' || playGame == 'y');
        

        // closes scanner
        input.close();
    }
}