package numerictictactoe;

import java.util.ArrayList;

import boardgame.BoardGame;
import boardgame.Saveable;
import game.Player;

/**
 * The game controller for the Numerical TicTacToe game
 * The class implement the Savable interface
 * The class Extens the BoardGame class
 */
public class NumericalTTTGame extends BoardGame implements Saveable{

    private int turnCounter;
    private int winner;

    private ArrayList<Integer> oddNums;
    private ArrayList<Integer> evenNums;

    private Player player1;
    private Player player2;


    /**
     * The initializer for the Numerical TicTacToe game controller
     * @param wide the width of the grid
     * @param tall the height of the grid
     * @param currentPlayer1 the player1 that is playing the game
     * @param currentPlayer2 the player2 that is playing the game
     */
    public NumericalTTTGame(int wide, int tall, Player currentPlayer1, Player currentPlayer2) {
        super(wide, tall);
        setGrid(new NumericalTTTGrid(wide, tall));
        this.turnCounter = 0;
        this.setCurrentPlayer(1);
        this.winner = 0;

        this.player1 = currentPlayer1;
        this.player2 = currentPlayer2;

        this.oddNums = new ArrayList<Integer>();
        this.evenNums = new ArrayList<Integer>();
        resetNums();

        
    }


    /**
     * Clear the number options in the game
     */
    private void resetNums(){//add number from 1 to 9
        this.oddNums.clear();
        this.evenNums.clear();
        for(int i = 1; i<10; i++){
            if(i%2 == 1){
                this.oddNums.add(i);
            }else{
                this.evenNums.add(i);
            }
        }
    }

    /**
     * For getting the available odd numbers
     * @return the arraylist of the available odd numbers
     */
    protected ArrayList<Integer> getOddNums(){
        return this.oddNums;
    }

    /**
     * For getting the avalable even numbers
     * @return the arraylist of the available even numbers
     */
    protected ArrayList<Integer> getEvenNums(){
        return this.evenNums;
    }

    @Override
    public void newGame(){
        this.getGrid().emptyGrid();
        this.setCurrentPlayer(1);
        this.turnCounter = 0;
        this.winner = 0;
        resetNums();
        
    }


    @Override
    public String getStringToSave(){
        String saveString = "";

        if(this.getCurrentPlayer() == 1){
            saveString = saveString + "O\n";//odd
        }else{
            saveString = saveString + "E\n";//even
        }
        for(int i = 1; i< 4; i++){
            for(int j = 1; j<4; j++){
                if(this.getCell(j, i) != " "){//checking for empty cells
                    saveString = saveString + this.getCell(j, i);
                }
                if(j<3){
                    saveString = saveString + ",";
                }
            }
            saveString = saveString + "\n";
        }
        return saveString;
    }
    

    @Override
    public void loadSavedString(String toLoad) {
        String[] fileLines = toLoad.split("\n");
        int numCommas = 0;
        if(fileLines[0] == "O"){//if it is odd
            this.setCurrentPlayer(1);
        }else{//if it is even
            this.setCurrentPlayer(2);
        }
        for(int i = 1; i<4; i++){
            for(int j = 0; j<fileLines[i].length(); j++){
                if(fileLines[i].charAt(j) == ','){
                    numCommas++;
                    this.getGrid().setValue(numCommas+1, i, " ");
                }else{//numcommas will be either 0, 1, or 2
                    int num = Character.getNumericValue(fileLines[i].charAt(j));
                    this.getGrid().setValue(numCommas+1, i, String.valueOf(num));

                    if(num%2 == 0){
                        this.evenNums.remove(Integer.valueOf(num));
                    }else{
                        this.oddNums.remove(Integer.valueOf(num));
                    }
                    turnCounter++;
                    
                }
            }
            numCommas = 0;
        }
    }

