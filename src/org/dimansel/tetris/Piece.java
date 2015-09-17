package org.dimansel.tetris;

import java.awt.*;

public class Piece {
    /** I = 0;
      * J = 1;
      * L = 2;
      * O = 3;
      * S = 4;
      * T = 5;
      * Z = 6;
      */

    public boolean[][] data = new boolean[4][4];

    public Color color;
    public int x;
    public int y;
    private int size;

    public Piece(int id, int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;

        switch (id) {
            case 0:
                color = Color.CYAN;
                for (int a=0; a<4; a++) data[a][1]=true;
                break;
            case 1:
                color = Color.BLUE;
                for (int a=0; a<3; a++) data[a][1]=true;
                data[2][2]=true;
                break;
            case 2:
                color = Color.ORANGE;
                for (int a=0; a<3; a++) data[a][1]=true;
                data[0][2]=true;
                break;
            case 3:
                color = Color.YELLOW;
                for (int a=1; a<3; a++) data[a][1]=true;
                for (int a=1; a<3; a++) data[a][2]=true;
                break;
            case 4:
                color = Color.GREEN;
                for (int a=1; a<3; a++) data[a][1]=true;
                for (int a=0; a<2; a++) data[a][2]=true;
                break;
            case 5:
                color = Color.MAGENTA;
                for (int a=0; a<3; a++) data[a][1]=true;
                data[1][2]=true;
                break;
            case 6:
                color = Color.RED;
                for (int a=0; a<2; a++) data[a][1]=true;
                for (int a=1; a<3; a++) data[a][2]=true;
                break;
        }
    }

    public Piece(boolean[][] data, int x, int y, Color c, int size) {
        this.data = data;
        this.x = x;
        this.y = y;
        color = c;
        this.size = size;
    }

    public boolean stepDown(Field f) {
        if (isIntersection(0, 1, f)) return false;
        if (isOutside(0, 1, f)) return false;

        y+=1;
        return true;
    }

    public boolean stepLeft(Field f) {
        if (isIntersection(-1, 0, f)) return false;
        if (isOutside(-1, 0, f)) return false;

        x-=1;
        return true;
    }

    public boolean stepRight(Field f) {
        if (isIntersection(1, 0, f)) return false;
        if (isOutside(1, 0, f)) return false;

        x+=1;
        return true;
    }

    public boolean rotate(Field f) {
        boolean[][] dt = new boolean[4][4];
        for (int a=0; a<4; a++) {
            for (int b=0; b<4; b++) {
                dt[a][b] = data[4-b-1][a];
            }
        }

        Piece p = new Piece(dt, x, y, color, size);
        if (p.isIntersection(0, 0, f) || p.isOutside(0, 0, f)) return false;

        data = dt;
        return true;
    }

    public void dropDown(Field f) {
        int yOffset = 0;
        while (!isIntersection(0, yOffset, f) && !isOutside(0, yOffset, f))
            yOffset++;
        y+=yOffset-1;
    }

    public boolean isOutside(int xOffset, int yOffset, Field f) {
        for (int b=0; b<4; b++) {
            for (int a=0; a<4; a++) {
                int ny=(y+b+yOffset)*size;
                int nx=(x+a+xOffset)*size;
                if (!data[a][b]) continue;
                if (ny >= f.height || ny < 0) return true;
                if (nx >= f.width || nx < 0) return true;
            }
        }

        return false;
    }

    public boolean isIntersection(int xOffset, int yOffset, Field f) {
        for (int b=0; b<4; b++) {
            for (int a=0; a<4; a++) {
                for (int d=0; d<f.ih; d++) {
                    for (int c=0; c<f.iw; c++) {
                        int sx1 = x+a+xOffset;
                        int sy1 = y+b+yOffset;

                        if (sx1==c && sy1==d && f.data[c][d] && data[a][b])
                            return true;
                    }
                }
            }
        }

        return false;
    }

    public void render(Graphics g) {
        for (int b=0; b<4; b++) {
            for (int a=0; a<4; a++) {
                if (!data[a][b]) continue;

                g.setColor(color);
                g.fillRect((x+a)*size, (y+b)*size, size, size);
                g.setColor(Color.BLACK);
                g.drawRect((x+a)*size, (y+b)*size, size, size);
            }
        }
    }
}
