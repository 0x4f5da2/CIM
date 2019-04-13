package com.example.uibestpractice;

/**
 * Created by chen1 on 2018/2/28.
 */

public interface AnonymousClientUpdater {
    void newMsg(String newMsg);

    void UnhandledException(CInstMsgException ex);
}
