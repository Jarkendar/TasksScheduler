import static org.junit.jupiter.api.Assertions.*;

class InstanceTest {

    @org.junit.jupiter.api.Test
    void addLast() {
        Instance instance = new Instance(2);
        assertTrue(instance.getTasks()[0] == null && instance.getTasks()[1] == null);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertTrue(instance.getTasks()[0] == task1 && instance.getTasks()[1] == task2);
    }

    @org.junit.jupiter.api.Test
    void getDurationSum() {
        Instance instance = new Instance(2);
        assertEquals(instance.getDurationSum(), -1);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertEquals(instance.getDurationSum(), 3);
    }

    @org.junit.jupiter.api.Test
    void isInstanceCorrect() {
        Instance instance = new Instance(2);
        Task task1 = new Task(1, 2, 3);
        Task task2 = new Task(2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertFalse(instance.isInstanceCorrect());
        instance.getTasks()[1].setTimeStart(instance.getTasks()[1].getTimeEnd());
        assertTrue(instance.isInstanceCorrect());
    }
}