package numerictictactoe;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.Position;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.FlowLayout;

import boardgame.ui.PositionAwareButton;
import game.GameUI;
import game.Player;
import game.Saver;


/**
 * The class that will show the windows for Numerical TicTacToe
 */
public class NumericalTTTView extends JPanel {
    private static final int GRIDWIDTH = 3;// width and height to 3 since
    private static final int GRIDHEIGHT = 3;//that is how it is suppose to be

    private String defaultFilePath = "assets";

    private JLabel messageLabel;
    private NumericalTTTGame game;
    private PositionAwareButton[][] buttons;
    private JPanel buttonPanel;
    private GameUI root;


    private int width;
    private int height;

    /**
     * The initializer for the panel
     * @param currentPlayer1 player1
     * @param currentPlayer2 player2
     * @param currentGameFrame the parent frame
     * @param currentWidth the width of the grid
     * @param currentHeight the height of the grid
     */
    public NumericalTTTView(Player currentPlayer1, Player currentPlayer2,
                            GameUI currentGameFrame, int currentWidth, int currentHeight){
        super();

        this.width = currentWidth;
        this.height = currentHeight;
        this.setLayout(null);
        root = currentGameFrame;

        setGameController(new NumericalTTTGame(width, height, currentPlayer1, currentPlayer2));

        createBackButton();
        createTurnLabel();
        createResetButton();
        createLoadButton();
        createSaveButton();
        this.game.newGame();
        
        this.buttonPanel = makeButtonGrid(GRIDHEIGHT, GRIDWIDTH);
        this.add(this.buttonPanel);
        this.buttonPanel.setBounds(width/4, 100, width/3, height/3);
        
    }
    
    /**
     * Create save button for the panel 
     */
    private void createSaveButton(){
        JButton saveButton = new JButton("Save game");
        saveButton.setBounds(width-200, height - 320, 100, 50);
        this.add(saveButton);
        saveButton.addActionListener(e -> Saver.save(this.game,this));
    }

    
    /**
     * Create load button for the panel
     */
    private void createLoadButton(){
        JButton loadButton = new JButton("Load game");
        loadButton.setBounds(width-200, height - 260, 100, 50);
        this.add(loadButton);
        loadButton.addActionListener(e -> loadGame());
    }

    /**
     * The method that will load the game from a file
     */
    private void loadGame(){
        JFileChooser fileChooser = new JFileChooser(defaultFilePath);
        int response = fileChooser.showOpenDialog(this);
        if(response == JFileChooser.APPROVE_OPTION){
            File gameFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try{
                Scanner fileScanner = new Scanner(gameFile);
                String loadString = "";
                while(fileScanner.hasNextLine()){
                    loadString = loadString + fileScanner.nextLine() + "\n";
                }
                this.game.loadSavedString(loadString);
                updateLoadGame();
                fileScanner.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * update the game when the file is loaded
     */
    private void updateLoadGame(){
        updateMessageLabel();
        updateAllButtons();
    }

    /**
     * update the button grid of the game
     */
    private void updateAllButtons(){
        for(int i = 0; i< GRIDHEIGHT; i++){
            for(int j = 0; j< GRIDWIDTH; j++){
                buttons[i][j].setText(this.game.getCell(buttons[i][j].getAcross(), buttons[i][j].getDown()));
            }
        }
    }

    /**
     * create the label that will show game message
     */
    private void createTurnLabel(){
        this.messageLabel = new JLabel(this.game.getGameStateMessage());
        this.messageLabel.setBounds(width/4, 50, 150, 40);
        this.add(this.messageLabel);
    }

    /**
     * create the button to go back to the main menu
     */
    private void createBackButton(){
        JButton backButton = new JButton("Back");
        backButton.setBounds(width-200, height - 200, 100, 50);
        this.add(backButton);
        backButton.addActionListener(e -> {
            this.root.backToStartingScreen(this);
            clearButtons();
            this.game.newGame();
            updateMessageLabel();
        });
    }

    /**
     * create the reset button
     */
    private void createResetButton(){
        JButton resetButton = new JButton("New Game");
        resetButton.setBounds(width-200, height - 140, 100, 50);
        this.add(resetButton);
        resetButton.addActionListener(e -> {
            clearButtons();
            this.game.newGame();
            updateMessageLabel();
        });
    }

    /**
     * create the button that will clear the board
     */
    private void clearButtons(){
        for(int i = 0; i<this.buttons.length; i++){
            for(int j = 0; j<this.buttons[i].length; j++){
                buttons[i][j].setText(" ");
            }
        }
    }

    /**
     * Update the game state label
     */
    private void updateMessageLabel(){
        this.messageLabel.setText(this.game.getGameStateMessage());
    }


    /**
     * create the button grid, which uses gridLayout
     * @param tall height of the grid
     * @param wide width of the grid
     * @return the JPanel with the buttons
     */
    private JPanel makeButtonGrid(int tall, int wide){
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(wide, tall));
        for (int y=0; y<wide; y++){
            for (int x=0; x<tall; x++){ 
                //Create buttons and link each button back to a coordinate on the grid
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x+1); //made the choice to be 1-based
                buttons[y][x].setDown(y+1);
                buttons[y][x].addActionListener(e -> gridClicked(e));
                panel.add(buttons[y][x]);
            }
        }
        return panel;
    }

    /**
     * For update the grid when button is clicked
     * @param e update the button that fot pressed
     */
    private void gridClicked(ActionEvent e){
        if(this.game.isDone()){//prevent player for changing board when game is finished
            return;
        }
        PositionAwareButton button = (PositionAwareButton)e.getSource();
        String[] buttonOptions;
        if(this.game.getCurrentPlayer() == 1){
            buttonOptions = new String[this.game.getOddNums().size()+1];
            buttonOptions[0] = "back";  
            for(int i = 1; i<this.game.getOddNums().size()+1; i++){
                buttonOptions[i] = this.game.getOddNums().get(i-1).toString();
            }
        }else{
            buttonOptions = new String[this.game.getEvenNums().size()+1];
            buttonOptions[0] = "back"; 
            for(int i = 1; i< this.game.getEvenNums().size()+1; i++){
                buttonOptions[i] = this.game.getEvenNums().get(i-1).toString();
            }
        }
        if(button.getText() != " " && button.getText() != ""){
            return;
        }
        int input = takeInput(buttonOptions);
        if(input > 0){
            this.game.takeTurn(button.getAcross(), button.getDown(), buttonOptions[input]);
        }
        button.setText(this.game.getCell(button.getAcross(), button.getDown()));
        updateMessageLabel();
    }

    /**
     * prompt an optionPane to take inputs
     * @param options the list of availble inputs
     * @return
     */
    private int takeInput(String[] options){
        int response = JOptionPane.showOptionDialog(
            null, "Make your move", "Input", -1, -1,null, options, options[0]
        );


        return response;
    }

    /**
     * Set the Numerical TicTacToe cotroler
     * @param controller the controler for the game
     */
    public void setGameController(NumericalTTTGame controller){
        this.game = controller;
    }
}
