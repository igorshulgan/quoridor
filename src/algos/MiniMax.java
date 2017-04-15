package algos;

import game.*;
import play.Play;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by ihar_shulhan on 01/04/2017.
 */
public class MiniMax extends Player {

    static int[] movesStickX = new int[] {2, -2, 0, 0};
    static int[] movesStickY = new int[] {0, 0, 2, -2};
    static int[] movesX = new int[] {1, -1, 0, 0};
    static int[] movesY = new int[] {0, 0, 1, -1};
    static int WRONG = -1000000000;
    private static int MIN = -212323400;

    protected int depth;

    public MiniMax (int depth) {
        this.depth = depth;
    }

    @Override
    public void move(Map map, int playerNumber) throws Exception {
        moveOneStep(map, playerNumber, depth, playerNumber);
    }

    public int checkMove(Map map, int playerNumber, int newX, int newY, int depth, int playerNumberOriginal) throws Exception {
        Player player = map.getPlayer(playerNumber);
        Player otherPlayer = map.getPlayer(playerNumber ^ 1);

        int oldX = player.getPosition().getX();
        int oldY = player.getPosition().getY();
        player.setPosition(newX, newY);
        Move move = null;
        int oldOX = otherPlayer.getPosition().getX();
        int oldOY = otherPlayer.getPosition().getY();
        if (depth > 0) {
            move = moveOneStep(map, playerNumber ^ 1, depth - 1, playerNumberOriginal);
        }
        int result = Evaluation.evaluate(map, playerNumberOriginal, 0);

        reverseMove(map, otherPlayer, move, oldOX, oldOY);
        player.setPosition(oldX, oldY);

        return result;
    }

    public int checkStickPlacement(Map map, int playerNumber, int x1, int y1, int x2, int y2, int depth, int playerNumberOrigninal) throws Exception {
        Player player = map.getPlayer(playerNumber);
        Player otherPlayer = map.getPlayer(playerNumber ^ 1);

        map.putStick(x1, y1, x2, y2);
        player.reduceNumberOfSticks();
        /* if (Evaluation.evaluate(map, playerNumber, 0) == WRONG) {
            map.deleteStick(x1, y1, x2, y2);
            player.addStick();
            return WRONG;
        }*/
        Move move = null;
        int oldX = otherPlayer.getPosition().getX();
        int oldY = otherPlayer.getPosition().getY();
        if (depth > 0) {
            move = moveOneStep(map, playerNumber ^ 1, depth - 1, playerNumberOrigninal);
        }
        int result = Evaluation.evaluate(map, playerNumberOrigninal, 0);

        reverseMove(map, otherPlayer, move, oldX, oldY);
        map.deleteStick(x1, y1, x2, y2);
        player.addStick();

        return result;
    }

    private Move moveOneStep(Map map, int playerNumber, int depth, int playerNumberOriginal) throws Exception  {
        int maxValue = MIN;
        Move move = null;
        Player player = map.getPlayer(playerNumber);
        Player otherPlayer = map.getPlayer(playerNumber ^ 1);

        if (player.getNumberOfSticks() > 0) {
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 17; j++) {
                    for (int i1 = 0; i1 < movesX.length; i1++) {
                        int x1 = i;
                        int x2 = i + movesStickX[i1];
                        int y1 = j;
                        int y2 = j + movesStickY[i1];

                        if (Stick.check(x1, y1) && Stick.check(x2, y2) && map.checkStick(x1, y1, x2, y2)) {
                            int result = checkStickPlacement(map, playerNumber, x1, y1, x2, y2, depth, playerNumberOriginal);
                            if (playerNumber != playerNumberOriginal && result != WRONG) result *= -1;
                            if (result != WRONG && result > maxValue)  {
                                maxValue = result;
                                move = new Move(x1, y1, x2, y2, result);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            int newX = player.getPosition().getX() + movesX[i];
            int newY = player.getPosition().getY() + movesY[i];
            int stickX = newX * 2 - movesX[i];
            int stickY = newY * 2 - movesY[i];

            if (Point.check(newX, newY) && !map.getSticks().contains(new Stick(stickX, stickY))) {
                if (otherPlayer.getPosition().getX() == newX && otherPlayer.getPosition().getY() == newY) {
                    int oldNewX = newX;
                    int oldNewY = newY;
                    for (int j = 0; j < 4; j++) {
                        newX = oldNewX +  movesX[j];
                        newY = oldNewY + movesY[j];
                        stickX = newX * 2 - movesX[j];
                        stickY = newY * 2 - movesY[j];

                        if (Stick.check(stickX, stickY) && Point.check(newX, newY)  && !map.getSticks().contains(new Stick(stickX, stickY))
                                && (i == j || !Stick.check(oldNewX * 2 + movesX[i], oldNewY * 2 + movesY[i]) || map.getSticks().contains(new Stick(oldNewX * 2 + movesX[i], oldNewY * 2 + movesY[i])))) {
                            int result = checkMove(map, playerNumber, newX, newY, depth, playerNumberOriginal);
                            if (playerNumber != playerNumberOriginal && result != WRONG) result *= -1;
                            if (result != WRONG && result > maxValue) {
                                maxValue = result;
                                move = new Move(newX, newY, result);
                            }
                        }
                    }
                } else {
                    int result = checkMove(map, playerNumber, newX, newY, depth, playerNumberOriginal);
                    if (playerNumber != playerNumberOriginal && result != WRONG) result *= -1;
                    if (result != WRONG && result > maxValue) {
                        maxValue = result;
                        move = new Move(newX, newY, result);
                    }
                }
            }
        }

        if (move != null) makeMove(map, move, player);
        return move;
    }
}


