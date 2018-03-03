package com.chenzhicheng.cim.server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ServerGUI extends JFrame {

    private JPanel contentPane;
    private JButton clearChatBtn;
    private JTextArea groupChatArea;
    private JLabel onlineNum;
    private JButton clearLogBtn;
    private JButton showOnlineBtn;
    private JTextArea logArea;
    private ServerOnlineStatusHandler serverOnlineStatusHandler;

    /**
     * Create the frame.
     */
    public ServerGUI(ServerOnlineStatusHandler s) {
        serverOnlineStatusHandler = s;
        setResizable(false);
        setTitle("ChenZhicheng's Instant Message Server");
        setType(Type.UTILITY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 830, 563);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDashbord = new JLabel("Dashboard");
        lblDashbord.setFont(new Font("等线", Font.PLAIN, 35));
        lblDashbord.setBounds(46, 10, 206, 59);
        contentPane.add(lblDashbord);

        JLabel lblLog = new JLabel("Server Log");
        lblLog.setBounds(46, 117, 112, 15);
        contentPane.add(lblLog);

        logArea = new JTextArea();
        logArea.setWrapStyleWord(true);
        logArea.setLineWrap(true);
        logArea.setEditable(false);
        JScrollPane lScroller = new JScrollPane(logArea);
        lScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        lScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lScroller.setBounds(46, 142, 350, 333);
        contentPane.add(lScroller);


        showOnlineBtn = new JButton("Show Online User");
        showOnlineBtn.setBounds(46, 485, 169, 23);
        showOnlineBtn.addActionListener((e -> serverOnlineStatusHandler.showStatus()));
        contentPane.add(showOnlineBtn);

        clearLogBtn = new JButton("Clear Log");
        clearLogBtn.setBounds(225, 485, 171, 23);
        clearLogBtn.addActionListener((e) -> logArea.setText(""));
        contentPane.add(clearLogBtn);

        JLabel onlineUser = new JLabel("Online User:");
        onlineUser.setFont(new Font("等线", Font.PLAIN, 25));
        onlineUser.setBounds(46, 61, 140, 40);
        contentPane.add(onlineUser);

        onlineNum = new JLabel("0");
        onlineNum.setFont(new Font("等线", Font.PLAIN, 25));
        onlineNum.setBounds(209, 61, 93, 40);
        contentPane.add(onlineNum);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(46, 111, 350, 2);
        contentPane.add(separator_1);

        groupChatArea = new JTextArea();
        groupChatArea.setWrapStyleWord(true);
        groupChatArea.setLineWrap(true);
        groupChatArea.setEditable(false);
        JScrollPane gScroller = new JScrollPane(groupChatArea);
        gScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        gScroller.setBounds(450, 61, 350, 414);
        contentPane.add(gScroller);

        JLabel lblAnonymousGroupChat = new JLabel("Content of Anonymous Group Chat");
        lblAnonymousGroupChat.setBounds(450, 37, 234, 15);
        contentPane.add(lblAnonymousGroupChat);

        JSeparator separator_2 = new JSeparator();
        separator_2.setOrientation(SwingConstants.VERTICAL);
        separator_2.setBounds(423, 61, 16, 429);
        contentPane.add(separator_2);

        clearChatBtn = new JButton("Clear Chat Record");
        clearChatBtn.setBounds(450, 485, 350, 23);
        clearChatBtn.addActionListener((e) -> groupChatArea.setText(""));
        contentPane.add(clearChatBtn);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ServerGUI frame = new ServerGUI(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void appendGroupChatArea(String s) {
        this.groupChatArea.append(s);
        this.groupChatArea.setCaretPosition(this.groupChatArea.getText().length());
    }

    public void appendServerLog(String s) {
        this.logArea.append(s);
        this.logArea.setCaretPosition(this.logArea.getText().length());
    }

    synchronized public void newOnline() {
        onlineNum.setText(Integer.toString(Integer.parseInt(onlineNum.getText()) + 1));
    }

    synchronized public void newOffile() {
        onlineNum.setText(Integer.toString(Integer.parseInt(onlineNum.getText()) - 1));
    }
}

