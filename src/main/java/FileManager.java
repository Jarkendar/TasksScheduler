import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

    private static final String SEPARATOR = ";";

    public Instance readInstanceFromFile(String filename, int numberOfInstance) {
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        Instance instance = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            int allInstances = Integer.parseInt(normalize(line));
            for (int i = 0; i < allInstances; ++i){
                int taskInInstance = Integer.parseInt(normalize(bufferedReader.readLine()));
                if (i != numberOfInstance-1){
                    for (int j = 0; j < taskInInstance; ++j){
                        bufferedReader.readLine();
                    }
                    continue;
                }
                instance = new Instance(taskInInstance);
                for (int j = 0; j< taskInInstance; ++j){
                    String[] taskParam = normalize(bufferedReader.readLine()).split(SEPARATOR);
                    instance.addLast(new Task(Integer.parseInt(taskParam[0]),Integer.parseInt(taskParam[1]),Integer.parseInt(taskParam[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private String normalize(String string) {
        return string.replaceAll("\\s+", SEPARATOR).substring(1).trim();
    }

}
