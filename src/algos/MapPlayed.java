package algos;

public class MapPlayed {
    int wins;
    int loses;

    public MapPlayed(int wins, int loses, int totalGames) {
        this.wins = wins;
        this.loses = loses;
    }
    public MapPlayed() {
        wins = 0;
        loses = 0;
    }

    public void won() {
        wins++;
    }

    public void lost() {
        loses++;
    }
}
