import java.io.*;

public class FileManager {

    private static final String SEPARATOR = ";";
    private static final String SEPARATOR_TAB = "\t";

    public Instance readInstanceFromFile(String filename, int numberOfInstance) {
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        Instance instance = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int allInstances = Integer.parseInt(normalize(bufferedReader.readLine()));
            for (int i = 0; i < allInstances; ++i) {
                int taskInInstance = Integer.parseInt(normalize(bufferedReader.readLine()));
                if (i != numberOfInstance - 1) {
                    for (int j = 0; j < taskInInstance; ++j) {
                        bufferedReader.readLine();
                    }
                    continue;
                }
                instance = new Instance(taskInInstance);
                for (int j = 0; j < taskInInstance; ++j) {
                    String[] taskParam = normalize(bufferedReader.readLine()).split(SEPARATOR);
                    instance.addLast(new Task(j, Integer.parseInt(taskParam[0]), Integer.parseInt(taskParam[1]), Integer.parseInt(taskParam[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public Instance readInstanceFromFile(String filename){
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        Instance instance = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            bufferedReader.readLine();
            bufferedReader.readLine();
            bufferedReader.readLine();
            int startPoint = Integer.parseInt(bufferedReader.readLine());
            int taskInInstance = Integer.parseInt(filename.split("_")[0].split("h")[1]);
            instance = new Instance(taskInInstance);
            for (int j = 0; j < taskInInstance; ++j) {
                String[] taskParam = normalize(bufferedReader.readLine()).split(SEPARATOR);
                instance.addLast(new Task(j, Integer.parseInt(taskParam[0]), Integer.parseInt(taskParam[1]), Integer.parseInt(taskParam[2])));
            }
            instance.setStartPoint(startPoint);
            instance.expand();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        return instance;
    }

    public void writeInstanceToFile(String filename, int numberOfInstance, Instance instance, double h) {
        File file = new File(filename + "_" + numberOfInstance + "_" + (int) (h * 10) + ".txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(((int) (h * 10)) + "");
            bufferedWriter.newLine();
            bufferedWriter.write(createHHeader(instance, h, numberOfInstance));
            for (Task task : instance.getTasks()) {
                bufferedWriter.newLine();
                bufferedWriter.write(createTaskLine(task));
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkInstanceFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }
        Instance instance = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            double h = Integer.parseInt(bufferedReader.readLine())/10.0;
            int potentialCost = Integer.parseInt(bufferedReader.readLine());
            int instanceNumber = Integer.parseInt(bufferedReader.readLine());
            int startPoint = Integer.parseInt(bufferedReader.readLine());
            int taskInInstance = Integer.parseInt(filename.split("_")[0].split("h")[1]);
            instance = new Instance(taskInInstance);
            for (int j = 0; j < taskInInstance; ++j) {
                String[] taskParam = normalize(bufferedReader.readLine()).split(SEPARATOR);
                instance.addLast(new Task(j, Integer.parseInt(taskParam[0]), Integer.parseInt(taskParam[1]), Integer.parseInt(taskParam[2])));
            }
            instance.setStartPoint(startPoint);
            instance.expand();
            display(filename,taskInInstance, h, potentialCost, instanceNumber, startPoint, instance.calcCost(h));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    private String createHHeader(Instance instance, double h, int numberOfInstance) {
        return instance.calcCost(h) + "\n" + numberOfInstance + "\n" + instance.getStartPoint();
    }

    private String createTaskLine(Task task) {
        return task.getDuration() + "\t" + task.getTooEarlyMultiplier() + "\t" + task.getTooLateMultiplier();
    }

    private String normalize(String string) {
        return string.trim().replaceAll("\\s+", SEPARATOR);
    }

    private void display(String filename, int tasksInInstance, double h, int predictedCost, int numberOfInstance,int startPoint, int calcCost){
        String string = filename + "\n" +
                "Tasks in instance = "+ tasksInInstance + "\n" +
                "Instance number = "+ numberOfInstance + "\n" +
                "h multiplier = " + h + "\n" +
                "Predicted cost = " + predictedCost + "\n" +
                "Start point = " + startPoint + "\n" +
                "Calc cost = " + calcCost + "\n" +
                "Correct = " + (predictedCost == calcCost);
        System.out.println(string);

    }

}
