package game;

import java.util.Objects;

/**
 * Created by ihar_shulhan on 01/04/2017.
 */
public class Move implements Comparable<Move> {
    int x;
    int y;
    int x1;
    int x2;
    int y1;
    int y2;
    int type;
    int result;

    public Move(int x, int y, int result) {
        this.x = x;
        this.y = y;
        this.type = 0;
        this.result = result;
    }

    public Move(int x1, int y1, int x2, int y2, int result) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.type = 1;
        this.result = result;
    }

    @Override
    public int compareTo(Move o) {
        return Integer.compare(this.result, o.result);
    }

    public int getResult() {
        return result;
    }

    public int getType() {
        return type;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

}