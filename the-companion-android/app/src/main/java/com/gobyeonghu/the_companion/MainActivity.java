package com.gobyeonghu.the_companion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//코드 참조:https://webnautes.tistory.com/647

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback{
    boolean[] stop1 ;
    double[] lat1 ;
    double[] lng1 ;

    int num1;


    boolean[] stopa = {true, false, false,false,false,true,false,false,false,false,false,false,false,true,
            false,false,true,
            false, true,
            false,false,false,false,false,true};
    double[] lata = {37.246, 37.247, 37.249, 37.252, 37.255, 37.257, 37.254, 37.253, 37.253,37.251, 37.249,37.248,37.245,37.240,
            37.240,37.242,37.242,
            37.255,37.255,
            37.254, 37.253,37.251,37.249,37.247,37.245};
    double[] lnga = {127.077, 127.078, 127.078, 127.077, 127.073, 127.068,127.075,127.075,127.076,127.078,127.079,127.078,127.078,127.079,
            127.082, 127.081,127.079,
            127.076,127.074,
            127.077, 127.078,127.078,127.079,127.078,127.077};

    int numa=stopa.length;

    boolean[] stopb = {true, true};
    double[] latb = {37.246, 37.247};
    double[] lngb = {127.077,127.077};

    int numb=stopb.length;

    boolean[] stopc = {true, true};
    double[] latc = {37.390, 37.391};
    double[] lngc = {126.664,126.639};

    int numc=stopb.length;

    boolean[] stopd = {true, true};
    double[] latd = {36.652, 36.6424666};
    double[] lngd = {127.494,127.488931};

    int numd=stopd.length;


    TextView titleV, made_byV, textV;
    String rej_data,rej_num, title, made_by, text;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rej_num = getIntent().getStringExtra("rej_num");
        rej_data = getIntent().getStringExtra("rej_data");
        title = getIntent().getStringExtra("title");
        made_by = getIntent().getStringExtra("made_by");
        text = getIntent().getStringExtra("text");

        titleV=(TextView) findViewById(R.id.title);
        made_byV=(TextView) findViewById(R.id.made_by);
        textV=(TextView) findViewById(R.id.text);

        titleV.setText(title);
        made_byV.setText(made_by);
        textV.setText(text);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {


        if(rej_data.charAt(0)=='a'){
            num1=numa;
            lat1=lata;
            lng1=lnga;
            stop1=stopa;

        }
        else if(rej_data.charAt(0)=='b'){
            num1=numb;
            lat1=latb;
            lng1=lngb;
            stop1=stopb;

        }
        else if(rej_data.charAt(0)=='c'){
            num1=numc;
            lat1=latc;
            lng1=lngc;
            stop1=stopc;

        }
        else if(rej_data.charAt(0)=='d'){
            num1=numd;
            lat1=latd;
            lng1=lngd;
            stop1=stopd;

        }


            List<LatLng> coordinates = new ArrayList<>();
            for(int i=0; i<num1; i++){
                coordinates.add(new LatLng(lat1[i], lng1[i]));
            }
            PolylineOptions polylineOptions = new PolylineOptions().clickable(false);
            for (LatLng latLng : coordinates) {
                polylineOptions.add(latLng);
            }
            Polyline polyline1 = googleMap.addPolyline(polylineOptions);

            Circle circle;
            if(stop1[0]==true){
                circle = googleMap.addCircle(new CircleOptions()
                        .center(new LatLng(lat1[0], lng1[0]))
                        .radius(20)
                        .strokeColor(Color.RED)
                        .fillColor(Color.RED));
            }
            else if(stop1[0]==false){
                circle = googleMap.addCircle(new CircleOptions()
                        .center(new LatLng(lat1[0], lng1[0]))
                        .radius(12)
                        .strokeColor(Color.BLACK)
                        .fillColor(Color.BLACK));
            }
            for(int i=1; i<num1; i++){
                if(stop1[i]==true){
                    googleMap.addCircle(new CircleOptions()
                            .center(new LatLng(lat1[i], lng1[i]))
                            .radius(20)
                            .strokeColor(Color.RED)
                            .fillColor(Color.RED));
                }
                else if(stop1[i]==false){
                    googleMap.addCircle(new CircleOptions()
                            .center(new LatLng(lat1[i], lng1[i]))
                            .radius(12)
                            .strokeColor(Color.BLACK)
                            .fillColor(Color.BLACK));
                }
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1[0], lng1[0]), 16));






        /*
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(false)
                .add(
                        new LatLng(37.246,127.077),
                        new LatLng(37.247,127.078),
                        new LatLng(37.249,127.078),
                        new LatLng(37.252,127.077),
                        new LatLng(37.255,127.073),
                        new LatLng(37.257,127.068)));

        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(37.246,127.077))
                .radius(20)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));

        googleMap.addCircle(new CircleOptions()
                .center(new LatLng(37.247,127.078))
                .radius(20)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));

        googleMap.addCircle(new CircleOptions()
                .center(new LatLng(37.249,127.078))
                .radius(20)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));

        googleMap.addCircle(new CircleOptions()
                .center(new LatLng(37.252,127.077))
                .radius(20)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));

        googleMap.addCircle(new CircleOptions()
                .center(new LatLng(37.255,127.073))
                .radius(20)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));

        googleMap.addCircle(new CircleOptions()
                .center(new LatLng(37.257,127.068))
                .radius(20)
                .strokeColor(Color.RED)
                .fillColor(Color.RED));

         */






        /*
        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-27.457, 153.040),
                        new LatLng(-33.852, 151.211),
                        new LatLng(-37.813, 144.962),
                        new LatLng(-34.928, 138.599)));

// Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon1.setTag("alpha");
        */
        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.246,127.077), 16));

        // Set listeners for click events.
        /*
        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);


        // 기존에 사용하던 다음 2줄은 문제가 있습니다.
        // CameraUpdateFactory.zoomTo가 오동작하네요.
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10));
        */
    }

}



////////////////////////////////////////////////////
/*
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

                    //Toast.makeText(
                    //        getApplicationContext(),
                    //        "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                    //        Toast.LENGTH_LONG).show();


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
 */