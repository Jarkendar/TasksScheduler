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
            int index1 = random.nextInt(workInstance.getSizeInstance());
            int index2 = random.nextInt(workInstance.getSizeInstance());
            while (index1 == index2){
                index2 = random.nextInt(workInstance.getSizeInstance());
            }
            workInstance.swapTasks(index1,index2);
        }
        return workInstance.expand();
    }
}
