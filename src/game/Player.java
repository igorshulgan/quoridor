package game;

import jdk.nashorn.internal.ir.ObjectNode;
import play.Play;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by ihar_shulhan on 24/03/2017.
 */
public abstract class Player {

    private Point position;
    private int numberOfSticks = 10;
    private int numberOfMovesLeft = 100;

    public void setPosition(int x, int y) throws Exception {
        this.position = new Point(x, y);
    }

    abstract public void move(Map map, int playerNumber) throws Exception;

    public Point getPosition() {
        return position;
    }

    public int getNumberOfSticks() {
        return numberOfSticks;
    }

    public void reduceNumberOfSticks() throws Exception {
        if (numberOfSticks == 0) throw new Exception("Number of sticks below zero");
        numberOfSticks--;
    }

    public void addStick() throws Exception {
        if (numberOfSticks == 10) throw new Exception("Number of sticks greater than 10");
        numberOfSticks++;
    }

    public int getNumberOfMovesLeft() {
        return numberOfMovesLeft;
    }

    public void reduceNumberOfMoves() {
        numberOfMovesLeft--;
    }

    public void addNumberOfMoves() {
        numberOfMovesLeft++;
    }

    public static void makeMove(Map map, Move move, Player player) throws Exception {
        if (move.getType() == 1) {
            map.putStick(move.getX1(), move.getY1(), move.getX2(), move.getY2());
            player.reduceNumberOfSticks();
        } else {
            player.setPosition(move.getX(), move.getY());
            player.reduceNumberOfMoves();
        }
    }

    public static void reverseMove(Map map, Player player, Move move, int oldX, int oldY) throws Exception {
        if (move != null) {
            if (move.getType() == 1) {
                map.deleteStick(move.getX1(), move.getY1(), move.getX2(), move.getY2());
                player.addStick();
            } else {
                player.setPosition(oldX, oldY);
                player.addNumberOfMoves();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return Objects.deepEquals(position, player.position) && numberOfSticks == player.numberOfSticks;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, numberOfSticks);
    }
}
