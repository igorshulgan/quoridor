package game;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by ihar_shulhan on 01/04/2017.
 */
public class Evaluation {

    private static int[] movesX = new int[] {1, -1, 0, 0};
    private static int[] movesY = new int[] {0, 0, 1, -1};
    private static int WRONG = -1000000000;
    private static int MIN = -112323400;
    private static int MAX = -MIN;
    // private static SavedCalculations calculations = SavedCalculations.getInstance();

    public static int evaluate(Map map, int playerNumber, int first) throws Exception {
        Player player = map.players[playerNumber];
        Player otherPlayer = map.players[playerNumber ^ 1];
        int numberOfPaths = 0;
        int shortestPath = 0;

        ArrayList<Point> queue = new ArrayList<>();
        queue.add(new Point(player.getPosition(), first ^ 1 - 1));
        boolean visited[][] = new boolean[9][9];
        Integer result = null; // first == 0 ? null : calculations.getMapResult(map, playerNumber);
        if (result != null) {
            return result;
        } else {
            result = 0;
        }

        int counter = 0;
        visited[player.getPosition().getX()][player.getPosition().getY()] = true;
        while (counter < queue.size()) {

            Point point = queue.get(counter);
            counter++;

            if (numberOfPaths != 0 && (player.getNumberOfMovesLeft() - point.getValue() < 0 || numberOfPaths > 20)) {
                break;
            }

            if ((playerNumber == 0 && point.getX() == 8) || (playerNumber == 1 && point.getX() == 0)) {
                //if (numberOfPaths == 0) {
                    if (point.getValue() > player.getNumberOfMovesLeft() && numberOfPaths == 0) return MIN;
                    if (point.getValue() == 0) return MAX;
                    int freeMoves = player.getNumberOfMovesLeft() - point.getValue();
                    freeMoves *= freeMoves;
                    result += freeMoves - point.getValue();
                    shortestPath = point.getValue();
                    if (numberOfPaths == 0) result -= point.getValue() * point.getValue() * point.getValue() * 100;
                    numberOfPaths++;
                //} else {
                //    if (point.getValue() < player.getNumberOfMovesLeft())
                //        result--;
                //}
                continue;
            }


            for (int i = 0; i < 4; i++) {
                int newX = point.getX() + movesX[i];
                int newY = point.getY() + movesY[i];
                int stickX = point.getX() * 2 + movesX[i];
                int stickY = point.getY() * 2 + movesY[i];

                if (Point.check(newX, newY) && !visited[newX][newY] && !map.sticks.contains(new Stick(stickX, stickY))) {
                    int oldNewX = newX;
                    int oldNewY = newY;
                    if (otherPlayer.getPosition().getX() == newX && otherPlayer.getPosition().getY() == newY) {
                        visited[newX][newY] = true;
                        queue.add(new Point(newX, newY, point.getValue() + 2));
                        for (int j = 0; j < 4; j++) {
                            newX = oldNewX +  movesX[j];
                            newY = oldNewY + movesY[j];
                            stickX = newX * 2 - movesX[j];
                            stickY = newY * 2 - movesY[j];

                            if (Stick.check(stickX, stickY) && Point.check(newX, newY) && !visited[newX][newY] && !map.sticks.contains(new Stick(stickX, stickY))
                                    && (i == j || !Stick.check(oldNewX * 2 + movesX[i], oldNewY * 2 + movesY[i]) || map.sticks.contains(new Stick(oldNewX * 2 + movesX[i], oldNewY * 2 + movesY[i])))) {
                                visited[newX][newY] = true;
                                queue.add(new Point(newX, newY, point.getValue() + 1));
                            }
                        }
                    } else {
                        visited[newX][newY] = true;
                        queue.add(new Point(newX, newY, point.getValue() + 1));
                    }
                }
            }
        }
        if (numberOfPaths == 0) return WRONG;
        int shortestMoves = otherPlayer.getNumberOfMovesLeft() - shortestPath;
        result += Integer.max(0, (player.getNumberOfSticks() - 1) * shortestMoves * shortestMoves);
        int result2 = 0;
        if (first == 0) result2 = evaluate(map, playerNumber ^ 1, 1);
        if (result2 == WRONG) {
            // calculations.addMap(map, playerNumber, result2);
            return result2;
        }
        // if (first == 1) calculations.addMap(map, playerNumber, result);
        return result - result2;
    }
}
