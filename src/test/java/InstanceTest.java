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
        assertFalse(instance.isInstanceCorrect());
        instance.addLast(task2);
        assertFalse(instance.isInstanceCorrect());
        instance.getTasks()[1].setTimeStart(instance.getTasks()[1].getTimeEnd());
        assertTrue(instance.isInstanceCorrect());
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
    void calcInstanceCost() {
        Instance instance = new Instance(2);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertEquals(4 + 3, instance.calcInstanceCost(1.0));
        assertEquals(0 + 4, instance.calcInstanceCost(0.5));
        assertEquals(3 + 8, instance.calcInstanceCost(0.0));
        Instance correctInstance = new SimpleScheduler(instance).scheduleTask();
        assertTrue(correctInstance.isInstanceCorrect());
        assertEquals( 0 + 8, correctInstance.calcInstanceCost(0.5));
    }
}