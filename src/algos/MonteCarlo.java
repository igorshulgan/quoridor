package algos;

import game.*;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Double.NaN;

/**
 * Created by ihar_shulhan on 06/04/2017.
 */

public class MonteCarlo extends Player {
    static int[] movesStickX = new int[] {2, -2, 0, 0};
    static int[] movesStickY = new int[] {0, 0, 2, -2};
    static int[] movesX = new int[] {1, -1, 0, 0};
    static int[] movesY = new int[] {0, 0, 1, -1};
    static int WRONG = -1000000000;
    private static int MIN = -212323400;

    private static HashMap<SavedMap, MapPlayed> savedStates = SavedStates.savedStates;
    private static HashMap<SavedMap, Boolean> visited;
    private static HashMap<SavedMap, ArrayList<Move>> savedMoves = SavedStates.savedMoves;

    public MonteCarlo() {
        visited = new HashMap<>();
    }

    public static void train(int games) throws Exception {
        Player player1 = new MonteCarlo();
        Player player2 = new MonteCarlo();
        Map map = new Map(player1, player2);

        for (int i = 0; i < games; i++) {
            MonteCarlo.simulation(map, 0);
            if (i % 2 == 0) {
                player1 = new MiniMaxAlphaBeta(1);
                player2 = new MonteCarlo();
            } else {
                player1 = new MonteCarlo();
                player2 = new MiniMaxAlphaBeta(1);
            }

            map = new Map(player1, player2);
        }
    }

    private static double evaluateState(MapPlayed mapPlayed, double totalGames) {
        double result = (double) mapPlayed.wins / (double) (mapPlayed.wins + mapPlayed.loses)
                + Math.sqrt(totalGames * 2 / (double) (mapPlayed.loses + mapPlayed.wins));
        if (mapPlayed.loses + mapPlayed.wins == 0) {
            result = 1;
        }
        return result;
    }

    @Override
    public void move(Map map, int playerNumber) throws Exception {
        moveOneStep(map, playerNumber, 0);
    }

    private static void moveOneStep(Map map, int playerNumber, int experiments) throws Exception {
        SavedMap currentMap = new SavedMap(map, playerNumber ^ 1);
        MapPlayed currentMapPlayed = savedStates.getOrDefault(currentMap, new MapPlayed());
        double maxValue = -1000000000;
        Move move = null;
        Player player = map.getPlayer(playerNumber);

        ArrayList<Move> moves = savedMoves.get(currentMap);
        if (moves == null) {
            moves = new ArrayList<>();
            move = MiniMaxAlphaBeta.moveOneStep(map, playerNumber, 0, 0, MIN, -MIN, -MIN);
            moves.add(move);
            savedMoves.put(currentMap, moves);
            return;
        } else {
            for (Move move1 : moves) {
                int oldX = player.getPosition().getX();
                int oldY = player.getPosition().getY();
                makeMove(map, move1, player);
                SavedMap newMap = new SavedMap(map, playerNumber);
                //if (visited.get(newMap) != null) {
                 //   reverseMove(map, player, move1, oldX, oldY);
                 //   continue;
                //}
                MapPlayed mapPlayed = savedStates.get(newMap);
                if (mapPlayed == null) {
                    mapPlayed = new MapPlayed();
                }
                double value = evaluateState(mapPlayed, currentMapPlayed.wins + currentMapPlayed.loses);
                if (value >= maxValue && Evaluation.evaluate(map, playerNumber, 0) != WRONG) {
                    move = move1;
                }
                reverseMove(map, player, move1, oldX, oldY);
            }
        }

        if (move != null) {
            int oldX = player.getPosition().getX();
            int oldY = player.getPosition().getY();
            makeMove(map, move, player);
            MapPlayed mapPlayed = savedStates.get(new SavedMap(map, playerNumber));
            if (mapPlayed != null && (mapPlayed.loses + mapPlayed.wins) % 3 == 0 && experiments == 1) {
                reverseMove(map, player, move, oldX, oldY);
                Move move1 = MiniMaxAlphaBeta.moveOneStep(map, playerNumber, 0, 0, MIN, -MIN, move.getResult());
                if (move1 == null) {
                    reverseMove(map, player, move1, oldX, oldY);
                    makeMove(map, move, player);
                }
            }
        } else {
            throw new Exception("Can't move");
        }
        //visited.put(new SavedMap(map, playerNumber), true);
    }

    public static int simulation(Map map, int playerNumber) throws Exception {
        Player player1 = map.getPlayer(0);
        Player player2 = map.getPlayer(1);

        if (player1.getPosition().getX() != 8 && player2.getPosition().getX() != 0
                && player1.getNumberOfMovesLeft() > 0 && player2.getNumberOfMovesLeft() > 0) {
            SavedMap savedMap = new SavedMap(map, playerNumber ^ 1);
            MapPlayed mapPlayed = savedStates.get(savedMap);
            if (mapPlayed == null) {
                mapPlayed = new MapPlayed();
                savedStates.put(savedMap, mapPlayed);
            }
            moveOneStep(map, playerNumber, 1);
            int result = simulation(map, playerNumber ^ 1);
            if (result == 1) {
                mapPlayed.won();
            } else {
                mapPlayed.lost();
            }
            savedStates.replace(savedMap, mapPlayed);
        }

        SavedMap savedMap = new SavedMap(map, playerNumber);
        MapPlayed mapPlayed = savedStates.get(savedMap);
        if (mapPlayed == null) {
            mapPlayed = new MapPlayed();
        }
        int result = 1;
        if (player1.getPosition().getX() == 8 && player1.getNumberOfMovesLeft() > 0) {
            if (playerNumber == 1) {
                mapPlayed.won();
            } else {
                result = -1;
                mapPlayed.lost();
            }

        } else {
            if (playerNumber == 1) {
                result = -1;
                mapPlayed.lost();
            } else {
                mapPlayed.won();
            }
        }
        savedStates.replace(savedMap, mapPlayed);
        visited.clear();
        return result;
    }


}

