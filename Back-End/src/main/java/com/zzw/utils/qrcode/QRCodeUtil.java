package com.zzw.utils.qrcode;

import com.baidu.aip.util.Base64Util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Component
@ConfigurationProperties("qr")
public class QRCodeUtil {

    @Value("${qr.url}")
    private String url;  //扫描二维码后要前往的路径
    @Value("${qr.format}")
    private String format;  //生成的二维码图片的格式（jpg/png）

    public String getQRCodeUrl(){
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bt = null;
        try {
            bt = writer.encode(url, BarcodeFormat.QR_CODE,200,200);
            //1、拿到流信息进行base64的编码处理就行了
            //将二维码bt以字节数组的形式保存到输出流当中去
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bt);
            ImageIO.write(image,format,outputStream);  //这一步完成后outputStream对象中就有了二维码的字节流了

            //2、对字节流进行base64的处理
            String encode = Base64Util.encode(outputStream.toByteArray());

            //3、生成url base64访问
            String url = new String("data:image/png;base64," + encode);

            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
