package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class SavedMap implements Serializable {
    ArrayList<Stick> sticks = new ArrayList<>();
    int x1;
    int x2;
    int y1;
    int y2;
    int playerNumber;
    int numberOfSticks1;
    int numberOfSticks2;

    public SavedMap(Map map, int playerNumber) {
        this.playerNumber = playerNumber;
        this.sticks.addAll(map.sticks);
        this.x1 = map.getPlayer(0).getPosition().getX();
        this.x2 = map.getPlayer(1).getPosition().getX();
        this.y1 = map.getPlayer(0).getPosition().getY();
        this.y2 = map.getPlayer(1).getPosition().getY();
        this.numberOfSticks1 = map.getPlayer(0).getNumberOfSticks();
        this.numberOfSticks2 = map.getPlayer(1).getNumberOfSticks();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SavedMap savedMap = (SavedMap) o;

        return Objects.deepEquals(sticks, savedMap.sticks) && playerNumber == savedMap.playerNumber && x1 == savedMap.x1
                && x2 == savedMap.x2 && y1 == savedMap.y1 && y2 == savedMap.y2 && numberOfSticks1 == savedMap.numberOfSticks1 && numberOfSticks2 == savedMap.numberOfSticks2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sticks, playerNumber, x1, x2, y1, y2);
    }

    @Override
    public String toString() {
        return "SavedMap{" +
                "sticks=" + sticks +
                ", x1=" + x1 +
                ", x2=" + x2 +
                ", y1=" + y1 +
                ", y2=" + y2 +
                ", playerNumber=" + playerNumber +
                ", numberOfSticks1=" + numberOfSticks1 +
                ", numberOfSticks2=" + numberOfSticks2 +
                '}';
    }
}
