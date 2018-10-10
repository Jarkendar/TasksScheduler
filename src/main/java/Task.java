public class Task {
    private int duration;
    private int tooEarlyMultiplier;
    private int tooLateMultiplier;
    private int timeStart;

    public Task(int duration, int tooEarlyMultiplier, int tooLateMultiplier) {
        this.duration = duration;
        this.tooEarlyMultiplier = tooEarlyMultiplier;
        this.tooLateMultiplier = tooLateMultiplier;
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

    public Task clone(){
        return new Task(this.duration, this.tooEarlyMultiplier, this.tooLateMultiplier);
    }

    @Override
    public String toString() {
        return "Task{" + duration +
                ";" + tooEarlyMultiplier +
                ";" + tooLateMultiplier +
                ";" + timeStart +
                ";" + getTimeEnd() +
                '}';
    }
}
