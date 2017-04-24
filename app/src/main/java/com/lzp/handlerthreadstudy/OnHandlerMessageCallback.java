package com.lzp.handlerthreadstudy;

/**
 * Created by li.zhipeng on 2017/4/14.
 *
 *      Handler处理信息结束的回调函数
 */

public interface OnHandlerMessageCallback {

    void callback(int what, Object obj);
}
