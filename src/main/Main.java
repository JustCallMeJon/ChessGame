package main;

import javax.swing.*;
import java.awt.*;

public class Main {

    //Views
    private static final String MENU_VIEW = "menu";
    private static final String GAME_VIEW = "game";

    private static JPanel views;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame window = new JFrame("Chess Game");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            //Both views live in the same container, so switching cards never resizes the window
            views = new JPanel(new CardLayout());
            views.setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
            views.add(createMenuView(), MENU_VIEW);

            window.add(views);
            window.pack();

            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }

    private static JPanel createMenuView() {

        JPanel menu = new JPanel(new GridBagLayout());
        menu.setBackground(Color.black);

        JLabel title = new JLabel("Chess");
        title.setFont(new Font("Book Antiqua", Font.BOLD, 72));
        title.setForeground(Color.white);

        JButton standardButton = createButton("Standard Chess");
        JButton nineSixtyButton = createButton("Chess 960");

        standardButton.addActionListener(e -> startGame(false));
        nineSixtyButton.addActionListener(e -> startGame(true));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(12, 0, 12, 0);

        menu.add(title, c);
        menu.add(standardButton, c);
        menu.add(nineSixtyButton, c);

        return menu;
    }

    private static JButton createButton(String text) {

        JButton button = new JButton(text);
        button.setFont(new Font("Book Antiqua", Font.PLAIN, 25));
        button.setPreferredSize(new Dimension(260, 50));
        button.setFocusPainted(false);

        return button;
    }

    private static void startGame(boolean chessNineSixty) {

        GamePanel gp = new GamePanel();

        //The piece lists are static, so clear anything an earlier setup left behind
        GamePanel.pieces.clear();

        if (chessNineSixty) {
            gp.chessNineSixty();
        } else {
            gp.setPieces();
        }

        //The constructor copied the lists while the board was still empty,
        //so simPieces has to be refilled once the pieces are placed
        GamePanel.simPieces.clear();
        GamePanel.simPieces.addAll(GamePanel.pieces);

        views.add(gp, GAME_VIEW);
        ((CardLayout) views.getLayout()).show(views, GAME_VIEW);

        gp.lauchGame();
    }
}
