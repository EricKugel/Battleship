package eric.kugel.battleship.algorithms;

import eric.kugel.battleship.game.*;


public class Standard implements Algorithm {
    private Battleship battleship;

    public Standard(Battleship battleship) {
        this.battleship = battleship;
    }

    public void shoot() {
        Square[][] grid = battleship.getGrid();

        int row = (int) (Math.random() * grid.length);
        int col = (int) (Math.random() * grid[0].length);
        while (grid[row][col].isHit() || grid[row][col].isMiss()) {
            row = (int) (Math.random() * grid.length);
            col = (int) (Math.random() * grid[0].length);
        }

        battleship.shoot(grid[row][col]);
    }

    public void shipSunk(Ship ship) {
        battleship.log("Ship sunk");
    }

    public void gameOver(int moves) {
        battleship.log("Finished in " + moves + " moves");
    }
}
