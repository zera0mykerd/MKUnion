package mykerd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ICC implements CommandExecutor {
  Main plugin;
  
  public ICC(Main plugin) {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args) {
    if (cs.hasPermission("ChatChat.icc")) {
      for (int i = 0; i <= 120; i++)
        cs.sendMessage(""); 
      String cc = this.plugin.getConfig().getString("Config.ICC.message");
      cc.replace("&a", "§a");
      cc.replace("&b", "§b");
      cc.replace("&c", "§c");
      cc.replace("&d", "§d");
      cc.replace("&f", "§f");
      cc.replace("&1", "§1");
      cc.replace("&2", "§2");
      cc.replace("&3", "§3");
      cc.replace("&4", "§4");
      cc.replace("&5", "§5");
      cc.replace("&6", "§6");
      cc.replace("&7", "§7");
      cs.sendMessage(cc);
      return true;
    } 
    String noperm = this.plugin.getConfig().getString("Config.permissions.message");
    cs.sendMessage(noperm);
    return true;
  }
}
