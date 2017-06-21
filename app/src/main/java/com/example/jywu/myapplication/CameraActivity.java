package com.example.jywu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback,Camera.AutoFocusCallback {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Button mTaskPicture;
    private Camera camera;
    int lastid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(R.layout.activity_camera);
        initView();
    }
    private void initView(){
        lastid = getlastId();
        mSurfaceView = (SurfaceView) this.findViewById(R.id.svPreview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mTaskPicture = (Button) this.findViewById(R.id.taskPicture);

        mTaskPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    camera.autoFocus(CameraActivity.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if(success){
            camera.takePicture(null,null,jpeg);

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            releaseCameraAndPreview();
            camera = Camera.open(0);
            Log.d("camera open","!!!!!!!!!!!!");

        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }
            if(Build.VERSION.SDK_INT>=8) {
                camera.setDisplayOrientation(90);
            }
            try {
                camera.setPreviewDisplay(holder);
            }
            catch (IOException exception){
                camera.release();
                camera = null;
            }
    }
    private void releaseCameraAndPreview() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters = camera.getParameters();

        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setPreviewSize(640, 480);

        camera.setParameters(parameters);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;

    }

    public void saveBitmap(Bitmap bitmap) {

        FileOutputStream fOut;
        try {
            File dir = new File("/sdcard/demo/");
            if (!dir.exists()) {
                dir.mkdir();
            }


            String tmp = "/sdcard/demo/94iAccounting"+String.valueOf(lastid+1)+".jpg";
            fOut = new FileOutputStream(tmp);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            sendToGallery(this, tmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finish();
    }

    public Bitmap rotationBitmap(Bitmap picture) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(picture,picture.getWidth(),picture.getHeight(),true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
        return rotatedBitmap;
    }

    private int getlastId(){

        MyDBHelper DBhelper = new MyDBHelper(this,"hello",null,1);
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        int lastId = 0;
        Cursor c = db.query(DBhelper.TABLE_NAME,null,null,null,null,null,null);
        while (c.moveToNext()){
            lastId++;
        }
        Log.d("ID:",String.valueOf(lastId));
        return lastId;
    }

    public void sendToGallery(Context ctx, String path) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(new File(path));
        intent.setData(contentUri);
        ctx.sendBroadcast(intent);
    }
    Camera.PictureCallback jpeg = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] imgData, Camera camera) {
            if (imgData != null) {
                Bitmap picture = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                picture = rotationBitmap(picture);
                saveBitmap(picture);
            }

            camera.startPreview();
        }
    };
}
