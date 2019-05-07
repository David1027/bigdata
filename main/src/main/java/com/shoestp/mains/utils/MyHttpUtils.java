package com.shoestp.mains.utils;//package com.shoestp.bigdata.utils;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//import java.io.IOException;
//
//public class MyHttpUtils {
//
//  public static String get(String url, String params) throws IOException {
//    OkHttpClient client = new OkHttpClient();
//    Request request = new Request.Builder().url(url).build();
//    try (Response response = client.newCall(request).execute()) {
//      return response.body().string();
//    }
//  }
//}
