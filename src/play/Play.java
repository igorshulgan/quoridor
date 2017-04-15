package play;

import algos.Human;
import algos.MiniMax;
import algos.MiniMaxAlphaBeta;
import algos.MonteCarlo;
import game.*;

import java.util.Date;
import java.util.Objects;

/**
 * Created by ihar_shulhan on 01/04/2017.
 */
public class Play {
    public static void main(String[] args) throws Exception {
        // MiniMax initiates with depth as a path param
        // 0 depth means check all my possible steps, 1 - my steps and steps of opposite player
        Player player1;
        Player player2;
        Map map;
        //MonteCarlo.train(1000); //number of training games
        int wins = 0;
        int loses = 0;

        Long now = new Date().getTime();
        for (int p = 0; p < 1; p++) {
            player1 = new MiniMaxAlphaBeta(0);
            player2 = new MiniMaxAlphaBeta(1);
            map = new Map(player1, player2);
            int i = 0;
            while (player1.getPosition().getX() != 8 && player2.getPosition().getX() != 0
                    && player1.getNumberOfMovesLeft() > 0 && player2.getNumberOfMovesLeft() > 0) {
                map.draw();
                Player player = i % 2 == 0 ? player1 : player2;
                int oldL = map.getSticks().size();
                player.move(map, i % 2);
                if (Evaluation.evaluate(map, i % 2, 0) == -1000000000) {
                    int a = 0;
                }
                System.out.println("Player " + (i % 2 + 1) + " moved to " + player.getPosition());
                for (int j = oldL; j < map.getSticks().size(); j++) {
                    System.out.print("New " + map.getSticks().get(j) + "\n");
                }
                i++;
                System.out.println(player1.getNumberOfMovesLeft() + " " + player2.getNumberOfMovesLeft());
            }
            map.draw();
            if (i % 2 == 0) {
                System.out.println("Player 2 won!");
                loses++;
            } else {
                System.out.println("Player 1 won!");
                wins++;
            }
            System.out.println("Sticks left " + (player1.getNumberOfSticks() + " " + player2.getNumberOfSticks()));
            System.out.println("Moves left " + (player1.getNumberOfMovesLeft() + " " + player2.getNumberOfMovesLeft()));
            System.out.println("Time " + (new Date().getTime() - now));
        }
        System.out.println("Wins " + wins + " Loses " + loses);

    }
}
