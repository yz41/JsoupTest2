package util;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo.Content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class HtmlParseUtil2 {
    public static void main(String[] args)  {


        String url = "http://www.xinhuanet.com/legal/ej.htm?page=fzzt";

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);//不启用ActiveX
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.getOptions().setDownloadImages(false);//不下载图片
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        HtmlPage page = null;
        try {
            page = webClient.getPage(url);//尝试加载给出的网页

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
//        WebResponse pageWebResponse = page.getWebResponse();
//        System.out.println(pageWebResponse);

        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
        Document parse = Jsoup.parse(pageXml);


        Element ul = parse.getElementsByTag("ul").eq(4).get(0);//定位到那一列
        Elements li = ul.getElementsByTag("li");
        ArrayList<Content> arrayList = new ArrayList<>();
        for (Element el : li) {
            String title = el.getElementsByTag("a").eq(1).text();
            String time = el.getElementsByClass("time").eq(0).text();
            Content content = new Content();
            content.setTitle(title);
            content.setTime(time);
            arrayList.add(content);
        }
        System.out.println(arrayList);

        //        Elements elementsByClass = parse.getElementsByClass("xpage-more-btn");
//        Elements img = elementsByClass.get(0).getElementsByTag("img");
//
//        System.out.println(img);

//        return arrayList;
//        System.out.println(li);
    }
}