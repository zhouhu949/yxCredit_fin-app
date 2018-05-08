package com.zw.base.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author xiahaiyang
 * @Create 2017年11月14日19:34:11
 **/
public class ContextToPdf {
    public static void insertPDF(List<Map<String,Object>> listValue, List<String> headList,String url) throws IOException {
        // 1.新建document对象
        Document document = new Document();
        FileOutputStream fos = null;
        try {
            //fos = new FileOutputStream(map.get("url").toString());
            fos = new FileOutputStream(url);
            // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
            // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            // 3.打开文档
            document.open();
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfChinese);
            font.setSize(5);
            PdfPTable table = new PdfPTable(headList.size());
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
            //cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
            Phrase phraseTitle=new Phrase();
            phraseTitle.add("通话记录");
            phraseTitle.setFont(font);
            cell.setColspan(headList.size());
            cell.addElement(phraseTitle);
            //cell1.setUseBorderPadding(true);
            cell.setBorderWidth(0.1f);
            cell.setBorderColor(BaseColor.BLACK);
            table.addCell(cell);
            table.completeRow();
            for(int i=0;i<headList.size();i++){
                PdfPCell cell1 = new PdfPCell();
                Phrase phrase=new Phrase();
                phrase.add(headList.get(i));
                phrase.setFont(font);
                cell1.addElement(phrase);
                //cell1.setUseBorderPadding(true);
                cell1.setBorderWidth(0.1f);
                cell1.setBorderColor(BaseColor.BLACK);
                table.addCell(cell1);
            }
            for (int i=0;i<listValue.size();i++){
                Map<String,Object> map=listValue.get(i);

                PdfPCell cell1 = new PdfPCell();
                Phrase phrase=new Phrase();
                phrase.add(map.get("phone_num")==null?"":map.get("phone_num").toString());
                phrase.setFont(font);
                cell1.addElement(phrase);
                //cell1.setUseBorderPadding(true);
                cell1.setBorderWidth(0.1f);
                cell1.setBorderColor(BaseColor.BLACK);
                table.addCell(cell1);

                PdfPCell cell2 = new PdfPCell();
                Phrase phrase1=new Phrase();
                phrase1.add(map.get("phone_num_loc")==null?"":map.get("phone_num_loc").toString());
                phrase1.setFont(font);
                cell2.addElement(phrase1);
                //cell1.setUseBorderPadding(true);
                cell2.setBorderWidth(0.1f);
                cell2.setBorderColor(BaseColor.BLACK);
                table.addCell(cell2);

                PdfPCell cell3 = new PdfPCell();
                Phrase phrase2=new Phrase();
                phrase2.add(map.get("contact_name")==null?"":map.get("contact_name").toString());
                phrase2.setFont(font);
                cell3.addElement(phrase2);
                //cell1.setUseBorderPadding(true);
                cell3.setBorderWidth(0.1f);
                cell3.setBorderColor(BaseColor.BLACK);
                table.addCell(cell3);

                PdfPCell cell4 = new PdfPCell();
                Phrase phrase3=new Phrase();
                phrase3.add(map.get("needs_type")==null?"":map.get("needs_type").toString());
                phrase3.setFont(font);
                cell4.addElement(phrase3);
                //cell1.setUseBorderPadding(true);
                cell4.setBorderWidth(0.1f);
                cell4.setBorderColor(BaseColor.BLACK);
                table.addCell(cell4);

                PdfPCell cell5 = new PdfPCell();
                Phrase phrase4=new Phrase();
                phrase4.add(map.get("call_cnt")==null?"":map.get("call_cnt").toString());
                phrase4.setFont(font);
                cell5.addElement(phrase4);
                //cell1.setUseBorderPadding(true);
                cell5.setBorderWidth(0.1f);
                cell5.setBorderColor(BaseColor.BLACK);
                table.addCell(cell5);

                PdfPCell cell6 = new PdfPCell();
                Phrase phrase5=new Phrase();
                phrase5.add(map.get("call_len")==null?"":map.get("call_len").toString());
                phrase5.setFont(font);
                cell6.addElement(phrase5);
                //cell1.setUseBorderPadding(true);
                cell6.setBorderWidth(0.1f);
                cell6.setBorderColor(BaseColor.BLACK);
                table.addCell(cell6);

                PdfPCell cell7 = new PdfPCell();
                Phrase phrase6=new Phrase();
                phrase6.add(map.get("call_out_cnt")==null?"":map.get("call_out_cnt").toString());
                phrase6.setFont(font);
                cell7.addElement(phrase6);
                //cell1.setUseBorderPadding(true);
                cell7.setBorderWidth(0.1f);
                cell7.setBorderColor(BaseColor.BLACK);
                table.addCell(cell7);

                PdfPCell cell8 = new PdfPCell();
                Phrase phrase7=new Phrase();
                phrase7.add(map.get("call_out_len")==null?"":map.get("call_out_len").toString());
                phrase7.setFont(font);
                cell8.addElement(phrase7);
                //cell1.setUseBorderPadding(true);
                cell8.setBorderWidth(0.1f);
                cell8.setBorderColor(BaseColor.BLACK);
                table.addCell(cell8);

                PdfPCell cell9 = new PdfPCell();
                Phrase phrase8=new Phrase();
                phrase8.add(map.get("call_in_cnt")==null?"":map.get("call_in_cnt").toString());
                phrase8.setFont(font);
                cell9.addElement(phrase8);
                //cell1.setUseBorderPadding(true);
                cell9.setBorderWidth(0.1f);
                cell9.setBorderColor(BaseColor.BLACK);
                table.addCell(cell9);

                PdfPCell cell10 = new PdfPCell();
                Phrase phrase9=new Phrase();
                phrase9.add(map.get("call_in_len")==null?"":map.get("call_in_len").toString());
                phrase9.setFont(font);
                cell10.addElement(phrase9);
                //cell1.setUseBorderPadding(true);
                cell10.setBorderWidth(0.1f);
                cell10.setBorderColor(BaseColor.BLACK);
                table.addCell(cell10);

                PdfPCell cell11 = new PdfPCell();
                Phrase phrase10=new Phrase();
                phrase10.add(map.get("p_relation")==null?"":map.get("p_relation").toString());
                phrase10.setFont(font);
                cell11.addElement(phrase10);
                //cell1.setUseBorderPadding(true);
                cell11.setBorderWidth(0.1f);
                cell11.setBorderColor(BaseColor.BLACK);
                table.addCell(cell11);

                PdfPCell cell12 = new PdfPCell();
                Phrase phrase11=new Phrase();
                phrase11.add(map.get("contact_1w")==null?"":map.get("contact_1w").toString());
                phrase11.setFont(font);
                cell12.addElement(phrase11);
                //cell1.setUseBorderPadding(true);
                cell12.setBorderWidth(0.1f);
                cell12.setBorderColor(BaseColor.BLACK);
                table.addCell(cell12);

                PdfPCell cell13 = new PdfPCell();
                Phrase phrase12=new Phrase();
                phrase12.add(map.get("contact_1m")==null?"":map.get("contact_1m").toString());
                phrase12.setFont(font);
                cell13.addElement(phrase12);
                //cell1.setUseBorderPadding(true);
                cell13.setBorderWidth(0.1f);
                cell13.setBorderColor(BaseColor.BLACK);
                table.addCell(cell13);

                PdfPCell cell14 = new PdfPCell();
                Phrase phrase13=new Phrase();
                phrase13.add(map.get("contact_3m")==null?"":map.get("contact_3m").toString());
                phrase13.setFont(font);
                cell14.addElement(phrase13);
                //cell1.setUseBorderPadding(true);
                cell14.setBorderWidth(0.1f);
                cell14.setBorderColor(BaseColor.BLACK);
                table.addCell(cell14);

                PdfPCell cell15 = new PdfPCell();
                Phrase phrase14=new Phrase();
                phrase14.add(map.get("contact_3m_plus")==null?"":map.get("contact_3m_plus").toString());
                phrase14.setFont(font);
                cell15.addElement(phrase14);
                //cell1.setUseBorderPadding(true);
                cell15.setBorderWidth(0.1f);
                cell15.setBorderColor(BaseColor.BLACK);
                table.addCell(cell15);

                PdfPCell cell16 = new PdfPCell();
                Phrase phrase15=new Phrase();
                phrase15.add(map.get("contact_early_morning")==null?"":map.get("contact_early_morning").toString());
                phrase15.setFont(font);
                cell16.addElement(phrase15);
                //cell1.setUseBorderPadding(true);
                cell16.setBorderWidth(0.1f);
                cell16.setBorderColor(BaseColor.BLACK);
                table.addCell(cell16);

                PdfPCell cell17 = new PdfPCell();
                Phrase phrase16=new Phrase();
                phrase16.add(map.get("contact_morning")==null?"":map.get("contact_morning").toString());
                phrase16.setFont(font);
                cell17.addElement(phrase16);
                //cell1.setUseBorderPadding(true);
                cell17.setBorderWidth(0.1f);
                cell17.setBorderColor(BaseColor.BLACK);
                table.addCell(cell17);

                PdfPCell cell18 = new PdfPCell();
                Phrase phrase17=new Phrase();
                phrase17.add(map.get("contact_noon")==null?"":map.get("contact_noon").toString());
                phrase17.setFont(font);
                cell18.addElement(phrase17);
                //cell1.setUseBorderPadding(true);
                cell18.setBorderWidth(0.1f);
                cell18.setBorderColor(BaseColor.BLACK);
                table.addCell(cell18);

                PdfPCell cell19 = new PdfPCell();
                Phrase phrase18=new Phrase();
                phrase18.add(map.get("contact_afternoon")==null?"":map.get("contact_afternoon").toString());
                phrase18.setFont(font);
                cell19.addElement(phrase18);
                //cell1.setUseBorderPadding(true);
                cell19.setBorderWidth(0.1f);
                cell19.setBorderColor(BaseColor.BLACK);
                table.addCell(cell19);

                PdfPCell cell20 = new PdfPCell();
                Phrase phrase19=new Phrase();
                phrase19.add(map.get("contact_night")==null?"":map.get("contact_night").toString());
                phrase19.setFont(font);
                cell20.addElement(phrase19);
                //cell1.setUseBorderPadding(true);
                cell20.setBorderWidth(0.1f);
                cell20.setBorderColor(BaseColor.BLACK);
                table.addCell(cell20);

                PdfPCell cell21 = new PdfPCell();
                Phrase phrase20=new Phrase();
                phrase20.add(map.get("contact_all_day")==null?"":map.get("contact_all_day").toString());
                phrase20.setFont(font);
                cell21.addElement(phrase20);
                //cell1.setUseBorderPadding(true);
                cell21.setBorderWidth(0.1f);
                cell21.setBorderColor(BaseColor.BLACK);
                table.addCell(cell21);

                PdfPCell cell22 = new PdfPCell();
                Phrase phrase21=new Phrase();
                phrase21.add(map.get("contact_weekday")==null?"":map.get("contact_weekday").toString());
                phrase21.setFont(font);
                cell22.addElement(phrase21);
                //cell1.setUseBorderPadding(true);
                cell22.setBorderWidth(0.1f);
                cell22.setBorderColor(BaseColor.BLACK);
                table.addCell(cell22);

                PdfPCell cell23 = new PdfPCell();
                Phrase phrase22=new Phrase();
                phrase22.add(map.get("contact_weekend")==null?"":map.get("contact_weekend").toString());
                phrase22.setFont(font);
                cell23.addElement(phrase22);
                //cell1.setUseBorderPadding(true);
                cell23.setBorderWidth(0.1f);
                cell23.setBorderColor(BaseColor.BLACK);
                table.addCell(cell23);

                PdfPCell cell24 = new PdfPCell();
                Phrase phrase23=new Phrase();
                phrase23.add(map.get("contact_holiday")==null?"":map.get("contact_holiday").toString());
                phrase23.setFont(font);
                cell24.addElement(phrase23);
                //cell1.setUseBorderPadding(true);
                cell24.setBorderWidth(0.1f);
                cell24.setBorderColor(BaseColor.BLACK);
                table.addCell(cell24);

            }

            document.add(table);
            // 4.添加一个内容段落
            //document.add(new Paragraph(context, font));
            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }


}
