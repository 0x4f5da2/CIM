package com.chenzhicheng.cim.server;

public class ServerController implements ServerOnlineStatusHandler, AnonymousServerUpdater, ServerUpdater {
    private ServerGUI serverGUI;
    private ServerModel serverModel;
    private Thread anonymousServerModelThread;
    private Thread serverModelThread;

    public ServerController() {
        serverGUI = new ServerGUI(this);
        anonymousServerModelThread = new Thread(new AnonymousServerModel(this));
        serverModel = new ServerModel(this);
        serverModelThread = new Thread(serverModel);
    }

    public static void main(String[] args) {
        new ServerController().run();
    }

    public void run() {
        serverGUI.setVisible(true);
        anonymousServerModelThread.start();
        serverModelThread.start();
    }

    @Override
    synchronized public void showStatus() {
        serverGUI.appendServerLog(serverModel.getOnlineUser());
    }

    @Override
    synchronized public void newLog(String s) {
        serverGUI.appendServerLog(s);
    }

    @Override
    synchronized public void newOnline() {
        serverGUI.newOnline();
    }

    @Override
    synchronized public void newOffile() {
        serverGUI.newOffile();
    }

    @Override
    synchronized public void newAnonymousMsg(String s) {
        serverGUI.appendGroupChatArea(s);
    }
}
