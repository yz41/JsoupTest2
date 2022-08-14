package util;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;



public class HtmlParseUtil2 {
    public static void main(String[] args)  {


        String url = "http://da.wa.news.cn/nodeart/page?nid=11227931&pgnum=1&cnt=10&attr=&tp=1&orderby=1";
        String url2 = "http://da.wa.news.cn/nodeart/page?nid=11227931&pgnum=2&cnt=10&attr=&tp=1&orderby=1";
        String url3 = "http://da.wa.news.cn/nodeart/page?nid=11227931&pgnum=3&cnt=10&attr=&tp=1&orderby=1";
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
            String contentAsString = page.getWebResponse().getContentAsString();
            System.out.println(contentAsString);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束


    }
}
