package util;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;

import java.io.IOException;


public class HtmlParseUtil2 {
    public static void main(String[] args) throws IOException {


        String url1 = "http://da.wa.news.cn/nodeart/page?nid=11227931&pgnum=1&cnt=10&attr=&tp=1&orderby=1";
        String url2 = "http://da.wa.news.cn/nodeart/page?nid=11227931&pgnum=2&cnt=10&attr=&tp=1&orderby=1";
        String url3 = "http://da.wa.news.cn/nodeart/page?nid=11227931&pgnum=3&cnt=10&attr=&tp=1&orderby=1";
//        String InboxJson= Jsoup.connect(url)
//                .timeout(1000000)
//                .header("Accept", "text/javascript")
//                .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:40.0) Gecko/20100101 Firefox/40.0")
//                .get()
//                .body()
//                .text();
        String json1 = Jsoup.connect(url1).ignoreContentType(true).execute().body();
        String json2 = Jsoup.connect(url2).ignoreContentType(true).execute().body();
        String json3 = Jsoup.connect(url3).ignoreContentType(true).execute().body();
        System.out.println(json1);
        System.out.println(json2);
        System.out.println(json3);



    }
}
