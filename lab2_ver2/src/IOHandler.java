import java.io.*;
import java.util.Scanner;

public class IOHandler {

    /** Starts loop that takes from user a path to scv or xml file, analyses it and writes result to console
     'quit' exits the loop */
    public void run() {
        AddressList addresses;

        String input = getUserInput();
        while (!input.equals("quit")) {
            System.out.println("Running...\n");
            try {
                long startTime = System.currentTimeMillis();

                addresses = readFile(input);
                printResult(addresses);

                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                System.out.println("Time in sec: " + (time * 0.001));
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal filename");
            } catch (IOException e) {
                System.out.println("File not found");
            }

            input = getUserInput();
        }
    }

    private String getUserInput()
    {
        System.out.print("\nTo quit enter 'quit'\n");
        System.out.print("Enter path to file(.csv or .xml): ");

        Scanner in = new Scanner(System.in);

        return in.nextLine();
    }

    private AddressList readFile(String fileName) throws IllegalArgumentException, IOException {
        int i = fileName.lastIndexOf('.');
        if (i == -1) {
            throw new IllegalArgumentException();
        }
        String extension = fileName.substring(i + 1);

        Parser parser = switch (extension) {
            case "csv" -> new CsvParser();
            case "xml" -> new XmlParser();
            default -> throw new IllegalArgumentException();
        };

        return parser.parse(fileName);
    }

    private void printResult(AddressList addresses) {
        System.out.println("Duplicates:");
        for (String address : addresses.duplicates.keySet()) {
            System.out.println(address + ": " + addresses.duplicates.get(address));
        }
        System.out.println("Buildings:");
        for (String key : addresses.buildingsCount.keySet()) {
            String city = key.split(";")[0];
            String floor = key.split(";")[1];
            System.out.println("City: " + city + ", floor number: " + floor);
            System.out.println("Number of houses with that many floors: " + addresses.buildingsCount.get(key) + "\n");
        }
    }
}