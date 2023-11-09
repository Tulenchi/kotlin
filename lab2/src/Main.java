import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
        String choice = "";
        String line = "";
        String splitBy = ";";

        while (true)
        {
            System.out.print("\nEnter the path to the file " +
                    "or 'quit' if you want to complete the program\n");
            choice = in.nextLine();

            //csv: "C:\Users\olya-\OneDrive\Рабочий стол\программирование\kotlin\address.csv"
            //xml: "C:\Users\olya-\OneDrive\Рабочий стол\программирование\kotlin\address.xml"

            if (Objects.equals(choice, "quit"))
            {
                System.exit(0);
            }

            try {
                boolean containsCsv = choice.contains(".csv");
                boolean containsXml = choice.contains(".xml");

                if (containsCsv) {
                    BufferedReader reader = new BufferedReader(new FileReader(choice));

                    HashMap<String, Integer> HashmapFromFileDuplicate = new HashMap<>();
                    HashMap<String, Integer> HashmapFromFileFloor = new HashMap<>();

                    long startTime = System.currentTimeMillis();
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(splitBy);
                        String city = parts[0];
                        String street = parts[1];
                        String house = parts[2];
                        String floor = parts[3];
                        String key = city + ";" + street + ";" + house + ";" + floor;
                        String keyFloor = city + ";" + floor;

                        if (HashmapFromFileDuplicate.containsKey(key)) {
                            HashmapFromFileDuplicate.put(key, HashmapFromFileDuplicate.get(key) + 1);
                        } else {
                            HashmapFromFileDuplicate.put(key, 1);
                        }

                        if (HashmapFromFileFloor.containsKey(keyFloor)) {
                            HashmapFromFileFloor.put(keyFloor, HashmapFromFileFloor.get(keyFloor) + 1);
                        } else {
                            HashmapFromFileFloor.put(keyFloor, 1);
                        }

                    }
                    long endTime = System.currentTimeMillis();

                    for (Map.Entry<String, Integer> entry : HashmapFromFileDuplicate.entrySet()) {
                        String kl = entry.getKey();
                        Integer vl = entry.getValue();
                        if (vl != 1) {
                            System.out.println("House: " + kl + " \nNumber of duplicates: " + vl + "\n");
                        }
                    }

                    for (Map.Entry<String, Integer> entry : HashmapFromFileFloor.entrySet()) {
                        String kl1 = entry.getKey();
                        Integer vl1 = entry.getValue();
                        System.out.println("House (city, floor): " + kl1 + " \nNumber of houses with that many floors: " + vl1 + "\n");

                    }

                    long time = endTime - startTime;
                    System.out.println("Time in sec: " + (time * 0.001));
                }

                else if (containsXml)
                {
                    DocumentBuilderFactory dbf;
                    DocumentBuilder        db ;
                    Document               doc;

                }

                else {
                    System.out.println("Wrong file format");
                }
            }catch(FileNotFoundException e)
            {
                System.out.println("Wrong file name");
            }
        }
    }
}