package br.com.viniciusoh.apirequester;

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

    public RequestManager(String url, int request_type) {
        this.url = url;
        this.request_type = request_type;
    }

    public void makeRequest(Map<String,String> headers, Map<String,String> params, RequestCallback callback) {
        String response = "";
//        String response = "";
//        for (int i = 0; i < headers.length; i++) {
//            response += headers[i] + "\n";
//        }
//
//        for (int i = 0; i < params.length; i++) {
//            response += params[i] + "\n";
//        }

        URL url = null;
        try {
            url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(getRequestType());
            con.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            con.setRequestProperty("Accept","*/*");

            response += getRequestType() + " " + this.url;

//            for (String k: headers.keySet()) {
//                con.setRequestProperty(k, headers.get(k));
//            }


//            con.setDoOutput(true);
//
//
//            DataOutputStream out = new DataOutputStream(con.getOutputStream());
//            out.writeBytes(ParameterStringBuilder.getParamsString(params));
//            out.flush();
//            out.close();

            int status = con.getResponseCode();
            boolean success = false;
            InputStream responseStream;
            if (status == 200) {
                success = true;
                responseStream = con.getInputStream();
            } else {
                success = false;
                responseStream = con.getErrorStream();
                response += "\nResponse status code: " + status;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(responseStream));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            response += "\n" + content.toString();
            callback.callback(success, response);
        } catch (Exception e) {
            callback.callback(false, e.toString());
            e.printStackTrace();
        }
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
