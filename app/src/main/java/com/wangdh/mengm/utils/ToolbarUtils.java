package com.wangdh.mengm.utils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
/**
 *
 */

public class ToolbarUtils {
    public static void init(Toolbar toolbar, int icon, AppCompatActivity activity){
        toolbar.setNavigationIcon(icon);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> activity.finish());
    }

    public static void initTitle(Toolbar toolbar, int icon,String title, AppCompatActivity activity){
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(icon);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> activity.finish());
    }
    public static void inittwo(Toolbar toolbar, int color, String title, int icon, AppCompatActivity activity){
        toolbar.setTitleTextColor(color);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(icon);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> activity.finish());
    }
}
