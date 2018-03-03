package com.chenzhicheng.cim.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientLoginGUI extends JFrame {

    private JPanel contentPane;
    private JTextField username;
    private JPasswordField password;
    private JButton loginButt;
    private JButton groupChatBtn;

    /**
     * Create the frame.
     */
    public ClientLoginGUI() {
        setType(Type.UTILITY);
        setTitle("ChenZhicheng's Instant Message");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblChenzhichengsInstentMessage = new JLabel("ChenZhicheng's Instant Message");
        lblChenzhichengsInstentMessage.setFont(new Font("等线", Font.PLAIN, 25));
        lblChenzhichengsInstentMessage.setBounds(35, 10, 363, 74);
        contentPane.add(lblChenzhichengsInstentMessage);

        username = new JTextField();
        username.setBounds(95, 120, 250, 30);
        contentPane.add(username);
        username.setColumns(10);

        password = new JPasswordField();
        password.setColumns(10);
        password.setBounds(95, 187, 250, 30);
        password.setActionCommand("login");
        contentPane.add(password);

        JLabel lblUsername = new JLabel("username");
        lblUsername.setFont(new Font("等线", Font.PLAIN, 16));
        lblUsername.setBounds(95, 94, 74, 17);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("password");
        lblPassword.setFont(new Font("等线", Font.PLAIN, 16));
        lblPassword.setBounds(95, 160, 79, 15);
        contentPane.add(lblPassword);

        loginButt = new JButton("LOGIN");
        loginButt.setBounds(131, 242, 169, 30);
        loginButt.setActionCommand("login");
        contentPane.add(loginButt);

        JSeparator separator = new JSeparator();
        separator.setBounds(35, 292, 363, 17);
        contentPane.add(separator);

        groupChatBtn = new JButton("ANONYMOUS GROUP CHAT WITHOUT LOGGING IN");
        groupChatBtn.setBounds(35, 319, 363, 30);
        groupChatBtn.setActionCommand("anonymous");
        contentPane.add(groupChatBtn);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientLoginGUI frame = new ClientLoginGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JTextField getUsername() {
        return username;
    }

    public JTextField getPassword() {
        return password;
    }

    public JButton getLoginButt() {
        return loginButt;
    }

    public JButton getGroupChatBtn() {
        return groupChatBtn;
    }
}
