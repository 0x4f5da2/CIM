package com.chenzhicheng.cim.protocol;

import java.io.Serializable;

public class Protocol implements Serializable {
    private String from;
    private String to;
    private String message;
    private String password;
    private int status;
/*
status：
-1   未知错误
0    发送消息
1    登陆
2    重复登陆
4    用户名或密码错误
5    自己的消息，用于显示在GUI上
8    登陆成功
13   系统消息
 */

    public Protocol(String from, String password) {
        this.from = from;
        this.password = password;
        this.status = 1;
    }

    public Protocol(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.status = 0;
    }

    public Protocol(String from, int status, String to, String message) {
        this.from = from;
        this.status = status;
        this.to = to;
        this.message = message;
    }

    public Protocol(int status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public String getPassword() {
        return password;
    }

    public int getStatus() {
        return status;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return from + "," + to + "," + password + "," + status + "," + message;
    }
}