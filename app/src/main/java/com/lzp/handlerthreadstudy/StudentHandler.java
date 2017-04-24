package com.lzp.handlerthreadstudy;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/**
 * Created by li.zhipeng on 2017/4/14.
 * <p>
 * 学生的Handler处理器
 */

public class StudentHandler extends Handler {

    public static StudentHandler getInstance(String name){
        HandlerThread studentThread = new HandlerThread(name);
        studentThread.start();
        return new StudentHandler(studentThread.getLooper());
    }

    public StudentHandler(Looper looper){
        super(looper);
    }

    private OnHandlerMessageCallback callback;

    public void setOnHandlerMessageCallback(OnHandlerMessageCallback callback){
        this.callback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        // 接收到开始指令，老师开始发放试卷
        sleep();
        if (callback != null){
            callback.callback(msg.what, null);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void release(){
        getLooper().quit();
    }
}
