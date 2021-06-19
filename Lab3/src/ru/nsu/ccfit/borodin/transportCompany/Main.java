import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TransportCompany company = new TransportCompany();
        try {
            company.startCompany();
        } catch (ConfigException e) {
            e.printStackTrace();
            company.stopCompany();
        }

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        company.stopCompany();
    }
}
