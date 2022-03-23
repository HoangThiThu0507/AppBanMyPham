package com.example.projectcuoikhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    EditText etMessage;
    ImageView imgSend;
    RecyclerView rcvMessage;
    List<Message>messageList;
    AdapterMessage adapterMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        etMessage = findViewById(R.id.etMessage);
        imgSend = findViewById(R.id.imgSend);
        rcvMessage = findViewById(R.id.lvMessage);

        messageList = new ArrayList<>();
        adapterMessage = new AdapterMessage();
        adapterMessage.setData(messageList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvMessage.setLayoutManager(linearLayoutManager);
        rcvMessage.setAdapter(adapterMessage);
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SenMessage();
            }
        });
        etMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckKeyBoard();
            }
        });
    }

    private void CheckKeyBoard() {
        final View activityRoorView = findViewById(R.id.activityRoot);
        activityRoorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRoorView.getWindowVisibleDisplayFrame(r);
                int heightDiff = activityRoorView.getRootView().getHeight() - r.height();
                if(heightDiff > 0.25*activityRoorView.getRootView().getHeight()){
                    if(messageList.size() > 0){
                        rcvMessage.smoothScrollToPosition(messageList.size()-1);
                        activityRoorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    private void SenMessage() {
        String strMessage = etMessage.getText().toString().trim();
        if(TextUtils.isEmpty(strMessage)){
            return;
        }
        messageList.add(new Message(strMessage));
        adapterMessage.notifyDataSetChanged();
        rcvMessage.scrollToPosition(messageList.size() - 1);
        etMessage.setText("");
    }
}