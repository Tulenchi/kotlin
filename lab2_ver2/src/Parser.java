import java.io.IOException;

public abstract class Parser {
    public abstract AddressList parse(String fileName) throws IllegalArgumentException, IOException;

    protected boolean fileExtensionIsInvalid(String fileName, String requiredExtension) {
        int i = fileName.lastIndexOf('.');
        String extension;
        if (i != -1) {
            extension = fileName.substring(i + 1);
        } else {
            return true;
        }

        return !extension.equals(requiredExtension);
    }
}