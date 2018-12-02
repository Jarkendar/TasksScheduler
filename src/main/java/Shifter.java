public class Shifter {
    public static Instance shiftStartPoint(Instance instance, int boundary) {
        int bestStartPoint = 0;
        int currentResult = instance.calcCost(boundary);
        instance.setStartPoint(bestStartPoint + 1);
        instance.expand();
        while (instance.calcCost(boundary) < currentResult) {
            bestStartPoint = instance.getStartPoint();
            currentResult = instance.calcCost(boundary);
            instance.setStartPoint(bestStartPoint + 1);
            instance.expand();
        }
        instance.setStartPoint(bestStartPoint);
        return instance.expand();
    }
}
