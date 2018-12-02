import java.util.LinkedList;

public class RatioScheduler extends Scheduler {

    private LinkedList<Task> betterEarlier;
    private LinkedList<Task> betterLater;

    public RatioScheduler(Instance originInstance) {
        super(originInstance);
        betterEarlier = new LinkedList<>();
        betterLater = new LinkedList<>();
    }

    @Override
    public Instance scheduleTask(double h) {
        Task[] tasks = getOriginInstance().getTasks();
        int boundary = (int) (getOriginInstance().getDurationSum() * h);
        sortOfRatio(tasks, boundary);
        betterEarlier.sort((p1, p2) -> {
            if (p1.getEarlyToDuration() == p2.getEarlyToDuration()){
                if (p1.getTooEarlyMultiplier() == p2.getTooEarlyMultiplier()) {
                    return -Integer.compare(p1.getDuration(), p2.getDuration());
                } else {
                    return Integer.compare(p1.getTooEarlyMultiplier(), p2.getTooEarlyMultiplier());
                }
            }else {
                return Double.compare(p1.getEarlyToDuration(), p2.getEarlyToDuration());
            }
        });
        betterLater.sort((p1, p2) -> {
            if (p1.getLateToDuration() == p2.getLateToDuration()){
                if (p1.getTooLateMultiplier() == p2.getTooLateMultiplier()) {
                    return Integer.compare(p1.getDuration(), p2.getDuration());
                } else {
                    return -Integer.compare(p1.getTooLateMultiplier(), p2.getTooLateMultiplier());
                }
            }else {
                return Double.compare(-p1.getLateToDuration(), -p2.getLateToDuration());
            }

        });
        LinkedList<Task> result = new LinkedList<>(betterEarlier);
        result.addAll(result.size(), betterLater);
        Instance resultInstance = new Instance(result.toArray(new Task[0])).expand();
        return canSwapNeighbourTask(Shifter.shiftStartPoint(resultInstance, boundary), boundary);
    }

    private void sortOfRatio(Task[] tasks, int border) {
        for (Task task : tasks) {
            if (task.getRatio() < 1.0) {
                betterEarlier.addFirst(task);
            } else {
                betterLater.addFirst(task);
            }
        }
    }

    private Instance canSwapNeighbourTask(Instance instance, int boundary) {
        for (int i = 1; i < instance.getSizeInstance() - 1; i++) {
            Instance tmp = instance.clone();
            tmp.swapTasks(i - 1, i);
            if (instance.calcCost(boundary) > tmp.expand().calcCost(boundary)) {
                instance = tmp;
            }
        }
        return instance;
    }
}
