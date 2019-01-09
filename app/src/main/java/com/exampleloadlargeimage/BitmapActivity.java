package com.exampleloadlargeimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

public class BitmapActivity extends AppCompatActivity {
    private static final String TAG = "BitmapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Log.e(TAG, "displayMetrics:======== "+displayMetrics.densityDpi);
        Log.e(TAG, "displayMetrics:======== "+displayMetrics.density);


        BitmapFactory.Options tmpOptions = new BitmapFactory.Options();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_img, tmpOptions);
        Log.e(TAG, "bitmap======: " + tmpOptions.outWidth);
        Log.e(TAG, "bitmap======: " + tmpOptions.outHeight);

        BitmapFactory.Options Options = new BitmapFactory.Options();

        Bitmap bitmapNo = BitmapFactory.decodeResource(getResources(), R.drawable.test_img_no, Options);
        Log.e(TAG, "bitmapNo======: width" + bitmapNo.getWidth());
        Log.e(TAG, "bitmapNo======: height" + bitmapNo.getHeight());


        Bitmap bitmapTest = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_test);
        Log.e(TAG, "bitmapTest======: width" + bitmapTest.getWidth());
        Log.e(TAG, "bitmapTest======: height" + bitmapTest.getHeight());
        ImageView iv=findViewById(R.id.iv);
        iv.setImageBitmap(bitmapTest);
    }
}
