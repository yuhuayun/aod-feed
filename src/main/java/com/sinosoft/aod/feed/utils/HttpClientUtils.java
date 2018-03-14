package com.sinosoft.aod.feed.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

/**
 * http工具类
 * Created by LONGLEI on 2018/1/22.
 */
public class HttpClientUtils {
    private static Logger logger = Logger.getLogger(HttpClientUtils.class);

    private HttpClientUtils() {
    }

    public static String doGet(String url, Map<String, String> params, String charset) {

        String apiUrl = url;

        StringBuffer param = new StringBuffer();

        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key).trim());
            i++;
        }
        apiUrl += param;

        long responseLength = 0L;
        String responseContent = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(apiUrl);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseLength = entity.getContentLength();
                responseContent = EntityUtils.toString(entity, charset == null ? "UTF-8" : charset);
                EntityUtils.consume(entity);
            }

            logger.info("请求地址: " + httpGet.getURI());
            logger.info("响应状态: " + response.getStatusLine());
            logger.info("响应长度: " + responseLength);
            logger.info("响应内容: " + responseContent);
        } catch (ClientProtocolException var14) {
            logger.error("客户端协议错误导致异常，比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等" + var14.getCause(), var14);
        } catch (ParseException var15) {
            logger.error("解析服务端返回内容时出错。" + var15.getCause(), var15);
        } catch (IOException var16) {
            logger.error("网络I/O引起错误，如HTTP服务器未启动等。" + var16.getCause(), var16);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return responseContent;
    }


    public static String doPost(String url, Map<String, String> params, Charset charset) {

        String response = "";
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            NameValuePair[] paires = null;
            if (params != null && params.size() > 0) {
                paires = new NameValuePair[params.size()];
                int index = 0;
                for (Entry<String, String> entry : params.entrySet()) {
                    NameValuePair pair = new NameValuePair(entry.getKey(), entry.getValue());
                    paires[index] = pair;
                    index++;
                }
                // 请求参数作为数据请求体
                postMethod.setRequestBody(paires);
            }

            // 执行postMethod
            HttpClient client = new HttpClient();
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            } else {
                logger.error("响应状态码 = " + postMethod.getStatusCode());
            }
        } catch (HttpException e) {
            logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("发生网络异常", e);
            e.printStackTrace();
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }

        return response;

    }
}
