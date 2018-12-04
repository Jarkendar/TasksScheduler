import java.util.Random;

public class MutatorScheduler extends Scheduler{

    private int numberOfMutation;

    public MutatorScheduler(Instance originInstance, int numberOfMutation) {
        super(originInstance);
        this.numberOfMutation = numberOfMutation;
    }

    @Override
    public Instance scheduleTask(double h) {
        Instance workInstance = getOriginInstance().clone();
        Random random = new Random();
        for (int i = 0; i< numberOfMutation; ++i){
            int x = random.nextInt(workInstance.getSizeInstance());
            int y = random.nextInt(workInstance.getSizeInstance());
            while (x == y){
                y = random.nextInt(workInstance.getSizeInstance());
            }
            workInstance.swapTasks(x,y);
        }
        return workInstance.expand();
    }
}
