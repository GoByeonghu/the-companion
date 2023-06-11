package com.gobyeonghu.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddActivity_googlemap extends AppCompatActivity
        implements OnMapReadyCallback {

    EditText editTitle, editText, editStart, editEnd;
    Button btnPost, btnStratok, btnEndok;
    double lat, lon,slat, slon,elat, elon;


    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_googlemap);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_search);
        mapFragment.getMapAsync(this);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editText = (EditText) findViewById(R.id.editText);
        editStart = (EditText) findViewById(R.id.editStart);
        editEnd = (EditText) findViewById(R.id.editEnd);

        btnPost = (Button) findViewById(R.id.btnPost);
        btnStratok = (Button) findViewById(R.id.btnStartOk);
        btnEndok = (Button) findViewById(R.id.btnEndOk);

        btnStratok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                searchLocation(editStart.getText().toString().trim());
                slat=lat;
                slon=lon;
            }
        });

        btnEndok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                searchLocation(editEnd.getText().toString().trim());
                elat=lat;
                elon=lon;
            }
        });


        btnPost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "게시글을 생성하였습니다.",Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
    }

    // 장소 검색 버튼 클릭 시 호출되는 메서드
    public void searchLocation(String locationName) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                lat=address.getLatitude();
                lon=address.getLongitude();
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                // 위치를 맵에 표시
                map.addMarker(new MarkerOptions().position(latLng).title(locationName));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
            }
        } catch (IOException e) {
            Log.i("MY_GOOGLE_MAP","error");
            e.printStackTrace();
        }
    }


}