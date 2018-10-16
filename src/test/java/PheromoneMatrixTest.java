import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PheromoneMatrixTest {

    @Test
    void evaporatesPheromone() {
        PheromoneMatrix pheromoneMatrix = new PheromoneMatrix(2);
        Instance instance = new Instance(2);
        Task task1 = new Task(0,1, 2, 3);
        Task task2 = new Task(1,2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        pheromoneMatrix.addPheromoneOnPath(instance.expand().getTasks());
        pheromoneMatrix.evaporatesPheromone();
        assertEquals(0.0, pheromoneMatrix.getMatrix()[0][0]);
        assertEquals(PheromoneMatrix.PHEROMONE_ADDITION*PheromoneMatrix.EVAPORATE_MULTIPLIER_CONTAINS, pheromoneMatrix.getMatrix()[0][1]);
        assertEquals(0.0, pheromoneMatrix.getMatrix()[1][0]);
        assertEquals(0.0, pheromoneMatrix.getMatrix()[1][1]);
    }

    @Test
    void addPheromoneOnPath() {
        PheromoneMatrix pheromoneMatrix = new PheromoneMatrix(2);
        Instance instance = new Instance(2);
        Task task1 = new Task(0,1, 2, 3);
        Task task2 = new Task(1,2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        pheromoneMatrix.addPheromoneOnPath(instance.expand().getTasks());
        assertEquals(0.0, pheromoneMatrix.getMatrix()[0][0]);
        assertEquals(PheromoneMatrix.PHEROMONE_ADDITION, pheromoneMatrix.getMatrix()[0][1]);
        assertEquals(0.0, pheromoneMatrix.getMatrix()[1][0]);
        assertEquals(0.0, pheromoneMatrix.getMatrix()[1][1]);
    }
}