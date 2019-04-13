package com.example.uibestpractice;

/**
 * Created by chen1 on 2018/2/28.
 */

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.LogRecord;

public class AnonymousClientModel {
    private DatagramSocket clientSocket;
    private InetAddress IPAddress;
    private AnonymousClientUpdater updater;
    private String IP;
    private int port;
    private boolean running;
    private String s;
    private CInstMsgException cInstMsgException;
    private Handler handler;

    public AnonymousClientModel(String IP, int port, final AnonymousClientUpdater updater) throws CInstMsgException {
        this.running = true;
        this.IP = IP;
        this.port = port;
        this.handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        updater.newMsg(s);
                        break;
                    case -1:
                        updater.UnhandledException(cInstMsgException);
                        break;
                    default:
                        updater.UnhandledException(new CInstMsgException("UnknownError", "Unknown error"));
                }
            }
        };
        try {
            this.clientSocket = new DatagramSocket();
            this.IPAddress = InetAddress.getByName(this.IP);
            this.updater = updater;
            byte[] sendData = "---有新的人加入群聊---".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, this.port);
            clientSocket.send(sendPacket);
        } catch (Exception ex) {
            throw new CInstMsgException("ConnectionError", "Failed to connnect to the server!");
        }
        Thread thread = new Thread(new Receiver());
        thread.start();
    }

    public void stop() {
        this.running = false;
    }


    synchronized public void sendMessage(String nickName, String message) throws CInstMsgException {
        if ("".equals(nickName.trim())) {
            nickName = "Anonymous";
        }
        String toSend = nickName + "@" + TimeGetter.getTime() + ":\n" + message;
        if (toSend.getBytes().length > 1000) {
            throw new CInstMsgException("MessageToLong", "The message should be no longer than 1000 chars!");
        } else {
            try {
                byte[] sendData = toSend.getBytes().clone();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, this.port);
                clientSocket.send(sendPacket);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new CInstMsgException("MessageSendError", "A error occurred during sending the message!");
            }
        }

    }

    synchronized private void setMsg(String s) {
        this.s = s;
    }

    synchronized  private void setEx(CInstMsgException e){
        cInstMsgException = e;
    }


    public class Receiver implements Runnable {
        @Override
        public void run() {
            try {
                while (running) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    Message m = handler.obtainMessage();
                    setMsg(msg);
                    m.what = 1;
                    m.sendToTarget();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                setEx(new CInstMsgException("ConnectionError", "Failed to connnect to the server!"));
                Message m = handler.obtainMessage();
                m.what = -1;
                m.sendToTarget();
            }

        }
    }
}
