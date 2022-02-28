package eric.kugel.battleship.gui;

import javax.swing.*;

import java.awt.*;

import eric.kugel.battleship.logic.*;
public class Square extends JButton {
    public static final int SIZE = Main.SCREEN_SIZE / Board.GRID_SIZE;

    private Cell cell = null;

    public Square(Cell cell) {
        this.cell = cell;
        setPreferredSize(new Dimension(SIZE, SIZE));
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 84, 147));
        g.fillRect(0, 0, SIZE, SIZE);
        if (cell.getShip() != null && cell.getShip().isSunk()) {
            g.setColor(Color.GRAY);
            if (!cell.getShip().getX()) {
                g.fillRect(0, SIZE / 5, SIZE, SIZE * 3 / 5);
            } else {
                g.fillRect(SIZE / 5, 0, SIZE * 3 / 5, SIZE);
            }
        }

        if (cell.isMiss()) {
            g.setColor(Color.WHITE);
            g.fillArc(SIZE / 4, SIZE / 4, SIZE / 2, SIZE / 2, 0, 360);
        } else if (cell.isHit()) {
            g.setColor(Color.RED);
            g.fillArc(SIZE / 4, SIZE / 4, SIZE / 2, SIZE / 2, 0, 360);
        }
    }
}
