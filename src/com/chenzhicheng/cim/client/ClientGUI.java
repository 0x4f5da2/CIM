package com.chenzhicheng.cim.client;

import com.chenzhicheng.cim.util.TimeGetter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientGUI extends JFrame {

    private JPanel contentPane;
    private JTextField messageToSend;
    private JButton sendButt;
    private JTextArea textArea;
    private String usrID;
    private String me;
    private SendBtnHandler sendBtnHandler;

    public ClientGUI(String username, SendBtnHandler sendBtnHandler, String me) {
        this.sendBtnHandler = sendBtnHandler;
        this.me = me;
        setTitle(this.me + "@ChenZhicheng's Instant Message");
        setResizable(false);
        setType(Type.UTILITY);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(100, 100, 500, 580);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textArea = new JTextArea();

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane qScroller = new JScrollPane(textArea);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        qScroller.setBounds(42, 122, 400, 356);
        contentPane.add(qScroller);

        messageToSend = new JTextField();
        messageToSend.setBounds(40, 493, 301, 30);
        messageToSend.addActionListener((e) -> {
            sendBtnHandler.sendMsg(usrID, messageToSend.getText());
            newMsg(messageToSend.getText(), 5);
            messageToSend.setText("");
        });
        contentPane.add(messageToSend);
        messageToSend.setColumns(10);

        sendButt = new JButton("SEND");
        sendButt.setBounds(353, 493, 87, 30);
        sendButt.addActionListener((e) -> {
                    sendBtnHandler.sendMsg(usrID, messageToSend.getText());
                    newMsg(messageToSend.getText(), 5);
                    messageToSend.setText("");
                }
        );
        contentPane.add(sendButt);


        JLabel title = new JLabel("Message");
        title.setFont(new Font("等线", Font.PLAIN, 35));
        title.setBounds(42, 24, 283, 56);
        contentPane.add(title);

        JLabel enterUsername = new JLabel(username);
        usrID = username;
        enterUsername.setFont(new Font("等线", Font.PLAIN, 20));
        enterUsername.setBounds(42, 97, 161, 22);
        contentPane.add(enterUsername);
    }


    @Override
    public String toString() {
        return usrID;
    }

    public void newMsg(String msg, int stat) {
        if (stat == 0) {
            textArea.append(usrID + "@" + TimeGetter.getTime() + ":\n" + msg + "\n");
        } else if (stat == 13) {
            textArea.append(msg + "\n");
        } else if (stat == 5) {
            textArea.append(this.me + "@" + TimeGetter.getTime() + ":\n" + msg + "\n");
        } else {
            textArea.append("UNKNOWN MESSAGE\n");
        }
        textArea.setCaretPosition(textArea.getText().length());
    }

    @Override
    public int hashCode() {
        return usrID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
}