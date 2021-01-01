package com.commodity.mylibrary.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AaFaa
 * on 2020/9/26
 * in package com.example.demo.util
 * with project demo
 */
public class BitmapUtil {


    public static void saveBitmapToLocal(Context context, Bitmap bitmap) {
        BitmapUtil.saveBitmapToLocal(context, bitmap, generateFilename());
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap   bitmap 位图
     * @param fileName 文件名
     */
    public static void saveBitmapToLocal(Context context, Bitmap bitmap, String fileName) {
        try {
            String FILE_PATH = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/photos/";

            File file = new File(FILE_PATH, fileName + ".png");
            // file是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                // 文件夹不存在
                fileParent.mkdirs();
                // 创建文件夹
            } // 将图片保存到本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Toast.makeText(context, "图片保存在" + FILE_PATH + fileName + ".png", Toast.LENGTH_SHORT).show();

            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + FILE_PATH + fileName + ".png")));
        } catch (Exception e) {
            Toast.makeText(context, "图片保存失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    static String generateFilename() {

        //根据当前时间创建文件名
        String date =
                new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault()).format(new Date());
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + File.separator + "ARHome/" + date + "_screenshot";
    }
}
