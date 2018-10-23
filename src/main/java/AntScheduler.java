import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class AntScheduler extends Scheduler {

    private final static double INCREASE_PHEROMONE_CHANCE = 0.05;
    private final static double MAX_PHEROMONE_CHANCE = 0.85;
    private final static int INSTANCES_FOR_ITERATIONS = 200;
    private final static int BEST_INSTANCES = 10;
    private final static int MAX_TRIES = 3;
    private final static int MAX_THREAD = 4;

    private long timeForCalculations;
    private double chanceUsePheromones = 0.05;
    private PheromoneMatrix pheromoneMatrix;


    public AntScheduler(Instance originInstance, long timeForCalculations) {
        super(originInstance);
        this.timeForCalculations = timeForCalculations;
        pheromoneMatrix = new PheromoneMatrix(originInstance.getSizeInstance());
    }

    @Override
    public Instance scheduleTask(double h) {
        Instance workInstance = getOriginInstance().clone();
        int BOUNDARY = (int) (workInstance.getDurationSum() * h);
        long actualTime = System.currentTimeMillis();
        LinkedList<Instance> potentialInstances = new LinkedList<>();
        int epoc = 1;
        Thread[] threads = new Thread[MAX_THREAD];
        do{
            System.out.println(epoc++);
//CREATE INSTANCES
            for(int t = 0; t<MAX_THREAD; t++){
                threads[t] = new Thread(new InstanceGenerator(workInstance.clone(),potentialInstances, h));
                threads[t].setDaemon(true);
                threads[t].start();
            }
            for (int t = 0; t<MAX_THREAD; t++){
                try {
                    threads[t].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//TOURNAMENT
            LinkedList<Instance> winners = reduceInstancesInTournament(potentialInstances, BOUNDARY, BEST_INSTANCES);
            winners.sort(Comparator.comparingInt(instance -> instance.calcCost(BOUNDARY)));
//ADD TO MATRIX
            for (int i = 0; i <  winners.size(); ++i){
                pheromoneMatrix.addPheromoneOnPath(winners.get(i).getTasks(), i+1);
            }
//MANAGE VARIABLES
            pheromoneMatrix.evaporatesPheromone();
            increasePheromoneChance();
            potentialInstances = winners;
        }while (timeForCalculations > (System.currentTimeMillis() - actualTime));

        return reduceInstancesInTournament(potentialInstances, BOUNDARY, 1).getFirst();
    }

    private Instance createInstanceWithMatrix(Instance workInstance) {
        Random random = new Random();
        LinkedList<Task> tasks = new LinkedList<>();
        tasks.addLast(workInstance.getTaskOnIndex(random.nextInt(workInstance.getSizeInstance())));
        for (int i = 1; i < workInstance.getTasks().length; ++i) {
            boolean addedTask = false;
            for (int tryNumber = 0; tryNumber < MAX_TRIES; ++tryNumber) {
                int candidate = pheromoneMatrix.getPotentialNextTaskId(tasks.get(i - 1).getId());
                if (canAddTaskToList(candidate, tasks)) {
                    tasks.addLast(getTaskWithId(candidate, workInstance));
                    addedTask = true;
                    break;
                }
            }
            while (!addedTask) {
                int candidate = random.nextInt(workInstance.getSizeInstance());
                if (canAddTaskToList(candidate, tasks)) {
                    tasks.addLast(getTaskWithId(candidate, workInstance));
                    addedTask = true;
                }
            }
        }
        return new Instance(tasks.toArray(new Task[0])).expand();
    }

    private Task getTaskWithId(int id, Instance instance) {
        for (Task task : instance.getTasks()) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    private boolean canAddTaskToList(int candidateIndex, LinkedList<Task> tasks) {
        if (candidateIndex == -1) {
            return false;
        }
        for (Task task : tasks) {
            if (task.getId() == candidateIndex) {
                return false;
            }
        }
        return true;
    }

    private LinkedList<Instance> reduceInstancesInTournament(LinkedList<Instance> participants, int boundary, int remainNumber) {
        Random random = new Random();
        while (participants.size() != remainNumber) {
            int first = 0;
            int second = 0;
            while (first == second) {
                first = random.nextInt(participants.size());
                second = random.nextInt(participants.size());
            }
            if (participants.get(first).calcCost(boundary) < participants.get(second).calcCost(boundary)) {
                participants.remove(second);
            } else {
                participants.remove(first);
            }
        }
        return participants;
    }

    private void increasePheromoneChance() {
        chanceUsePheromones += chanceUsePheromones < MAX_PHEROMONE_CHANCE ? INCREASE_PHEROMONE_CHANCE : 0;
    }

    private void addFirstToList(LinkedList<Instance> list, Instance instance){
        synchronized (this){
            list.addFirst(instance);
        }
    }

    private class InstanceGenerator implements Runnable{

        private Instance instance;
        private LinkedList<Instance> potentialInstances;
        private Random random;
        private double h;

        public InstanceGenerator(Instance instance, LinkedList<Instance> potentialInstances, double h) {
            this.instance = instance;
            this.potentialInstances = potentialInstances;
            random = new Random(System.currentTimeMillis());
            this.h = h;
        }

        @Override
        public void run() {
            while (potentialInstances.size() < INSTANCES_FOR_ITERATIONS) {
                if (random.nextDouble() > chanceUsePheromones) {//RANDOM
                    addFirstToList(potentialInstances, new RandomScheduler(instance.clone()).scheduleTask(h));
                } else {
                    addFirstToList(potentialInstances, createInstanceWithMatrix(instance.clone()));
                }
            }
        }
    }
}
