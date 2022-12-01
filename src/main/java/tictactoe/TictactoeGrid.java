package tictactoe;

import boardgame.Grid;

/**
 * The grid for TicTacToe, which imlemented the Grid class
 */
public class TictactoeGrid extends Grid{

    /**
     * The initializer for the grid
     * @param wide the width of the grid
     * @param tall the height fo the grid
     */
    public TictactoeGrid(int wide, int tall) {
        super(wide, tall);
    }


    /**
     * X|O|X
     * -+-+-
     * O|X|O
     * -+-+-
     * X|O|X
     */

    @Override
    public String getStringGrid(){
        String gridString = "";

        for(int i = 1; i<4; i++){
            for(int j = 1; j< 4; j++){
                gridString = gridString + this.getValue(j, i);

                if(j<3){
                    gridString = gridString + "|";
                }
            }
            gridString = gridString + "\n";

            if(i < 3){
                gridString = gridString + "-+-+-\n";
            }
        }

        return gridString;
    }
    
}
