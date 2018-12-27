import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        if (args.length == 3) {
            double h = Double.parseDouble(args[2]);
            int resultSignal = -1;
            Instance instance;
//            Instance resultRatio = new RatioScheduler(instance).scheduleTask(h);
            long startTime = System.currentTimeMillis();
            try {
                Process process = Runtime.getRuntime().exec("python3 main.py "+args[0]+" "+args[1]+" "+args[2]+" javaResult");
                resultSignal = process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            long firstPartTime = (System.currentTimeMillis() - startTime)/1000;
            if (resultSignal == 0){
                //read instance from file
                instance = new FileManager().readInstanceFromFile("results/javaResult/"+args[0].split("\\.")[0]+"_"+args[1]+"_"+args[2].split("\\.")[1]+".txt");

            }else {
                instance = new FileManager().readInstanceFromFile(args[0],Integer.parseInt(args[1]));
            }
//            new FileManager().writeInstanceToFile(args[0].split("[.]")[0], Integer.parseInt(args[1]), resultRatio, h);
            new FileManager().writeInstanceToFile(args[0].split("[.]")[0], Integer.parseInt(args[1]), new AntScheduler(instance, 60*1000-firstPartTime, instance).scheduleTask(h), h);
        }else if (args.length == 1){
            new FileManager().checkInstanceFromFile(args[0]);
        }else if (args.length == 0){
            new CSVGenerator().generateCSV();
        } else {
            System.out.println("You didn't add params. File with instances and number of instance.");
        }
        System.out.println((args.length > 0 ? args[0] : "")+ " " + (args.length > 1 ? args[1] : "")+ " " + (args.length > 2 ? args[2] : "") + " " + (System.currentTimeMillis()-start) + "ms");
    }
}