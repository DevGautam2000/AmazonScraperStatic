import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AmazonScraper {

    //user agent used by thr browser
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36";

    //method throws any kind of exception and is not handled
    public static void main(String[] args) throws Exception {


        //Enter the product to search
        Scanner get = new Scanner(System.in);
        final  String query = get.next();

        //the webpage url
        final String url = "https://www.amazon.in/s/ref=nb_sb_noss?field-keywords=";

        //connect document using Jsoup and pass the query as the field-keywords= as in this webpage
        //the encoder is UTF-8
        //pass the user agents else the site would return 404
        Document document = Jsoup.connect(url+ URLEncoder.encode(query, StandardCharsets.UTF_8)).userAgent(USER_AGENT).get();


//      get results
        Elements texts = document.select(".a-text-normal.a-color-base.a-size-medium");

        //Array to store the product name and price
        Element[] txt = new Element[texts.size()];
        Element[] pr = new Element[texts.size()];

        //Crete file "Amz.txt" to write the fetched results
        PrintWriter printWriter = new PrintWriter("Amz.txt");

        //get each result
        for (int i=0; i<texts.size();i++) {

            txt[i] = document.select(".a-text-normal.a-color-base.a-size-medium").get(i);
            pr[i] = document.select(".a-price-whole").get(i);

            System.out.println(txt[i].text() +" --> "+ pr[i].text());  //----->this step is just to confirm that if we get the results or not

            //write into the txt file
            printWriter.write(txt[i].text()+ " --> Rs."+pr[i].text()+"\n");
        }

        //close the file
        printWriter.close();

    }
}
