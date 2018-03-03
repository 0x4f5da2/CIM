package com.chenzhicheng.cim.client;


import com.chenzhicheng.cim.exception.CInstMsgException;
import com.chenzhicheng.cim.util.TimeGetter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AnonymousClientModel {
    private DatagramSocket clientSocket;
    private InetAddress IPAddress;
    private AnonymousClientUpdater updater;
    private String IP;
    private int port;
    private boolean running;

    public AnonymousClientModel(String IP, int port, AnonymousClientUpdater updater) throws CInstMsgException {
        this.running = true;
        this.IP = IP;
        this.port = port;
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


    public class Receiver implements Runnable {
        @Override
        public void run() {
            try {
                while (running) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    updater.newMsg(msg);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                updater.UnhandledException(new CInstMsgException("ConnectionError", "Failed to connnect to the server!"));
            }
        }
    }
}
