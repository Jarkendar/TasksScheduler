import java.util.Arrays;

public class Instance {
    private Task[] tasks;

    public Instance(int size){
        tasks = new Task[size];
    }

    public void addLast(Task newTask){
        for (int i = 0; i< tasks.length; ++i){
            if (!isTaskExist(tasks[i])){
                tasks[i] = newTask;
                break;
            }
        }
    }

    public int getDurationSum() {
        int sum = 0;
        for (Task task : tasks) {
            if (isTaskExist(task)) {
                sum += task.getDuration();
            }else {
                return -1;
            }
        }
        return sum;
    }

    public boolean isInstanceCorrect(){
        for (int i = 1; i<tasks.length; ++i){
            if (tasks[i] == null || tasks[i-1] == null){
                return false;
            }
            if (tasks[i-1].getTimeEnd() > tasks[i].getTimeStart()){
                return false;
            }
        }
        return true;
    }

    public Instance clone(){
        Instance cloneInstance = new Instance(this.tasks.length);
        for (Task task : this.tasks){
            cloneInstance.addLast(task.clone());
        }
        return cloneInstance;
    }

    private boolean isTaskExist(Task task){
        return task != null;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public Task getTaskOnIndex(int index){
        if (index >= tasks.length || index < 0){
            return null;
        }
        return tasks[index];
    }

    public int getSizeInstance(){
        return tasks.length;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "tasks=" + Arrays.toString(tasks) +
                '}';
    }
}
