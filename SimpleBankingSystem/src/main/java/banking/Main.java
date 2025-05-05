package banking;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2 || !"-fileName".equals(args[0])) {
            System.out.println("Usage: <program-name> -fileName <database-file>");
            System.exit(1);
        } else {
            App.run(args[1]);
        }
    }
}