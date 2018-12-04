import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class RandomScheduler extends Scheduler {

    public RandomScheduler(Instance originInstance) {
        super(originInstance);
    }

    @Override
    public Instance scheduleTask(double h) {
        Instance workInstance = getOriginInstance().clone();
        LinkedList<Task> workTask = new LinkedList<>(Arrays.asList(workInstance.getTasks()));
        Collections.shuffle(workTask);
        return new Instance(workTask.toArray(new Task[0]));
    }
}
