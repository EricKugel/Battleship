package eric.kugel.battleship.logic;

public class Cell {
    private int row = -1;
    private int col = -1;

    private Ship ship = null;

    private Board battleship = null;

    private boolean miss = false;
    private boolean hit = false;

    public Cell(Board battleship, int row, int col) {
        this.battleship = battleship;
        this.row = row;
        this.col = col;
    }

    
    public void shoot() {
        if (ship == null) {
            miss = true;
        } else {
            hit = true;
            ship.hit();
        }
    }

    public Cell[] getNeighbors() {
        int neighborCount = 4;
        if (row == 0 || row == Board.GRID_SIZE - 1) {
            neighborCount -= 1;
        } if (col == 0 || col == Board.GRID_SIZE - 1) {
            neighborCount -= 1;
        }

        Cell[] neighbors = new Cell[neighborCount];

        int i = 0;
        int index = 0;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (i % 2 == 1) {
                    if (row + r > -1 && row + r < Board.GRID_SIZE && col + c > -1 && col + c < Board.GRID_SIZE) {
                        neighbors[index] = battleship.getGrid()[row + r][col + c];
                        index += 1;
                    }
                }
                i++;
            }
        }
        
        return neighbors;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        ship.addSquare(this);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isMiss() {
        return miss;
    }

    public boolean isHit() {
        return hit;
    }

    public Board getBattleship() {
        return battleship;
    }    
}   
