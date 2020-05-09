package com.wangdh.mengm.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import com.wangdh.mengm.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public  class StorageData {
    public StorageData(){}

    //保存图片路径
    public static void storePath(String path){
        File file = new File(Environment.getExternalStorageDirectory(),"storepath.txt");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file,false);
            out.write(path.getBytes("UTF-8"));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取文件保存路径
    public static String getString(){
        File file = new File(Environment.getExternalStorageDirectory(),"storepath.txt");
        FileInputStream input = null;
        ByteArrayOutputStream bout = null;
        try {
            bout = new ByteArrayOutputStream();
            input = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = input.read(buf)) != -1){
                bout.write(buf,0,len);
            }
            byte[] content = bout.toByteArray();
            return new String(content,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //设置头像图片
    public static void setHeadImage(ImageView photo, String url, Context context) {
        if (StorageData.getString() != null ){
            File file = new File(StorageData.getString());
            if (file.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(StorageData.getString());
                photo.setImageBitmap(bitmap);
            }else {
                photo.setImageResource(R.mipmap.xmm);
            }
        }else {
            if (url != "") {
                MyGlideImageLoader.displayImage(url,photo);
            }else {
                photo.setImageResource(R.mipmap.xmm);
            }
        }
    }
    public static String saveFile(Context c, String fileName, Bitmap bitmap) {
        return saveFile(c, "", fileName, bitmap);
    }

    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        String dateFolder = new SimpleDateFormat("yyyyMMdd HH-mm-ss", Locale.CHINA)
                .format(new Date());
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = Environment.getExternalStorageDirectory() + "/creatsoft/" + dateFolder + "/";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }
}
