public class Shifter {
    public static Instance shiftStartPoint(Instance instance, int boundary) {
        int bestStartPoint = 0;
        int shift = 1;
        int currentBestResult = instance.calcCost(boundary);
        byte changes = 0;
        instance.setStartPoint(bestStartPoint + shift);
        instance.expand();
        while (true) {
            while (instance.calcCost(boundary) < currentBestResult) {
                if (shift > 0){
                    shift++;
                }else {
                    shift--;
                }
                bestStartPoint = instance.getStartPoint();
                currentBestResult = instance.calcCost(boundary);
                instance.setStartPoint(bestStartPoint + shift);
                instance.expand();
                changes=0;
            }
            if (Math.abs(shift) == 1 && changes == 2){
                break;
            }
            if (shift > 0){
                shift = -1;
                instance.setStartPoint(bestStartPoint + shift);
                changes++;
            }else {
                shift = 1;
                instance.setStartPoint(bestStartPoint + shift);
                changes++;
            }
            instance.expand();
        }
        instance.setStartPoint(bestStartPoint);
        return instance.expand();
    }
}
