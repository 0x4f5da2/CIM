package com.example.uibestpractice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.Serializable;

public class MsgService extends Service implements AnonymousClientUpdater{
    private AnonymousClientModel anonymousClientModel = null;
    private MsgUpdater msgUpdater = null;
    public MsgService() {
    }

    @Override
    public void onDestroy() {
        if(anonymousClientModel != null){
            anonymousClientModel.stop();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if(anonymousClientModel != null){
            anonymousClientModel.stop();
        }
        return super.onUnbind(intent);
    }

    @Override
    public void newMsg(String newMsg) {
        msgUpdater.newMsg(newMsg);
    }

    @Override
    public void UnhandledException(CInstMsgException e) {
        msgUpdater.unhandledException(e);
    }

    class MsgBinder extends Binder implements Serializable {

        public void setup(String ip, int port, MsgUpdater msg) {
            try {
                msgUpdater = msg;
                anonymousClientModel = new AnonymousClientModel(ip, port, MsgService.this);
            } catch (CInstMsgException e) {
                msgUpdater.unhandledException(e);
            }
        }

        public void sendMsg(String p, String m) {
            try {
                anonymousClientModel.sendMessage(p, m);
            } catch (CInstMsgException e) {
                msgUpdater.unhandledException(e);
            }
        }
    }
}
