import java.util.Arrays;

public class Instance {
    private Task[] tasks;
    private int startPoint = 0;
    private int currentCost;
    private boolean costIsCorrect = false;

    public Instance(int size) {
        tasks = new Task[size];
    }

    public Instance(Task[] tasks){
        this.tasks = tasks;
    }

    public Instance(Task[] tasks, int currentCost, boolean costIsCorrect){
        this.tasks = tasks;
        this.currentCost = currentCost;
        this.costIsCorrect = costIsCorrect;
    }

    public void addLast(Task newTask) {
        for (int i = 0; i < tasks.length; ++i) {
            if (!isTaskExist(tasks[i])) {
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
            } else {
                return -1;
            }
        }
        return sum;
    }

    public boolean isCorrect() {
        for (int i = 1; i < tasks.length; ++i) {
            if (tasks[i] == null || tasks[i - 1] == null) {
                return false;
            }
            if (tasks[i - 1].getTimeEnd() > tasks[i].getTimeStart()) {
                return false;
            }
        }
        return true;
    }

    public int calcCost(double restrictMultiplier) {
        int fullCost = 0;
        if (!costIsCorrect) {
            int boundary = (int) (getDurationSum() * restrictMultiplier);
            for (int i = 0; i < tasks.length; ++i) {
                if (tasks[i].getTimeEnd() < boundary) {
                    fullCost += calcPartCost(boundary, tasks[i].getTooEarlyMultiplier(), tasks[i].getTimeEnd());
                } else {
                    fullCost += calcPartCost(boundary, tasks[i].getTooLateMultiplier(), tasks[i].getTimeEnd());
                }
            }
            currentCost = fullCost;
            costIsCorrect = true;
        }else {
            return currentCost;
        }
        return fullCost;
    }

    public int calcCost(int boundary){
        int fullCost = 0;
        for (int i = 0;  i < tasks.length; ++i){
            if (tasks[i].getTimeEnd() < boundary){
                fullCost += calcPartCost(boundary, tasks[i].getTooEarlyMultiplier(), tasks[i].getTimeEnd());
            }else {
                fullCost += calcPartCost(boundary, tasks[i].getTooLateMultiplier(), tasks[i].getTimeEnd());
            }
        }
        return fullCost;
    }

    private int calcPartCost(int boundary, int multiplier, int timeEnd){
        return multiplier*(Math.abs(boundary-timeEnd));
    }

    public void swapTasks(int index1, int index2){
        if ((index1 >= 0 && index1 < tasks.length) && (index2 >= 0 && index2 < tasks.length)) {
            Task tmp = tasks[index1];
            tasks[index1] = tasks[index2];
            tasks[index2] = tmp;
            costIsCorrect = false;
        }
    }

    public Instance expand(){
        tasks[0].setTimeStart(startPoint);
        for (int i = 1; i<tasks.length; ++i){
            tasks[i].setTimeStart(tasks[i-1].getTimeEnd());
        }
        costIsCorrect = false;
        return this;
    }

    public Instance clone() {
        Instance cloneInstance = new Instance(this.tasks.length);
        for (Task task : this.tasks) {
            cloneInstance.addLast(task.clone());
        }
        return cloneInstance;
    }

    private boolean isTaskExist(Task task) {
        return task != null;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public Task getTaskOnIndex(int index) {
        if (index >= tasks.length || index < 0) {
            return null;
        }
        return tasks[index];
    }

    public int getSizeInstance() {
        return tasks.length;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint < 0 ? 0 : startPoint;
        costIsCorrect = false;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "tasks=" + Arrays.toString(tasks) +
                '}';
    }
}
