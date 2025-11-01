import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvParser extends Parser {
    public String requiredExtension = "csv";

    @Override
    public AddressList parse(String fileName) throws IllegalArgumentException, IOException {
        if (fileExtensionIsInvalid(fileName, requiredExtension)) {
            throw new IllegalArgumentException();
        }

        AddressList addresses = new AddressList();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;
        line = reader.readLine();

        while ((line = reader.readLine()) != null) {
            addresses.addAddress(line.replaceAll("\"", ""));
        }

        return addresses;
    }
}