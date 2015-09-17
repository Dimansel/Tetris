package org.dimansel.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Field extends JPanel {
    public int width;
    public int height;
    public int pieceSize;
    private Random rnd;
    public Piece activePiece;
    public boolean[][] data;
    private Color[][] colors;
    public int iw;
    public int ih;

    public Field(int w, int h, int psize) {
        width = w;
        height = h;
        pieceSize = psize;
        rnd = new Random();
        iw = w/psize;
        ih = h/psize;
        data = new boolean[iw][ih];
        colors = new Color[iw][ih];

        setSize(w, h);
        setBackground(Color.GRAY);
    }

    public void newGame() {
        Window.timer.start();
        data = new boolean[iw][ih];
        colors = new Color[iw][ih];
        Window.score = 0;
        genRndPiece();
    }

    private void gameOver() {
        Window.timer.stop();
        JOptionPane.showMessageDialog(this, "Game over");
    }

    private void genRndPiece() {
        activePiece = new Piece(rnd.nextInt(7), rnd.nextInt(iw-3), -1, pieceSize);
        if (activePiece.isIntersection(0, 0, this)) gameOver();
    }

    private void checkLine() {
        for (int b=0; b<ih; b++) {
            int count = 0;
            for (int a=0; a<iw; a++) {
                if (data[a][b]) count++;
            }

            if (count == iw) clearLine(b);
        }
    }

    private void clearLine(int index) {
        Window.score += 10;

        for (int a=0; a<iw; a++) {
            data[a][index] = false;
            colors[a][index] = Color.BLACK;
        }

        if (index == 0)
            return;

        for (int b=index-1; b>=0; b--) {
            for (int a=0; a<iw; a++) {
                data[a][b+1]=data[a][b];
                colors[a][b+1]=colors[a][b];
                data[a][b]=false;
                colors[a][b]=Color.BLACK;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        activePiece.render(g);
        for (int b=0; b<ih; b++) {
            for (int a=0; a<iw; a++) {
                if (!data[a][b]) continue;
                g.setColor(colors[a][b]);
                g.fillRect(a * pieceSize, b * pieceSize, pieceSize, pieceSize);
                g.setColor(Color.BLACK);
                g.drawRect(a * pieceSize, b * pieceSize, pieceSize, pieceSize);
            }
        }
    }

    /**
     * Timer tick
     */
    public void tick() {
        if (!activePiece.stepDown(this)) {
            for (int b=0; b<4; b++) {
                for (int a=0; a<4; a++) {
                    if (!activePiece.data[a][b]) continue;
                    data[a+activePiece.x][b+activePiece.y]=true;
                    colors[a+activePiece.x][b+activePiece.y]=activePiece.color;
                }
            }
            checkLine();
            genRndPiece();
        }
        repaint();
    }
}
