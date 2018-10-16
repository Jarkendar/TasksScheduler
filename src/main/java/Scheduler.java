public abstract class Scheduler {
    private Instance originInstance;

    public Scheduler(Instance originInstance) {
        this.originInstance = originInstance;
    }

    public Instance getOriginInstance() {
        return originInstance;
    }

    abstract public Instance scheduleTask(double h);
}
