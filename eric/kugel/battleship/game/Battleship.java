package eric.kugel.battleship.game;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import eric.kugel.battleship.algorithms.*;

public class Battleship extends JFrame {
    public static final int GRID_SIZE = 10;
    public static final int SCREEN_SIZE = 800;

    private Square[][] grid = new Square[GRID_SIZE][GRID_SIZE];
    private Ship[] ships = new Ship[5];
    public static final int[] SHIP_LENGTHS = {5, 4, 3, 3, 2};

    private JTextArea messageArea = null;
    private int moves = 0;

    private Algorithm algorithm;

    public Battleship() {
        setTitle("Battleship");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initGUI();
        pack();

        this.algorithm = new Standard(this);

        Timer timer = new Timer(1000 / 6, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                algorithm.shoot();
            }
        });
        timer.start();
    }

    private void initGUI() {
        messageArea = new JTextArea("Welcome to Battleship.", 3, 0);
        messageArea.setMargin(new Insets(10, 10, 10, 10));
        messageArea.setEditable(true);
        messageArea.setLineWrap(true);
        messageArea.setBackground(Color.BLACK);
        messageArea.setForeground(Color.WHITE);
        messageArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        messageArea.setOpaque(true);
        JScrollPane scrollPane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.NORTH);

        JPanel main = new JPanel();
        main.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Square square = new Square(this, row, col);
                grid[row][col] = square;
                main.add(square);
            }
        }

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

        add(main);
    }

    public void shoot(Square square) {
        if (!square.isMiss() && !square.isHit()) {
            square.shoot();
            moves += 1;
        }
    }

    public void log(String text) {
        messageArea.append("\n" + text);
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
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

    public Square[][] getGrid() {
        return grid;
    }

    public static void main(String[] arg0) {
        new Battleship();
    }
}