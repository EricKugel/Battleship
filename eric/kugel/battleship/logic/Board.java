package eric.kugel.battleship.logic;

import eric.kugel.battleship.algorithms.*;

public class Board {
    public static final int GRID_SIZE = 10;

    private Cell[][] grid = new Cell[GRID_SIZE][GRID_SIZE];
    private Ship[] ships = new Ship[5];
    public static final int[] SHIP_LENGTHS = {5, 4, 3, 3, 2};
    
    private int moves = 0;

    private Algorithm algorithm;

    public Board(Algorithm algorithm) {
        this.algorithm = algorithm;
        init();
    }

    private void init() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Cell cell = new Cell(this, row, col);
                grid[row][col] = cell;
            }
        }
    }

    public void generateShips() {
        for (int i = 0; i < ships.length; i++) {
            Ship ship = Ship.createShip(grid, SHIP_LENGTHS[i]);
            ships[i] = ship;
            boolean[][] location = ship.getLocation();
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (location[row][col]) {
                        grid[row][col].setShip(ship);
                    }
                }
            }
        }
    }
    
    public void shoot(Cell cell) {
        if (!cell.isMiss() && !cell.isHit()) {
            cell.shoot();
            moves += 1;
        }
    }
    
    public void sink(Ship ship) {
        algorithm.shipSunk(ship);
        boolean gameOver = true;
        for (Ship s : ships) {
            if (!s.isSunk()) {
                gameOver = false;
                break;
            }
        }
        if (gameOver) {
            algorithm.gameOver(moves);
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }
}