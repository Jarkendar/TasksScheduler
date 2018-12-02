public class Shifter {
    public static Instance shiftStartPoint(Instance instance, int boundary) {
        int bestStartPoint = 0;
        int shift = 1;
        int currentResult = instance.calcCost(boundary);
        instance.setStartPoint(bestStartPoint + shift);
        instance.expand();
        while (true) {
            while (instance.calcCost(boundary) < currentResult) {
                if (shift > 0){
                    shift++;
                }else {
                    shift--;
                }
                bestStartPoint = instance.getStartPoint();
                currentResult = instance.calcCost(boundary);
                instance.setStartPoint(bestStartPoint + shift);
                instance.expand();
            }
            if (Math.abs(shift) == 1){
                break;
            }
            if (shift > 0){
                shift = -1;
                instance.setStartPoint(bestStartPoint + shift);
            }else {
                shift = 1;
                instance.setStartPoint(bestStartPoint + shift);
            }
        }
        instance.setStartPoint(bestStartPoint);
        return instance.expand();
    }
}
