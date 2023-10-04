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
    public static void main(String[] args)
    {
        System.out.print("Enter word:\n");
        Scanner in = new Scanner(System.in);
        String request = in.nextLine();

        //Search and request to the server
        SearchEngine engine = new SearchEngine();
        String result = engine.WikiSearch(request);
        System.out.printf(result);

        //Write to a file
        try(FileWriter writer = new FileWriter("result.json", false))
        {
            writer.write(result);
            writer.close();
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        System.out.println("\n");

        //Parsing
        /*GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        JsonObject res_fromJson = gson.fromJson(result, JsonObject.class);
        JsonObject query = res_fromJson.getAsJsonObject("query");
        JsonArray searchResults = query.getAsJsonArray("search");
        String title = null;

        FileWriter writer_res = new FileWriter("result_after.json");
        for (int i = 0; i < searchResults.size(); i++) {
            JsonObject result1 = searchResults.get(i).getAsJsonObject();
            title = result1.get("title").getAsString();
            System.out.println((i + 1) + ". " + title);
                writer_res.write(title);
                writer_res.append('\n');
        }

        writer_res.close();*/

        in.close();
    }
}
