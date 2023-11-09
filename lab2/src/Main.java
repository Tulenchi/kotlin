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
        HashMap<String, Integer> HashmapFromFileDuplicate1 = new HashMap<>();
        HashMap<String, String> HashmapFromFileFloor = new HashMap<>();

        while((line = reader.readLine()) != null)
        {
            String[] parts = line.split(splitBy);
            String city = parts[0];
            String street = parts[1];
            String house = parts[2];
            String floor = parts[3];
            String key = city + street + house + floor;

            if(!key.equals(""))
                HashmapFromFileDuplicate1.put(key, 1);

            if(!city.equals("") && !floor.equals(""))
                HashmapFromFileFloor.put(city, floor);

        }
        HashMap<String, Integer> HashmapFromFileDuplicate = new HashMap<>();
        for(Map.Entry<String, Integer> entry : HashmapFromFileDuplicate.entrySet())
        {
            String newkey = entry.getKey();
            Integer value = entry.getValue();

            if(HashmapFromFileDuplicate.containsKey(newkey))
            {
                HashmapFromFileDuplicate.put(newkey, HashmapFromFileDuplicate.get(newkey) + 1);
            }
            else
                HashmapFromFileDuplicate.put(newkey, 1);
        }

        for (Map.Entry<String, Integer> entry : HashmapFromFileDuplicate.entrySet()) {
            String kl = entry.getKey();
            Integer vl = entry.getValue();
            System.out.println("Ключ: " + kl + ", Количество повторений: " + vl);
        }

        /*String csvFile = "C:\\Users\\olya-\\OneDrive\\Рабочий стол\\программирование\\kotlin\\address.csv";
        String line;
        String csvSplitBy = ";";

        HashMap<String, Integer> duplicatesMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);
                String key = values[0] + values[1] + values[2] + values[3];

                if (duplicatesMap.containsKey(key)) {
                    duplicatesMap.put(key, duplicatesMap.get(key) + 1);
                } else {
                    duplicatesMap.put(key, 1);
                }
            }

            for (String key : duplicatesMap.keySet()) {
                if (duplicatesMap.get(key) > 1) {
                    System.out.println("Повторение: " + key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}