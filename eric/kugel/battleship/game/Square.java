package eric.kugel.battleship.game;
import javax.swing.*;

import java.awt.*;

public class Square extends JButton {
    public static final int SIZE = Battleship.SCREEN_SIZE / Battleship.GRID_SIZE;

    private int row = -1;
    private int col = -1;

    private Ship ship = null;

    private Battleship battleship = null;

    private boolean miss = false;
    private boolean hit = false;

    public Square(Battleship battleship, int row, int col) {
        this.battleship = battleship;
        this.row = row;
        this.col = col;

        setPreferredSize(new Dimension(SIZE, SIZE));
    }

    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 84, 147));
        g.fillRect(0, 0, SIZE, SIZE);
        if (ship != null && ship.isSunk()) {
            g.setColor(Color.GRAY);
            if (!ship.getX()) {
                g.fillRect(0, SIZE / 5, SIZE, SIZE * 3 / 5);
            } else {
                g.fillRect(SIZE / 5, 0, SIZE * 3 / 5, SIZE);
            }
        }

        if (miss) {
            g.setColor(Color.WHITE);
            g.fillArc(SIZE / 4, SIZE / 4, SIZE / 2, SIZE / 2, 0, 360);
        } else if (hit) {
            g.setColor(Color.RED);
            g.fillArc(SIZE / 4, SIZE / 4, SIZE / 2, SIZE / 2, 0, 360);
        }
    }

    public void shoot() {
        if (ship == null) {
            miss = true;
        } else {
            hit = true;
            ship.hit();
        }
        repaint();
    }

    public Square[] getNeighbors() {
        int neighborCount = 4;
        if (row < 0 || row > Battleship.GRID_SIZE) {
            neighborCount -= 1;
        } if (col < 0 || col > Battleship.GRID_SIZE) {
            neighborCount -= 1;
        }

        Square[] neighbors = new Square[neighborCount];

        int i = 0;
        int index = 0;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (i % 2 == 1) {
                    neighbors[index] = battleship.getGrid()[row + r][col + c];
                    index += 1;
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

    public Battleship getBattleship() {
        return battleship;
    }

    
}   
