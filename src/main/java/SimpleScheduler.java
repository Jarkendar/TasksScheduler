public class SimpleScheduler extends Scheduler{

    public SimpleScheduler(Instance instance){
        super(instance);
    }

    public Instance scheduleTask(){
        Instance workInstance = getOriginInstance().clone();
        for (int i = 1; i < workInstance.getSizeInstance(); ++i){
            workInstance.getTaskOnIndex(i).setTimeStart(workInstance.getTaskOnIndex(i-1).getTimeEnd());
        }
        return workInstance;
    }
}