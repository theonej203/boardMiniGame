package tictactoe;

import boardgame.BoardGame;
import boardgame.Saveable;
import game.Player;
import game.TextUI;

/**
 * The class That will control the TicTacToe game
 * The class implemented the Savable interface
 * The class extedned the BoardGame class
 */
public class TictactoeGame extends BoardGame implements Saveable{

    private int turnCounter;
    private int winner;

    private Player player1;
    private Player player2;

    private TextUI textUI;


    /**
     * The initializer for the class, which will create the Specific JPanel
     * @param wide the width of grid
     * @param tall the height of grid
     * @param currentPlayer1 player 1
     * @param currentPlayer2 player 2
     */
    public TictactoeGame(int wide, int tall, Player currentPlayer1, Player currentPlayer2) {
        super(wide, tall);
        setGrid(new TictactoeGrid(wide, tall));

        this.turnCounter = 0;
        this.setCurrentPlayer(1);
        this.winner = 0;

        this.player1 = currentPlayer1;
        this.player2 = currentPlayer2;


        setTextUI(new TextUI(this, player1, player2, (TictactoeGrid)this.getGrid()));
    }

    /**
     * Set the text ui
     * @param ui 
     */
    public void setTextUI(TextUI ui){
        this.textUI = ui;
    }

    /**
     * switch the application to CLI
     */
    public void startCLI(){
        this.textUI.gameStart();
    }


    @Override
    public void newGame(){
        this.getGrid().emptyGrid();
        this.setCurrentPlayer(1);
        this.turnCounter = 0;
        this.winner = 0;
        
    }

    @Override
    public String getStringToSave() {
        String saveString = "";

        if(this.getCurrentPlayer() == 1){
            saveString = saveString + "X\n";
        }else{
            saveString = saveString + "O\n";
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
        
        if(fileLines[0] == "O"){//see if it is player 2
            this.setCurrentPlayer(2);
        }else{
            this.setCurrentPlayer(1);
        }
        for(int i = 1; i<4; i++){
            for(int j = 0; j<fileLines[i].length(); j++){
                if(fileLines[i].charAt(j) == ','){
                    numCommas++;
                    this.getGrid().setValue(numCommas+1, i, " ");
                }else{
                    this.getGrid().setValue(numCommas+1, i, Character.toString(fileLines[i].charAt(j)));
                    this.turnCounter++;
                }
            }
            numCommas = 0;
            
        }
        
    }

    @Override
    public boolean takeTurn(int across, int down, String input){
        
        if(this.getGrid().getValue(across, down) == " "){
            this.getGrid().setValue(across, down, input);

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
            this.getGrid().setValue(across, down, input == 1 ? "X":"O");
            this.turnCounter++;
            if(checkMove(across, down, input== 1 ? "X":"O")){
                this.turnCounter = 9;
                this.winner = this.getCurrentPlayer();
            }
            this.incrementCurrentPlayer();
            return true;
        }
        return false;
    }

    private boolean checkMove(int across, int down, String input){//return true if winning condition is met

        if(this.getCell(across, 1) == this.getCell(across, 2)//check for horizontal
        && this.getCell(across, 2) == this.getCell(across, 3)){
            return true;
        }else if(this.getCell(1, down) == this.getCell(2, down)//check for vertical
        && this.getCell(2, down) == this.getCell(3, down)){
            return true;
        }else if(across == down){// check for '\'
            if(this.getCell(1, 1) == this.getCell(2, 2)
            && this.getCell(2, 2) == this.getCell(3, 3)){
                return true;
            }
        }else if(across + down == 4){//check for '/'
            if(this.getCell(1, 3) == this.getCell(2, 2)
            && this.getCell(2, 2) == this.getCell(3, 1)){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isDone() {//when someone won, game willset turncounter to 9
        return this.turnCounter == 9;
    }

    @Override
    public int getWinner() {
        if(isDone()){//player turn will increment when player make a move.
            return this.winner;
        }
        return 0;
    }

    @Override
    public String getGameStateMessage() {

        if(isDone()){
            if(this.winner != 0){
                return "Player "+ Integer.toString(this.winner) + " won!";
            }else{
                return "It's a draw.";
            }
        }else{
            return "Player "+ Integer.toString(this.getCurrentPlayer()) 
            + "'s turn ("+ (this.getCurrentPlayer() == 1? "X":"O") + ")";
        }
        
    }
    
}
