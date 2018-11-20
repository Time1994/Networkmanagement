package com.eroadcar.networkmanagement;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.eroadcar.networkmanagement.bean.UserBean;

import java.util.LinkedList;
import java.util.List;


public class MyApplication extends Application {
    private static Context sContext;
    public static boolean isDownload;
    public static String pathUrl;
    private List<Activity> activityList = new LinkedList<Activity>();
    public static String shopnum, inputnum, pwdnum;
    private UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
           }

    public static Context getContext() {
        return sContext;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            try {
                activity.finish();
            } catch (Exception ex) {

            }
        }
        // System.exit(0);
    }

    @Override
    public void onLowMemory() {
        System.gc();
        super.onLowMemory();
    }

}
