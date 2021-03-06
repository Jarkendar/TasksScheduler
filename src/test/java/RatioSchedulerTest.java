import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RatioSchedulerTest {

    @Test
    void ratioScheduler() {
        Instance instance = new Instance(2);
        Task task1 = new Task(0,1, 2, 3);
        Task task2 = new Task(1,2, 3, 4);
        instance.addLast(task1);
        instance.addLast(task2);
        assertFalse(instance.isCorrect());
        RatioScheduler ratioScheduler = new RatioScheduler(instance);
        Instance returnInstance = ratioScheduler.scheduleTask(1.0);
        assertTrue(returnInstance.isCorrect());
    }

}