import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class SaxAddressParser extends DefaultHandler {
    private final ArrayList<Address> addressList = new ArrayList<>();
    private Address address = null;

    public ArrayList<Address> getAddressList() {
        return addressList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("item")) {
            address = new Address();
            String city = attributes.getValue("city");
            String street = attributes.getValue("street");
            String house = attributes.getValue("house");
            String floor = attributes.getValue("floor");

            String sb = city + ";" + street + ";" + house + ";" + floor;
            address.setAddress(sb);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("item")) {
            addressList.add(address);
        }
    }
}