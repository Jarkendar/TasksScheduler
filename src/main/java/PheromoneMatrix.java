public class PheromoneMatrix {
    private final static double EVAPORATE_MULTIPLIER_CONTAINS = 0.9;

    double[][] matrix; //[] row|from , [] column|to

    public PheromoneMatrix(int size) {
        this.matrix = new double[size][size];
        for (int i = 0; i<size; ++i){
            for (int j = 0; j<size; ++j){
                matrix[i][j] = 0.0;
            }
        }
    }

    public void evaporatesPheromone(){
        for (int i = 0; i<matrix.length; ++i){
            for (int j = 0; j<matrix[i].length; ++j){
                matrix[i][j] *= EVAPORATE_MULTIPLIER_CONTAINS;
            }
        }
    }

}
