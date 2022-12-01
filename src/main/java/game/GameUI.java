package game;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.w3c.dom.Text;

import numerictictactoe.NumericalTTTView;
import tictactoe.TictactoeView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;


/**
 * This class holds the main method that will run the program
 * @author GuangLi Lin
 */
public class GameUI extends JFrame {
    private static final int DEFAULTWIDTH = 700;
    private static final int DEFAULTHEIGHT = 500;

    private Player player1;
    private Player player2;
    
    private JPanel tictactoePanel;
    private JPanel numericTictactoPanel;
    private JPanel mainMenuPanel;
    private JPanel playerLabelPanel;

    private JFileChooser fileChooser;
    private JMenuBar menuBar;

    private String filePath = "assets";


    /**
     * Initializer of the game program
     * @param title The name of the application
     */
    public GameUI(String title){
        super();
        this.setSize(DEFAULTWIDTH, DEFAULTHEIGHT);
        this.setTitle(title);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.player1 = new Player();
        this.player2 = new Player();
        

        this.fileChooser = new JFileChooser(filePath);
        
        createMenuBar();
        createMainMenuPanel();

        int response = JOptionPane.showConfirmDialog(this, "Do you want to load player 1?",
                                                     "Load Player 1?", JOptionPane.YES_NO_OPTION);

        if(response == JOptionPane.YES_OPTION){
            playerLoader(player1);
        }

        response = JOptionPane.showConfirmDialog(this, "Do you want to load player 2?", 
                                                "Load Player 2?", JOptionPane.YES_NO_OPTION);
        
        if(response == JOptionPane.YES_OPTION){
            playerLoader(player2);
        }

        this.tictactoePanel = new TictactoeView(player1, player2,this, DEFAULTWIDTH, DEFAULTHEIGHT);
        this.numericTictactoPanel = new NumericalTTTView(player1, player2,this, DEFAULTWIDTH, DEFAULTHEIGHT);
    }

    /**
     * This is the method the other panels will call if they want to return to main menu
     * @param panelToReturn the panel that called the method
     */
    public void backToStartingScreen(JPanel panelToReturn){
        this.remove(panelToReturn);
        this.add(this.mainMenuPanel);
        validate();
        repaint();
    }

    /**
     * This replace the current panel with the selected panel
     * @param panelToGo the panel wish to show
     */
    private void goToContentPanel(JPanel panelToGo){
        this.remove(this.mainMenuPanel);
        this.add(panelToGo);
        
        validate();
        repaint();
    }

    /**
     * this is for exiting the game when pressing the quit button
     */
    private void exitGame(){
        this.dispose();
    }

    /**
     * This is for creating the menu bar, which will house the option to load and save players
     * This component will use the FlowLayout layout manager
     */
    private void createMenuBar(){
        this.menuBar = new JMenuBar();
        this.setJMenuBar(this.menuBar);
        this.menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        JMenu settingMenu = createSettingMenu();
        this.menuBar.add(settingMenu, FlowLayout.LEFT);

        this.playerLabelPanel = createPlayerLablePanel();
        this.playerLabelPanel.setPreferredSize(new Dimension(600,20));
        this.menuBar.add(playerLabelPanel, FlowLayout.CENTER);

    }

    /**
     * This is the panel that will show the current player's names
     * @return the panel that will pe placed in the menu bar
     */
    private JPanel createPlayerLablePanel(){
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(null);

        JLabel player1Label = new JLabel("Player 1: " + this.player1.getPlayerName());
        player1Label.setBounds(150, -15, 200, 50);
        JLabel player2Label = new JLabel("Player 2: " + this.player2.getPlayerName());
        player2Label.setBounds(350, -15, 200, 50);
        labelPanel.add(player1Label);
        labelPanel.add(player2Label);

        return labelPanel;
    }

