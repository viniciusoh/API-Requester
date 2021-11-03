package br.com.viniciusoh.apirequester;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Map;

public class RequestManager {
    public static final int REQUEST_TYPE_GET = 1;
    public static final int REQUEST_TYPE_POST = 2;
    public static final int REQUEST_TYPE_PUT = 3;
    public static final int REQUEST_TYPE_UPDATE = 4;

    private String url;
    private int request_type;
    OkHttpClient client = new OkHttpClient();

    public RequestManager(String url, int request_type) {
        this.url = url;
        this.request_type = request_type;
    }

    public void makeRequest(Map<String,String> headers, Map<String,String> params, RequestCallback callback) {
        if (request_type == REQUEST_TYPE_GET) {
            get(this.url, params, callback);
        } else if (request_type == REQUEST_TYPE_POST) {
            post(this.url, headers, params, callback);
        }
    }

    public void get(String url, Map<String,String>params, RequestCallback callback) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                if (param.getValue() == null || param.getValue().length() == 0)     continue;
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        Request request = new Request.Builder().url(httpBuilder.build()).build();
        String msgLog = "GET " + request.url();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.callback(false, msgLog + "\n" + e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                callback.callback(response.isSuccessful(), msgLog + "\n" + response.body().string());
            }
        });
    }

    private void post(String url, Map<String, String> headers, Map<String, String> params, RequestCallback callback) {

    }

    private String getRequestType() {
        switch (request_type) {
            case REQUEST_TYPE_GET:
                return "GET";
            case REQUEST_TYPE_POST:
                return "POST";
            case REQUEST_TYPE_PUT:
                return "PUT";
            case REQUEST_TYPE_UPDATE:
                return "UPDATE";
            default:
                return "";
        }

    }


    public static class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }
    }

    public interface RequestCallback{
        void callback(boolean success, String response);
    }
}
