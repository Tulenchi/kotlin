import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class Main
{
    public static void main(String[] args) throws IOException {
        System.out.print("Enter word:\n");
        Scanner in = new Scanner(System.in);
        String request = in.nextLine();

        //Search and request to the server
        SearchEngine engine = new SearchEngine();
        String result_json = engine.WikiSearch(request);
        System.out.printf(result_json);

        //Write to a file
        try(FileWriter writer = new FileWriter("result.json", false))
        {
            writer.write(result_json);
            writer.close();
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        System.out.println("\n");

        //Parsing
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        JsonObject fromjs = gson.fromJson(result_json, JsonObject.class);
        JsonObject query = fromjs.getAsJsonObject("query");
        JsonArray res_fromJson = query.getAsJsonArray("search");

        String title = null;
        FileWriter writer_res = new FileWriter("titles.json");
        int i;

        //Titles output
        for (i = 0; i < res_fromJson.size(); i++)
        {
            JsonObject titles = res_fromJson.get(i).getAsJsonObject();
            title = titles.get("title").getAsString();
            System.out.println((i + 1) + ". " + title);
            writer_res.write((i + 1) + ". " + title + '\n');
        }

        System.out.print("Enter article number:\n");
        int choice = in.nextInt();

        JsonObject id = res_fromJson.get(choice - 1).getAsJsonObject();
        String pageid = id.get("pageid").getAsString();

        String wiki_url = "https://ru.wikipedia.org/w/index.php?curid=" + pageid;
        System.out.println("URL:" + wiki_url);

        writer_res.close();
        in.close();
    }
}
