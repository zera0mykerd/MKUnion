package mykerd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleSpawn implements CommandExecutor {
	
	Main plugin;
	
	  public SimpleSpawn(Main plugin) {
		    this.plugin = plugin;
		  }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (sender instanceof Player) {
	      Player player = (Player)sender;
	      if (cmd.getName().equalsIgnoreCase("setspawn")) {
	        Player player1 = (Player)sender;
	        if (player1.hasPermission("simplespawn.setspawn")) {
	          player1.sendMessage(ChatColor.LIGHT_PURPLE + "Hai impostato con successo lo spawn!");
	          Location loc = player.getLocation();
	          int x = loc.getBlockX();
	          int y = loc.getBlockY();
	          int z = loc.getBlockZ();
	          player.getWorld().setSpawnLocation(x, y, z);
	          return true;
	        } 
	      } 
	    } 
	    Player p = (Player)sender;
	    if (cmd.getName().equalsIgnoreCase("spawn") && 
	      p.hasPermission("simplespawn.spawn")) {
	      if (sender instanceof Player);
	      World world = Bukkit.getWorld("World");
	      p.sendMessage(ChatColor.BLUE + "Sei stato teletrasportato allo spawn!");
	      p.teleport(world.getSpawnLocation());
	    } 
	    return true;
	  }
	}

