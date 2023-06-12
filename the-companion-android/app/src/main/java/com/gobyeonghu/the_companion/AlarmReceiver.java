package com.gobyeonghu.the_companion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver implements SensorEventListener{
    double x = 0.0;
    double y = 0.0;
    double z = 0.0;
    boolean isConnected = false;
    double lat; // 위도
    double lon; // 경도

    Context all;
    @Override
    public void onReceive(Context context, Intent intent) {
        all = context;

        // GPS 좌표, Wi-Fi 연결 여부, 선형 가속 벡터 값을 얻고 파일에 저장
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // GPS 좌표
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                lat=location.getLatitude();
                lon=location.getLongitude();
                //saveDataToFile("GPS", location.getLatitude() + "," + location.getLongitude());
            }
        }

        // Wi-Fi 연결 여부
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        isConnected = networkInfo != null && networkInfo.isConnected();
        //saveDataToFile("Wi-Fi", String.valueOf(isConnected));

        // 선형 가속 벡터 값
        // 선형 가속 벡터 값
        Sensor linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (linearAccelerationSensor != null) {
            sensorManager.registerListener(this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        //saveDataToFile("Linear Acceleration", x + "," + y + "," + z);

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String entry = timestamp + lat+" , "+lon+","+ x +","+ y +","+ z +"," + String.valueOf(isConnected)+ "\n";
        Log.i("a",entry);

        File file = new File(context.getFilesDir(), "sensor_data.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(entry);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (all.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "Missing WRITE_EXTERNAL_STORAGE permission");
                return;
            }
        }

         */


        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String entry = timestamp + "," + dataType + ","  + data + "\n";
        Log.i("a",entry);

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
