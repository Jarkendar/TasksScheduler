import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstanceTest {

    @Test
    void addLast() {
        Instance instance = new Instance(2);
        assertTrue(instance.getTasks()[0] == null && instance.getTasks()[1] == null);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertTrue(instance.getTasks()[0] == task1 && instance.getTasks()[1] == task2);
    }

    @Test
    void getDurationSum() {
        Instance instance = new Instance(2);
        assertEquals(-1, instance.getDurationSum());
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertEquals(3, instance.getDurationSum());
    }

    @Test
    void isInstanceCorrect() {
        Instance instance = new Instance(2);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        assertFalse(instance.isCorrect());
        instance.addLast(task2);
        assertFalse(instance.isCorrect());
        instance.getTasks()[1].setTimeStart(instance.getTasks()[1].getTimeEnd());
        assertTrue(instance.isCorrect());
    }

    @Test
    void cloneIsIdentity() {
        Instance instance = new Instance(2);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        Instance cloneInstance = instance.clone();
        assertEquals(2, cloneInstance.getTasks().length);
        for (int i = 0; i < instance.getTasks().length; ++i) {
            assertEquals(instance.getTasks()[i].getDuration(), cloneInstance.getTasks()[i].getDuration());
            assertEquals(instance.getTasks()[i].getTooEarlyMultiplier(), cloneInstance.getTasks()[i].getTooEarlyMultiplier());
            assertEquals(instance.getTasks()[i].getTooLateMultiplier(), cloneInstance.getTasks()[i].getTooLateMultiplier());
        }
    }

    @Test
    void getTaskOnIndex() {
        Instance instance = new Instance(2);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertNull(instance.getTaskOnIndex(-1));
        assertNull(instance.getTaskOnIndex(2));
        assertEquals(instance.getTaskOnIndex(1), task2);
    }

    @Test
    void calcCost() {
        Instance instance = new Instance(2);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertEquals(4 + 3, instance.calcCost(1.0));
        assertEquals(0 + 4, instance.calcCost(0.5));
        assertEquals(3 + 8, instance.calcCost(0.0));
        Instance correctInstance = new SimpleScheduler(instance).scheduleTask();
        assertTrue(correctInstance.isCorrect());
        assertEquals( 0 + 8, correctInstance.calcCost(0.5));
    }

    @Test
    void swapTasks(){
        Instance instance = new Instance(2);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertTrue(instance.getTasks()[0] == task1 && instance.getTasks()[1] == task2);
        instance.swapTasks(0,2);
        assertTrue(instance.getTasks()[0] == task1 && instance.getTasks()[1] == task2);
        instance.swapTasks(-1,1);
        assertTrue(instance.getTasks()[0] == task1 && instance.getTasks()[1] == task2);
        instance.swapTasks(0,1);
        assertTrue(instance.getTasks()[0] == task2 && instance.getTasks()[1] == task1);

    }
}