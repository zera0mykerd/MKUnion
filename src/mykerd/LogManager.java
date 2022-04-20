package mykerd;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class LogManager {
  private static final DateFormat LOG_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
  
  private final Logger logger;
  
  private final File logDirectory;
  
  private LogWriter writer;
  
  public LogManager(Logger logger, File logDirectory) {
    this.logger = logger;
    this.logDirectory = logDirectory;
  }
  
  public void writeLog(String message) {
    this.writer.getWriteQueue().add("[" + LOG_TIME_FORMAT.format(new Date()) + "] " + message);
  }
  
  public void start() {
    this.writer = new LogWriter(this.logger, this.logDirectory);
    this.writer.start();
  }
  
  public void stop() {
    this.writer.interrupt();
  }
}
