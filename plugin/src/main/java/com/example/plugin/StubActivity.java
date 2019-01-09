package com.example.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author lt
 * @package com.example.plugin
 * @date 2018/11/23 4:05 PM
 * @describe TODO
 * @project
 */
public class StubActivity extends Activity {
    private static final String TAG = "StubActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
    }
}
