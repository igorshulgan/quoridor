package game;

import java.util.Objects;

/**
 * Created by ihar_shulhan on 01/04/2017.
 */
public class Point implements Comparable<Point>{
    int x;
    int y;
    int value;

    public Point(int x, int y) throws Exception {
        if (!check(x, y)) {
            throw new Exception("Wrong coordinates");
        }
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int value) throws Exception {
        if (!check(x, y)) {
            throw new Exception("Wrong coordinates");
        }
        this.x = x;
        this.y = y;
        this.value = value;
    }


    public Point(Point other, int value) {
        this.x = other.getX();
        this.y = other.getY();
        this.value = value;
    }

    public static boolean check(int x, int y) {
        if (x < 0 || x > 8 || y < 0 || y > 8) {
            return false;
        } else {
            return true;
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Point o) {
        return Integer.compare(this.value, o.value);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
