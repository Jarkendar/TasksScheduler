import java.util.Comparator;
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
        sortOfRatio(tasks);
        betterEarlier.sort(Comparator.comparingDouble(Task::getRatio));
        betterLater.sort(Comparator.comparingDouble(Task::getRatio));
        LinkedList<Task> result = new LinkedList<>(betterEarlier);
        result.addAll(result.size(), betterLater);
        return new Instance(result.toArray(new Task[0])).expand();
    }

    private void sortOfRatio(Task[] tasks){
        for (Task task : tasks) {
            if (task.getRatio() < 1.0) {
                betterEarlier.addFirst(task);
            } else {
                betterLater.addFirst(task);
            }
        }
    }
}
