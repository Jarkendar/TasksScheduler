public class Main {

    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println(args[0]+ " " + args[1]);
            Instance instance = new FileManager().readInstanceFromFile(args[0],Integer.parseInt(args[1]));
            new FileManager().writeInstanceToFile(args[0], Integer.parseInt(args[1]), new AntScheduler(instance, 10 * 60 * 1000).scheduleTask(0.2), 0);
        }else{
            System.out.println("You didn't add params. File with instances and number of instance.");
        }
    }
}
