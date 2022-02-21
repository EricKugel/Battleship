import java.util.ArrayList;

public class Ship {
    private boolean[][] location;
    private boolean d;
    private boolean x;
    private boolean sunk = false;

    private ArrayList<Square> squares = new ArrayList<Square>();

    public Ship(boolean[][] location, boolean d, boolean x) {
        this.location = location;
        this.d = d;
        this.x = x;
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

    public void addSquare(Square square) {
        squares.add(square);
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
}
