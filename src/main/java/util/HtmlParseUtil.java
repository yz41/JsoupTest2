package util;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParseUtil {


    public List<Content> parseXH() throws IOException {
        String url = "http://www.xinhuanet.com/legal/ej.htm?page=fzzt";
//        Document document = Jsoup.parse(new URL(url), 30000);
//        Document document1 = Jsoup.connect(url).timeout(500000).get();
//        System.out.println(document1.html());
//        Elements elementsByClass = document.getElementsByClass("grid-700 box-cont");
//        String text = elementsByClass.eq(0).text();
//        Element list = document.getElementById("list");
//        Elements ul = document.getElementsByTag("ul").eq(2);
//        Elements time = document.getElementsByClass("time");
//        String txt = ul.text();
//        Elements li = list.getElementsByTag("li");
//        Elements elements = list.getElementsByTag("li");
//        Elements tit = list.getElementsByClass("tit");
//        Elements elements = document.getElementsByClass("xpage-container");
//        Elements time2 = document.getElementsByClass("txt");
//        System.out.println(document);

//        String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//

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

//        DomElement elementByName = page.getElementByName("xpage-more-btn");
//        System.out.println("______________");
//        System.out.println(elementByName);
        Page click = ((DomElement) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div").get(0)).click();
        System.out.println(click);
//        ((DomElement) page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div").get(0)).click();
//        String s = next.asXml();
//        Document parse1 = Jsoup.parse(s);
//
//        System.out.println(parse1.html());

        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
        Document parse = Jsoup.parse(pageXml);

        Elements elementsByClass = parse.getElementsByClass("xpage-more-btn");
        Elements img = elementsByClass.get(0).getElementsByTag("img");

        System.out.println(img);


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

        return arrayList;
//        System.out.println(li);

    }


}