    /**
     * This is for update the player labels in labelPanel created by the method createPlayerPanel
     */
    private void updatePlayerLabel(){
        
        if(this.playerLabelPanel.getComponent(0) instanceof JLabel){
            JLabel tempLabel = (JLabel) this.playerLabelPanel.getComponent(0);
            tempLabel.setText("Player "+Integer.toString(1)+": "+this.player1.getPlayerName());
        }

        if(this.playerLabelPanel.getComponent(1) instanceof JLabel){
            JLabel tempLabel = (JLabel) this.playerLabelPanel.getComponent(1);
            tempLabel.setText("Player "+Integer.toString(2)+": "+this.player2.getPlayerName());
        }
        
    }

    /**
     * this is for creating the dropdown menu and it's options
     * The options are for loading and saving players.
     * @return the JMenu that will be placed on the jmenubar
     */
    private JMenu createSettingMenu(){
        JMenu settingMenu = new JMenu("Setting");


        JMenuItem player1Item = new JMenuItem("Load Player 1");
        settingMenu.add(player1Item);
        player1Item.addActionListener(e -> playerLoader(this.player1));
        settingMenu.addSeparator();

        JMenuItem player2Item = new JMenuItem("Load Player 2");
        player2Item.addActionListener(e -> playerLoader(this.player2));
        settingMenu.add(player2Item);
        settingMenu.addSeparator();


        JMenuItem savePlayer1Item = new JMenuItem("Save Player 1");
        settingMenu.add(savePlayer1Item);
        savePlayer1Item.addActionListener(e -> Saver.save(player2,this.mainMenuPanel));
        settingMenu.addSeparator();

        JMenuItem savePlayer2Item = new JMenuItem("Save Player 2");
        settingMenu.add(savePlayer2Item);
        savePlayer2Item.addActionListener(e -> Saver.save(player2,this.mainMenuPanel));


        
        return settingMenu;
    }


    /**
     * This is for loading the desired player
     * @param player either player1 or player2. will be updated by reading the file
     */
    private void playerLoader(Player player){

        int response = this.fileChooser.showOpenDialog(this);
        File playerFile = null;

        if(response == JFileChooser.APPROVE_OPTION){
            playerFile = new File(this.fileChooser.getSelectedFile().getAbsolutePath());
        }


        if(playerFile != null){
            try {
                Scanner fileScanner = new Scanner(playerFile);
                String fileString = "";
    
                while(fileScanner.hasNextLine()){
                    fileString = fileString + fileScanner.nextLine() + "\n";
                }
    
                player.loadSavedString(fileString);
    
                updatePlayerLabel();
    
                fileScanner.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            } 

        }
    }


    /**
     * This is for a fast way to create buttons that is desired
     * 
     * @param content the string to display on the button
     * @param x the x location of the button in the panel
     * @param y the y location of the button in the panel
     * @return a created button that has been added to the main menu panel
     */
    private JButton createJButton(String content, int x, int y){
        JButton button = new JButton(content);
        this.mainMenuPanel.add(button);
        button.setBounds(x, y, 150, 50);
        return button;
    }
    
    /**
     * This is for creating the starting panel, where player can make their moves
     */
    private void createMainMenuPanel(){
        this.mainMenuPanel = new JPanel();
        this.mainMenuPanel.setLayout(null);
        this.add(this.mainMenuPanel);

        JButton tictactoeButton = createJButton("TicTacToe", DEFAULTWIDTH/2-75, 25);
        tictactoeButton.addActionListener(e -> goToContentPanel(this.tictactoePanel));

        JButton numericTTTButton = createJButton("<html>Numeric<br>TicTacToe<html>", DEFAULTWIDTH/2-75, 80);
        numericTTTButton.addActionListener(e -> goToContentPanel(this.numericTictactoPanel));

        JButton quitButton = createJButton("Quit", DEFAULTWIDTH/2-75, DEFAULTHEIGHT-200);
        quitButton.addActionListener(e -> exitGame());
    
        this.mainMenuPanel.setVisible(true);
        
    }

    /**
     * the main function
     * @param args
     */
    public static void main(String[] args){
        GameUI newGameUi = new GameUI("Board Games");
        newGameUi.setVisible(true);
        
    }
}
