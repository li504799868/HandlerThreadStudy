package com.lzp.handlerthreadstudy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    /**
     * 老师的操作Handler
     * */
    private TeacherHandler teacherHandler;

    /**
     * 学生的操作handler
     * */
    private StudentHandler studentHandler;

    /**
     * 线程池
     * */
//    private ExecutorService threadPool = Executors.newSingleThreadExecutor();

    /**
     * 更新UI的Handler
     * */
    private Handler mainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    textView.setText("发放试卷完毕，学生开始答题...");
                    break;
                case 1:
                    textView.setText("考试时间到，老师收试卷...");
                    break;
                case 2:
                    textView.setText("考试结束...");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        // 初始化handler
        initHandlerThread();
        // 开始考试
        startExamination();

//        threadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                //
//            }
//        });
    }

    private void startExamination() {
        teacherHandler.sendEmptyMessage(0);
        textView.setText("考试开始，老师发放试卷...");
    }

    /**
     * 初始化HandlerThread
     * */
    private void initHandlerThread() {
        Log.e("lzp", "mainLooper:" + getMainLooper().toString());

        teacherHandler = TeacherHandler.getInstance("teacher");
        teacherHandler.setOnHandlerMessageCallback(new OnHandlerMessageCallback() {
            @Override
            public void callback(int what, Object obj) {
                switch (what){
                    // 试卷发送结束
                    case 0:
                        // 更新ui
                        mainHandler.sendEmptyMessage(0);
                        // 学生开始答题
                        studentHandler.sendEmptyMessage(0);
                        break;
                    // 试卷回收结束
                    case 1:
                        mainHandler.sendEmptyMessage(2);
                        break;
                }
            }
        });

        studentHandler = StudentHandler.getInstance("student");
        studentHandler.setOnHandlerMessageCallback(new OnHandlerMessageCallback() {
            @Override
            public void callback(int what, Object obj) {
                // 学生答题结束
                mainHandler.sendEmptyMessage(1);
                teacherHandler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        teacherHandler.release();
        studentHandler.release();
    }

    private void testLooper(){
        new Thread(){
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Looper.prepare();
                textView.setText("屌爆了哦");
//                Toast.makeText(MainActivity.this, "hahaha", Toast.LENGTH_SHORT).show();
                Looper.loop();

            }
        }.start();
    }
}
