package com.example.practnumber3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    private String message;
    final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_message);
    }

    public void onSendMessage(View view){
        EditText editText = (EditText) findViewById(R.id.message);
        Intent intent = new Intent(CreateMessageActivity.this, ReceiveMessageActivity.class);
        intent.setType("text/plain");
        intent.putExtra("messageSend", editText.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Message: onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Message: onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Message: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Message: onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"Message: onRestart");
    }
}
