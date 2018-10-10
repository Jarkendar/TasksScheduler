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

}
