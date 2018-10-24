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
        int boundary = (int)(getOriginInstance().getDurationSum()*h);
        sortOfRatio(tasks, boundary);
        betterEarlier.sort(Comparator.comparingDouble(Task::getTooEarlyMultiplier));
        betterLater.sort(Comparator.comparingDouble(Task::getTooLateMultiplier).reversed());
        LinkedList<Task> result = new LinkedList<>(betterEarlier);
        result.addAll(result.size(), betterLater);
        Instance resultInstance = new Instance(result.toArray(new Task[0])).expand();
        canSwapNeighbourTask(resultInstance, boundary);
        return canSwapNeighbourTask(shiftStartPoint(resultInstance, boundary), boundary);
    }

    private void sortOfRatio(Task[] tasks, int border){
        for (Task task : tasks) {
            if (task.getRatio() < 1.0) {
//                if (fitBeforeBorder(border, task)) {
                    betterEarlier.addFirst(task);
//                }else {
//                    betterLater.addFirst(task);
//                }
            } else {
                betterLater.addFirst(task);
            }
        }
    }

    private Instance canSwapNeighbourTask(Instance instance, int boundary){
        for (int i = 1;  i <  instance.getSizeInstance()-1; i++){
            Instance tmp = instance.clone();
            tmp.swapTasks(i-1, i);
            if (instance.calcCost(boundary) > tmp.expand().calcCost(boundary)){
                instance = tmp;
            }
        }
        return instance;
    }

    private Instance shiftStartPoint(Instance instance, int boundary){
        int bestStartPoint = 0;
        int currentResult = instance.calcCost(boundary);
        instance.setStartPoint(bestStartPoint+1);
        instance.expand();
        while (instance.calcCost(boundary) < currentResult){
            bestStartPoint = instance.getStartPoint();
            currentResult = instance.calcCost(boundary);
            instance.setStartPoint(bestStartPoint+1);
            instance.expand();
        }
        instance.setStartPoint(bestStartPoint);
        return instance.expand();
    }
}
