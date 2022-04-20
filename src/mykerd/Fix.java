package mykerd;


import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fix implements CommandExecutor {
  private String perfix = "§9§lPowaFixer §3§l| §b§o";
  private String ptask = "§9§lPowaTask §3§l| §b§o";
  Main plugin;
  
  public Fix(Main plugin) {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    Player p = (Player)sender;
	    p.sendMessage(String.valueOf(this.ptask) + "Attendere...");
	    waitTime(2000);
	    if (p.hasPermission("fix.use")) {
	      if (command.getName().equalsIgnoreCase("Fix")) {
	        if (args.length == 0) {
	          p.teleport(new Location(p.getWorld(), p.getLocation().getX(), p
	                .getLocation().getY() + 2.0D, p.getLocation().getZ(), p
	                .getLocation().getYaw(), p.getLocation().getPitch()));
	          p.sendMessage(String.valueOf(this.perfix) + "Teletrasportato a 2 blocchi in alto.");
	          return false;
	        } 
	        Player t = Bukkit.getServer().getPlayer(args[0]);
	        if (t == null) {
	          sender.sendMessage("Giocatore non online");
	          return true;
	        } 
	        t.teleport(new Location(t.getWorld(), t.getLocation().getX(), t
	              .getLocation().getY() + 2.0D, t.getLocation().getZ(), t
	              .getLocation().getYaw(), t.getLocation().getPitch()));
	        t.sendMessage(String.valueOf(this.perfix ) + "Teletrasportato a 2 blocchi." + "§8|" + "§b§oSei stato fixato da: §6§l" + sender.getName());
	      } 
	    } else {
	      sender.sendMessage("§8§l(§0§l!§8§l) §1§l§o» §7§oAccesso negato al comando.");
	    } 
	    return true;
	  }
  
  private void waitTime(int MS) { 
      try
      {
          TimeUnit.MILLISECONDS.sleep(MS);
      }
      catch(InterruptedException ex)
      {
          Thread.currentThread().interrupt();
      }
  }
	}

  
//  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//	    Player p = (Player)sender;
//	    
//	    p.sendMessage("§9§lPowaTask §3§l| §b§o Attendere...");
//		  
//         Bukkit.getServer().getScheduler().scheduleSyncDelayedTask( this, new Runnable() {
//		  private String perfix = "§9§lPowaFixer §3§l| §b§o";
//
//		@Override
//		  public void run() {
//			    if (p.hasPermission("fix.use")) {
//				      if (command.getName().equalsIgnoreCase("Fix")) {
//				        if (args.length == 0) {
//				        	  p.teleport(new Location(p.getWorld(), p.getLocation().getX(),
//						              p.getLocation().getY() + 2.0D, p.getLocation().getZ(),
//						              p.getLocation().getYaw(), p.getLocation().getPitch()));
//				          p.sendMessage(String.valueOf(this.perfix) + "Teletrasportato a 2 blocchi sopra!");
//				          return;
//				        } 
//				        Player t = Bukkit.getServer().getPlayer(args[0]);
//				        if (t == null) {
//				          sender.sendMessage("Giocatore non online");
//				          return;
//				        } 
//				        t.teleport(new Location(t.getWorld(), t.getLocation().getX(),
//				              t.getLocation().getY() + 2.0D, t.getLocation().getZ(),
//				              t.getLocation().getYaw(), t.getLocation().getPitch()));
//				        t.sendMessage(String.valueOf(this.perfix ) + "Teletrasportato a 2 blocchi." + "§8|" + "§b§oSei stato fixato da: §6§l" + sender.getName());
//				      } 
//				    } else {
//				      sender.sendMessage("Accesso negato al comando!");
//				    } 
//				    return;
//			  }
//		  
//		  },40L);
//		return false;
//  }
//}
