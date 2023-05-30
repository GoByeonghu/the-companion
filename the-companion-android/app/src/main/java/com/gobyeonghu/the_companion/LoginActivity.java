package com.gobyeonghu.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText editID, editPW;
    Button btnLogin, btnMoveRegister;
    boolean success = false;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editID = (EditText) findViewById(R.id.editID);
        editPW = (EditText) findViewById(R.id.editPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnMoveRegister = (Button) findViewById(R.id.btnMoveRegister);



        btnMoveRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                th.start();
            }
        });
    }

    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                LocalSetting temp = new LocalSetting();
                String page = temp.URL+"auth/user/login/";
                // URL 객체 생성
                URL url = new URL(page);
                Log.i("HI1","1");
                //String data = "{ \"id\" : \"006\", \"pw\" : \"1234\", \"email\" : \"7\" }";
                JSONObject body = new JSONObject();
                user_id=editID.getText().toString().trim();
                body.put("id", editID.getText().toString().trim());
                body.put("pw", editPW.getText().toString().trim());
                String data=body.toString();

                // 연결 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                Log.i("HI2",data);
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
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setConnectTimeout(10000);
                    // POST 요청방식
                    conn.setRequestMethod("POST");

                    // 포스트 json body전달
                    /////////////////////
                    /*
                    try(OutputStream os = conn.getOutputStream()) {
                        byte[] input = data.getBytes("utf-8");
                        Log.i("HI2_2",input.toString());/////////////////////
                        os.write(input, 0, input.length);
                    }

                     */
                    byte[] input = data.getBytes("utf-8");

                    // 출력 스트림을 열고 데이터를 전송
                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(input, 0, input.length);
                        os.flush();/////
                    }
                    /////////////////////

                    //conn.getOutputStream().write(params.getBytes("utf-8"));
                    // url에 접속 성공하면 (200)
                    Log.i("HI3","2");

                    //conn.getResponseCode();

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
                        success=true;
                        Log.i("HI5", "결과 문자열 :" +sb.toString());
                        Log.i("HI6","2");
                        // 응답 Json 타입일 경우

                        /*
                        JSONArray jsonResponse = new JSONArray(sb.toString());
                        Log.i("tag_i", "확인 jsonArray : " + jsonResponse);
                        */
                        JSONObject resultJson = new JSONObject(sb.toString());
                        Log.i("tag_i", "확인 jsonArray : " + resultJson.toString());

                        try{
                            File file = new File(getFilesDir(), "token.txt");
                            FileOutputStream outFs = new FileOutputStream(file);
                            String str = resultJson.getString("token");
                            outFs.write(str.getBytes());
                            outFs.close();
                        }
                        catch (IOException e){
                            e.printStackTrace();}

                        try{
                            File file = new File(getFilesDir(), "id.txt");
                            FileOutputStream outFs = new FileOutputStream(file);
                            String str = user_id;
                            outFs.write(str.getBytes());
                            outFs.close();
                        }
                        catch (IOException e){
                            e.printStackTrace();}

                        try{
                            File file = new File(getFilesDir(), "isLogin.txt");
                            FileOutputStream outFs = new FileOutputStream(file);
                            String str = "o";
                            outFs.write(str.getBytes());
                            outFs.close();
                        }
                        catch (IOException e){
                            e.printStackTrace();}
                    }
                    else{
                        success=false;
                    }
                    // 연결 끊기
                    conn.disconnect();

                    if(success == true){
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("id", "user_id");
                        //resultIntent.putExtra("email", "johndoe@example.com");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                }

                //백그라운드 스레드에서는 메인화면을 변경 할 수 없음
                // runOnUiThread(메인 스레드영역)

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다." + sb.toString(), Toast.LENGTH_SHORT).show();

                        if(success == true){
                            Log.i("tag_bbbbbbb", "success~");

                            /*
                            Intent intent = new Intent(getApplicationContext(),
                                    SettingActivity.class);

                            startActivity(intent);
                             */

                            finish();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    "Login Faile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }catch (Exception e) {
                Log.i("tag_e", "error :" + e);
                Toast.makeText(getApplicationContext(), "error" , Toast.LENGTH_SHORT).show();
            }
        }
    });
}