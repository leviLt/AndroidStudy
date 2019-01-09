package com.example.lt.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: =======");
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ========");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ============");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ============");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ============");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ==========");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ==========");
    }
}
