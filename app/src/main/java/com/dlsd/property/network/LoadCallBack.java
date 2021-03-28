package com.dlsd.property.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

//添加对请求时对话框的处理
public abstract class LoadCallBack<T> extends BaseCallBack<T> {
    private Context context;
    //private SpotsDialog spotsDialog;

    public LoadCallBack(Context context) {
        this.context = context;
        //spotsDialog = new SpotsDialog(context);
    }

    private void showDialog() {
        //spotsDialog.show();
    }

    private void hideDialog() {
//        if (spotsDialog != null) {
//            spotsDialog.dismiss();
//        }
    }

    public void setMsg(String str) {
        //spotsDialog.setMessage(str);
    }

    public void setMsg(int resId) {
        //spotsDialog.setMessage(context.getString(resId));
    }


    @Override
    protected void OnRequestBefore(Request request) {
        showDialog();

    }

    @Override
    protected void onFailure(Call call, IOException e) {
        hideDialog();
    }

    @Override
    protected void onResponse(Response response) {
        hideDialog();
    }

    @Override
    protected void inProgress(int progress, long total, int id) {

    }
}
