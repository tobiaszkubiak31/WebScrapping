import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class runner {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
    }
}
