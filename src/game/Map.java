package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by ihar_shulhan on 24/03/2017.
 */
public class Map {
    Player players[] = new Player[2];
    ArrayList<Stick> sticks = new ArrayList<>();


    public Map(Player playerOne, Player playerTwo) throws Exception {
        this.players[0] = playerOne;
        this.players[1] = playerTwo;

        playerOne.setPosition(0, 4);
        playerTwo.setPosition(8, 4);
    }

    public void putStick(int x1, int y1, int x2, int y2) throws Exception {
        if (!Stick.check(x1, y1) || !Stick.check(x2, y2) || !checkStick(x1, y1, x2, y2)) {
            throw new Exception("Stick is wrong");
        }
        sticks.add(new Stick(x1, y1));
        try {
            sticks.add(new Stick(x2, y2, sticks.get(sticks.size() - 1).id));
        } catch (Exception e) {
            sticks.remove(new Stick(x1, y1));
            throw e;
        }
    }

    public boolean checkStick(int x1, int y1, int x2, int y2) throws Exception {

        boolean horizontal = x1 % 2 == 1 && x1 == x2 && (y2 - y1) * (y2 - y1) == 4;
        boolean horizontalStickDoesntHaveUpperPart = !Stick.check(x1 + 1, (y1 + y2) / 2) || !sticks.contains(new Stick(x1 + 1, (y1 + y2) / 2));
        boolean horizontalStickDoesntHaveLowerPart = !Stick.check(x1 - 1, (y1 + y2) / 2) || !sticks.contains(new Stick(x1 - 1, (y1 + y2) / 2));
        boolean horizontalStickIdsAreTheSame = !horizontalStickDoesntHaveLowerPart && !horizontalStickDoesntHaveUpperPart && sticks.get(sticks.indexOf(new Stick(x1 - 1, (y1 + y2) / 2))).getId() == sticks.get(sticks.indexOf(new Stick(x1 - 1, (y1 + y2) / 2))).getId();

        boolean vertical = y1 % 2 == 1 && y1 == y2 && (x2 - x1) * (x2 - x1) == 4;
        boolean verticalStickDoesntHaveUpperPart = !Stick.check((x1 + x2) / 2, y1 + 1) || !sticks.contains(new Stick((x1 + x2) / 2, y1 + 1));
        boolean verticalStickDoesntHaveLowerPart = !Stick.check((x1 + x2) / 2, y1 - 1) || !sticks.contains(new Stick((x1 + x2) / 2, y1 - 1));
        boolean verticalStickIdsAreTheSame = !verticalStickDoesntHaveLowerPart && !verticalStickDoesntHaveUpperPart && sticks.get(sticks.indexOf(new Stick((x1 + x2) / 2, y1 + 1))).getId() == sticks.get(sticks.indexOf(new Stick((x1 + x2) / 2, y1 - 1))).getId();

        return (((horizontal && (horizontalStickDoesntHaveLowerPart || horizontalStickDoesntHaveUpperPart || !horizontalStickIdsAreTheSame))
                    || (vertical && (verticalStickDoesntHaveLowerPart || verticalStickDoesntHaveUpperPart || !verticalStickIdsAreTheSame)))
                && !sticks.contains(new Stick(x1, y1)) && !sticks.contains(new Stick(x2, y2)));
    }
    public void deleteStick(int x1, int y1, int x2, int y2) throws Exception {
        if (!((x1 == x2 && (y2 - y1) * (y2 - y1) == 4)
                || (y1 == y2 && (x2 - x1) * (x2 - x1) == 4))) {
            throw new Exception("Stick can't be deleted");
        }
        sticks.remove(new Stick(x1, y1));
        sticks.remove(new Stick(x2, y2));
    }

    public Player getPlayer(int x) {
        return players[x];
    }

    public ArrayList<Stick> getSticks() {
        return sticks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Map map = (Map) o;

        if (!Arrays.deepEquals(players, map.players)) return false;
        return Arrays.deepEquals(sticks.toArray(), map.sticks.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(players, sticks);
    }

    public void draw() throws Exception {
        for (int i = 16; i >= 0; i--) {
            if (i % 2 == 0)
                System.out.print(Integer.toString(i / 2) + '|');
            else
                System.out.print(" |");
            for (int j = 0; j < 17; j++) {
                if (Stick.check(i, j) && sticks.contains(new Stick(i, j))) {
                    if (i % 2 == 1)
                        System.out.print('-');
                    else
                        System.out.print('|');
                } else {
                    if (i % 2 == 0 && j % 2 == 0) {
                        if (players[0].getPosition().getX() == i / 2 && players[0].getPosition().getY() == j / 2) {
                            System.out.print('X');
                        } else {
                            if (players[1].getPosition().getX() == i / 2 && players[1].getPosition().getY() == j / 2) {
                                System.out.print('O');
                            } else {
                                System.out.print(' ');
                            }
                        }
                    } else {
                        System.out.print(' ');
                    }
                }
            }
            System.out.println("|");
        }
        System.out.println("  0 1 2 3 4 5 6 7 8");
    }
}
