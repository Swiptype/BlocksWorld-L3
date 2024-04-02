package blocksworld.main;

public class TimeTaken {
    
    private long startTime;
    private long endTime;

    public TimeTaken() {
        this.startTime = 0;
        this.endTime = 0;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void end(boolean show) {
        this.endTime = System.currentTimeMillis();
        if (show) System.out.println(endTime-startTime);
    }

    public void end() {
        this.end(false);
    }

    public long showMillisTaken() {
        return this.endTime-this.startTime;
    }

    public double getSecondTaken() {
        return ((double) this.endTime-this.startTime) / 1000;
    }

    public double getMinuteTaken() {
        return this.getSecondTaken() / 60;
    }

    public double getHourTaken() {
        return this.getMinuteTaken() / 60;
    }

}
