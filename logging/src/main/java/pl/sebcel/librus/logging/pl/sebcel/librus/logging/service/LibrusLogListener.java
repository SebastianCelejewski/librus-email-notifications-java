package pl.sebcel.librus.logging.pl.sebcel.librus.logging.service;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LibrusLogListener implements LogListener {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void logged(LogEntry logEntry) {
        String line = "";
        line += "["+df.format(logEntry.getTime())+"]";
        line += "["+logEntry.getBundle().getSymbolicName()+"]";
        line += "["+logEntry.getClass().getSimpleName()+"]";
        line += "["+logEntry.getLevel()+"]";
        line += " "+logEntry.getMessage();
        System.out.println(line);
    }
}
