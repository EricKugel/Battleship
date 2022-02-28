package eric.kugel.battleship.game;
import java.util.ArrayList;

public class Ship {
    private boolean[][] location;
    private boolean d;
    private boolean x;
    private boolean sunk = false;
    public static int GRID_SIZE = Battleship.GRID_SIZE;

    private ArrayList<Square> squares = new ArrayList<Square>();

    public Ship(boolean[][] location, boolean d, boolean x) {
        this.location = location;
        this.d = d;
        this.x = x;
    }

    public void hit() {
        boolean sunk = true;
        for (Square square : squares) {
            if (!square.isHit()) {
                sunk = false;
                break;
            }
        }
        if (sunk) {
            sink();
        }
    }

    private void sink() {
        this.sunk = true;
        squares.get(0).getBattleship().sink(this);
        repaint();
    }

    private void repaint() {
        for (Square square : squares) {
            square.repaint();
        }
    }

    public int getLength() {
        return squares.size();
    }

    public void addSquare(Square square) {
        squares.add(square);
    }

    public boolean[][] getLocation() {
        return location;
    }

    public boolean getD() {
        return d;
    }

    public boolean getX() {
        return x;
    }

    public boolean isSunk() {
        return sunk;
    }

    public static Ship createShip(Square[][] grid, int shipLength) {
        Ship ship = null;
        while (ship == null) {
            boolean x = Math.random() > .5;
            boolean d = Math.random() > .5;
            ship = generateShip(d, x, shipLength, grid);
        }
        return ship;
    }

    private static Ship generateShip(boolean d, boolean x, int shipLength, Square[][] grid) {
        int min = 0;
        int max = GRID_SIZE - 1;
        if (d) {
            min = min + shipLength;
        } else {
            max = max - shipLength;
        }
        int startRow = (int) (Math.random() * (max - min) + min);
        int startCol = (int) (Math.random() * GRID_SIZE);
        if (!x) {
            int temp = startRow;
            startRow = startCol;
            startCol = temp;
        }

        boolean[][] location = new boolean[GRID_SIZE][GRID_SIZE];
        for (int j = 0; j < shipLength; j++) {
            int row = startRow;
            int col = startCol;
            if (d) {
                if (x) {
                    row -= j;
                } else {
                    col -= j;
                }
            } else {
                if (x) {
                    row += j;
                } else {
                    col += j;
                }
            }
            location[row][col] = true;
            if (grid[row][col].getShip() != null) {
                return null;
            }
        }

        return new Ship(location, d, x);
    }
}
