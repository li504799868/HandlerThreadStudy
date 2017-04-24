package com.lzp.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    /**
     * 单线程池
     * */
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startFirstThread();
        startSecondThread();
        Log.e("lzp", "over");

    }

    private void startFirstThread(){
//
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++){
//                    Log.e("lzp", "first:"+ i);
//                    if (i == 50){
////                        startSecondThread();
//                        try {
//                            sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        };
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++){
                    Log.e("lzp", "first:"+ i);
                    if (i == 50){
//                        startSecondThread();
                        try {
                            synchronized (this){
                                wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void startSecondThread(){
//        Thread thread =  new Thread(){
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++){
//                    Log.e("lzp", "second:"+ i);
//                }
//            }
//        };
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++){
                    Log.e("lzp", "second:"+ i);
                }
            }
        });
    }


}
