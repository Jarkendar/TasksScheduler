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
        sortOfRatio(tasks, getOriginInstance().getDurationSum());
        betterEarlier.sort(Comparator.comparingDouble(Task::getTooEarlyMultiplier));
        betterLater.sort(Comparator.comparingDouble(Task::getTooLateMultiplier).reversed());
        LinkedList<Task> result = new LinkedList<>(betterEarlier);
        result.addAll(result.size(), betterLater);
        Instance resultInstance = new Instance(result.toArray(new Task[0])).expand();
        canSwapNeighbourTask(resultInstance, getOriginInstance().getDurationSum());
        return resultInstance;
    }

    private void sortOfRatio(Task[] tasks, int border){
        for (Task task : tasks) {
            if (task.getRatio() < 1.0) {
                if (fitBeforeBorder(border, task)) {
                    betterEarlier.addFirst(task);
                }else {
                    betterLater.addFirst(task);
                }
            } else {
                betterLater.addFirst(task);
            }
        }
    }

    private void canSwapNeighbourTask(Instance instance, int boundary){
        for (int i = 1;  i <  instance.getSizeInstance()-1; i++){
            Instance tmp = instance.clone();
            tmp.swapTasks(i-1, i);
            if (instance.calcCost(boundary) > tmp.expand().calcCost(boundary)){
                instance = tmp;
            }
        }
    }

    private boolean fitBeforeBorder(int border, Task task){
        int sum = 0;
        for (Task t : betterEarlier){
            sum += t.getDuration();
        }
        return sum + task.getDuration() <= border;
    }
}
