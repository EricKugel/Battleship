package eric.kugel.battleship.algorithms;

import java.util.ArrayList;

import eric.kugel.battleship.game.*;


public class Standard implements Algorithm {
    private Battleship battleship;

    private ArrayList<Square> unsunkShips = new ArrayList<Square>();

    public Standard(Battleship battleship) {
        this.battleship = battleship;
    }

    public void shoot() {
        Square targetSquare = unsunkShips.size() > 0 ? target(unsunkShips.size() - 1) : random();
        battleship.shoot(targetSquare);
        if (targetSquare.isHit() && !targetSquare.getShip().isSunk()) {
            unsunkShips.add(targetSquare);
        }

        for (int i = 0; i < unsunkShips.size(); i++) {
            if (unsunkShips.get(i).getShip().isSunk()) {
                unsunkShips.remove(i);
                i -= 1;
            }
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
        
        for (Square check : square.getNeighbors()) {
            if (check.isHit()) {
                int oppositeRow = check.getRow() != square.getRow() ? square.getRow() - (check.getRow() - square.getRow()) : square.getRow();
                int oppositeCol = check.getCol() != square.getCol() ? square.getCol() - (check.getCol() - square.getCol()) : square.getCol();
                if (oppositeRow >= 0 && oppositeRow < Battleship.GRID_SIZE && oppositeCol >= 0 && oppositeCol < Battleship.GRID_SIZE) {
                    Square opposite = grid[oppositeRow][oppositeCol];
                    if (!opposite.isHit() && !opposite.isMiss()) {
                        return opposite;
                    }
                }
            }
        }

        for (Square check : square.getNeighbors()) {
            if (!check.isMiss() && !check.isHit()) {
                return check;
            }
        }
        
        if (unsunkShipIndex > 0) {
            return target(unsunkShipIndex - 1);
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
