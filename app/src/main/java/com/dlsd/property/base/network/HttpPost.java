package com.dlsd.property.base.network;

import com.dlsd.property.utils.LogUtil;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * POST 请求
 * Created by Super丶C on 2017/10/30.
 */

public abstract class HttpPost<T> extends HttpTask<T> {

    public abstract Map<String, String> getBody();

    @Override
    protected Request createRequest() {
        String url = createUrl();
        Map<String, String> params = getBody();

        FormBody.Builder builder = new FormBody.Builder();
        StringBuilder sb = new StringBuilder();
        sb.append(" form:\n");
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    builder.add(entry.getKey(), String.valueOf(entry.getValue()));
                    sb.append(entry.getKey()).append('=').append(String.valueOf(entry.getValue())).append('\n');
                }
            }
        }
        LogUtil.i("POST", "url:" + url + sb.toString());
        return new Request.Builder().url(url).post(builder.build()).build();
    }
}
