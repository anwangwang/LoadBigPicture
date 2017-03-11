package com.awwhome.loadbigpicture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private ImageView iv_big_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 找到图片控件
        iv_big_picture = (ImageView) findViewById(R.id.iv_big_picture);


    }

    // 加载大图片
    public void laodbigpicture(View view) {
        Log.d(TAG, "laodbigpicture: 开始加载大图片");
        String path = Environment.getExternalStorageDirectory().getPath();
        BitmapFactory.Options options = new BitmapFactory.Options();

        // true 返回一个null,不去真正解析位图，但是能够返回图片的一些信息（宽高）
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path + "/dog.jpg", options);

        // 1.获取大图片的宽高
        int imgHeight = options.outHeight;
        int imgWidth = options.outWidth;

        // 2.获取手机的宽高 获取 windowmanager 实例
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        int screenHeight = manager.getDefaultDisplay().getHeight();
        int screenWidth = manager.getDefaultDisplay().getWidth();


        // 3.计算压缩比
        int scale = 1; // 默认的缩放比为1
        int scaleX = imgWidth / screenWidth; // 宽
        int scaleY = imgHeight / screenHeight; // 高

        // 4.按照大一点的压缩比压缩图片
        if (scaleX >= scaleY && scaleX > scale) {
            scale = scaleX;
        }
        if (scaleY > scaleX && scaleY > scale) {
            scale = scaleY;
        }

        Log.d(TAG, "laodbigpicture: 计算出来的缩放比：" + scale);
        // 5.按照缩放比显示图片在控件上
        options.inSampleSize = scale;

        // 6.开始真正的解析位图
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path + "/dog.jpg", options);

        // 7.把图片显示在控件上
        iv_big_picture.setImageBitmap(bitmap);

    }
}
