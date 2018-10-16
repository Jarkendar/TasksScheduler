import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouletteTest {

    @Test
    void randNext() {
        double[] chances = {0.0, 1.0};
        assertEquals(1, new Roulette().randNext(chances));
        double[] chances2 = {0.0, 1.0, 0.0};
        assertEquals(1, new Roulette().randNext(chances2));
        double[] chances3 = {0.0, 1.0, 2.0};
        int result = new Roulette().randNext(chances3);
        assertTrue(result == 1 || result == 2);
    }
}