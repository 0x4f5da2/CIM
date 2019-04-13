package com.chenzhicheng.cim.server;

import com.chenzhicheng.cim.protocol.Protocol;
import com.chenzhicheng.cim.util.TimeGetter;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerModel implements Runnable {
    private ArrayList<ClientData> allUser;
    private ServerUpdater serverUpdater;

    public ServerModel(ServerUpdater s) {
        this.serverUpdater = s;
        this.allUser = new ArrayList<ClientData>();
    }

    public void run() {
        try {
            ServerSocket serverSock = new ServerSocket(56789);  //todo: customize the port here

            while (true) {
                try {
                    Socket clientSocket = serverSock.accept();
                    Thread t = new Thread(new ClientHandler(clientSocket));
                    t.start();
                    System.out.println("got a connection");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private boolean sendTo(Protocol protocol) {

        for (ClientData tmp : allUser) {
            if (tmp.getUsername().equals(protocol.getTo())) {
                try {
                    tmp.getOos().writeObject(protocol);
                    tmp.getOos().flush();
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }

    private boolean verify(String username, String password) {  //todo: write your own verification code here
        return true;
    }

    public String getOnlineUser() {
        StringBuilder sb = new StringBuilder();
        sb.append("Online User@" + TimeGetter.getTime() + ":\n");
        boolean onUserOnline = true;
        for (ClientData tmp : allUser) {
            sb.append(tmp.getUsername() + "\n");
            onUserOnline = false;
        }
        if (onUserOnline) {
            return "NO USER ONLINE@" + TimeGetter.getTime() + "\n";
        } else {
            return sb.toString();
        }

    }

    public class ClientHandler implements Runnable {
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private Socket sock;
        private boolean isLogin;
        private String username;


        public ClientHandler(Socket clientSocket) {
            try {
                sock = clientSocket;
                ois = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                oos = new ObjectOutputStream(clientSocket.getOutputStream());
                isLogin = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            Protocol tmp;
            try {
                while ((tmp = (Protocol) ois.readObject()) != null) {
                    if (tmp.getStatus() == 1) {
                        if (verify(tmp.getFrom(), tmp.getPassword())) {
                            ClientData newNode = new ClientData(tmp.getFrom(), ois, oos, sock);
                            if (!allUser.contains(newNode)) {
                                allUser.add(newNode);
                                oos.writeObject(new Protocol(8));
                                oos.flush();
                                serverUpdater.newLog(tmp.getFrom() + " connected@" + TimeGetter.getTime() + "\n");
                                serverUpdater.newOnline();
                                isLogin = true;
                                username = tmp.getFrom();
                            } else {
                                oos.writeObject(new Protocol(2));
                                oos.flush();
                                sock.close();
                                serverUpdater.newLog(tmp.getFrom() + " connect failed@" + TimeGetter.getTime() + "\n");
                                break;
                            }
                        } else {
                            oos.writeObject(new Protocol(4));
                            oos.flush();
                            sock.close();
                            break;
                        }
                        continue;
                    }
                    if (tmp.getStatus() == 0) {
                        boolean status = sendTo(tmp);
                        if (!status) {
                            try {
                                Thread.sleep(100);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            oos.writeObject(new Protocol(tmp.getTo(), 13, tmp.getFrom(), "USER OFFLINE OR NOT FOUND"));
                            oos.flush();
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                if (isLogin) {
                    Iterator<ClientData> it = allUser.iterator();
                    while (it.hasNext()) {
                        ClientData delTmp = it.next();
                        if (delTmp.getUsername().equals(username)) {
                            try {
                                delTmp.getSock().close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            it.remove();
                        }
                    }
                    serverUpdater.newLog(username + " disconnected@" + TimeGetter.getTime() + "\n");
                    serverUpdater.newOffile();
                }
            }
        }
    }
}
