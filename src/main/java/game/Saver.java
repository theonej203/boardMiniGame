package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import boardgame.Saveable;


/**
 * The class that is responsible for saving the savables
 * The calss don't load the savables since that is not a requirement
 */
public class Saver {

    private static final String DEFAULTFOLDER = "assets";
    
    /**
     * The method for saving the savable object
     * @param toSave the object to be saved
     * @param parent to get the jfile chooser from the player
     */
    public static void save(Saveable toSave, JPanel parent){
        JFileChooser filechooser = new JFileChooser(DEFAULTFOLDER);
        int response =  filechooser.showSaveDialog(parent);

        if(response == JFileChooser.APPROVE_OPTION){
            File file = filechooser.getSelectedFile();
        
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(toSave.getStringToSave());
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

        
    }
}
