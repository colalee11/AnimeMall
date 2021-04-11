package com.dlsd.property.network;

import android.util.Log;

import com.dlsd.property.MyApp;
import com.dlsd.property.constant.UrlConfig;
import com.dlsd.property.models.response.LoginTokenResp;
import com.dlsd.property.utils.SPUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private static final String TAG = "ljh";
    private String url = UrlConfig.LOGIN_TOKEN;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Log.e(TAG, "response.code=" + response.code());
        if (isTokenExpired(response)) {
            Log.e(TAG, "自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            String newToken = getNewToken();
            Log.e(TAG, "intercept:新的请求头 " + newToken);
            //使用新的Token，创建新的请求
            Request newRequest = chain.request()
                    .newBuilder()
                    .header("Authorization", "Bearer " + newToken)
                    .build();
            //重新请求
            return chain.proceed(newRequest);
        }
        return response;
    }

    //判断Token是否过期,我这里是因为Token过期会返回401,可以通过判断新旧token是否一致
    private boolean isTokenExpired(Response response) {
        if (response.code() == 401) {
            Log.e(TAG, "token失效");
            return true;
        }
        return false;
    }

    //用同步方法获取新的Token
    private String getNewToken() throws IOException {
        // 通过获取token的接口，同步请求接口
        String newToken = "";
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("grant_type", "refresh_token");
        map.put("refresh_token", MyApp.mUserLogin.getPhone());
        // GsonUtil只是有个把Map变成String的工具类
        RequestBody requestBody = buildFormData(map);
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .addHeader("Authorization", "Basic YXBwOmFwcA==").build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            //坑人点，response.body().string()只能调用一次，否则会报java.lang.IllegalStateException: closed
            //第一次调用.body().string(),OkHttp就会默默地释放资源,再次调用就会抛出异常,有兴趣的可以去看源码
            String relut = response.body().string();
            Log.e(TAG, "getNewToken: " + relut);
            JSONObject jsonObject = new JSONObject(relut);
            if (jsonObject.has("code")) {
                //MyApp.getAppContext().startActivity(new Intent(MyApp.getAppContext(), LoginActivity.class));
            } else {
                Gson gson = new Gson();
                LoginTokenResp bean = gson.fromJson(relut, LoginTokenResp.class);
                newToken = bean.getAccess_token();
                MyApp.mUserLogin.setPhone(bean.getAccess_token());
                MyApp.mUserLogin.setPhone(bean.getRefresh_token());
                SPUtil.put(MyApp.getAppContext(), SPUtil.ACCESS_TOKEN, bean.getAccess_token());
                SPUtil.put(MyApp.getAppContext(), SPUtil.REFRESH_TOKEN, bean.getRefresh_token());
            }
        } catch (Exception e) {
            Log.e("存储token异常",""+e.getMessage());
            e.printStackTrace();
        }
        return newToken;
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
}
