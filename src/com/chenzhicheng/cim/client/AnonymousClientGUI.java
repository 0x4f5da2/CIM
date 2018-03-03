package com.chenzhicheng.cim.client;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AnonymousClientGUI extends JFrame {

    private JPanel contentPane;
    private JTextField messageToSend;
    private JButton sendButt;
    private JTextArea textArea;
    private JTextField nickName;
    private SendBtnHandler sendBtnHandler;

    /**
     * Create the frame.
     */
    public AnonymousClientGUI(SendBtnHandler s) {
        this.sendBtnHandler = s;
        setTitle("ChenZhicheng's Instant Message");
        setResizable(false);
        setType(Type.UTILITY);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
        qScroller.setBounds(40, 90, 400, 356);
        contentPane.add(qScroller);

        messageToSend = new JTextField();
        messageToSend.setBounds(40, 493, 301, 30);
        messageToSend.addActionListener((e) -> {
            sendBtnHandler.sendMsg(this.nickName.getText(), this.messageToSend.getText());
            this.messageToSend.setText("");
        });
        contentPane.add(messageToSend);
        messageToSend.setColumns(10);

        sendButt = new JButton("SEND");
        sendButt.setBounds(353, 493, 87, 30);
        sendButt.addActionListener((e) -> {
            sendBtnHandler.sendMsg(this.nickName.getText(), this.messageToSend.getText());
            this.messageToSend.setText("");
        });

        contentPane.add(sendButt);
        JLabel title = new JLabel("Anynomous Group Chat");
        title.setFont(new Font("等线", Font.PLAIN, 35));
        title.setBounds(42, 24, 400, 56);
        contentPane.add(title);

        nickName = new JTextField();
        nickName.setColumns(10);
        nickName.setBounds(131, 456, 309, 30);
        contentPane.add(nickName);

        JLabel lblNewLabel = new JLabel("Send as:");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
        lblNewLabel.setBounds(40, 456, 71, 27);
        contentPane.add(lblNewLabel);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AnonymousClientGUI frame = new AnonymousClientGUI(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void newMsg(String s) {
        this.textArea.append(s + "\n");
        this.textArea.setCaretPosition(this.textArea.getText().length());
    }
}

