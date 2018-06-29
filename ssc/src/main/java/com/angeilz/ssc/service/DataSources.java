package com.angeilz.ssc.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class DataSources {

    public static final String CQ_PREFIX = "http://zst.cjcp.com.cn/cjwssc/view/ssc_zonghe2-ssc-1-";
    public static final String XJ_PREFIX = "http://zst.cjcp.com.cn/cjwssc/view/ssc_zonghe2-ssc-1-";
    public static final String TJ_PREFIX = "http://zst.cjcp.com.cn/cjwssc/view/ssc_zst5-tianjinssc-1-";
    public static final String SUFFIX = "-9.html";
    public static final String CONNECTOR = "-";

    public static Document getHtmlDoc(String address) {
        HttpURLConnection con = getHttpURLConnection(address);
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuilder lines = new StringBuilder();
        try {
            inputStreamReader = new InputStreamReader(con.getInputStream(), "gbk");
        } catch (Exception e) {
            try {
                inputStreamReader = new InputStreamReader(con.getInputStream(), "gbk");
            } catch (IOException e1) {
                try {
                    inputStreamReader = new InputStreamReader(con.getInputStream(), "gbk");
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return null;
                }
            }
        }
        try {
            reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                lines.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Document doc = Jsoup.parse(lines.toString());
        return doc;
    }


    private static HttpURLConnection getHttpURLConnection(String address) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(address);
            con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(5000);
            con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            con.addRequestProperty("User-Agent", "Mozilla");
            con.addRequestProperty("Referer", "google.com");
            con.setRequestProperty("User-Agent", "Mozilla");
            System.out.println(con.getHeaderField("Location"));

            boolean redirect = false;

            // normally, 3xx is redirect
            int status = 0;
            try {
                status = con.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }

            System.out.println("Response Code ... " + status);

            //做网站的人，都知道可以在apache,iis里配置 301,302转向，这样对搜索引擎也是有好的。
            // 如果用java开发程序得到这个链接的话，通常是得不到真实的转向地址的，这需要手工处理
            //可以通过 HTTP response 的 头部 :"Location" 得到这个转向的url
            if (redirect) {
                // get redirect url from "location" header field
                String newUrl = con.getHeaderField("Location");

                // get the cookie if need, for login
                String cookies = con.getHeaderField("Set-Cookie");

                // open the new connnection again
                con = (HttpURLConnection) new URL(newUrl).openConnection();
                con.setRequestProperty("Cookie", cookies);
                con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                con.addRequestProperty("User-Agent", "Mozilla");
                con.addRequestProperty("Referer", "google.com");

                System.out.println("Redirect to URL : " + newUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return con;
    }

    private static List<String> getData(String address) {
        List<String> list = new ArrayList<String>();
        Document doc = getHtmlDoc(address);
        if (doc == null) {
            return list;
        }
        Element pageData = doc.getElementById("pagedata");
        Elements trElements = pageData.children();
        for (Element ele : trElements) {
            //期号
            String phase = ele.child(1).html().replaceAll("\\D", "");//去除非数字的字符
            //号码
            String number = ele.child(2).html().replaceAll("\\D", "").substring(0, 5);//去除非数字的字符
            list.add(phase + "|" + number);
        }
        return list;
    }

    public static List<String> getDataByTimeZone(String beginTime, String endTime, String tableName) {
        String prefix = "";
        if ("cq".equals(tableName)) {
            prefix = CQ_PREFIX;
        } else if ("xj".equals(tableName)) {
            prefix = XJ_PREFIX;
        } else if ("tj".equals(tableName)) {
            prefix = TJ_PREFIX;
        }
        String address = prefix + beginTime + CONNECTOR + endTime + SUFFIX;
        return getData(address);
    }

    public static void main(String[] args) {
        getDataByTimeZone("20180628001", "20180629084", "tj");
    }
}
