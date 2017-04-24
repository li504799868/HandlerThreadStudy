package com.lzp.handlerthreadstudy;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/**
 * Created by li.zhipeng on 2017/4/14.
 *
 *      老师的Handler处理器
 */

public class TeacherHandler extends Handler {

    public static TeacherHandler getInstance(String name){
        HandlerThread teacherThread = new HandlerThread(name);
        teacherThread.start();
        return new TeacherHandler(teacherThread.getLooper());
    }

    private TeacherHandler(Looper looper){
        super(looper);
    }

    private OnHandlerMessageCallback callback;

    public void setOnHandlerMessageCallback(OnHandlerMessageCallback callback){
        this.callback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            // 接收到开始指令，老师开始发放试卷
            case 0:
                sleep();
                if (callback != null){
                    callback.callback(0, null);
                }
                break;

            // 学生答题结束，老师开始收试卷
            case 1:
                sleep();
                if (callback != null){
                    callback.callback(1, null);
                }
                break;
        }
    }

    private void sleep(){
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
