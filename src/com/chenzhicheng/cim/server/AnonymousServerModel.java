package com.chenzhicheng.cim.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

public class AnonymousServerModel implements Runnable {
    private ArrayList<AnonymousClientData> allUser;
    private DatagramSocket serverSocket;
    private AnonymousServerUpdater anonymousServerUpdater;

    public AnonymousServerModel(AnonymousServerUpdater a) {
        this.allUser = new ArrayList<AnonymousClientData>();
        this.anonymousServerUpdater = a;
    }


    public void run() {
        try {
            serverSocket = new DatagramSocket(45678);
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                AnonymousClientData newAdd = new AnonymousClientData(IPAddress, port, System.currentTimeMillis());
                if (!allUser.contains(newAdd)) {
                    allUser.add(newAdd);
                }
                receiveData = null;
                sendAll(sentence, newAdd);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendAll(String strToSend, AnonymousClientData e) {
        //todo
        anonymousServerUpdater.newAnonymousMsg(strToSend + "\n");
        Iterator<AnonymousClientData> it = allUser.iterator();
        while (it.hasNext()) {
            byte[] sendData = new byte[1024];
            AnonymousClientData tmp = it.next();
            if(tmp.equals(e)) continue;
            if (System.currentTimeMillis() - tmp.getTimestamp() > 600000) {
                it.remove();
            } else {
                try {
                    sendData = strToSend.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, tmp.getIpAddr(), tmp.getPort());
                    serverSocket.send(sendPacket);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            sendData = null;

        }
    }
}
