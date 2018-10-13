public class Main {

    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println(args[0]+ " " + args[1]);
            Instance instance = new FileManager().readInstanceFromFile(args[0],Integer.parseInt(args[1]));
            new FileManager().writeInstanceToFile(args[0], Integer.parseInt(args[1]), new SimpleScheduler(instance).scheduleTask(), 1234);
        }else{
            System.out.println("You didn't add params. File with instances and number of instance.");
        }
    }
}
