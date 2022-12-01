package tictactoe;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import boardgame.ui.PositionAwareButton;
import game.GameUI;
import game.Player;
import game.Saver;
import game.TextUI;


/**
 * The class that will show the TicTacToe game in a JPanel
 */
public class TictactoeView extends JPanel{

    private static final int GRIDWIDTH = 3;// width and height to 3 since
    private static final int GRIDHEIGHT = 3;//that is how it is suppose to be

    private String defaultFilePath = "assets";

    private JLabel messageLabel;
    private TictactoeGame game;
    private PositionAwareButton[][] buttons;
    private JPanel buttonPanel;
    private GameUI root;


    private int width;
    private int height;


    /**
     * The initializer for the JPanel
     * @param player1 
     * @param player2 
     * @param gameFrame
     * @param currentWidth the width for the grid
     * @param currentHeight the height for the grid
     */
    public TictactoeView(Player player1, Player player2, GameUI gameFrame, int currentWidth, int currentHeight){
        super();
        
        this.width = currentWidth;
        this.height = currentHeight;
        this.setLayout(null);
        root = gameFrame;

        setGameController(new TictactoeGame(width, height, player1, player2));

        createBackButton();
        createResetButton();
        createTurnLabel();
        createLoadButton();
        createSaveButton();
        createCLIButton();
        this.game.newGame();

        

        this.buttonPanel = makeButtonGrid(GRIDHEIGHT, GRIDWIDTH);

        this.add(this.buttonPanel);
        this.buttonPanel.setBounds(width/4, 100, width/3, height/3);
        

    }

    /**
     * Create the button that will switch to CLI
     * this will be added to the JPanel
     */
    private void createCLIButton(){
        JButton saveButton = new JButton("Switch to CLIy");
        saveButton.setBounds(width-200, height - 380, 100, 50);
        this.add(saveButton);
        saveButton.addActionListener(e -> switchToCLI());
    }

    /**
     * The method to switch to CLI version of TicTacToe
     */
    private void switchToCLI(){
        this.root.dispose();
        this.game.startCLI();
    }

    /**
     * The button to save the game
     */
    private void createSaveButton(){
        JButton saveButton = new JButton("Save game");
        saveButton.setBounds(width-200, height - 320, 100, 50);
        this.add(saveButton);
        saveButton.addActionListener(e -> Saver.save(this.game,this));
    }

    /**
     * The button for loading the game
     */
    private void createLoadButton(){
        JButton loadButton = new JButton("Load game");
        loadButton.setBounds(width-200, height - 260, 100, 50);
        this.add(loadButton);
        loadButton.addActionListener(e -> loadGame());
    }

    /**
     * The method for loading the game from a fileChooser
     */
    private void loadGame(){
        JFileChooser fileChooser = new JFileChooser(defaultFilePath);
        int response = fileChooser.showOpenDialog(this);
        if(response == JFileChooser.APPROVE_OPTION){
            File gameFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
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
     * Update the game after load
     */
    private void updateLoadGame(){
        updateMessageLabel();
        updateAllButtons();
    }

    /**
     * update all buttons after load
     */
    private void updateAllButtons(){
        for(int i = 0; i< GRIDHEIGHT; i++){
            for(int j = 0; j< GRIDWIDTH; j++){
                buttons[i][j].setText(this.game.getCell(buttons[i][j].getAcross(), buttons[i][j].getDown()));
            }
        }
    }

    /**
     * create button that will go back to main menu
     */
    private void createBackButton(){
        JButton backButton = new JButton("back");
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
     * the button to reset the board
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
     * the label to show the game status
     */
    private void createTurnLabel(){
        this.messageLabel = new JLabel(this.game.getGameStateMessage());
        this.messageLabel.setBounds(width/4, 50, 150, 40);
        this.add(this.messageLabel);
    }

    /**
     * Update label
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
                buttons[y][x].setText(this.game.getNextValue());
                buttons[y][x].setAcross(x+1); //made the choice to be 1-based
                buttons[y][x].setDown(y+1);
                buttons[y][x].addActionListener(e -> gridClicked(e));
                panel.add(buttons[y][x]);
            }
        }
        return panel;
    }

    /**
     * clear all buttons
     */
    private void clearButtons(){
        for(int i = 0; i<this.buttons.length; i++){
            for(int j = 0; j<this.buttons[i].length; j++){
                buttons[i][j].setText(" ");
            }
        }
    }

    /**
     * update the cell 
     * @param e to get the button that got pressed
     */
    private void gridClicked(ActionEvent e){
        if(this.game.isDone()){//prevent player for changing board when game is finished
            return;
        }
        PositionAwareButton button = (PositionAwareButton)e.getSource();

        this.game.takeTurn(button.getAcross(), button.getDown(), this.game.getCurrentPlayer() == 1 ? "X":"O");

        button.setText(this.game.getCell(button.getAcross(), button.getDown()));
        updateMessageLabel();
        
    }

    /**
     * Set the TicTacToe game controler
     * @param controller the controler for the game
     */
    private void setGameController(TictactoeGame controller){
        this.game = controller;
    }
}
