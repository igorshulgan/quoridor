package algos;

import game.Map;
import game.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by ihar_shulhan on 07/04/2017.
 */
public class Human extends Player{
    @Override
    public void move(Map map, int playerNumber) throws Exception {
        Scanner in = new Scanner(System.in);
        int stick = in.nextInt();
        if (stick == 1) {
            int x1 = in.nextInt();
            int y1 = in.nextInt();
            int x2 = in.nextInt();
            int y2 = in.nextInt();
            map.putStick(x1, y1, x2, y2);
            reduceNumberOfSticks();
        } else {
            int x1 = in.nextInt();
            int y1 = in.nextInt();
            setPosition(x1, y1);
            reduceNumberOfMoves();
        }

    }
}
