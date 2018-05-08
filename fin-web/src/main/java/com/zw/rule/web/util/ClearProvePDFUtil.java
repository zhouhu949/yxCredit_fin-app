package com.zw.rule.web.util;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/7/14 0014.
 */
public class ClearProvePDFUtil {
    private static Logger logger = LoggerFactory.getLogger(ClearProvePDFUtil.class);
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

    }

    public static void generatePdf(String directoryName,String fileName,JSONObject params) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = null;
        File directory = null;
        File file = null;
        try {
            directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new File(directoryName + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        document.open();
        chapter5(document, writer,params);
        document.close();
    }

    private static void chapter5(Document document, PdfWriter writer, JSONObject params) throws Exception {
        String custName=params.getString("custName");
        String custIc=params.getString("custIc");
        String year=params.getString("year");
        String month=params.getString("month");
        String day=params.getString("day");
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Font fontChinesed = new Font(bfChinese, 20, Font.NORMAL);
        Font fontChinesed1 = new Font(bfChinese, 15, Font.NORMAL);

        Paragraph par = new Paragraph("\n\n结清证明", fontChinesed);
        par.setAlignment(Element.TITLE);

        Paragraph p1 = new Paragraph("\n", fontChinesed1);
        p1.add("         兹证明     "+custName+"（身份证号  "+custIc+"）    作为借款人与我司作为第三方推荐的出借人之间的债务关系于   "+year+" 年  "+month+" 月  "+day+" 日   结清，各方签订的相关协议（具体协议以第三方登记备案为准）于当日自动失效。特此证明！（以下空白无正文）");
        p1.setAlignment(Element.ALIGN_LEFT);

        Paragraph p2 = new Paragraph("\n\n\n\n\n见证方：合肥小指信息科技有限公司", fontChinesed1);
        p2.add("\n 日  期：   "+year+"\t年\t"+month+"\t月\t"+day+"\t日\t");
        p2.setAlignment(Element.ALIGN_RIGHT);
        try {
            document.add(par);
            document.add(p1);
            document.add(p2);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
