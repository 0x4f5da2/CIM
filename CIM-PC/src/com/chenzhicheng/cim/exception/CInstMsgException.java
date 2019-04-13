package com.chenzhicheng.cim.exception;

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
