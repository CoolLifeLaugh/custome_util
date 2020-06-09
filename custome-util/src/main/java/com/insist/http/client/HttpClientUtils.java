package com.insist.http.client;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="richard.xf@alibaba-inc.com">richard.xf</a>
 * @version 1.0
 * @since 2020/2/29 11:22
 */
public class HttpClientUtils {
    private static final String ENCODING = "UTF-8";
    private static final int SOCKET_TIMEOUT = 1000;
    private static final int CONNECT_TIMEOUT = 1000;

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url    提交的URL
     * @param params 提交params
     * @return 提交响应
     */
    public static String submitPostUrl(String url, String params) {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            final HttpPost httpPost = new HttpPost(url);
            final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(params, ENCODING));
            response = httpClient.execute(httpPost);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            logger.error("submitPostUrl-occur-exception", e);
            throw new RuntimeException("HTTP_REQUEST_ERROR");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (Exception e) {
                logger.error("submitPostUrl HttpClient close error", e);
            }
        }
        return responseText;
    }


    public static String submitPostForm(String url,final Map<String,String> postParams){
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : postParams.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }

        try {
            final HttpPost httpPost = new HttpPost(url);
            final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT).build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(params, ENCODING));
            response = httpClient.execute(httpPost);
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        }catch (Exception e){
            logger.error("http request error", e);
            throw new RuntimeException("HTTP_REQUEST_ERROR");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (Exception e) {
                logger.error("submitPostForm HttpClient close error", e);
            }
        }

        return responseText;
    }
}
