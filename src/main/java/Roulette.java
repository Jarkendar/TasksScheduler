import java.util.Random;

public class Roulette {

    public int randNext(double[] chances){
        double[] levelChances = createLevelsChance(chances);
        if (levelChances[levelChances.length-1] == 0.0){
            return -1;
        }
        double choice = new Random().nextDouble() * levelChances[levelChances.length-1];
        return getDrawIndex(choice, levelChances);
    }

    private double[] createLevelsChance(double[] chances){
        double[] array = new double[chances.length];
        double sum = 0.0;
        for (int i = 0; i < array.length; ++i){
            sum += chances[i];
            array[i] = sum;
        }
        return array;
    }

    private int getDrawIndex(double choice, double[] levels){
        for (int i = 0; i < levels.length; ++i){
            if (choice < levels[i]){
                return i;
            }
        }
        return -1;
    }
}
