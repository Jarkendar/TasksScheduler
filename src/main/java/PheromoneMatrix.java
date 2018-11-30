public class PheromoneMatrix {
    public final static double EVAPORATE_MULTIPLIER_CONTAINS = 0.9;
    public final static double PHEROMONE_ADDITION = 1.0;

    private double[][] matrix; //[] row|from , [] column|to

    public PheromoneMatrix(int size) {
        this.matrix = new double[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                matrix[i][j] = 0.0;
            }
        }
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void addPheromoneOnPath(Task[] tasks, int multiplier) {
        for (int i = 1; i < tasks.length; ++i) {
            addPheromoneOnPoint(tasks[i - 1].getId(), tasks[i].getId(), multiplier);
        }
    }

    private void addPheromoneOnPoint(int from, int to, int multiplier) {
        matrix[from][to] += PHEROMONE_ADDITION * multiplier;
    }

    public int getPotentialNextTaskId(int from) {
        return new Roulette().randNext(matrix[from]);
    }

    public void evaporatesPheromone() {
        int maxThreads = 4;
        Thread[] threads = new Thread[maxThreads];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MatrixEvaporator(i, maxThreads));
            threads[i].setDaemon(true);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class MatrixEvaporator implements Runnable {
        private int startNumber;
        private int step;

        public MatrixEvaporator(int startNumber, int step) {
            this.startNumber = startNumber;
            this.step = step;
        }

        @Override
        public void run() {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = startNumber; j < matrix[0].length; j += step) {
                    matrix[i][j] *= EVAPORATE_MULTIPLIER_CONTAINS;
                }
            }
        }
    }

}
