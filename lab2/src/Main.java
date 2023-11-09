import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String choice;
        String line = "";
        String splitBy = ";";

        System.out.print("Enter the path to the file " +
                "or 'quit' if you want to complete the program\n");
        choice = in.nextLine();

        //csv: "C:\Users\olya-\OneDrive\Рабочий стол\программирование\kotlin\address.csv"
        //xml: "C:\Users\olya-\OneDrive\Рабочий стол\программирование\kotlin\address.xml"

        if (Objects.equals(choice, "quit"))
        {
            System.exit(0);
        }

        BufferedReader reader = new BufferedReader(new FileReader(choice));
        HashMap<String, Integer> HashmapFromFileDuplicate = new HashMap<>();
        HashMap<String, String> HashmapFromFileFloor = new HashMap<>();

        while((line = reader.readLine()) != null)
        {
            String[] parts = line.split(splitBy);
            String city = parts[0];
            String street = parts[1];
            String house = parts[2];
            String floor = parts[3];
            String key = city + ";" + street + ";"  + house + ";"  + floor;

            if (HashmapFromFileDuplicate.containsKey(key)) {
                HashmapFromFileDuplicate.put(key, HashmapFromFileDuplicate.get(key) + 1);
            } else {
                HashmapFromFileDuplicate.put(key, 1);
            }

            if(!city.equals("") && !floor.equals(""))
                HashmapFromFileFloor.put(city, floor);

        }

        for (Map.Entry<String, Integer> entry : HashmapFromFileDuplicate.entrySet()) {
            String kl = entry.getKey();
            Integer vl = entry.getValue();
            if (vl != 1) {
                System.out.println("House: " + kl + " \nNumber of duplicates: " + vl + "\n\n");
            }
        }
    }
}