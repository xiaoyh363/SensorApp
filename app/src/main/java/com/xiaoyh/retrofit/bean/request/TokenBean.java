package com.xiaoyh.retrofit.bean.request;

import android.support.annotation.NonNull;
import com.xiaoyh.retrofit.util.common.SPUtil;

public class TokenBean {

    private int appId = SPUtil.INSTANCE.getInt("appId");

    private String token = SPUtil.INSTANCE.getString("token");

    private static TokenBean instance = new TokenBean();

    private TokenBean() {
    }

    public static TokenBean getInstance() {
        return instance;
    }

    public int getAppId() {
        return appId;
    }

    public String getToken() {
        return token;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isEmpty() {
        return this.token == null;
    }

    @NonNull
    @Override
    public String toString() {
        return "appId : " + appId + " , " + "token : " + token;
    }
}
