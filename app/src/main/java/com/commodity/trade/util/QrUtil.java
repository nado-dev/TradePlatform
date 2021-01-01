package com.commodity.trade.util;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * Created by AaFaa
 * on 2020/12/15
 * in package com.commodity.trade.util
 * with project trade
 * @author Aaron
 */
public class QrUtil {
    /**
     * @param content                   字符串内容
     * @param width                     二维码宽度
     * @param height                    二维码高度
     * @param characterSet              编码方式（一般使用UTF-8）
     * @param errorCorrectionLevel      容错率 L：7% M：15% Q：25% H：35%
     * @param margin                    空白边距（二维码与边框的空白区域）
     * @param colorBlack                黑色色块
     * @param colorWhite                白色色块
     * @return  BitMap
     */
    public static Bitmap createQrCodeBitmap(String content, int width, int height,
                                            String characterSet, String errorCorrectionLevel,
                                            String margin, int colorBlack, int colorWhite) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /* 1.设置二维码相关配置 */
            Hashtable <EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(characterSet)) {
                hints.put(EncodeHintType.CHARACTER_SET, characterSet);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(errorCorrectionLevel)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /* 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /* 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        //黑色色块像素设置
                        pixels[y * width + x] = colorBlack;
                    } else {
                        // 白色色块像素设置
                        pixels[y * width + x] = colorWhite;
                    }
                }
            }
            /* 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
