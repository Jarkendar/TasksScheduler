public class PheromoneMatrix {
    public final static double EVAPORATE_MULTIPLIER_CONTAINS = 0.9;
    public final static double PHEROMONE_ADDITION = 1.0;

    private double[][] matrix; //[] row|from , [] column|to

    public PheromoneMatrix(int size) {
        this.matrix = new double[size][size];
        for (int i = 0; i<size; ++i){
            for (int j = 0; j<size; ++j){
                matrix[i][j] = 0.0;
            }
        }
    }

    public double[][] getMatrix(){
        return matrix;
    }

    public void addPheromoneOnPath(Task[] tasks){
        for (int i = 1; i<tasks.length; ++i){
            addPheromoneOnPoint(tasks[i-1].getId(), tasks[i].getId());
        }
    }

    private void addPheromoneOnPoint(int from, int to){
        matrix[from][to] += PHEROMONE_ADDITION;
    }

    public void evaporatesPheromone(){
        for (int i = 0; i<matrix.length; ++i){
            for (int j = 0; j<matrix[i].length; ++j){
                matrix[i][j] *= EVAPORATE_MULTIPLIER_CONTAINS;
            }
        }
    }

}
