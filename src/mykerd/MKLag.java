package mykerd;

import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MKLag implements CommandExecutor {
	Main plugin;
	
	  public MKLag(Main plugin) {
		    this.plugin = plugin;
		  }
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (label.equalsIgnoreCase("mklag"))
	      if (sender instanceof Player) {
	        Player player = (Player)sender;
	        player.sendMessage("§r");
	        player.sendMessage("§r §r §r §r §r §r §r §r §r §r §r §r §r §r §r §r §r §r §1§l⚡ §d§l§kII §r §4§l§nPowa§c§l§nAverage§r §5§l§kIII§r §1§l⚡");
	        player.sendMessage("§r");
	        player.sendMessage("§6§lNome del server: §e§o" + Bukkit.getServerName());
	        player.sendMessage("§6§lOrario del server: §e§o" + (new Date()).toString());
	        player.sendMessage("§6§lLatenza del server (Ping): §e§o" + (((CraftPlayer)player).getHandle()).ping + " MS");
	        player.sendMessage("§6§lTPS attuali del server: §e§o" + getServerLagAverage());
	        player.sendMessage("§6§lVersione bukkit: §e§o" + Bukkit.getBukkitVersion());
	        player.sendMessage("§6§lConnessioni respinte: §e§o" + Bukkit.getConnectionThrottle());
	        player.sendMessage("§6§lIndirizzo IP: §e§o" + Bukkit.getIp());
	        player.sendMessage("§6§lPorta server: §e§o" + Bukkit.getPort());
	        player.sendMessage("§6§lServer timeouts: §e§o" + Bukkit.getIdleTimeout());
	        player.sendMessage("§6§lVersione server: §e§o" + Bukkit.getVersion());
	        player.sendMessage("§6§lDistanza di render: §e§o" + Bukkit.getViewDistance());
	        player.sendMessage("§6§lTipo di mondo: §e§o" + Bukkit.getWorldType());
	        player.sendMessage("§6§lLimite spawn ambiente: §e§o" + Bukkit.getAmbientSpawnLimit());
	        player.sendMessage("§6§lLimite spawn animali: §e§o" + Bukkit.getAnimalSpawnLimit());
	        player.sendMessage("§6§lLimite spawn mostri: §e§o" + Bukkit.getMonsterSpawnLimit());
	        player.sendMessage("§6§lRaggio spawning: §e§o" + Bukkit.getSpawnRadius());
	        player.sendMessage("§6§lTicks spawn animali: §e§o" + Bukkit.getTicksPerAnimalSpawns());
	        player.sendMessage("§6§lTicks spawn mostri: §e§o" + Bukkit.getTicksPerMonsterSpawns());
	        player.sendMessage("§6§lLimite spawn in acque: §e§o" + Bukkit.getWaterAnimalSpawnLimit());
	        player.sendMessage("§r");
	        player.sendMessage("§e§lPing giocatore: §6§o" + ((CraftPlayer) player).getHandle().ping + " §nMS");
	        player.sendMessage("§e§lIP giocatore: §6§o" + ((CraftPlayer) player).getAddress());
	        player.sendMessage("§e§lTempo giocatore: §6§o" + ((CraftPlayer) player).getHandle().getPlayerTime());
	        //player.sendMessage("§6§l: §e§o" + Bukkit.());
	      } else {
	        //sender.sendMessage("Comando eseguito con successo!");
	      }  
	    return false;
	  }
	  
	  public static double getServerLagAverage() {
	    double[] recentTps = (((CraftServer)Bukkit.getServer()).getHandle().getServer()).recentTps;
	    double result = 0.0D;
	    byte b;
	    int i;
	    double[] arrayOfDouble1;
	    for (i = (arrayOfDouble1 = recentTps).length, b = 0; b < i; ) {
	      double d = arrayOfDouble1[b];
	      result += d;
	      b++;
	    } 
	    return result / recentTps.length;
	  }
	}


