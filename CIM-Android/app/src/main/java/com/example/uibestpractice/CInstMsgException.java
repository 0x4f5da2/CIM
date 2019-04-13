package com.example.uibestpractice;

/**
 * Created by chen1 on 2018/2/28.
 */

public class CInstMsgException extends Exception {
    private String exceptionDetail = null;

    public CInstMsgException(String message, String exceptionDetail) {
        super(message);
        this.exceptionDetail = exceptionDetail;
    }

    public String getDetailedMsg() {
        return this.exceptionDetail;
    }
}
