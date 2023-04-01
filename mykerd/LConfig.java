package mykerd;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class LConfig {
  private static final LogConfig logging = new LogConfig();
  
  public static LogConfig getLogging() {
    return logging;
  }
  
  public static void load(Main plugin) {
    plugin.saveDefaultConfig();
    FileConfiguration config = plugin.getConfig();
    logging.load(config.getConfigurationSection("logging"));
  }
  
  public static class LogConfig {
    private boolean enabled;
    
    private boolean logFilter;
    
    private boolean messageReceive;
    
    private boolean messageSend;
    
    private String customPath;
    
    private LogConfig() {}
    
    public boolean isEnabled() {
      return this.enabled;
    }
    
    public String getCustomPath() {
      return this.customPath;
    }
    
    public boolean isLogFilter() {
      return this.logFilter;
    }
    
    public boolean isMessageReceive() {
      return this.messageReceive;
    }
    
    public boolean isMessageSend() {
      return this.messageSend;
    }
    
    private void load(ConfigurationSection config) {
      if (config == null)
        return; 
      this.enabled = config.getBoolean("enabled");
      this.logFilter = config.getBoolean("logFilter");
      this.messageReceive = config.getBoolean("messageReceive");
      this.messageSend = config.getBoolean("messageSend");
      this.customPath = config.getString("path");
    }
  }
}
