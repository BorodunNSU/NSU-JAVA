package ru.nsu.ccfit.borodin.transportCompany;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String configName = "/config.json";

        TransportCompany company = new TransportCompany();
        try {
            company.startCompany(configName);
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            company.stopCompany();
        } catch (ConfigException | InterruptedException e) {
            e.printStackTrace();
            Log.severe("Error occurred in transport company" , e);
        }
    }
}
