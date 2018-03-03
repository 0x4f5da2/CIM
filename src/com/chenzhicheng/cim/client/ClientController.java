package com.chenzhicheng.cim.client;


import com.chenzhicheng.cim.exception.CInstMsgException;
import com.chenzhicheng.cim.protocol.Protocol;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController implements SendBtnHandler, ClientUpdater, ActionListener {
    private static String HOST_IP = "127.0.0.1";
    private static int CLIENT_PORT = 56789;
    private static int ANONYMOUS_PORT = 45678;
    private ClientModel clientModel = null;
    private ClientLoginGUI clientLoginGUI = null;
    private ClientStartMsgGUI clientStartMsgGUI = null;
    private AnonymousClientController anonymousClientController = null;
    private DefaultListModel<ClientGUI> defaultListModel = null;
    private boolean isLogin = false;
    private String username = null;

    public ClientController() {
        this.isLogin = false;
        clientLoginGUI = new ClientLoginGUI();
        clientLoginGUI.getLoginButt().addActionListener(this);
        clientLoginGUI.getGroupChatBtn().addActionListener(this);
        clientLoginGUI.getPassword().addActionListener(this);

        clientStartMsgGUI = new ClientStartMsgGUI();
        clientStartMsgGUI.getMessageStartBtn().addActionListener(this);
        clientStartMsgGUI.getUsernameToStart().addActionListener(this);

        defaultListModel = new DefaultListModel<ClientGUI>();
        clientStartMsgGUI.getRecentContactsList().setModel(defaultListModel);
        clientStartMsgGUI.getRecentContactsList().addListSelectionListener((e) -> {
                    if (!clientStartMsgGUI.getRecentContactsList().getValueIsAdjusting()) {
                        ClientGUI clientGUI = clientStartMsgGUI.getRecentContactsList().getSelectedValue();
                        if (clientGUI == null) {
                            return;
                        }
                        clientGUI.setVisible(true);
                        clientStartMsgGUI.getRecentContactsList().clearSelection();
                        clientStartMsgGUI.getRecentContactsList().updateUI();
                    }
                }
        );
    }

    public static void main(String[] args) {
        new ClientController().run();
    }

    public void run() {
        clientLoginGUI.setVisible(true);
    }

    @Override
    public void sendMsg(String param, String msg) {
        try {
            clientModel.send(username, param, msg);
        } catch (CInstMsgException ex) {
            JOptionPane.showMessageDialog(null, ex.getDetailedMsg(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void newMsg(Protocol protocol) {
        //todo
        int index = defaultListModel.indexOf(new ClientGUI(protocol.getFrom(), null, username));
        if (index >= 0) {
            ClientGUI clientGUI = defaultListModel.getElementAt(index);
            clientGUI.newMsg(protocol.getMessage(), protocol.getStatus());
            clientGUI.setVisible(true);
        } else {
            ClientGUI clientGUI = new ClientGUI(protocol.getFrom(), this, username);
            clientGUI.newMsg(protocol.getMessage(), protocol.getStatus());
            clientGUI.setVisible(true);
            defaultListModel.addElement(clientGUI);
            clientStartMsgGUI.getRecentContactsList().updateUI();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login":
                try {
                    clientModel = new ClientModel(HOST_IP, CLIENT_PORT, this);
                    Thread.sleep(50);
                    clientModel.login(clientLoginGUI.getUsername().getText(), clientLoginGUI.getPassword().getText());
                    isLogin = true;
                } catch (CInstMsgException ex) {
                    JOptionPane.showMessageDialog(null, ex.getDetailedMsg(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (isLogin) {
                    username = clientLoginGUI.getUsername().getText();
                    clientLoginGUI.setVisible(false);
                    clientStartMsgGUI.setVisible(true);
                    clientStartMsgGUI.setTitle(username + "@ChenZhicheng's Instant Message");
                }
                break;
            case "anonymous":
                try {
                    anonymousClientController = new AnonymousClientController(HOST_IP, ANONYMOUS_PORT);
                } catch (CInstMsgException ex) {
                    JOptionPane.showMessageDialog(null, ex.getDetailedMsg(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "startMsg":
                String usr = clientStartMsgGUI.getUsernameToStart().getText();
                int index = defaultListModel.indexOf(new ClientGUI(usr, null, username));
                if (index >= 0) {
                    ClientGUI clientGUI = defaultListModel.getElementAt(index);
                    clientGUI.setVisible(true);
                    clientStartMsgGUI.getRecentContactsList().updateUI();
                } else {
                    ClientGUI clientGUI = new ClientGUI(usr, this, username);
                    clientGUI.setVisible(true);
                    defaultListModel.addElement(clientGUI);
                    clientStartMsgGUI.getRecentContactsList().updateUI();
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "An unknown error occurred", "UnknownError", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    @Override
    public void UnhandledException(CInstMsgException ex) {
        JOptionPane.showMessageDialog(null, ex.getDetailedMsg(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
    }
}

