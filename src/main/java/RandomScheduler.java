import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class RandomScheduler extends Scheduler {

    public RandomScheduler(Instance originInstance) {
        super(originInstance);
    }

    @Override
    public Instance scheduleTask(double h) {
        Instance workInstance = getOriginInstance().clone();
        LinkedList<Task> workTask = new LinkedList<>(Arrays.asList(workInstance.getTasks()));
        LinkedList<Task> tasks = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < workInstance.getSizeInstance(); ++i){
            int position = random.nextInt(workTask.size());
            tasks.addFirst(workTask.get(position));
            workTask.remove(position);
        }
        return new Instance(tasks.toArray(new Task[0])).expand();
    }
}
