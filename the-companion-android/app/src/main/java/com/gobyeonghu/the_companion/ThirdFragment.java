package com.gobyeonghu.the_companion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class ThirdFragment extends Fragment {String data="a";
    LinearLayout post1, post2, post3, post4;
    TextView id1, id2, id3, id4;
    Button btn_ok1, btn_ok2, btn_ok3, btn_ok4;
    Button btn_match1, btn_match2, btn_match3, btn_match4;


    public void setData(String data){
        this.data = data;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        post1 = view.findViewById(R.id.post1);
        post2 = view.findViewById(R.id.post2);
        post3 = view.findViewById(R.id.post3);
        post4 = view.findViewById(R.id.post4);

        id1 =view.findViewById(R.id.id1);
        id2 =view.findViewById(R.id.id2);
        id3 =view.findViewById(R.id.id3);
        id4 =view.findViewById(R.id.id4);

        btn_ok1 = view.findViewById(R.id.btn_ok1);
        btn_ok2 = view.findViewById(R.id.btn_ok2);
        btn_ok3 = view.findViewById(R.id.btn_ok3);
        btn_ok4 = view.findViewById(R.id.btn_ok4);

        //btn_match1 = view.findViewById(R.id.btn_match1);
        //btn_match2 = view.findViewById(R.id.btn_match2);
        btn_match3 = view.findViewById(R.id.btn_match3);
        btn_match4 = view.findViewById(R.id.btn_match4);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id1.setText("청주 여행 갈사람");
        post1.setVisibility(View.VISIBLE);
        //post2.setVisibility(View.VISIBLE);
        //post3.setVisibility(View.VISIBLE);

        btn_ok1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                intent.putExtra("rej_num", "1");
                intent.putExtra("rej_data", "d");
                intent.putExtra("title", "청주 여행 갈사람");
                intent.putExtra("made_by", "khu_kbh");
                intent.putExtra("text", "청주 여행 동행자 찾아요");
                startActivity(intent);


                //Toast.makeText(getContext(), "email: kjhg@naver.com" , Toast.LENGTH_SHORT).show();
            }
        });

        btn_ok2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                intent.putExtra("rej_num", "10");
                intent.putExtra("rej_data", "a");
                intent.putExtra("title", " ");
                intent.putExtra("made_by", id2.getText());
                intent.putExtra("text", " ");
                startActivity(intent);
            }
        });

        btn_ok3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                intent.putExtra("rej_num", "10");
                intent.putExtra("rej_data", "a");
                intent.putExtra("title", " ");
                intent.putExtra("made_by", id3.getText());
                intent.putExtra("text", " ");
                startActivity(intent);

            }
        });




        //textView.setText("새로운 텍스트");
    }
}