package com.wangdh.mengm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * SharedPreferences管理类
 */
public class SharedPreferencesMgr {
    private static SharedPreferencesMgr prefsUtil;
    private static Context context;
    private static SharedPreferences sPrefs;
    private static SharedPreferences.Editor editor;

    public synchronized static SharedPreferencesMgr getInstance() {
        return prefsUtil;
    }

    private SharedPreferencesMgr(Context context, String fileName) {
        this.context = context;
        sPrefs = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        editor = this.sPrefs.edit();
    }

    public static void init(Context context, String fileName) {
        prefsUtil = new SharedPreferencesMgr(context, fileName);
        new SharedPreferencesMgr(context, fileName);
    }

    public static String fileName;

    public static int getInt(String key, int defaultValue) {
        return sPrefs.getInt(key, defaultValue);
    }

    public static void setInt(String key, int value) {
        sPrefs.edit().putInt(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sPrefs.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        sPrefs.edit().putBoolean(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        if (sPrefs == null)
            return defaultValue;
        return sPrefs.getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        if (sPrefs == null)
            return;
        sPrefs.edit().putString(key, value).commit();
    }
    /**
     * 移除Key对应的value值
     */
    public static void remove(Context context,String key){

        SharedPreferences sp = context.getSharedPreferences("mengm",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key).commit();
    }

    public static void clearAll() {
        if (sPrefs == null)
            return;
        sPrefs.edit().clear().commit();
    }

    public void putObject(String key, Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            editor.putString(key, objectVal);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> T getObject(String key, Class<T> clazz) {
        if (sPrefs.contains(key)) {
            String objectVal = sPrefs.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
