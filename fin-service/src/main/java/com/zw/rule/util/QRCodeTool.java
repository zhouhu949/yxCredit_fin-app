package com.zw.rule.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.output.ByteArrayOutputStream;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by Administrator on 2017/11/22 0022.
 */
public class QRCodeTool {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "png";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    private static BufferedImage createImage(String content, String imgPath,
                                             boolean needCompress) throws IOException, WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入logo图片
        QRCodeTool.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source
     *            二维码图片
     * @param imgPath
     *            LOGO图片地址
     * @param needCompress
     *            是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath,
                                    boolean needCompress) throws IOException, WriterException {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println(""+imgPath+"   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     *            内容
     * @param imgPath
     *            LOGO地址
     * @param destPath
     *            存放目录
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath,
                              boolean needCompress) throws Exception {
        BufferedImage image = QRCodeTool.createImage(content, imgPath,
                needCompress);
        mkdirs(destPath);
        String file = new Random().nextInt(99999999)+".jpg";
        ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     * @author lanyuan
     * Email: mmm333zzz520@163.com
     * @date 2013-12-11 上午10:16:36
     * @param destPath 存放目录
     */
    public static void mkdirs(String destPath) {
        File file =new File(destPath);
        //当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     *            内容
     * @param imgPath
     *            LOGO地址
     * @param destPath
     *            存储地址
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath)
            throws Exception {
        QRCodeTool.encode(content, imgPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content
     *            内容
     * @param destPath
     *            存储地址
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String destPath,
                              boolean needCompress) throws Exception {
        QRCodeTool.encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content
     *            内容
     * @param destPath
     *            存储地址
     * @throws Exception
     */
    public static void encode(String content, String destPath) throws Exception {
        QRCodeTool.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     *            内容
     * @param imgPath
     *            LOGO地址
     * @param output
     *            输出流
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath,
                              OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeTool.createImage(content, imgPath,
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 生成二维码
     *
     * @param content
     *            内容
     * @param output
     *            输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output)
            throws Exception {
        QRCodeTool.encode(content, null, output, false);
    }

    /**
     * 解析二维码
     *
     * @param file
     *            二维码图片
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path
     *            二维码图片地址
     * @return
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        return QRCodeTool.decode(new File(path));
    }

    public static String encodeBase64(String content, String imgPath) throws IOException, WriterException  {
        BufferedImage image = QRCodeTool.createImage(content, imgPath,true);
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, FORMAT_NAME, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/png;base64," + imageString;
    }

    //测试代码
    public static void main(String[] args) throws Exception {
        StringBuffer sendRequest = new StringBuffer();
        sendRequest.append("{");
        sendRequest.append("\"empNumber\": \"");//业务员工号
        sendRequest.append("zzl4596a");
        sendRequest.append("\",");
        sendRequest.append("\"allMoney\": \"");//商品总价
        sendRequest.append("6000");
        sendRequest.append("\",");
        sendRequest.append("\"downPayMoney\": \"");//首付金额
        sendRequest.append("1000");
        sendRequest.append("\",");
        sendRequest.append("\"merchantId\": \"");//商户id
        sendRequest.append("db3f781b-961b-4799-bddc-a019e00b3745");
        sendRequest.append("\",");
        sendRequest.append("\"productId\": \"");//产品id
        sendRequest.append("13c3f190-0a80-4d00-bfbf-0f52f01328e5");
        sendRequest.append("\",");
        sendRequest.append("\"idJson\": \"");//服务包id集合;
        sendRequest.append("'e3959b30-e9db-473b-950f-b084f1e9e2cd','d786a249-f328-4b70-9f65-6001280a8568'");
        sendRequest.append("\",");
        sendRequest.append("\"merchandiseId\": \"");//商品id
        sendRequest.append("625fa000-fc91-4d66-be14-2180eb6768dd");
        sendRequest.append("\"");
        sendRequest.append("}");
        String data = sendRequest.toString();
//        QRCodeTool.encode(text, "F:\\qrCode/logo.jpg", "F:\\qrCode", true);
        String base64String = QRCodeTool.encodeBase64(data, "D:\\3.png");
        System.out.print("=========="+base64String);
//        String base64String1 = QRCodeTool.decode("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAAEsCAIAAAD2HxkiAAAGUElEQVR42u3cQZLcMAwEQf3/0+M3\n" +
//                "rEMguqms84QskUheuOHnJ2m1xxJIEEoQSoJQglAShBKEkiCUIJQEoQShJAglCCVBKEEoCUIJQkmR\n" +
//                "CJ+SXlum4ee3vOdfn//Wft09bxBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhP0Ibx2+6UOq\n" +
//                "BcM0km/OG4QQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEII4b0IWzbJd51Ba18g9F0QQgghhPYF\n" +
//                "Qt8FIYQQQgghhL4LQgghhBBCCH0XhBAa1rym92V6fyGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQggh\n" +
//                "hBBCCCGEsBnt1vPTDhEIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEII/ee/G6i2vjftsEi7\n" +
//                "9G+fNwghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQwlsQprU1lH5/5vct8wahIYYQQgj9HkII\n" +
//                "DTGEEELo9xBCaIghhBBCv4cQQkMM4S0I25te3OnnpB1Gu0NcP40QQgghhBBCCCGEEEIIIYQQQggh\n" +
//                "hBBCCCGEEEIIIYTXItwa+q3h2BruNORbc9KFHEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGE\n" +
//                "sB/h9PC14E871NKGuOWPCiCEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCE8W9qQtV/uT69z\n" +
//                "2jr8qoIQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIexHePelas6h045zq8w5hBBCCCGEEEII\n" +
//                "IYQQQgghhBBCCCGEEEIIIYQQQggh7EHYcsnbjjkNYft3Tb8nhBBCCCGEEEIIIYQQQgghhBBCCCGE\n" +
//                "EEIIIYQQQgjhVxHufswc2q1/d/qQSvv9Nw9lCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBDC\n" +
//                "HoTTi7uF3OV41zq0HGoQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYS3I9wamrTntxxet+5v\n" +
//                "2iEFIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEJ4C8K0oZl+zvTlb9qwPmGl7SOEEEIIIYQQ\n" +
//                "QgghhBBCCCGEEEIIIYQQQgghhBBCCCGEMy/dMvTtQzY9lO3PgRBCCCGEEEIIIYQQQgghhBBCCCGE\n" +
//                "EEIIIYQQQgghhPDsELQM39bl+C+slvWfnkMIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEII\n" +
//                "s4e75RK/5f13L7t75xNCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBDC7EVPe87Wun1tfc4c\n" +
//                "IhBCaH0ghBBCCCGE0PpACCGEEEIIofWBEEIIIYQQQuvTj/Brw5G2bi2HV9r7734XhBBCCCGEEEII\n" +
//                "IYQQQgghhBBCCCGEEEIIIYQQQghhD8IWbFvvP/2cZ6mW90/bXwghhBBCCCGEEEIIIYQQQgghhBBC\n" +
//                "CCGEEEIIIYQQwtsRtlxqt1wi37ov089JW38IIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEMJb\n" +
//                "EO6+9PnNSLuUn14f+3VyziGEEEIIbSqEEEIIIYQQ2lQIIYQQQgghtKkQQgghhBD2IGzvLQxpqLYw\n" +
//                "pOGf3pd33x9CCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhLAH4VPSmUXMH772P0LY+v2ZfYQQ\n" +
//                "QgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIexH2HL53nIJvnVpfushmDnnEEIIIYQQQgghhBBC\n" +
//                "CCGEEEIIIYQQQgghhBBCCCGE9yK0qf+3DtPrn3Zoth9SEEIIIYQQQgghhBBCCCGEEEIIIYQQQggh\n" +
//                "hBBCCCGEEGYj/NoQ34qh5dCEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEMBvh9Oa1/PFA\n" +
//                "2jqnzc/LRyqEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCOG1CLcur7829C3rlvZdu/sCIYQQ\n" +
//                "QgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEELYjzCtNPze88x3ffqyHkIIIYTQcEMIIYQQQgih4YYQ\n" +
//                "QgghhBBCww0hhBBCCOEgQkkQShBKglCCUBKEEoSSIJQglAShBKEkCCUIJUEoQSgJQglCSRBKJf0D\n" +
//                "pyCEclZufy0AAAAASUVORK5CYII=");
//        System.out.print("=========="+base64String1);
    }
}
