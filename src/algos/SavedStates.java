package algos;

import game.Move;
import game.SavedMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ihar_shulhan on 07/04/2017.
 */
public class SavedStates {
    private static SavedStates ourInstance = new SavedStates();

    public static SavedStates getInstance() {
        return ourInstance;
    }

    private SavedStates() {
        savedStates = new HashMap<>();
        savedMoves = new HashMap<>();
    }

    public static HashMap<SavedMap, MapPlayed> savedStates;
    public static HashMap<SavedMap, ArrayList<Move>> savedMoves;
}
