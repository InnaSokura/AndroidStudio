package com.example.practnumber3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReceiveMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_receive_message);

        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.message);
        textView.setText(intent.getStringExtra("messageSend"));
    }

}
