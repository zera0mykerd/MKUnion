package mykerd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWriter extends Thread {
  @SuppressWarnings("unused")
private static final int MAX_SIZE = 26214400;
  
@SuppressWarnings("unused")
private static final String LATEST_NAME = "latest";
  
  private static final DateFormat NAME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  
  private final Logger logger;
  
  private final File logDirectory;
  
  private final LinkedBlockingQueue<String> writeQueue = new LinkedBlockingQueue<>();
  
  private final Date lastWrite = new Date();
  
  private BufferedWriter writer;
  
  public LogWriter(Logger logger, File logDirectory) {
    this.logger = logger;
    this.logDirectory = logDirectory;
    rotate(this.lastWrite);
    setDaemon(true);
    setName("log4j-exploit-log-writer");
    setUncaughtExceptionHandler((t, ex) -> this.logger.log(Level.SEVERE, "Caught uncaught exception in thread " + t.getName(), ex));
    if (!logDirectory.exists() && !logDirectory.mkdirs())
      throw new IllegalStateException("Cannot create directories for path " + logDirectory.getAbsolutePath()); 
  }
  
  public void run() {
    long lastRotateCheck = System.currentTimeMillis();
    File currentFile = getLatestLog();
    try {
      while (!isInterrupted()) {
        String line;
        try {
          line = this.writeQueue.take();
        } catch (InterruptedException e) {
          break;
        } 
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRotateCheck > 1000L) {
          lastRotateCheck = currentTime;
          if (isRotateRequired(currentFile)) {
            closeWriter();
            currentFile = rotate(this.lastWrite);
            this.writer = null;
          } 
        } 
        if (this.writer == null)
          try {
            this.writer = new BufferedWriter(new FileWriter(currentFile, true));
          } catch (IOException ex) {
            this.logger.log(Level.SEVERE, "Error while instantiating new log writer", ex);
            break;
          }  
        try {
          this.lastWrite.setTime(currentTime);
          this.writer.write(line + System.lineSeparator());
          this.writer.flush();
        } catch (IOException ex) {
          this.logger.log(Level.SEVERE, "Error while writing line to log file", ex);
        } 
      } 
    } finally {
      closeWriter();
    } 
  }
  
  public LinkedBlockingQueue<String> getWriteQueue() {
    return this.writeQueue;
  }
  
  private void closeWriter() {
    if (this.writer == null)
      return; 
    try {
      this.writer.flush();
      this.writer.close();
    } catch (IOException ex) {
      this.logger.log(Level.SEVERE, "Error while closing log writer!", ex);
    } 
  }
  
  private boolean isRotateRequired(File file) {
    if (!file.exists() || !file.isFile())
      return false; 
    Instant lastWrite = this.lastWrite.toInstant().truncatedTo(ChronoUnit.DAYS);
    Instant current = (new Date()).toInstant().truncatedTo(ChronoUnit.DAYS);
    if (!lastWrite.equals(current))
      return true; 
    try {
      if (Files.size(file.toPath()) >= 26214400L)
        return true; 
    } catch (IOException ex) {
      this.logger.log(Level.SEVERE, "Error while checking file size of file " + file.getAbsolutePath(), ex);
      return false;
    } 
    return false;
  }
  
  private File rotate(Date date) {
    File latestLog = getLatestLog();
    if (!latestLog.exists())
      return latestLog; 
    if (!latestLog.isFile())
      throw new IllegalStateException(latestLog.getAbsolutePath() + " is not a file!"); 
    String baseName = NAME_FORMAT.format(date);
    String newName = baseName;
    int iteration = 1;
    File newFile;
    while ((newFile = new File(this.logDirectory, newName + ".log")).exists())
      newName = baseName + "-" + iteration++; 
    if (!latestLog.renameTo(newFile))
      this.logger.severe(latestLog.getAbsolutePath() + " could not be renamed to " + newFile.getAbsolutePath()); 
    return newFile;
  }
  
  private File getLatestLog() {
    return new File(this.logDirectory, "latest.log");
  }
}
