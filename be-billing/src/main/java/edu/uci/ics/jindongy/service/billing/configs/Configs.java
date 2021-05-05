package edu.uci.ics.jindongy.service.billing.configs;

public class Configs {

    private String scheme;
    private String hostName;
    private int port;
    private String path;

    public Configs() {
    }

    public Configs(String scheme, String hostName, int port, String path) {
        this.scheme = scheme;
        this.hostName = hostName;
        this.port = port;
        this.path = path;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }
}

