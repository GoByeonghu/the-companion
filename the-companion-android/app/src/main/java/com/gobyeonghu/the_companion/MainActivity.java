package com.gobyeonghu.the_companion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // target data
    long time;
    double latitude, longitude;
    float acceleration_x, acceleration_y, acceleration_z;
    boolean onWifi;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private NetworkInfo network;
    // GPSTracker class
    private GpsInfo gps;

    //가속도센서
    private SensorManager sensorManager;
    private Sensor sensor;

    //declare variable for xml component
    TextView Text_id, Text_time, Text_gps, Text_vector, Text_wifi, Text_direction;
    Button Button_detect, Button_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("동행 (test ver)");

        Text_id = (TextView) findViewById(R.id.text_id);
        Text_time = (TextView) findViewById(R.id.text_time);
        Text_gps = (TextView) findViewById(R.id.text_gps);
        Text_vector = (TextView) findViewById(R.id.text_vector);
        Text_wifi = (TextView) findViewById(R.id.text_wifi);
        Text_direction = (TextView) findViewById(R.id.text_direction);

        Button_detect = (Button) findViewById(R.id.button_detect);
        Button_delete = (Button) findViewById(R.id.button_delete);

        //click detect button event
        Button_detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //gps
                // 권한 요청을 해야 함
                if (!isPermission) {
                    callPermission();
                    return;
                }
                gps = new GpsInfo(MainActivity.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    //String.valueOf(latitude);
                    //String.valueOf(longitude);
                    /*
                    Toast.makeText(
                            getApplicationContext(),
                            "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                            Toast.LENGTH_LONG).show();

                     */
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }

                //가속도 센서
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                sensorManager.registerListener(sensorlistener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

                //시간
                time = System.currentTimeMillis();

                //와이파이
                network = new NetworkInfo();
                network.isNetworkAvailable(MainActivity.this);

                Text_id.setText("[id]:  gobyeonghu");
                Text_gps.setText("[GPS]: 위도: " + latitude + "경도: " + longitude);
                Text_time.setText("[time]: "+Long.toString(time));
                Text_vector.setText("[vector]: x:"+Float.toString(acceleration_x)+"y:"+Float.toString(acceleration_y)+"z:"+Float.toString(acceleration_z));
                Text_wifi.setText("[Wi-fi]: "+Boolean.toString(onWifi));
                //Text_direction.setText("gobyeonghu");

                Text_id.setVisibility(View.VISIBLE);
                Text_time.setVisibility(View.VISIBLE);
                Text_gps.setVisibility(View.VISIBLE);
                Text_vector.setVisibility(View.VISIBLE);
                Text_wifi.setVisibility(View.VISIBLE);
                //Text_direction.setVisibility(View.VISIBLE);
            }
        });


        //click delete button event
        Button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Text_id.setVisibility(View.INVISIBLE);
                Text_time.setVisibility(View.INVISIBLE);
                Text_gps.setVisibility(View.INVISIBLE);
                Text_vector.setVisibility(View.INVISIBLE);
                Text_wifi.setVisibility(View.INVISIBLE);
                Text_direction.setVisibility(View.INVISIBLE);
            }
        });
    }

    public SensorEventListener sensorlistener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            acceleration_x = event.values[0];
            acceleration_y = event.values[1];
            acceleration_z = event.values[2];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            int a =3;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener((sensorlistener));
    }



    ///permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    //권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }


}