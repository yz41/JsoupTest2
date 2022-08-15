package util;


import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo.Content;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class HtmlParseUtil2 {
    public static void main(String[] args)  {

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("信息.txt"));
            String url = "http://www.xinhuanet.com/legal/ej.htm?page=fzzt";
//            String url = "http://da.wa.news.cn/nodeart/page?nid=11227931&pgnum=%s&cnt=10&attr=&tp=1&orderby=1";

            List<Content> contents = new HtmlParseUtil2().getData(url);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < contents.size(); i++) {

                stringBuffer.append(contents.get(i).getTitle() + "\t" + contents.get(i).getTime());
                stringBuffer.append("\n");
            }
            out.write(String.valueOf(stringBuffer));
            out.close();
            System.out.println("Html信息爬取成功！");
        } catch (IOException e) {
        }


    }

    static List<Content> getData(String url) throws IOException {
//        ArrayList<Content> arrayList = new ArrayList<>();


//        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);//关闭警告
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
//            page = null;

            page = webClient.getPage(url);//尝试加载给出的网页
//            HtmlPage page2 = webClient.getPage(url);
//            WebResponse webResponse2 = page2.getWebResponse();
//            String contentAsString = webResponse2.getContentAsString(StandardCharsets.UTF_8);
//            System.out.println(contentAsString.toString());
            webClient.waitForBackgroundJavaScript(3000);//异步JS执行需要耗时,所以这里线程要阻塞3秒,等待异步JS执行结束

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }

//        Object o = page.getByXPath("/html/body/div[4]/div/div[2]/div/div/div/img").get(0);
//        System.out.println(o);
//        HtmlDivision htmlDivision = (HtmlDivision)page.getFirstByXPath("/html/body/div[4]/div/div[2]/div/div/div/img");//定位到图片
//        Page click = htmlDivision.click();
//        WebResponse webResponse = click.getWebResponse();
//        webResponse.to
//        Page click = firstByXPath.click();//点击图片
//        firstByXPath.setAttribute("");//这里应该要写什么东西
//        System.out.println(webResponse.toString());
//        WebResponse webResponse = firstByXPath.click().getWebResponse();//获取点击图片后返回的结果
//        WebResponse webResponse = click.getWebResponse();
//        String contentAsString = webResponse.getContentAsString(StandardCharsets.UTF_8);
////        JSONObject jsonObject = JSONObject.fromObject(firstByXPath.click());
//        System.out.println(contentAsString);

//        WebResponse webResponse = page.getWebResponse();
//        System.out.println(webResponse.toString());
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
//        System.out.println("+++++++++++++++");
//        System.out.println(arrayList);
//        System.out.println("++++++++++++++++++++");
//        WebResponse pageWebResponse = page.getWebResponse();
//        System.out.println(pageWebResponse);
        //        Elements elementsByClass = parse.getElementsByClass("xpage-more-btn");
//        Elements img = elementsByClass.get(0).getElementsByTag("img");
//
//        System.out.println(img);

//        return arrayList;
//        System.out.println(li);
        return arrayList;
    }
}