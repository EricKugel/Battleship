package eric.kugel.battleship.gui;

import javax.swing.*;

import java.awt.*;

import eric.kugel.battleship.algorithms.*;
import eric.kugel.battleship.logic.*;

public class Main extends JFrame {
    public static final int SCREEN_SIZE = 800;
    
    private JTextArea messageArea = null;

    public Main() {
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
        main.setLayout(new GridLayout(Board.GRID_SIZE, Board.GRID_SIZE));


    }

    public void log(String text) {
        messageArea.append("\n" + text);
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    public static void main (String[] arg0) {
        new Main();
    }
}
