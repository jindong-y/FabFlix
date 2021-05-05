package edu.uci.ics.jindongy.service.idm.configs;

import edu.uci.ics.jindongy.service.idm.logger.ServiceLogger;

public class ServiceConfigs {

    // TODO COMPLETE THIS CLASS

    public static final int MIN_SERVICE_PORT = 1024;
    public static final int MAX_SERVICE_PORT = 65535;

    // Default gateway configs
    private final String DEFAULT_SCHEME = "http://";
    private final String DEFAULT_HOSTNAME = "0.0.0.0";
    private final int DEFAULT_PORT = 6243;
    private final String DEFAULT_PATH = "/api/idm";
    // Default logger configs
    private final String DEFAULT_OUTPUTDIR = "./logs/";
    private final String DEFAULT_OUTPUTFILE = "test.log";

    // Service configs
    private String scheme;
    private String hostName;
    private int port;
    private String path;

    // Logger configs
    private String outputDir;
    private String outputFile;

    // Database configs
    private String dbUsername;
    private String dbPassword;
    private String dbHostname;
    private int dbPort;
    private String dbName;
    private String dbDriver;
    private String dbSettings;

    // If any DB configs are invalid, set this to false
    private boolean dbConfigValid = true;

    // Session configs
    private long timeout;
    private long expiration;

    public ServiceConfigs() {
    }

    public ServiceConfigs(ConfigsModel cm) throws NullPointerException {
        if (cm == null) {
            ServiceLogger.LOGGER.severe("ConfigsModel not found.");
            throw new NullPointerException("ConfigsModel not found.");
        } else {
            // Set service configs
            scheme = cm.getServiceConfig().get("scheme");
            if (scheme == null) {
                scheme = DEFAULT_SCHEME;
                System.err.println("Scheme not found in configuration file. Using default.");
            } else {
                System.err.println("Scheme: " + scheme);
            }

            hostName = cm.getServiceConfig().get("hostName");
            if (hostName == null) {
                hostName = DEFAULT_HOSTNAME;
                System.err.println("Hostname not found in configuration file. Using default.");
            } else {
                System.err.println("Hostname: " + hostName);
            }

            port = Integer.parseInt(cm.getServiceConfig().get("port"));
            if (port == 0) {
                port = DEFAULT_PORT;
                System.err.println("Port not found in configuration file. Using default.");
            } else if (port < MIN_SERVICE_PORT || port > MAX_SERVICE_PORT) {
                port = DEFAULT_PORT;
                System.err.println("Port is not within valid range. Using default.");
            } else {
                System.err.println("Port: " + port);
            }

            path = cm.getServiceConfig().get("path");
            if (path == null) {
                path = DEFAULT_PATH;
                System.err.println("Path not found in configuration file. Using default.");
            } else {
                System.err.println("Path: " + path);
            }

            // Set logger configs
            outputDir = cm.getLoggerConfig().get("outputDir");
            if (outputDir == null) {
                outputDir = DEFAULT_OUTPUTDIR;
                System.err.println("Logging output directory not found in configuration file. Using default.");
            } else {
                System.err.println("Logging output directory: " + outputDir);
            }

            outputFile = cm.getLoggerConfig().get("outputFile");
            if (outputFile == null) {
                outputFile = DEFAULT_OUTPUTFILE;
                System.err.println("Logging output file not found in configuration file. Using default.");
            } else {
                System.err.println("Logging output file: " + outputFile);
            }

            // Set DB Configs
            // TODO
            dbUsername = cm.getDatabaseConfig().get("dbUsername");
            if(dbUsername == null){
                System.err.println("dbUsername not found");
            }else{
                System.err.println("dbUsername: " + dbUsername);
            }

            dbPassword = cm.getDatabaseConfig().get("dbPassword");
            if(dbPassword == null){
                System.err.println("dbPassword not found");
            }else{
                System.err.println("dbPassword: " + dbPassword);
            }

            dbHostname = cm.getDatabaseConfig().get("dbHostname");
            if(dbHostname == null){
                System.err.println("dbHostname not found");
            }else{
                System.err.println("dbHostname: " + dbHostname);
            }

            if(cm.getDatabaseConfig().get("dbPort")==null) dbPort =0;
            else dbPort = Integer.parseInt(cm.getDatabaseConfig().get("dbPort"));
            if(dbPort == 0){
                System.err.println("dbPort not found");
            }else{
                System.err.println("dbPort: " + dbPort);
            }

            dbDriver = cm.getDatabaseConfig().get("dbDriver");
            if(dbDriver == null){
                System.err.println("dbDriver not found");
            }else{
                System.err.println("dbDriver: " + dbDriver);
            }

            dbName = cm.getDatabaseConfig().get("dbName");
            if(dbDriver == null){
                System.err.println("dbName not found");
            }else{
                System.err.println("dbName: " + dbName);
            }

            dbSettings = cm.getDatabaseConfig().get("dbSettings");
            if(dbSettings == null){
                System.err.println("dbSettings not found");
            }else{
                System.err.println("dbSettings: " + dbSettings);
            }


            // Set session configs
            // TODO
            if(cm.getSessionConfig().get("timeout")==null) timeout =0;
            else timeout = Long.parseLong(cm.getSessionConfig().get("timeout"));
            if(timeout == 0){
                System.err.println("timeout not found");
            }else{
                System.err.println("timeout: " + timeout);
            }

            if(cm.getSessionConfig().get("expiration")==null) expiration =0;
            else expiration = Long.parseLong(cm.getSessionConfig().get("expiration"));
            if(expiration == 0){
                System.err.println("expiration not found");
            }else{
                System.err.println("expiration: " + expiration);
            }
        }
    }

    public void currentConfigs() {
        ServiceLogger.LOGGER.config("Scheme: " + scheme);
        ServiceLogger.LOGGER.config("Hostname: " + hostName);
        ServiceLogger.LOGGER.config("Port: " + port);
        ServiceLogger.LOGGER.config("Path: " + path);
        ServiceLogger.LOGGER.config("Logger output directory: " + outputDir);

        // Log the current DB configs
        // TODO
        ServiceLogger.LOGGER.config("dbUsername: " + dbUsername);
        ServiceLogger.LOGGER.config("dbPassword: " + dbPassword);
        ServiceLogger.LOGGER.config("dbHostname: " + dbHostname);
        ServiceLogger.LOGGER.config("dbPort: " + dbPort);
        ServiceLogger.LOGGER.config("dbDriver: " + dbDriver);
        ServiceLogger.LOGGER.config("dbName: " + dbName);
        ServiceLogger.LOGGER.config("dbSettings: " + dbSettings);
        // Log the current session configs
        // TODO
        ServiceLogger.LOGGER.config("timeout: " + timeout);
        ServiceLogger.LOGGER.config("expiration: " + expiration );

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

    public String getOutputDir() {
        return outputDir;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getDbUrl() {
        return "jdbc:mysql://" + dbHostname + ":" + dbPort + "/" + dbName + dbSettings;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbHostname() {
        return dbHostname;
    }

    public int getDbPort() {
        return dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbSettings() {
        return dbSettings;
    }

    public boolean isDbConfigValid() {
        return dbConfigValid;
    }

    public long getTimeout() {
        return timeout;
    }

    public long getExpiration() {
        return expiration;
    }
}