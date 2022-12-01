package game;

import java.util.Scanner;

import tictactoe.TictactoeGame;
import tictactoe.TictactoeGrid;

/**
 * The text ui that currently is only responsible for playing the TicTacToe game on the CLI
 */
public class TextUI {


    private TictactoeGame game;
    private TictactoeGrid grid;

    private Player player1;
    private Player player2;



    private Boolean keepPlaying = true;
    private Boolean validInput = false;

    private Scanner input = new Scanner(System.in);
    private String choice = "";

    private int row = 0;
    private int col = 0;
    
    
    /**
     * Initialize the Text ui
     * @param currentGame the current game that is being played
     * @param currentPlayer1 the player1 that is playing the game
     * @param currentPlayer2 the player2 that is playing the game
     * @param currentGrid the grid that the game is beinng played on
     */
    public TextUI(TictactoeGame currentGame, Player currentPlayer1, Player currentPlayer2, TictactoeGrid currentGrid){
        this.game = currentGame;
        this.player1 = currentPlayer1;
        this.player2 = currentPlayer2;
        this.grid = currentGrid;
    }


    /**
     * For taking in column in the fange 1 to 3
     */
    private void enterCol(){
        System.out.println("Enter column number (1 - 3): ");
        while(!validInput){
            try {
                col = Integer.parseInt(input.nextLine());  
                if(col > 3 || col < 1){
                    System.out.println("Invalid choice");    
                }else{
                    validInput = true;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a column number from 1 to 3");
            }
        }
        validInput = false;
    }

    /**
     * for taking in rows in the range of 1 to 3
     */
    private void enterRow(){
        System.out.println("Enter row number (1 - 3): ");
        while(!validInput){
            try {
                row = Integer.parseInt(input.nextLine());  
                if(row > 3 || row < 1){
                    System.out.println("Invalid choice");    
                }else{
                    validInput = true;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a row number from 1 to 3");
            }
        }
        validInput = false;
    }

    /*
     * The method that will be played when the user want to play it in CLI
     * 
     */
    public void gameStart(){
        while(keepPlaying){
            while(!game.isDone()){
                System.out.println(game.getGameStateMessage());
                System.out.println(grid.getStringGrid());
                enterRow();
                enterCol();
                if(!this.game.takeTurn(col, row, this.game.getCurrentPlayer())){
                    System.out.println("Cell already populated");
                }
            }
            System.out.println(game.getGameStateMessage());
            System.out.println("\nDo you want to keep playing? Y or y for yes, everything else for no: ");
            choice = input.nextLine();
            if(!choice.equals("Y") && !choice.equals("y")){
                keepPlaying = false;
            }else{
                game.newGame();
            }
        }
        input.close();
    }


}
