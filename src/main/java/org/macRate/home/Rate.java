package org.macRate.home;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
public class Rate {
    private static String BING_URL = "https://openexchangerates.org/api/latest.json?app_id=0651d700b381490f81db74e3a62cc029";



    public static void main(String[] args) throws IOException {


        String url = BING_URL;
        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        final Request request = new Request.Builder()
                .url(url)//请求的url
                .get()//设置请求方式，get()/post() 查看Builder()方法知，在构建时默认设置请求方式为GET
                .build(); //构建一个请求Request对象

        //建立/Call
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
              
                getfeedback(response.body().string());
            }
        });

    }

    private static void getfeedback(String string) throws IOException {
        Path readmePath = Paths.get("rate.json");
        FileWriter fw = new FileWriter(String.valueOf(readmePath));
        fw.write(string);
        fw.close();
    }
}
