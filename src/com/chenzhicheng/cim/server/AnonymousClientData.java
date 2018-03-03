package com.chenzhicheng.cim.server;

import java.net.InetAddress;

class AnonymousClientData {
    private InetAddress ipAddr;
    private int port;
    private long timestamp;

    public AnonymousClientData(InetAddress ipAddr, int port, long timestamp) {
        this.ipAddr = ipAddr;
        this.port = port;
        this.timestamp = timestamp;
    }

    public InetAddress getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(InetAddress ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnonymousClientData that = (AnonymousClientData) o;

        if (System.currentTimeMillis() - timestamp > 600000)
            return false;

        if (port != that.port) return false;
        return ipAddr.equals(that.ipAddr);
    }

    @Override
    public int hashCode() {
        int result = ipAddr.hashCode();
        result = 31 * result + port;
        return result;
    }
}
