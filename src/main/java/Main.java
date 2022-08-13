import pojo.Content;
import util.HtmlParseUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args)  {
//        String string= new HtmlParseUtil().parseXH().forEach(this.toString()+"\n");

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("信息.txt"));



            List<Content> contents = new HtmlParseUtil().parseXH();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < contents.size(); i++) {

                stringBuffer.append(contents.get(i).getTitle()+"\t"+contents.get(i).getTime());
                stringBuffer.append("\n");
            }
            out.write(String.valueOf(stringBuffer));
            out.close();
            System.out.println("信息创建成功！");
        } catch (IOException e) {
        }


    }
}
