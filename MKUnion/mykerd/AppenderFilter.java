package mykerd;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.filter.AbstractFilterable;

public class AppenderFilter {
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static void tryApply(Main plugin) {
    try {
      List<String> names = apply(plugin);
      plugin.getLogger().info("Successfully applied filters to Appenders " + String.join(", ", (Iterable)names));
    } catch (Throwable t) {
      plugin.getLogger().warning("Could not add filters to logger. Logger version is probably incompatible!");
    } 
  }
  
  private static List<String> apply(Main plugin) {
    Map<String, Appender> appenders = ((Logger)LogManager.getRootLogger()).getAppenders();
    LoggerFilter filter = new LoggerFilter(plugin);
    List<Appender> filterable = (List<Appender>)appenders.values().stream().filter(appender -> appender instanceof AbstractFilterable).collect(Collectors.toList());
    filterable.forEach(appender -> ((AbstractFilterable)appender).addFilter((Filter)filter));
    return (List<String>)filterable.stream()
      .map(Appender::getName)
      .collect(Collectors.toList());
  }
  
  private static class LoggerFilter extends AbstractFilter {
    @SuppressWarnings("unused")
	private final Main plugin;
    
    public LoggerFilter(Main plugin) {
      this.plugin = plugin;
    }
    
    public Filter.Result filter(LogEvent event) {
      String message = event.getMessage().getFormattedMessage();
      if (message.indexOf('$') != -1 && Main.pattern.matcher(message.toLowerCase()).find())
        try {
          return Filter.Result.DENY;
        } finally {
          @SuppressWarnings("unused")
		Exception exception = null;
        }  
      return super.filter(event);
    }
  }
}
