public class Task {
    private int id;
    private int duration;
    private int tooEarlyMultiplier;
    private int tooLateMultiplier;
    private int timeStart;
    private double ratio;
    private double earlyToDuration;
    private double lateToDuration;

    public Task(int id, int duration, int tooEarlyMultiplier, int tooLateMultiplier) {
        this.id = id;
        this.duration = duration;
        this.tooEarlyMultiplier = tooEarlyMultiplier;
        this.tooLateMultiplier = tooLateMultiplier;
        ratio = ((double)tooEarlyMultiplier)/((double) tooLateMultiplier);
        earlyToDuration = ((double) tooEarlyMultiplier)/((double) duration);
        lateToDuration = ((double) tooLateMultiplier)/((double) duration);
    }

    public double getEarlyToDuration() {
        return earlyToDuration;
    }

    public double getLateToDuration() {
        return lateToDuration;
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public int getTooEarlyMultiplier() {
        return tooEarlyMultiplier;
    }

    public int getTooLateMultiplier() {
        return tooLateMultiplier;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    public int getTimeEnd() {
        return timeStart + duration;
    }

    public double getRatio() {
        return ratio;
    }

    public Task clone(){
        return new Task(this.id, this.duration, this.tooEarlyMultiplier, this.tooLateMultiplier);
    }

    @Override
    public String toString() {
        return "Task{" + id +
                ";" + duration +
                ";" + tooEarlyMultiplier +
                ";" + tooLateMultiplier +
                ";" + timeStart +
                ";" + getTimeEnd() +
                '}';
    }
}
