package com.testproject;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class CallApi {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

         // one instance, reuse
        private final OkHttpClient httpClient = new OkHttpClient();

        public static void main(String[] args) throws Exception {

            CallApi obj = new CallApi();

           /* System.out.println("Testing 1 - Send Http GET request");
            obj.sendGet();*/

            System.out.println("Testing 2 - Send Http POST request");
            final int[] i = {0};
            /*while(i<999999999){
                obj.sendPost();
                System.out.println("["+i+++"]");
            }*/

            ExecutorService executorService = Executors.newFixedThreadPool(100);


            while(i[0]<999999999)
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        obj.sendPost();
                        System.out.println("["+ i[0]+++"]");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            executorService.shutdown();


        }

        private void sendGet() throws Exception {

            Request request = new Request.Builder()
                    .url("https://www.google.com/search?q=mkyong")
                    .addHeader("custom-key", "mkyong")  // add request headers
                    .addHeader("User-Agent", "OkHttp Bot")
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
            }

        }

        private void sendPost() throws Exception {

            RequestBody body = RequestBody.create(JSON, "{\n" +
                    "    \"text\": \""+RandomGenerator.getAlphaNumericString(10)+"\"\n" +
                    "}");



            Request request = new Request.Builder()
                    .url("http://localhost:8080/tweets")
                    .addHeader("User-Agent", "OkHttp Bot")
                    .post(body)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
            }

        }

}
