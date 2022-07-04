package com.decathlon.cndp_interface_dev.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.decathlon.cndp_interface_dev.pojo.param.Ops;
import com.decathlon.cndp_interface_dev.pojo.param.ParamRootBean;
import com.github.kevinsawicki.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

public final class HttpRequestUtils {
    private static final String CONTENT_TYPE_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final int TIMEOUT = 120000;
    private static final String PROXY_URL = "proxy-internet-ali-cnsh.dktapp.cloud";
    private static CloseableHttpClient httpClient;

    private static final Log log = LogFactory.getLog(HttpRequestUtils.class);

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(50);
//		RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static void main(String[] args) {
        String API_KEY = "f0c471f6173bfc3116a41e00a2a4296b";
        String API01_PATH = "http://restapi.amap.com/v3/batch?key=" + API_KEY;

        List<Ops> opsList = new ArrayList<>();
        Ops ops = new Ops();
        ops.setUrl("/v3/geocode/regeo?key=" + API_KEY + "&location=113.2654530,40.0527740");
        opsList.add(ops);
        Ops ops2 = new Ops();
        ops2.setUrl("/v3/geocode/regeo?key=" + API_KEY + "&location=121.533027,31.323040");
        opsList.add(ops2);
        ParamRootBean paramRootBean = new ParamRootBean();
        paramRootBean.setOps(opsList);
        String json = com.alibaba.fastjson.JSONObject.toJSONString(paramRootBean);
        String httpPostJson = null;
        try {
            httpPostJson = HttpRequestUtils.httpPost(API01_PATH, json, "application/json", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray parseArray = JSON.parseArray(httpPostJson);
        for (Object object : parseArray) {
            JSONObject subParseObject = JSON.parseObject(object.toString());
            String township = subParseObject.getJSONObject("body").getJSONObject("regeocode")
                    .getJSONObject("addressComponent").getString("township");
            System.out.println(township);
        }
        System.out.println(httpPostJson);

        // String httpPostJson = HttpRequestUtils.get(
        // "https://restapi.amap.com/v3/geocode/regeo?key=&location=113.2654530,40.0527740%7C121.533027,31.323040&poitype=&radius=1000&extensions=all&batch=true&roadlevel=0");

        // JSONObject subParseObject = JSON.parseObject(httpPostJson);
        // JSONArray parseArray = subParseObject.getJSONArray("regeocodes");
        // for (Object object : parseArray) {
        // String township =
        // JSON.parseObject(object.toString()).getJSONObject("addressComponent").getString("township");
        // System.out.println(township);
        // }

        // System.out.println(!true);
    }

    public static JSONArray getUserCommentScore(String title, String userComment, String appid, String url,
                                                boolean isproxy) {
        if (StringUtils.isEmpty(userComment)) {
            return null;
        }
        String userCommentEncode = userComment;
        JSONArray parseArrayScore = null;
        for (int i = 0; i < 5; i++) {
            try {
                Map<String, String> params = new HashMap<>();
                params.put("appid", appid);
                params.put("text", userCommentEncode);
                if (StringUtils.isNotEmpty(title)) {
                    params.put("title", title);
                }
                String httpPost = HttpRequestUtils.httpPost(url, null, CONTENT_TYPE_X_WWW_FORM_URLENCODED, isproxy,
                        null, params);
                JSONObject parseObjectScore = JSON.parseObject(httpPost);
                if (parseObjectScore != null && !parseObjectScore.isEmpty()) {
                    String scoreString = parseObjectScore.getString("result");
                    if (StringUtils.isNotEmpty(scoreString)) {
                        parseArrayScore = JSON.parseArray(scoreString);
                    }
                }
                return parseArrayScore;
            } catch (Exception e) {
                System.out.println(
                        "HttpRequestUtils.getUserCommentScore error -->> " + e.getMessage() + " -->> i -->> " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                userCommentEncode= MyStringUtils.replaceEmoji(userCommentEncode);
            }

        }
        return parseArrayScore;
    }

    public static String get(String url) {
        return get(url, false, null);
    }

    public static String get(String url, boolean isproxy, Map<String, String> headers){
        return get(url ,isproxy ,null ,null,headers);
    }

    public static String post(String url, Map<String, String> params) {
        String buildQuery = null;
        try {
            buildQuery = buildQuery(params, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post(url, buildQuery, false, null);
    }

    public static String post(String url, Map<String, String> params, boolean isproxy, String authorization) {
        String buildQuery = null;
        try {
            buildQuery = buildQuery(params, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post(url, buildQuery, isproxy, authorization);
    }

    public static String post(String url, Map<String, String> params, boolean isproxy, String authorization,
                              String contentType) {
        String buildQuery = null;
        try {
            buildQuery = buildQuery(params, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post(url, buildQuery, isproxy, authorization, contentType);
    }

    public static String post(String url, String jsonString, boolean isproxy, String authorization) {
        return post(url, jsonString, isproxy, authorization, null);
    }

    public static String post(String url, String jsonString, boolean isproxy, String authorization,
                              String contentType) {
        HttpPost httpPost = new HttpPost(url);
        String result = "";
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        try {
            RequestConfig requestConfig = null;
            if (!isproxy) {
                requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT)
                        .setSocketTimeout(TIMEOUT).setCookieSpec(CookieSpecs.STANDARD).build();
            } else {
                HttpHost proxy = new HttpHost(PROXY_URL, 3128, "http");
                requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(TIMEOUT)
                        .setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT)
                        .setCookieSpec(CookieSpecs.STANDARD).build();
            }
            if (authorization != null) {
                httpPost.setHeader("Authorization", authorization);
            }
            httpPost.setConfig(requestConfig);
            if (StringUtils.isEmpty(contentType)) {
                httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            } else {
                httpPost.addHeader("Content-type", contentType);
            }
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder("");
            String line = "";
            String separator = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + separator);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (isNotEmpty(name) && isNotEmpty(value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }
        return query.toString();
    }

    private static boolean isNotEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return true;
            }
        }
        return false;
    }

    public static String httpPost(String url, String data, String contentType, boolean isproxy) throws Exception {
        return httpPost(url, data, contentType, isproxy, null);
    }

    public static String httpPost(String url, String data, String contentType, boolean isproxy, String authorization)
            throws Exception {
        return httpPost(url, data, contentType, isproxy, null, null);
    }

    public static String httpPost(String url, boolean isproxy, Map<String, String> paramsMap) throws Exception {
        return httpPost(url, "", null, isproxy, null, paramsMap);
    }

    public static String httpPost(String url, boolean isproxy, Map<String, String> paramsMap, String authorization) throws Exception {
        return httpPost(url, "", null, isproxy, authorization, paramsMap);
    }

    public static String httpPost(String url, String data, String contentType, boolean isproxy, String authorization,
                                  Map<String, String> paramsMap) throws Exception {
        int size = 3;
        String result = "";
        StringBuilder buf = null;
        if ((paramsMap != null) && (!paramsMap.isEmpty())) {
            buf = new StringBuilder();
            Set<Map.Entry<String, String>> entrys = paramsMap.entrySet();
            for (Map.Entry<String, String> entry : entrys) {
                buf.append((String) entry.getKey()).append("=")
                        .append(URLEncoder.encode((String) entry.getValue(), "UTF-8")).append("&");
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        URLConnection connection = null;
        HttpURLConnection hc = null;
        PrintWriter out = null;
        BufferedReader input = null;
        for (int i = 1; i <= size; i++) {
            try {
                URL U = new URL(url);
                if (isproxy) {
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_URL, 3128));
                    connection = U.openConnection(proxy);
                } else {
                    connection = U.openConnection();
                }
                connection.setConnectTimeout(TIMEOUT);
                connection.setReadTimeout(TIMEOUT);
                if (!(connection instanceof HttpURLConnection)) {
                    return "request fail";
                }
                if ((contentType == null) || (contentType.trim().length() == 0)) {
                    contentType = CONTENT_TYPE_X_WWW_FORM_URLENCODED;
                }
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", contentType);
                if (StringUtils.isNotEmpty(data)) {
                    connection.setRequestProperty("Content-Length", Integer.toString(data.length()));
                }
                if (authorization != null) {
                    connection.setRequestProperty("Authorization", authorization);
                }
                hc = (HttpURLConnection) connection;
                hc.setRequestMethod("POST");
                if (StringUtils.isNotEmpty(data)) {
                    out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
                    out.write(data);
                    out.flush();
                }
                if (buf != null) {
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(buf.toString().getBytes("UTF-8"));
                }
                input = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = input.readLine()) != null) {
                    result = result + line.trim();
                }
                return result.trim();
            } catch (Exception e) {
                if (i == size) {
                    throw e;
                }
            } finally {
                if (out != null) {
                    out.close();
                }
                if (input != null) {
                    input.close();
                }
                if (hc != null) {
                    hc.disconnect();
                }
            }
        }
        return result;
    }

    public static String getV2(String url,String authorization,Map<String, String> paramsRepMap,boolean isEnableProxy)
    {
        HttpRequest httpRequest = HttpRequest.get(url);
        httpRequest.header("Authorization", authorization);
        if (isEnableProxy) {
            httpRequest.useProxy(PROXY_URL, 3128);
        }
        httpRequest.connectTimeout(TIMEOUT);
        httpRequest.readTimeout(TIMEOUT);
        return httpRequest.form(paramsRepMap).body();
    }

    public static String get(String url, boolean isproxy, String authorization, String apiKey,
                             Map<String, String> headers) {
        String result = "";
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        for (int i = 0; i < 3; i++) {
            try {
                HttpGet httpGet = new HttpGet(url);
                RequestConfig requestConfig = null;
                if (!isproxy) {
                    requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT)
                            .setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT)
                            .setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
                } else {
                    HttpHost proxy = new HttpHost(PROXY_URL, 3128, "http");
                    requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(TIMEOUT)
                            .setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT)
                            .setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
                }
                if (authorization != null) {
                    httpGet.addHeader("Authorization", authorization);
                }
                if (apiKey != null) {
                    httpGet.addHeader("X-Api-Key", apiKey);
                }
                httpGet.setConfig(requestConfig);

                if (headers != null) {
                    for (Entry<String, String> entry : headers.entrySet()) {
                        httpGet.addHeader(entry.getKey(), entry.getValue());
                    }
                }else {
                    httpGet.addHeader("Content-type", "application/json; charset=utf-8");
                    httpGet.setHeader("Accept", "application/json");
                }
                response = httpClient.execute(httpGet);
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder("");
                String line = "";
                String separator = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + separator);
                }
                result = sb.toString();
                if (result.indexOf("400 Bad Request") != -1) {
                    throw new IllegalArgumentException("400 Bad Request");
                }
                return result;
            } catch (Exception e) {
                log.error("HttpRequestUtils.get error -->> " + e.getMessage() + " -->> i -->> " + i
                        + " -->> result -->> " + result);
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (response != null) {
                        response.close();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }
}