package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CauHoiTroGiupActivity extends AppCompatActivity {
    LinearLayout question_1,question_2,question_3,question_4,question_5,question_6,question_7,question_8,question_9,question_10;
    LinearLayout answer_1,answer_2,answer_3,answer_4,answer_5,answer_6,answer_7,answer_8,answer_9,answer_10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau_hoi_tro_giup);
        question_1 = findViewById(R.id.question_1);
        question_2 = findViewById(R.id.question_2);
        question_3 = findViewById(R.id.question_3);
        question_4 = findViewById(R.id.question_4);
        question_5 = findViewById(R.id.question_5);
        question_6 = findViewById(R.id.question_6);
        question_7 = findViewById(R.id.question_7);
        question_8 = findViewById(R.id.question_8);
        question_9 = findViewById(R.id.question_9);
        question_10 = findViewById(R.id.question_10);
        answer_1 = findViewById(R.id.answer_1);
        answer_2 = findViewById(R.id.answer_2);
        answer_3 = findViewById(R.id.answer_3);
        answer_4 = findViewById(R.id.answer_4);
        answer_5 = findViewById(R.id.answer_5);
        answer_6 = findViewById(R.id.answer_6);
        answer_7 = findViewById(R.id.answer_7);
        answer_8 = findViewById(R.id.answer_8);
        answer_9 = findViewById(R.id.answer_9);
        answer_10 = findViewById(R.id.answer_10);
        question_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_1.setVisibility(View.VISIBLE);
            }
        });
        question_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_2.setVisibility(View.VISIBLE);
            }
        });
        question_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_3.setVisibility(View.VISIBLE);
            }
        });
        question_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_4.setVisibility(View.VISIBLE);
            }
        });
        question_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_5.setVisibility(View.VISIBLE);
            }
        });
        question_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_6.setVisibility(View.VISIBLE);
            }
        });
        question_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_7.setVisibility(View.VISIBLE);
            }
        });
        question_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_8.setVisibility(View.VISIBLE);
            }
        });
        question_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_9.setVisibility(View.VISIBLE);
            }
        });
        question_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gone();
                answer_10.setVisibility(View.VISIBLE);
            }
        });
    }
    private  void Gone(){
        answer_1.setVisibility(View.GONE);
        answer_2.setVisibility(View.GONE);
        answer_3.setVisibility(View.GONE);
        answer_4.setVisibility(View.GONE);
        answer_5.setVisibility(View.GONE);
        answer_6.setVisibility(View.GONE);
        answer_7.setVisibility(View.GONE);
        answer_8.setVisibility(View.GONE);
        answer_9.setVisibility(View.GONE);
        answer_10.setVisibility(View.GONE);
    }
}