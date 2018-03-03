package com.chenzhicheng.cim.client;

import com.chenzhicheng.cim.exception.CInstMsgException;

public interface AnonymousClientUpdater {
    void newMsg(String newMsg);

    void UnhandledException(CInstMsgException ex);
}
