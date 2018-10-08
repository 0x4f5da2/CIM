package com.chenzhicheng.cim.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientStartMsgGUI extends JFrame {

    private JPanel contentPane;
    private JTextField usernameToStart;
    private JButton messageStartBtn;
    private JList<ClientGUI> recentContactsList;

    /**
     * Create the frame.
     */
    public ClientStartMsgGUI() {
        setTitle("ChenZhicheng's Instant Message");
        setResizable(false);
        setType(Type.UTILITY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 544);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Start a message");
        lblNewLabel.setFont(new Font("等线", Font.PLAIN, 35));
        lblNewLabel.setBounds(33, 29, 350, 38);
        contentPane.add(lblNewLabel);

        usernameToStart = new JTextField();
        usernameToStart.setBounds(33, 120, 270, 38);
        usernameToStart.setActionCommand("startMsg");
        contentPane.add(usernameToStart);
        usernameToStart.setColumns(10);

        messageStartBtn = new JButton("START");
        messageStartBtn.setBounds(313, 120, 93, 37);
        messageStartBtn.setActionCommand("startMsg");
        contentPane.add(messageStartBtn);

        JLabel lblEnterUsername = new JLabel("Enter username:");
        lblEnterUsername.setBounds(33, 94, 129, 15);
        contentPane.add(lblEnterUsername);

        JLabel lblRecentContacts = new JLabel("Recent contacts:");
        lblRecentContacts.setFont(new Font("等线", Font.PLAIN, 18));
        lblRecentContacts.setBounds(33, 188, 248, 38);
        contentPane.add(lblRecentContacts);

        recentContactsList = new JList<ClientGUI>();
        JScrollPane recentContactsScroller = new JScrollPane(recentContactsList);
        recentContactsScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        recentContactsScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        recentContactsScroller.setBounds(33, 236, 373, 244);
        contentPane.add(recentContactsScroller);

        JSeparator separator = new JSeparator();
        separator.setBounds(33, 176, 373, 15);
        contentPane.add(separator);
    }


    public JButton getMessageStartBtn() {
        return messageStartBtn;
    }

    public JTextField getUsernameToStart() {
        return usernameToStart;
    }

    public void setUsernameToStart(JTextField usernameToStart) {
        this.usernameToStart = usernameToStart;
    }

    public JList<ClientGUI> getRecentContactsList() {
        return recentContactsList;
    }
}

