package mykerd;

import io.netty.util.AttributeKey;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;
import org.bukkit.plugin.java.JavaPlugin;

public class Fixer extends JavaPlugin {
  public void onDisable() {
    try {
      @SuppressWarnings("rawtypes")
	Class<AttributeKey> attributeKeyClass = AttributeKey.class;
      Field namesField = attributeKeyClass.getDeclaredField("names");
      namesField.setAccessible(true);
      @SuppressWarnings("unchecked")
	ConcurrentMap<String, Boolean> names = (ConcurrentMap<String, Boolean>)namesField.get((Object)null);
      for (String name : names.keySet()) {
        if (name.toLowerCase().startsWith("protocol"))
          names.remove(name); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
