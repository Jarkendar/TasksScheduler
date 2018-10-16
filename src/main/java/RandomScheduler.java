import java.util.LinkedList;
import java.util.Random;

public class RandomScheduler extends Scheduler {

    public RandomScheduler(Instance originInstance) {
        super(originInstance);
    }

    @Override
    public Instance scheduleTask(double h) {
        Instance workInstance = getOriginInstance().clone();
        LinkedList<Task> tasks = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < workInstance.getSizeInstance(); ++i){
            tasks.add(random.nextInt(tasks.size()+1), workInstance.getTaskOnIndex(i));
        }
        return new Instance(tasks.toArray(new Task[0])).expand();
    }
}
