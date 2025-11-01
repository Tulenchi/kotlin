import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlParser extends Parser {
    public String requiredExtension = "xml";

    @Override
    public AddressList parse(String fileName) throws IllegalArgumentException, IOException {
        if (fileExtensionIsInvalid(fileName, requiredExtension)) {
            throw new IllegalArgumentException();
        }

        AddressList addresses = new AddressList();

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxAddressParser handler = new SaxAddressParser();
            saxParser.parse(new File(fileName), handler);
            ArrayList<Address> addressList = handler.getAddressList();

            for (Address address : addressList) {
                addresses.addAddress(address.getAddress());
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }

        return addresses;
    }
}