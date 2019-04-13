package com.chenzhicheng.cim.client;

import com.chenzhicheng.cim.exception.CInstMsgException;
import com.chenzhicheng.cim.util.TimeGetter;

import javax.swing.*;

public class AnonymousClientController implements AnonymousClientUpdater, SendBtnHandler {
    private AnonymousClientModel model;
    private AnonymousClientGUI gui;
    private String IP;
    private int port;

    public AnonymousClientController(String IP, int port) throws CInstMsgException {
        this.IP = IP;
        this.port = port;
        this.gui = new AnonymousClientGUI(this);
        this.gui.setVisible(true);
        this.model = new AnonymousClientModel(this.IP, this.port, this);
    }

    @Override
    synchronized public void newMsg(String newMsg) {
        gui.newMsg(newMsg);
    }

    @Override
    public void sendMsg(String from, String msg) {
        if ("".equals(from.trim())) {
            from = "Anonymous";
        }
        String toSend = from + "@" + TimeGetter.getTime() + ":\n" + msg;
        gui.newMsg(toSend);
        try {
            model.sendMessage(from, msg);
        } catch (CInstMsgException ex) {
            JOptionPane.showMessageDialog(null, ex.getDetailedMsg(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void UnhandledException(CInstMsgException ex) {
        JOptionPane.showMessageDialog(null, ex.getDetailedMsg(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
    }
}
