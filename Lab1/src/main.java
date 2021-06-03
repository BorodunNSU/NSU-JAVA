import java.io.*;

public class main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.print("Write at least two arguments (e.g. 'input.txt output.csv')");
        } else {
            WordStatistic wordStatistic = new WordStatistic();
            try {
                for (int i = 0; i <= args.length - 2; ++i) {
                    wordStatistic.addFile(args[i]);
                }
                wordStatistic.writeCSV(args[args.length - 1]);
            }catch (IOException exception){
                System.err.print("Exception: " + exception.getLocalizedMessage());
            }
        }
    }
}
