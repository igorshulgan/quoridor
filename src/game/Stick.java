package game;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ihar_shulhan on 01/04/2017.
 */
public class Stick implements Serializable {
    int x;
    int y;
    int id;
    static AtomicInteger counter = new AtomicInteger(0);

    public Stick(int x, int y) throws Exception {
        if (!check(x, y)) {
            throw new Exception("Stick is wrong");
        }
        this.x = x;
        this.y = y;
        this.id = counter.getAndIncrement();
    }
    public Stick(int x, int y, int id) throws Exception {
        if (!check(x, y)) {
            throw new Exception("Stick is wrong");
        }
        this.x = x;
        this.y = y;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Stick{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static boolean check(int x, int y) {
        if (x >= 0 && x < 17 && y >= 0 && y < 17
                && ((x % 2 != 0 && y % 2 == 0) || (y % 2 != 0 && x % 2 == 0))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stick stick = (Stick) o;

        if (x != stick.x) return false;
        return y == stick.y;

    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getId() {
        return id;
    }
}
