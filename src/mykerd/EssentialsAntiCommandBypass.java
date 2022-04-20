package mykerd;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EssentialsAntiCommandBypass extends JavaPlugin implements Listener {
  public void onEnable() {
    getConfig().options().copyDefaults(true);
    saveConfig();
    getServer().getPluginManager().registerEvents(this, (Plugin)this);
  }
  
  public void onDisable() {}
  
  @EventHandler
  public void commandPreprocess(PlayerCommandPreprocessEvent e) {
    if (e.getMessage().startsWith("/e"))
      if (getConfig().getBoolean("allow-ops")) {
        if (!e.getPlayer().isOp()) {
          e.getPlayer().sendMessage(ChatColor.RED + getConfig().getString("errormessage"));
          e.setCancelled(true);
        } 
      } else {
        e.getPlayer().sendMessage(ChatColor.RED + getConfig().getString("errormessage"));
        e.setCancelled(true);
      }  
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (label.equalsIgnoreCase("seacb")) {
      if (args.length == 0) {
        sender.sendMessage(ChatColor.GRAY + "EssentialsAntiCommandBypass mod creato da zera_mykerd");
        sender.sendMessage(ChatColor.GRAY + "Usa /seacb reload per ricaricare il config.");
        return true;
      } 
      if (args.length == 1 && 
        args[0].equalsIgnoreCase("reload")) {
        if (sender.isOp()) {
          reloadConfig();
          sender.sendMessage(ChatColor.GREEN + "Fatto.");
        } else {
          sender.sendMessage(ChatColor.RED + "Devi essere un operatore per eseguire questo comando.");
        } 
        return true;
      } 
      sender.sendMessage(ChatColor.RED + "Sintassi errata rilevata!");
    } 
    return false;
  }
}

