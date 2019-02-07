package be.pxl.beerno;

public class Stats {

    private int rewardPoints;
    private int totalBeers;
    private long timeTaken;

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public int getTotalBeers() {
        return totalBeers;
    }

    public void setTotalBeers(int totalBeers) {
        this.totalBeers = totalBeers;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "rewardPoints=" + rewardPoints +
                ", totalBeers=" + totalBeers +
                ", timeTaken=" + timeTaken +
                '}';
    }
}
