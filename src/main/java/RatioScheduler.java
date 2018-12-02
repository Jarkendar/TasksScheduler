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
        Task[] tasksWithout = getOriginInstance().clone().getTasks();
        int boundary = (int) (getOriginInstance().getDurationSum() * h);

        sortOfRatio(tasks);
        betterEarlier.sort((p1, p2) -> {
            if (p1.getEarlyToDuration() == p2.getEarlyToDuration()) {
                if (p1.getTooEarlyMultiplier() == p2.getTooEarlyMultiplier()) {
                    return -Integer.compare(p1.getDuration(), p2.getDuration());
                } else {
                    return Integer.compare(p1.getTooEarlyMultiplier(), p2.getTooEarlyMultiplier());
                }
            } else {
                return Double.compare(p1.getEarlyToDuration(), p2.getEarlyToDuration());
            }
        });
        betterLater.sort((p1, p2) -> {
            if (p1.getLateToDuration() == p2.getLateToDuration()) {
                if (p1.getTooLateMultiplier() == p2.getTooLateMultiplier()) {
                    return Integer.compare(p1.getDuration(), p2.getDuration());
                } else {
                    return -Integer.compare(p1.getTooLateMultiplier(), p2.getTooLateMultiplier());
                }
            } else {
                return Double.compare(-p1.getLateToDuration(), -p2.getLateToDuration());
            }
        });
        LinkedList<Task> resultCostDuration = new LinkedList<>(betterEarlier);
        resultCostDuration.addAll(resultCostDuration.size(), betterLater);
        Instance instanceCostDuration = new Instance(resultCostDuration.toArray(new Task[0])).expand();
        Instance swapCostDuration = canSwapNeighbourTask(Shifter.shiftStartPoint(instanceCostDuration, boundary), boundary);

        betterEarlier.clear();
        betterLater.clear();
        sortOfRatio(tasksWithout);
        betterEarlier.sort((p1, p2) -> {
            if (p1.getTooEarlyMultiplier() == p2.getTooEarlyMultiplier()) {
                return -Integer.compare(p1.getDuration(), p2.getDuration());
            } else {
                return Integer.compare(p1.getTooEarlyMultiplier(), p2.getTooEarlyMultiplier());
            }
        });
        betterLater.sort((p1, p2) -> {
            if (p1.getTooLateMultiplier() == p2.getTooLateMultiplier()) {
                return Integer.compare(p1.getDuration(), p2.getDuration());
            } else {
                return -Integer.compare(p1.getTooLateMultiplier(), p2.getTooLateMultiplier());
            }
        });
        LinkedList<Task> resultWithout = new LinkedList<>(betterEarlier);
        resultWithout.addAll(resultWithout.size(), betterLater);
        Instance instanceWithout = new Instance(resultWithout.toArray(new Task[0])).expand();
        Instance swapWithout = canSwapNeighbourTask(Shifter.shiftStartPoint(instanceWithout, boundary), boundary);

        return swapCostDuration.calcCost(boundary) < swapWithout.calcCost(boundary) ? swapCostDuration : swapWithout;
    }

    private void sortOfRatio(Task[] tasks) {
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