    @Override
    public boolean takeTurn(int across, int down, String input){
        if(this.getGrid().getValue(across, down) == " "){
            this.getGrid().setValue(across, down, input);
            if(Integer.parseInt(input) %2 == 0){
                this.evenNums.remove(Integer.valueOf(input));
            }else{
                this.oddNums.remove(Integer.valueOf(input));
            }
            this.turnCounter++;
            if(checkMove(across, down, input)){
                this.turnCounter = 9;
                this.winner = this.getCurrentPlayer();
                if(this.winner == 1){
                    this.player1.incrementGameWon();
                    this.player2.incrementGameLost();
                }else{
                    this.player1.incrementGameLost();
                    this.player2.incrementGameWon();
                }
            }
            this.incrementCurrentPlayer();
            if(turnCounter == 9){
                this.player1.incrementGamePlayed();
                this.player2.incrementGamePlayed();
            }
            
            return true;
        }
        return false;
    }

    @Override
    public boolean takeTurn(int across, int down, int input){
        if(this.getGrid().getValue(across, down) == " "){
            this.getGrid().setValue(across, down, input);
            this.turnCounter++;
            if(checkMove(across, down, String.valueOf(input))){
                this.turnCounter = 9;
                this.winner = this.getCurrentPlayer();

            }
            this.incrementCurrentPlayer();
            return true;
        }
        return false;
    }

    
    
    /**
     * Check for across winning condition
     * @param across the row to check
     * @return if there is winning condition
     */
    private boolean checkAcross(int across){// check for "--"
        try {
            return (Integer.parseInt(this.getCell(across, 1)) + Integer.parseInt(this.getCell(across, 2)) 
            + Integer.parseInt(this.getCell(across, 3)) == 15);
                
        } catch (NumberFormatException e){ 
            return false;
        }
    }

    /**
     * Check for down winning condition
     * @param down the column to check
     * @return if there is winning condition
     */
    private boolean checkDown(int down){//check for "|"
        try {
            return (Integer.parseInt(this.getCell(1, down)) + Integer.parseInt(this.getCell(2, down)) 
            + Integer.parseInt(this.getCell(3, down)) == 15);
        } catch (Exception e){ 
            return false;
        }
    }

    /**
     * check for left diagonal winning condition
     * @param across check if across is valid
     * @param down check if down is valid
     * @return if there is winning condition
     */
    private boolean checkLeftDiagonal(int across, int down){// check for '\'
        try {
            if(across == down){
                return (Integer.parseInt(this.getCell(1, 1))+ Integer.parseInt(this.getCell(2, 2)) 
                + Integer.parseInt(this.getCell(3, 3)) == 15);
            }else{
                return false;
            }
        } catch (Exception e) { 
            return false;
        }
    }

    /**
     * check for right diagonal winning contion
     * @param across check if across is valid
     * @param down check if down is valid
     * @return
     */
    private boolean checkRightDiagonal(int across, int down){// check for '?'
        try {
            if(across + down == 4){//check for '/'
                return(Integer.parseInt(this.getCell(1, 3)) + Integer.parseInt(this.getCell(2, 2)) 
                + Integer.parseInt(this.getCell(3, 1)) == 15);

            }else{
                return false;
            }
        } catch (Exception e) { 
            return false;
        }
    }

    /**
     * This method will called the 4 previous mentioned check methods
     * @param across the row number
     * @param down the column number
     * @param input the input used to check
     * @return true if there is a winning condition
     */
    private boolean checkMove(int across, int down, String input){

        Boolean ifWon = false;

        ifWon = ifWon || checkAcross(across);
        ifWon = ifWon || checkDown(down);
        ifWon = ifWon || checkLeftDiagonal(across, down);
        ifWon = ifWon || checkRightDiagonal(across,down);

        return ifWon;
    }


    @Override
    public boolean isDone(){//when someone won, game willset turncounter to 9
        return this.turnCounter == 9;
    }

    @Override
    public int getWinner(){
        if(isDone()){//player turn will increment when player make a move.
            return this.winner;
        }
        return 0;
    }

    @Override
    public String getGameStateMessage(){

        if(isDone()){
            if(this.winner != 0){
                return "Player "+ Integer.toString(this.winner) + " won!";
            }else{
                return "It's a draw.";
            }
        }else{
            return "Player "+ Integer.toString(this.getCurrentPlayer()) 
            + "'s turn ("+ (this.getCurrentPlayer() == 1? "Odd":"Even") + ")";
        }
        
    }
    
}
