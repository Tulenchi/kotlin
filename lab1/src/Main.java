import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.net.URI;
import java.util.regex.Pattern;

public class Main
{
    private static String ReadWordFromConsole(Scanner in) {
        String request;
        while (true) {
            System.out.print("Enter word: ");
            request = in.nextLine();
            if (request.isBlank()) {
                System.out.println("Please enter word to search\n");
            } else break;
        }
        return request;
    }

    public static int ReadArticleNumber(Scanner in, int max) {
        String choice_str;
        int choice;

        while (true) {
            System.out.print("\nEnter article number: ");

            choice_str = in.nextLine().strip();
            Pattern pattern = Pattern.compile("\\d+");
            if (!choice_str.isBlank() && pattern.matcher(choice_str).matches()) {
                choice = Integer.parseInt(choice_str);
                if (0 < choice && choice <= max) break;
            }
            System.out.println("Please enter number from 1 to " + max);
        }
        return choice;
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        Scanner in = new Scanner(System.in);

        // Search and send request to the server
        SearchEngine engine = new SearchEngine();
        String result_json;

        String request;
        while (true) {
            try {
                request = ReadWordFromConsole(in);
                result_json = engine.WikiSearch(request);
                break;
            } catch (IOException e) {
                System.out.println("Error: cannot connect to Wikipedia. Please check your internet connection");
            }
        }

        System.out.println("Received json: " + result_json + "\n");

        // Parsing
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        JsonObject json = gson.fromJson(result_json, JsonObject.class);
        JsonObject query = json.getAsJsonObject("query");
        JsonArray result = query.getAsJsonArray("search");

        if (result.isEmpty())
        {
            System.out.println("No results found");
            System.exit(0);
        }

        // Titles output
        String title;
        for (int i = 0; i < result.size(); i++)
        {
            JsonObject titles = result.get(i).getAsJsonObject();
            title = titles.get("title").getAsString();
            System.out.println((i + 1) + ". " + title);
        }

        int choice = ReadArticleNumber(in, result.size());

        // Getting pageId
        JsonObject id = result.get(choice - 1).getAsJsonObject();
        String pageId = id.get("pageid").getAsString();

        // Open wiki
        String wiki_url = "https://ru.wikipedia.org/w/index.php?curid=" + pageId;
        System.out.println("LINK:" + wiki_url);
        Desktop.getDesktop().browse(new URI(wiki_url));

        in.close();
    }
}
