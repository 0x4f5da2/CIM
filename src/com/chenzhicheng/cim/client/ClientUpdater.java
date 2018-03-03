package com.chenzhicheng.cim.client;

import com.chenzhicheng.cim.exception.CInstMsgException;
import com.chenzhicheng.cim.protocol.Protocol;

public interface ClientUpdater {
    void newMsg(Protocol protocol);

    void UnhandledException(CInstMsgException ex);
}
