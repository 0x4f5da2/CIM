package com.example.uibestpractice;

/**
 * Created by chen1 on 2018/2/28.
 */

public interface MsgUpdater {
    void newMsg(String s);
    void unhandledException(CInstMsgException e);
}
