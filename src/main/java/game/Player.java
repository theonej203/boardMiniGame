package game;

import javax.swing.JOptionPane;

import boardgame.Saveable;


/**
 * This is the Playetr class that will house all the information about the player. 
 * It will implement the savable interface
 */
public class Player implements Saveable{
    
    private String playerName;
    private int gameWon;
    private int gamePlayed;
    private int gameLost;
    


    /**
     * The initializer that takes in a loadable string
     * @param loadString the string to load the player
     */
    public Player(String loadString){
        loadSavedString(loadString);
    }


    /**
     * This will initialize a default player
     */
    public Player(){
        this.playerName = "guest";
        this.gameWon = 0;
        this.gamePlayed = 0;
        this.gameLost = 0;
    }

    /**
     * increment player game won
     */
    public void incrementGameWon(){
        this.gameWon++;
    }
    
    /**
     * incement player game loss
     */
    public void incrementGameLost(){
        this.gameLost++;
    }
    
    /**
     * increment player game played
     */
    public void incrementGamePlayed(){
        this.gamePlayed++;
    }

    /**
     * Get the player's name
     * @return the player's name
     */
    public String getPlayerName(){
        return this.playerName;
    }

    /**
     * Get the number of games won by the player
     * @return number of gameswon
     */
    public int getGameWon(){
        return this.gameWon;
    }

    /**
     * Get the nnumber of games played by the player
     * @return number of games played
     */
    public int getGamePlayed(){
        return this.gamePlayed;
    }

    /**
     * Get the number of games lost
     * @return number of games lost
     */
    public int getGameLost(){
        return this.gameLost;
    }

    
    

    @Override
    public String getStringToSave() {
        return this.playerName + "\n"
            + Integer.toString(this.gameWon) + "\n"
            + Integer.toString(this.gamePlayed) + "\n"
            + Integer.toString(this.gameLost) + "\n";
    }

    @Override
    public void loadSavedString(String toLoad) {

        try{
            String[] parsedString = toLoad.split("\n",5);
            

            this.playerName = parsedString[0];
            this.gameWon = Integer.parseInt(parsedString[1]);
            this.gamePlayed = Integer.parseInt(parsedString[2]);
            this.gameLost = Integer.parseInt(parsedString[3]);
        } catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null, "Invalid file format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Load player sucessfully", "Error", JOptionPane.INFORMATION_MESSAGE);
        
    }
}
