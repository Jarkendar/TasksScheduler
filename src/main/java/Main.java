public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        if (args.length == 3) {
            System.out.println(args[0]+ " " + args[1]+ " " + args[2]);
            double h = Double.parseDouble(args[2]);
            Instance instance = new FileManager().readInstanceFromFile(args[0],Integer.parseInt(args[1]));
            new FileManager().writeInstanceToFile(args[0].split("[.]")[0], Integer.parseInt(args[1]), new RatioScheduler(instance).scheduleTask(h), h);
        }else if (args.length == 1){
            new FileManager().checkInstanceFromFile(args[0]);
        }else {
            System.out.println("You didn't add params. File with instances and number of instance.");
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}


//ograniczenie złożoności do O(n^2)
