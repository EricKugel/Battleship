package eric.kugel.battleship.algorithms;

import eric.kugel.battleship.game.*;

import java.awt.event.*;

public class Human implements Algorithm {
    private Battleship battleship = null;

    public Human(Battleship battleship) {
        this.battleship = battleship;
        for (Square[] row : battleship.getGrid()) {
            for (Square square : row) {
                square.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        battleship.shoot(square);
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
