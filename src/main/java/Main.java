public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        if (args.length == 3) {
            double h = Double.parseDouble(args[2]);
            Instance instance = new FileManager().readInstanceFromFile(args[0],Integer.parseInt(args[1]));
            Instance resultRatio = new RatioScheduler(instance).scheduleTask(h);
//            new FileManager().writeInstanceToFile(args[0].split("[.]")[0], Integer.parseInt(args[1]), resultRatio, h);
            new FileManager().writeInstanceToFile(args[0].split("[.]")[0], Integer.parseInt(args[1]), new AntScheduler(instance, 58*1000, resultRatio).scheduleTask(h), h);
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


//ograniczenie złożoności do O(n^2)
