package eric.kugel.battleship.algorithms;

import java.util.ArrayList;

import eric.kugel.battleship.game.*;


public class Standard implements Algorithm {
    private Battleship battleship;

    private Square lastHit = null;

    private ArrayList<Square> unsunkShips = new ArrayList<Square>();

    public Standard(Battleship battleship) {
        this.battleship = battleship;
    }

    public void shoot() {
        Square targetSquare = null;
        if (unsunkShips.size() > 0) {
            targetSquare = target(unsunkShips.get(0));
        } else {
            targetSquare = random();
        }

        battleship.shoot(targetSquare);
        if (targetSquare.isHit() && !targetSquare.getShip().isSunk()) {
            unsunkShips.add(targetSquare);
        }
    }

    private Square random() {
        Square[][] grid = battleship.getGrid();
        int row = (int) (Math.random() * grid.length);
        int col = (int) (Math.random() * grid[0].length);
        while (grid[row][col].isHit() || grid[row][col].isMiss()) {
            row = (int) (Math.random() * grid.length);
            col = (int) (Math.random() * grid[0].length);
        }
        return grid[row][col];
    }

    private Square target(int unsunkShipIndex) {
        Square[][] grid = battleship.getGrid();
        Square square = unsunkShips.get(unsunkShipIndex);
        int row = square.getRow();
        int col = square.getCol();

        // hacky code warning
        int i = 0;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (i % 2 == 1) {
                    if (row + r > -1 && row + r < grid.length && col + c > -1 && col + c < grid[0].length) {
                        Square check = grid[row + r][col + c];
                        // look on other side (make a straight line)
                        
                    }
                }
                i++;
            }
        }

        for (Square check : square.getNeighbors()) {
            if (check.isHit()) {
                Square opposite = null;
                if (r == 0) {
                    opposite = grid[row][col + (c * -1)];
                } else {
                    opposite = grid[row + (r * -1)][col];
                }
                
                if (!opposite.isHit() && !opposite.isMiss()) {
                    return opposite;
                }
            }
        }
        
        if (unsunkShipIndex < unsunkShips.size() - 1) {
            return target(unsunkShipIndex + 1);
        } else {
            return random();
        }
    }

    public void shipSunk(Ship ship) {
        battleship.log("Ship sunk");
    }

    public void gameOver(int moves) {
        battleship.log("Finished in " + moves + " moves");
    }
}
