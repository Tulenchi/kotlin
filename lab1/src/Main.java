import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.print("Enter word:\n");
        Scanner in = new Scanner(System.in);
        String request = in.nextLine();
        SearchEngine engine = new SearchEngine();
        String result = engine.WikiSearch(request);
        System.out.printf(result);
        in.close();
    }
}
