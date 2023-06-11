package com.gobyeonghu.the_companion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class SecondFragment extends Fragment {
    String data="a";
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
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        post1 = view.findViewById(R.id.post1);
        post2 = view.findViewById(R.id.post2);
        post3 = view.findViewById(R.id.post3);
        post4 = view.findViewById(R.id.post4);

        id1 =view.findViewById(R.id.title1);
        id2 =view.findViewById(R.id.title2);
        id3 =view.findViewById(R.id.title3);
        id4 =view.findViewById(R.id.title4);

        btn_ok1 = view.findViewById(R.id.btn_ok1);
        btn_ok2 = view.findViewById(R.id.btn_ok2);
        btn_ok3 = view.findViewById(R.id.btn_ok3);
        btn_ok4 = view.findViewById(R.id.btn_ok4);

        btn_match1 = view.findViewById(R.id.btn_match1);
        btn_match1 = view.findViewById(R.id.btn_match2);
        btn_match1 = view.findViewById(R.id.btn_match3);
        btn_match1 = view.findViewById(R.id.btn_match4);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        id1.setText("같이 여행가요");
        id2.setText("일출보러 갈사람");
        id3.setText("택시 같이 타요");
        */
        id1.setText("영통에서 조깅할사람");
        id2.setText("송도 여행가요");

        post1.setVisibility(View.VISIBLE);
        post2.setVisibility(View.VISIBLE);
        //post3.setVisibility(View.VISIBLE);

        btn_ok1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                intent.putExtra("rej_num", "10");
                intent.putExtra("rej_data", "b");
                intent.putExtra("title", "영통에서 조깅할사람");
                intent.putExtra("made_by", "bhwkd");
                intent.putExtra("text", "7월 부터 아침마다 조깅할사람 찾아요");
                startActivity(intent);
            }
        });

        btn_ok2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                intent.putExtra("rej_num", "10");
                intent.putExtra("rej_data", "c");
                intent.putExtra("title", "송도 여행가요");
                intent.putExtra("made_by", "test_user014");
                intent.putExtra("text", "송도 여행이 처음인데 가이드 해주실분 찾아요.\n 밥살게요");
                startActivity(intent);
            }
        });

        btn_ok3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),
                        MainActivity.class);
                intent.putExtra("rej_num", "10");
                intent.putExtra("rej_data", "b");
                intent.putExtra("title", " ");
                intent.putExtra("made_by", id3.getText());
                intent.putExtra("text", " ");
                startActivity(intent);
            }
        });
    }
}
