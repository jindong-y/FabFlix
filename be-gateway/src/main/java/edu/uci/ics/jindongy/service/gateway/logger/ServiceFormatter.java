package edu.uci.ics.jindongy.service.gateway.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ServiceFormatter extends Formatter {
    private static final int MAX_BUF = 1000;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public String format(LogRecord record) {
        StringBuffer buf = new StringBuffer(MAX_BUF);
        switch (record.getLevel().getName()) {
            case "CONFIG":
                buf.append(ANSI_WHITE);
                break;
            case "SEVERE":
                buf.append(ANSI_RED);
                break;
            case "WARNING":
                buf.append(ANSI_YELLOW);
                break;
            default:
                buf.append(ANSI_GREEN);

        }
        buf.append(calculateDate(record.getMillis()));
        buf.append("[" + record.getLevel() + "]");
        buf.append("[" + /*record.getSourceClassName() + "." +*/ record.getSourceMethodName() + "]  ");
        buf.append(record.getMessage() + "\n");
        return buf.toString();
    }

    private String calculateDate(long ms) {
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        Date date = new Date(ms);
        return sdf.format(date);
    }
}
