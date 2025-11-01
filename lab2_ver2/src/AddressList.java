import java.util.HashMap;

public class AddressList {
    public HashMap<String, Integer> addresses = new HashMap<>();
    public HashMap<String, Integer> buildingsCount = new HashMap<>();
    public HashMap<String, Integer> duplicates = new HashMap<>();

    public void addAddress(String address) {
        updateBuildingsCount(address);

        if (addresses.containsKey(address)) {
            addresses.put(address, addresses.get(address) + 1);
            duplicates.put(address, addresses.get(address));
        }
        else {
            addresses.put(address, 1);
        }
    }

    private void updateBuildingsCount(String address) {
        String key = address.substring(0, address.indexOf(";")) +
                address.substring(address.lastIndexOf(";"));
        buildingsCount.put(key, buildingsCount.getOrDefault(key, 0) + 1);
    }
}