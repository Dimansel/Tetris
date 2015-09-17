package org.dimansel.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements ActionListener, KeyListener {
    public static final int WIDTH = 355;
    public static final int HEIGHT = 528;
    private Field field;
    public static Timer timer;
    public static int score;

    public Window() {
        field = new Field(WIDTH-5, HEIGHT-28, 25);
        timer = new Timer(300, this);
        /**
         * INITIALIZING WINDOW
         */
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((size.width - WIDTH) / 2, (size.height - HEIGHT) / 2);
        setSize(WIDTH, HEIGHT);
        setTitle("Tetris. Score: 0");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(field);
        addKeyListener(this);
        setVisible(true);

        field.newGame();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        field.tick();
        setTitle("Tetris. Score: "+String.valueOf(score));
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_N:
                field.newGame();
                break;
            case KeyEvent.VK_LEFT:
                if (field.activePiece.stepLeft(field))
                    repaint();
                break;
            case KeyEvent.VK_RIGHT:
                if (field.activePiece.stepRight(field))
                    repaint();
                break;
            case KeyEvent.VK_UP:
                if (field.activePiece.rotate(field))
                    repaint();
                break;
            case KeyEvent.VK_DOWN:
                field.activePiece.dropDown(field);
                repaint();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public static void main(String[] args) {
        new Window();
    }
}
