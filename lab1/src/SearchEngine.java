import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

public class SearchEngine
{
    public String WikiSearch(String requestString) throws IOException, InterruptedException {
        URI uri;
        try
        {
            uri = new URI("https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=" + URLEncoder.encode(requestString, StandardCharsets.UTF_8));
        } catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
        HttpRequest request = HttpRequest.newBuilder(uri).build();

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.body();
    }
}