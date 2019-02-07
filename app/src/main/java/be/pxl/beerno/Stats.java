package be.pxl.beerno;

public class Stats {

    private static int totalBeersDrank;

    public static void incrementBeersDrank() {
        totalBeersDrank++;
    }

    public static void decrementBeersDrank() {
        totalBeersDrank--;
        totalBeersDrank = Math.max(0, totalBeersDrank);
    }

    public static int getTotalBeersDrank() {
        return totalBeersDrank;
    }
}
