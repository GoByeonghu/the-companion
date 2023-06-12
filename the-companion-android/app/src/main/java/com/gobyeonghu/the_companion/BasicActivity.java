package com.gobyeonghu.the_companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONObject;

public class BasicActivity extends AppCompatActivity{
    boolean success = false;
    String result_data0="aa",result_data1="bb", result_data2="cc" ;
    JSONObject resultJson1;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        AddActivity_googlemap.class);
                startActivity(intent);
            }
        });
        //isLogin();
        //Intent intent = new Intent(BasicActivity.this, LoginActivity.class);
        //startActivityForResult(intent, 1);
        th.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //isLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //isLogin();
    }

    private boolean isLogin(){
        boolean result =false;
        String str;
        try{
            FileInputStream inFs = openFileInput("isLogin.txt");
            byte[] txt = new byte[30];
            inFs.read(txt);
            str = new String(txt);
            inFs.close();

            if(str.charAt(0) == 'o'){
                Log.i("IsLogin?", "o");
                result = true;
            }
            else {
                Log.i("IsLogin?", "x");
                result = false;
            }

        }
        catch (IOException e){
            Log.i("IsLogin?", "no file");
            e.printStackTrace();
            result =false;}

        if(result==false){
            //Intent intent = new Intent(getApplicationContext(),
            //        LoginActivity.class);
            //startActivity(intent);
            Intent intent = new Intent(BasicActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }

        return result;
    }

    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                LocalSetting temp = new LocalSetting();
                String page = temp.URL+"data/recommend_list/auto/";
                // URL 객체 생성
                URL url = new URL(page);

                //JSONObject body = new JSONObject();
                //body.put("id", editID.getText().toString().trim());
                //body.put("pw", editPW.getText().toString().trim());
                //String data=body.toString();

                // 연결 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                // 결과값 저장 문자열
                final StringBuilder sb = new StringBuilder();
                // 연결되면
                if(conn != null) {
                    // 응답 타임아웃 설정
                    conn.setRequestProperty("Accept", "application/json");
                    //conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setConnectTimeout(10000);
                    // GET 요청방식
                    conn.setRequestMethod("POST");

                    // url에 접속 성공하면 (200)
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // 결과 값 읽어오는 부분
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                conn.getInputStream(), "utf-8"
                        ));
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        // 버퍼리더 종료
                        br.close();
                        Log.i("GGGGG", "2");
                        success=true;
                        // 응답 Json 타입일 경우

                        /*
                        JSONArray jsonResponse = new JSONArray(sb.toString());
                        Log.i("tag_i", "확인 jsonArray : " + jsonResponse);
                        */
                        resultJson1 = new JSONObject(sb.toString());
                        result_data0=resultJson1.toString();
                        Log.i("tag_i", "확인 jsonArray : " + resultJson1.toString());
                    }
                    else{
                        Log.i("GGGGG", "3");
                        success=false;
                    }
                    // 연결 끊기
                    conn.disconnect();

                    if(success == true){

                    }
                }
                //백그라운드 스레드에서는 메인화면을 변경 할 수 없음
                // runOnUiThread(메인 스레드영역)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다." + sb.toString(), Toast.LENGTH_SHORT).show();

                        if(success == true){
                            //TODO
                            ViewPager2Adapter viewPager2Adapter
                                    = new ViewPager2Adapter(getSupportFragmentManager(), getLifecycle());

                            //데이터 전송
                            viewPager2Adapter.setData(result_data0,result_data1, result_data2);

                            ViewPager2 viewPager2 = findViewById(R.id.pager);
                            viewPager2.setAdapter(viewPager2Adapter);



                            //=== TabLayout기능 추가 부분 ============================================
                            TabLayout tabLayout = findViewById(R.id.tabLayout);
                            new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                                @Override
                                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                    String tabTitle;
                                    switch (position) {
                                        case 0:
                                            tabTitle = "자동 추천";
                                            break;
                                        case 1:
                                            tabTitle = "추천 게시글";
                                            break;
                                        case 2:
                                            tabTitle = "내 게시글";
                                            break;
                                        default:
                                            tabTitle = "Tab " + (position + 1);
                                            break;
                                    }
                                    tab.setText(tabTitle);
                                }
                            }).attach();

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

/*

public class BasicActivity extends AppCompatActivity implements LocationListener, SensorEventListener {
    private static final String TAG = BasicActivity.class.getSimpleName();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private LocationManager locationManager;
    private SensorManager sensorManager;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    double x = 0.0;
    double y = 0.0;
    double z = 0.0;
    boolean isConnected = false;
    double lat; // 위도
    double lon; // 경도

    Button btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        btnSetting = (Button) findViewById(R.id.btnSetting);

        btnSetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        SettingActivity.class);
                startActivity(intent);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //읽고쓰기
        // storage permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        Log.i("****1*****","ok");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
        }


        // 위치 권한 확인
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }

        // Wi-Fi 상태 변경 수신자 등록
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);

        // 선형 가속도 센서 등록
        Sensor linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (linearAccelerationSensor != null) {
            sensorManager.registerListener(this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.d(TAG, "Linear acceleration sensor not available");
        }

        //알람권환확인인
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Missing SCHEDULE_EXACT_ALARM permission");
                return;
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Missing USE_EXACT_ALARM permission");
                return;
            }
        }

       // AlarmManager 설정
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        // 5분마다 알람 설정
        long intervalMillis = 60 * 1000; // 60초
        long triggerTime = System.currentTimeMillis()+ intervalMillis;;
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, intervalMillis, alarmIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiStateReceiver);
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Log.d(TAG, "Location permission denied");
            }
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lon=location.getLongitude();
        //saveDataToFile("GPS", location.getLatitude() + "," + location.getLongitude());
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            isConnected = networkInfo != null && networkInfo.isConnected();
            //saveDataToFile("Wi-Fi", String.valueOf(isConnected));
        }
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            //saveDataToFile("Linear Acceleration", x + "," + y + "," + z);
        }
    }

    private void saveDataToFile(String dataType, String data) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String entry = dataType + "," + timestamp + "," + data + "\n";

        File file = new File(Environment.getExternalStorageDirectory(), "sensor_data.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(entry);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 */