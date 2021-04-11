package com.dlsd.property.network;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.dlsd.property.MyApp;
import com.dlsd.property.activitys.base.LoginActivity;
import com.dlsd.property.crash.FileUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.dlsd.property.crash.CrashHandler.getGlobalpath;

public class OkHttpManager {

    private static OkHttpManager mOkHttpManager;

    private OkHttpClient mOkHttpClient;

    private Gson mGson;

    private Handler handler;

    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new TokenInterceptor()).build();
        mGson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }

    //创建 单例模式（OkHttp官方建议如此操作）
    public static OkHttpManager getInstance() {
        if (mOkHttpManager == null) {
            mOkHttpManager = new OkHttpManager();
        }
        return mOkHttpManager;
    }

    /***********************
     * 对外公布的可调方法
     ************************/

    public void getRequest(Context context, String url, final BaseCallBack callBack) {
        Request request = buildRequest(context, url, "", null, HttpMethodType.GET);
        doRequest(context, request, callBack);
    }

    public void getRequest(Context context, String url, Map<String, String> params, final BaseCallBack callBack) {
        Request request = buildRequest(context, url, "", params, HttpMethodType.GET);
        doRequest(context, request, callBack);
    }

    public void postRequest(Context context, String url, String type, final BaseCallBack callBack, Map<String, String> params) {
        Request request = buildRequest(context, url, type, params, HttpMethodType.POST);
        doRequest(context, request, callBack);
    }

    public void postRequest(Context context, String url, String type, final BaseCallBack callBack, JSONObject params) {
        Request request = buildFileRequest(context, url, type, params, HttpMethodType.POST);
        doRequest(context, request, callBack);
    }

    /**
     * 登录接口专用
     *
     * @param context
     * @param url
     * @param type
     * @param callBack
     * @param params
     */
    public void postLoginRequest(Context context, String url, String type, final BaseCallBack callBack, Map<String, String> params) {
        Request request = buildLoginRequest(context, url, type, params, HttpMethodType.POST);
        doRequest(context, request, callBack);
    }

    public void postFileRequest(Context context, String url, String type, final BaseCallBack callBack, JSONObject params) {
        Request request = buildFileRequest(context, url, type, params, HttpMethodType.POST);
        doRequest(context, request, callBack);
    }

    //去进行网络 异步 请求
    private void doRequest(Context context, Request request, final BaseCallBack callBack) {
        callBack.OnRequestBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //callBack.onFailure(call, e);
                callBackFailure(callBack, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(response);
                String result = response.body().string();
                Log.e("ljh", "onResponse = " + result);
                writeFile("url = " + response.toString() + "\r\n" + "onResponse = " + result);
                if (response.isSuccessful()) {

                    if (callBack.mType == String.class) {
//                        callBack.onSuccess(call, response, result);
                        callBackSuccess(callBack, call, response, result);
                    } else {
                        try {
                            Object object = mGson.fromJson(result, callBack.mType);//自动转化为 泛型对象
//                            callBack.onSuccess(call, response, object);
                            callBackSuccess(callBack, call, response, object);
                        } catch (JsonParseException e) {
                            //json解析错误时调用
                            callBackError(callBack, call, response.code());
                        }

                    }
                } else if (response.code() == 401) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else {
                    callBackError(callBack, call, response.code());
                }

            }

        });


    }

    //创建 Request对象
    private Request buildRequest(Context context, String url, String type, Map<String, String> params, HttpMethodType methodType) {
        Request.Builder builder = new Request.Builder();
        if (methodType == HttpMethodType.GET) {
            if (params != null) {
                Iterator<String> it = params.keySet().iterator();
                StringBuffer sb = null;
                while (it.hasNext()) {
                    String key = it.next();
                    String value = params.get(key);
                    if (sb == null) {
                        sb = new StringBuffer();
                        sb.append("?");
                    } else {
                        sb.append("&");
                    }
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                }
                if (null != sb) {
                    url += sb.toString();
                }
            }
            Log.e("ljh", "" + url);
            writeFile("onRequest = " + url);
            builder.url(url);
            builder.get();
        } else if (methodType == HttpMethodType.POST) {
            builder.url(url);
            RequestBody requestBody;
            if (null != type && type.equals("json")) {
                String p = mGson.toJson(params);
                Log.e("ljh", "" + p);
                writeFile("onRequest = " + p);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                requestBody = RequestBody.create(JSON, p);
            } else {
                requestBody = buildFormData(params);
                Log.e("ljh", "" + params.toString());
                writeFile("onRequest = " + params.toString());
            }
            builder.post(requestBody);
        }
        if (null != MyApp.mUserLogin) {
            builder.addHeader("Authorization", "Bearer " + MyApp.mUserLogin.getPhone());
        }
        return builder.build();
    }


    //创建 Request对象
    private Request buildFileRequest(Context context, String url, String type, JSONObject params, HttpMethodType methodType) {
        Request.Builder builder = new Request.Builder();
        if (methodType == HttpMethodType.POST) {
            builder.url(url);
            RequestBody requestBody;
            String p = params.toString();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            requestBody = RequestBody.create(JSON, p);
            builder.post(requestBody);
        }
        if (null != MyApp.mUserLogin) {
            builder.addHeader("Authorization", "Bearer " + MyApp.mUserLogin.getPhone());
        }
        return builder.build();
    }

    //创建 Request对象
    private Request buildLoginRequest(Context context, String url, String type, Map<String, String> params, HttpMethodType methodType) {
        Request.Builder builder = new Request.Builder();
        if (methodType == HttpMethodType.POST) {
            builder.url(url);
            RequestBody requestBody;
            if (null != type && type.equals("json")) {
                String p = mGson.toJson(params);
                writeFile("onRequest = " + p);
                Log.e("ljh", "" + p);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                requestBody = RequestBody.create(JSON, p);
            } else {
                Log.e("ljh", "" + params.toString());
                writeFile("onRequest = " + params.toString());
                requestBody = buildFormData(params);
            }
            builder.post(requestBody);
        }
        builder.addHeader("Authorization", "Basic YXBwOmFwcA==");
        return builder.build();
    }

    //构建请求所需的参数表单
    private RequestBody buildFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "android");
        builder.add("version", "1.0");
        builder.add("key", "123456");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private void callBackSuccess(final BaseCallBack callBack, final Call call, final Response response, final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(call, response, object);
            }
        });

    }

    private void callBackError(final BaseCallBack callBack, final Call call, final int code) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onEror(call, code, null);
            }
        });

    }

    private void callBackFailure(final BaseCallBack callBack, final Call call, IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        });

    }

    enum HttpMethodType {
        GET, POST
    }


    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private String writeFile(String sb) {
        try {
            StringBuffer sbtotle = new StringBuffer();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(new Date());
            sbtotle.append("\r\n" + date);
            sbtotle.append("\r\n" + sb);
            String time = formatter.format(new Date());
            String fileName = "crash-resp-" + time + ".log";
            if (FileUtil.hasSdcard()) {
                String path = getGlobalpath();
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();

                FileOutputStream fos = new FileOutputStream(path + fileName, true);
                fos.write(sbtotle.toString().getBytes());
                fos.flush();
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

}

