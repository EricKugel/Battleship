package eric.kugel.battleship.algorithms;

import java.awt.event.*;

import eric.kugel.battleship.logic.*;

public class Human implements Algorithm {
    private Board battleship = null;

    public Human(Board battleship) {
        this.battleship = battleship;
        for (Cell[] row : battleship.getGrid()) {
            for (Cell cell : row) {
                cell.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        battleship.shoot(cell);
                    }
                });
            }
        }
    }
    
    public void shoot() {

    }

    public void shipSunk(Ship ship) {
        battleship.log("Ship of length " + ship.getLength() + " sunk");
    }
    
    public void gameOver(int moves) {
        battleship.log("You won in " + moves + " moves");
    }
}
