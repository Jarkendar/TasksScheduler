import java.io.*;

public class CSVGenerator {

    private static final String FILE_NAME = "Nazwa pliku";
    private static final String INSTANCE = "Instancja";
    private static final String MULTIPLIER = "h";
    private static final String SITE_RESULT = "Wynik ze strony";
    private static final String RESULT = "Wynik";
    private static final String MISTAKE = "Błąd (%)";
    private static final String TIME = "Czas obliczeń";
    private static final String DEFAULT_SEPARATOR = ",";
    private int[][] patternResults = {{1936, 1042, 1586, 2139, 1187, 1521, 2170, 1720, 1574, 1869},
            {89588, 74854, 85363, 87730, 76424, 86724, 79854, 95361, 73605, 72399},
            {1581233, 1715332, 1644947, 1640942, 1468325, 1413345, 1634912, 1542090, 1684055, 1520515},
            {6411581, 6112598, 5985538, 6096729, 6348242, 6082142, 6575879, 6069658, 6188416, 6147295}};
    private String[] filenameToTable = {"sch10.txt", "sch100.txt", "sch500.txt", "sch1000.txt"};
    private String[] filename = {"sch10", "sch100", "sch500", "sch1000"};
    private int[] h = {2, 4, 6, 8};
    private String suffix = ".txt";

    public void generateCSV() {
        File resultCSV = new File("result.csv");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultCSV))) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 4; ++i) {
                stringBuilder.append(generateHeader()).append("\n");
                for (int j = 0; j < 10; ++j) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(prepareFileName(filename[i], j+1, h[i]))));
                    bufferedReader.readLine();
                    int fileResult = Integer.parseInt(bufferedReader.readLine().trim());
                    double mistake = countMistake(fileResult, patternResults[i][j]);
                    int timeMilis = findTime(filenameToTable[i], j + 1, h[i]);
                    stringBuilder.append(filenameToTable[i]).append(DEFAULT_SEPARATOR)
                            .append(j+1).append(DEFAULT_SEPARATOR)
                            .append(h[i]/10.0).append(DEFAULT_SEPARATOR)
                            .append(patternResults[i][j]).append(DEFAULT_SEPARATOR)
                            .append(fileResult).append(DEFAULT_SEPARATOR)
                            .append(mistake).append(DEFAULT_SEPARATOR)
                            .append(timeFormater(timeMilis)).append("\n");
                }
                stringBuilder.append("\n");
            }
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String timeFormater(int duration) {
        int millis = duration % 1000;
        int seconds = (duration / 1000) % 60;
        int minutes = (duration / (1000 * 60)) % 60;
        int hours = (duration / (1000 * 60 * 60)) % 60;
        return hours + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : minutes)
                + "." + (millis < 100 ? (millis < 10 ? "00" + millis : "0" + millis) : millis);
    }

    private int findTime(String filename, int k, int h) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("times.txt")))) {
            String line = "";
            double hd = (double) h / 10.0;
            while (true) {
                line = bufferedReader.readLine();
                String[] word = line.split(" ");
                if (word[0].equals(filename) && Integer.parseInt(word[1]) == k && Double.parseDouble(word[2]) == hd) {
                    return Integer.parseInt(word[3].substring(0, word[3].length() - 2));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private String prepareFileName(String prefix, int instance, int h) {
        return prefix + "_" + instance + "_" + h + suffix;
    }

    private double countMistake(int result, int resultSite) {
        return ((double) result - (double) resultSite) / ((double) resultSite)*100.0;
    }

    private String generateHeader() {
        return FILE_NAME + DEFAULT_SEPARATOR + INSTANCE + DEFAULT_SEPARATOR + MULTIPLIER + DEFAULT_SEPARATOR
                + SITE_RESULT + DEFAULT_SEPARATOR + RESULT + DEFAULT_SEPARATOR + MISTAKE + DEFAULT_SEPARATOR + TIME;
    }


}
