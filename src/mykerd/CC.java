package mykerd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CC implements CommandExecutor {
  Main plugin;
  
  public CC(Main plugin) {
    this.plugin = plugin;
  }
	
	  public boolean onCommand(CommandSender cs, Command cmd, String str, String[] args) {
		    if (!cs.hasPermission("ChatClear.cc")) {
		      String noperm = this.plugin.getConfig().getString("Config.permissions.message");
		      cs.sendMessage(noperm);
		      return true;
		    } 
		    for (int i = 0; i <= 120; i++)
		      Bukkit.broadcastMessage(""); 
		    str.replace("&a", "§a");
		    str.replace("&b", "§b");
		    str.replace("&c", "§c");
		    str.replace("&d", "§d");
		    str.replace("&f", "§f");
		    str.replace("&1", "§1");
		    str.replace("&2", "§2");
		    str.replace("&3", "§3");
		    str.replace("&4", "§4");
		    str.replace("&5", "§5");
		    str.replace("&6", "§6");
		    str.replace("&7", "§7");
		    String cc = this.plugin.getConfig().getString("Config.CC.message");
		    cc.replace("&name", cs.getName());
		    Bukkit.broadcastMessage(cc);
		    return true;
		  }

}