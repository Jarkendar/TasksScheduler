import java.util.LinkedList;
import java.util.Random;

public class SimpleScheduler {
    private Instance instance;

    public SimpleScheduler(Instance instance){
        this.instance = instance;
    }

    public Instance scheduleTask(){
        Instance workInstance = instance.clone();
        for (int i = 1; i < workInstance.getSizeInstance(); ++i){
            workInstance.getTaskOnIndex(i).setTimeStart(workInstance.getTaskOnIndex(i-1).getTimeEnd());
        }
        return workInstance;
    }

    public Instance randomScheduler(){
        Instance workInstance = instance.clone();
        LinkedList<Task> tasks = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < instance.getSizeInstance(); ++i){
            tasks.add(random.nextInt(tasks.size()+1), workInstance.getTaskOnIndex(i));
        }
        return new Instance(tasks.toArray(new Task[0])).expand();
    }

}