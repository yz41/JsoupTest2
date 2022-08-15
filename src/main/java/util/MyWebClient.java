package util;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import pojo.Content;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyWebClient {

    public static List<Content> getData(String url) throws IOException {
        WebClient webClient = new WebClient();
        List<Content> data = new ArrayList<>();
//        String url = "http://da.wa.news.cn/nodeart/page?nid=11227931&pgnum=%s&cnt=10&attr=&tp=1&orderby=1";
        for (int i = 1; i <= 3; i++) {
            String dynamicUrl = String.format(url, i);
            Page page = webClient.getPage(dynamicUrl);
            WebResponse webResponse = page.getWebResponse();
            String contentAsString = webResponse.getContentAsString(StandardCharsets.UTF_8);
            String titlePattern = "\"Title\":\"[\\d+\\u4e00-\\u9fa5+、\\s]*";
            String timePattern = "\"PubTime\":\"[\\d+\\-]*";
            Pattern r1 = Pattern.compile(titlePattern);
            Pattern r2 = Pattern.compile(timePattern);
            Matcher titleMatcher = r1.matcher(contentAsString);
            Matcher timeMatcher = r2.matcher(contentAsString);
            List<String> titleList = new ArrayList<>();
            List<String> timeList = new ArrayList<>();

            while (titleMatcher.find()) {
                String titleBeforeDeal = titleMatcher.group();
                titleList.add(titleBeforeDeal.split(":")[1].substring(1));
            }
            while (timeMatcher.find()) {
                String timeBeforeDeal = timeMatcher.group();
                timeList.add(timeBeforeDeal.split(":")[1].substring(1));
            }
            for (int j = 0; j < timeList.size(); j++) {
                Content content = new Content();
                content.setTitle(titleList.get(j));
                content.setTime(timeList.get(j));
                data.add(content);
            }
        }
        return data;
    }

    public static void main(String[] args) throws IOException {
//        List<Content> data = getData();
//        data.forEach(System.out::println);
    }
}
