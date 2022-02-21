import javax.swing.*;

import java.awt.*;

public class Battleship extends JFrame {
    public static final int GRID_SIZE = 10;
    public static final int SCREEN_SIZE = 800;

    private Square[][] grid = new Square[GRID_SIZE][GRID_SIZE];
    private Ship[] ships = new Ship[5];

    private JTextArea messageArea = null;

    public Battleship() {
        setTitle("Battleship");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initGUI();
        pack();
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

        int[] shipLengths = {5, 4, 3, 3, 2};
        for (int i = 0; i < ships.length; i++) {
            Ship ship = null;
            while (ship == null) {
                boolean x = Math.random() > .5;
                boolean d = Math.random() > .5;
                ship = generateShip(d, x, shipLengths[i]);
            }
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

    private Ship generateShip(boolean d, boolean x, int shipLength) {
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

    public void buttonClicked(Square square) {
        if (!square.isMiss() && !square.isHit()) {
            square.shoot();
        }
    }

    private void log(String text) {
        messageArea.append("\n" + text);
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    public void sink(Ship ship) {
        log("Ship of length " + ship.getLength() + " sunk");
        boolean gameOver = true;
        for (Ship s : ships) {
            if (!s.isSunk()) {
                gameOver = false;
                break;
            }
        }
        if (gameOver) {
            log("Congratulations. All ships have been sunk.");
        }
    }

    public static void main(String[] arg0) {
        new Battleship();
    }
}