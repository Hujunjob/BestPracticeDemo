package com.hiscene.rxjavastudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivityTAG";
    private static final String PATH = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571929516766&di=d8286cf3fbd4fac80b1d75d8a6c1be22&imgtype=0&src=http%3A%2F%2Fpic33.nipic.com%2F20131008%2F13661616_134400215000_2.jpg";

    private static final int IMAGE = 419;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        progressBar = findViewById(R.id.progress_circular);

        findViewById(R.id.btn_download).setOnClickListener(view -> {
            downloadByNormal();
        });
        findViewById(R.id.btn_rxdownload).setOnClickListener(view -> {
            downloadByRxJava();
        });
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.build().create(Host.class).hello();
    }

    interface Host{
        void hello();
    }

    /**
     * 传统下载方法
     */
    private void downloadByNormal() {
        showProcess();
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadImage();
            }
        }).start();
    }

    private void downloadImage() {
        try {
            URL url = new URL(PATH);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);

            //响应
            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //消息池
                Message message = mHandler.obtainMessage();
                message.what = IMAGE;
                message.obj = bitmap;
                mHandler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case IMAGE:
                    Bitmap bitmap = (Bitmap) message.obj;
                    imageView.setImageBitmap(bitmap);
                    hideProgress();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    private void showProcess() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgress() {
        if (progressBar != null) {

            progressBar.setVisibility(View.GONE);
        }
    }

    /**************************  RxJava方式 ****************************/
    private void downloadByRxJava() {
        String strings[] = {"步惊云", "聂飞", "雄霸"};
        Observable                     //被观测者
                .fromArray(strings)
                .subscribe(s -> {         //简化版的观测者
                    Log.d(TAG, "downloadByRxJava: " + s);
                });

        Observable.fromArray(strings)
                .subscribe(new Observer<String>() {     //完整版的观测者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        //事件源分发
        Observable
                //被观测者发送的是图片地址String
                .create((ObservableOnSubscribe<String>) emitter -> {
                    emitter.onNext(PATH);
                    emitter.onComplete();
                })
                //上游流下来的String进行下载
                .map(s -> {
                    try {
                        URL url = new URL(s);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setConnectTimeout(5000);

                        //响应
                        int code = httpURLConnection.getResponseCode();
                        if (code == HttpURLConnection.HTTP_OK) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            return BitmapFactory.decodeStream(inputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                //流下来的图片加水印
                .map(bitmap -> addTextWatermark(bitmap,"水印",200,R.color.colorPrimary,10,10,true,true))
                .subscribeOn(Schedulers.io()) //上游

                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//下游

                //最终订阅者（观测者）需要得到Bitmap结果
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

//    private Bitmap addTag(Bitmap bitmap){
//        Bitmap bitmap1 = Bitmap.createBitmap(bitmap);
//
//        return bitmap1;
//    }

    /**
     * 给一张Bitmap添加水印文字。
     *
     * @param bitmap      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小 ，单位pix。
     * @param color    水印字体颜色。
     * @param x        起始坐标x
     * @param y        起始坐标y
     *@param positionFlag  居左/居右
     * @param recycle  是否回收
     * @return 已经添加水印后的Bitmap。
     */
    public static Bitmap addTextWatermark(Bitmap bitmap, String content, int textSize, int color, float x, float y,boolean positionFlag, boolean recycle) {
        if (isEmptyBitmap(bitmap) || content == null) {
            return null;
        }
        Bitmap ret = bitmap.copy(bitmap.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, bitmap.getWidth() - x - bounds.width() - bounds.left, bitmap.getHeight() - bounds.height() - bounds.top - y, paint);

        if (positionFlag) {
            canvas.drawText(content, x, bitmap.getHeight() - bounds.height() - bounds.top - y, paint);

        } else {
            canvas.drawText(content, bitmap.getWidth() - x - bounds.width() - bounds.left, bitmap.getHeight() - bounds.height() - bounds.top - y, paint);

        }
        if (recycle && !bitmap.isRecycled())
            bitmap.recycle();
        return ret;
    }

    /**
     * Bitmap对象是否为空。
     */
    public static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

}
