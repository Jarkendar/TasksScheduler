import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {

    @Test
    void getRatio() {
        Task task = new Task(1,2,3,4);
        Task task1 = new Task(1,2,3,0);
        assertEquals(3.0/4.0, task.getRatio());
        assertEquals(Double.POSITIVE_INFINITY, task1.getRatio());
        assertTrue(task.getRatio() < task1.getRatio());
    }
}