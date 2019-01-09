package com.example.lt.cavans3d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView nomalImg, rotate1Img, rotate2Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nomalImg = findViewById(R.id.normal);
        rotate1Img = findViewById(R.id.rotate1_img);
        rotate2Img = findViewById(R.id.rotate2_img);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xvpn);

        nomalImg.setImageBitmap(bitmap);

        /**
         * 旋转中心为(0,0)
         */
        Matrix matrix = getMatrix();
        Bitmap bit = getBitmap(bitmap, matrix);
        rotate1Img.setImageBitmap(bit);

        /**
         * 旋转中心为(0,height/2)
         */
        matrix = getMatrix();
        matrix.preTranslate(0, -bitmap.getHeight() / 2);
        matrix.postTranslate(0, bitmap.getHeight() / 2);
        bit = getBitmap(bitmap, matrix);
        rotate2Img.setImageBitmap(bit);

    }

    private Bitmap getBitmap(Bitmap bitmap, Matrix matrix) {
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @NonNull
    private Matrix getMatrix() {
        Matrix matrix = new Matrix();
        Camera camera = new Camera();
        camera.save();
        camera.rotateY(45);
        camera.getMatrix(matrix);
        camera.restore();
        return matrix;
    }
}
