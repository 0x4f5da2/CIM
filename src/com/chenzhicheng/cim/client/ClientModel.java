package com.chenzhicheng.cim.client;

import com.chenzhicheng.cim.exception.CInstMsgException;
import com.chenzhicheng.cim.protocol.Protocol;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientModel {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean logged;
    private ClientUpdater clientUpdater;

    public ClientModel(String host, int port, ClientUpdater clientUpdater) throws CInstMsgException {
        try {
            socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            logged = false;
        } catch (Exception ex) {
            throw new CInstMsgException("ConnectionError", "Can't connect to the server, please check the Internet connection");
        }
        this.clientUpdater = clientUpdater;
    }

    public void login(String username, String password) throws CInstMsgException {
        if (logged) {
            throw new CInstMsgException("UnknownError", "An unknown error occurred");
        }
        Protocol ret = null;
        try {
            oos.writeObject(new Protocol(username, password));
            oos.flush();
            ret = (Protocol) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CInstMsgException("ConnectionError", "Can't connect to the server, please check the Internet connection");
        }
        if (ret != null) {
            switch (ret.getStatus()) {
                case -1:
                    throw new CInstMsgException("UnknownError", "An unknown error occurred");
                case 2:
                    throw new CInstMsgException("DuplicatedLogin", "Your account has already logged in");
                case 4:
                    throw new CInstMsgException("UsernameOrPasswordError", "Please enter correct username and password");
                case 8:
                    logged = true;
                    break;
                default:
                    throw new CInstMsgException("UnknownError", "An unknown error occurred");

            }
        }
        Thread thread = new Thread(new IncomingReader());
        thread.start();
    }

    synchronized public void send(String from, String to, String msg) throws CInstMsgException {
        try {
            oos.writeObject(new Protocol(from, to, msg));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CInstMsgException("SendError", "Please check the Internet connection!");
        }
    }


    class IncomingReader implements Runnable {
        public void run() {
            Protocol protocol;
            try {
                while ((protocol = (Protocol) ois.readObject()) != null) {
                    clientUpdater.newMsg(protocol);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                clientUpdater.UnhandledException(new CInstMsgException("ConnectionError", "Can't connect to the server, please check the Internet connection"));
            }
        }
    }
}
