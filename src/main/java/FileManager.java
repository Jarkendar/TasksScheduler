import java.io.*;

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
                    instance.addLast(new Task(j, Integer.parseInt(taskParam[0]),Integer.parseInt(taskParam[1]),Integer.parseInt(taskParam[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public void writeInstanceToFile(String filename, int numberOfInstance, Instance instance, double h){
        File file = new File(filename+"_"+numberOfInstance+"_"+(int)(h*10)+".txt");
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            bufferedWriter.write(((int)(h*10)) + "");
            bufferedWriter.newLine();
            bufferedWriter.write(createHHeader(instance, h, numberOfInstance));
            for (Task task : instance.getTasks()) {
                bufferedWriter.newLine();
                bufferedWriter.write(createTaskLine(task));
            }
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String createHHeader(Instance instance, double h, int numberOfInstance){
        return instance.calcCost(h)+"\n"+numberOfInstance+"\n"+instance.getStartPoint();
    }

    private String createTaskLine(Task task){
        return task.getDuration()+"\t"+task.getTooEarlyMultiplier()+"\t"+task.getTooLateMultiplier();
    }

    private String normalize(String string) {
        return string.replaceAll("\\s+", SEPARATOR).substring(1).trim();
    }

}
