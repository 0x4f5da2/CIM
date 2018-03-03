package com.chenzhicheng.cim.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientData {
    private String username;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket sock;

    public ClientData(String username, ObjectInputStream ois, ObjectOutputStream oos, Socket sock) {
        this.username = username;
        this.ois = ois;
        this.oos = oos;
        this.sock = sock;
    }

    public String getUsername() {
        return username;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    @Override
    public boolean equals(Object obj) {
        ClientData nodeTmp = (ClientData) obj;
        return getUsername().equals(nodeTmp.getUsername());
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }
}
