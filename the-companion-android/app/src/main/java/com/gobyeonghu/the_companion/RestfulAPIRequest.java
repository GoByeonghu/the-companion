package com.gobyeonghu.the_companion;

import android.util.Log;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RestfulAPIRequest {

    String URL, body ,method;
    String result;
    int status_code;

    // HttpUrlConnection
    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                String page = URL;
                // URL 객체 생성
                URL url = new URL(page);
                Log.i("HI1","1");
                String data = body;

                // 연결 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                Log.i("HI2","2");
                //conn.connect();




                // Post 파라미터

                String params = "param=1"
                        + "&param2=2" + "sale";


                // 결과값 저장 문자열
                final StringBuilder sb = new StringBuilder();
                // 연결되면
                if(conn != null) {
                    Log.i("tag1", "conn 연결");
                    // 응답 타임아웃 설정
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setConnectTimeout(10000);
                    // POST 요청방식
                    conn.setRequestMethod(method);

                    // 포스트 json body전달
                    /////////////////////
                    try(OutputStream os = conn.getOutputStream()) {
                        byte[] input = data.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }
                    /////////////////////

                    //conn.getOutputStream().write(params.getBytes("utf-8"));
                    // url에 접속 성공하면 (200)
                    Log.i("HI3","2");

                    //conn.getResponseCode();
                    status_code=conn.getResponseCode();
                    if( status_code== HttpURLConnection.HTTP_OK) {
                        // 결과 값 읽어오는 부분
                        Log.i("HI4","2");
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                conn.getInputStream(), "utf-8"
                        ));
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        // 버퍼리더 종료
                        br.close();
                        Log.i("tag", "결과 문자열 :" +sb.toString());
                        // 응답 Json 타입일 경우

                        /*
                        JSONArray jsonResponse = new JSONArray(sb.toString());
                        Log.i("tag", "확인 jsonArray : " + jsonResponse);
                        */

                    }
                    // 연결 끊기
                    conn.disconnect();
                }

            }catch (Exception e) {
                Log.i("tag", "error :" + e);

            }
        }
    });

    public int httpRequest(String method, String url, String json_body){

        LocalSetting temp = new LocalSetting();

        this.URL= temp.URL+url;
        this.method=method;
        this.body = json_body;
        th.start();

        return this.status_code;
    }

}
