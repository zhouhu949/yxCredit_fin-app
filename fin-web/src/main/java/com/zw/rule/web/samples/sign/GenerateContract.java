package com.zw.rule.web.samples.sign;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 生成合同
 * @Author xiahaiyang
 * @Create 2017年11月13日19:01:11
 **/
public class GenerateContract {
    //模板文件地址
    private static String inputUrl = "D:/miaofu.docx";
    private static String outputUrl = "D:/miaofu1.docx";

    public void runTest(){
        try {
            //解析docx模板并获取document对象
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //获取整个文本对象
            List<XWPFParagraph> allParagraph = document.getParagraphs();
            Calendar now = Calendar.getInstance();
            Map<String,String> map = new HashMap();
            map.put("name","夏海洋");
            String nameLast = "夏海洋";
            while(nameLast.length() < 27){
                nameLast += " ";
            }
            map.put("nameLast",nameLast);
            map.put("idCard","340102199403091515");
            map.put("phone","18019983538");
            map.put("yearSub",String.valueOf(now.get(Calendar.YEAR)).substring(2));
            map.put("year",String.valueOf(now.get(Calendar.YEAR)));
            map.put("month",String.valueOf(now.get(Calendar.MONTH)+1));
            map.put("day",String.valueOf(now.get(Calendar.DAY_OF_MONTH)));
            String yearLast = String.valueOf(now.get(Calendar.YEAR));
            String num = "";
            while(yearLast.length()+num.length() < 23){
                num += " ";
            }
            map.put("yearLast",num+yearLast);
            map.put("tongxin","合肥市瑶海区花溪新村19栋404室");
            map.put("email","2210937392@qq.com");
            map.put("benjin","123141");
            map.put("jieDate","2017年12月22日");
            map.put("huanDate","2017年12月23日");
            map.put("benjinChn","十贰万叁千壹百肆十壹元");
            map.put("jieTime","30天");
            map.put("lixi","3000元");
            map.put("bank","中国工商银行");
            map.put("bankAccount","6222021302019590255");
            //解析替换文本段落对象
            changeText(document,map);

            //生成新的word
            File file = new File(outputUrl);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 替换段落文本
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     */
    public static void changeText(XWPFDocument document, Map<String, String> textMap){
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {
            //判断此段落时候需要进行替换
            String text = paragraph.getText();
            if(checkText(text)){
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    String txt =changeValue(run.toString(), textMap);
                    System.out.println(txt);
                    run.setText(txt,0);
                }
            }
        }
    }

    /**
     * 判断文本中时候包含$
     * @param text 文本
     * @return 包含返回true,不包含返回false
     */
    public static boolean checkText(String text){
        boolean check  =  false;
        if(text.indexOf("$")!= -1){
            check = true;
        }
        return check;
    }

    /**
     * 匹配传入信息集合与模板
     * @param value 模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    public static String changeValue(String value, Map<String, String> textMap){
        Set<Entry<String, String>> textSets = textMap.entrySet();
        for (Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${"+textSet.getKey()+"}";
//            System.out.println(key+"--"+value.indexOf(key));
            if(value.indexOf(key)!= -1){
                value = textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
        if(checkText(value)){
            value = "";
        }
        return value;
    }

}
