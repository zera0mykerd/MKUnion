package mykerd;

import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stats implements CommandExecutor {
	  Main plugin;
	  
	  public Stats(Main plugin) {
		    this.plugin = plugin;
		  }
	  
	  
	  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		    Player p = (Player)sender;
		    if (label.equalsIgnoreCase("stats")) {
		      p.sendMessage("§r");
		      p.sendMessage("§0§l§k||| §b§l§m--- §b§l§o> §3§l§nPowa§9§l§nStats §r §b§l§o<§b§l§m---  §0§l§k|||");
		      p.sendMessage("§r");
		      p.sendMessage("§a§l[*] §2§lUccisioni: §e§o" + p.getStatistic(Statistic.PLAYER_KILLS));
		      p.sendMessage("§a§l[*] §2§lMorti: §e§o" + p.getStatistic(Statistic.DEATHS));
		      p.sendMessage("§a§l[*] §2§lMobKill: §e§o" + p.getStatistic(Statistic.MOB_KILLS));
		      p.sendMessage("§a§l[*] §2§lSaltini: §e§o" + p.getStatistic(Statistic.JUMP));
		      p.sendMessage("§a§l[*] §2§lEnchants: §e§o" + p.getStatistic(Statistic.ITEM_ENCHANTED));
		      p.sendMessage("§a§l[*] §2§lAnimali divorati: §e§o" + p.getStatistic(Statistic.ANIMALS_BRED));
		      p.sendMessage("§a§l[*] §2§lCasse aperte: §e§o" + p.getStatistic(Statistic.CHEST_OPENED));
		      p.sendMessage("§a§l[*] §2§lInterazioni faro: §e§o" + p.getStatistic(Statistic.BEACON_INTERACTION));
		      p.sendMessage("§a§l[*] §2§lCalderoni usati: §e§o" + p.getStatistic(Statistic.CAULDRON_USED));
		      p.sendMessage("§a§l[*] §2§lCalderoni riempiti: §e§o" + p.getStatistic(Statistic.CAULDRON_FILLED));
		      p.sendMessage("§a§l[*] §2§lArmature pulite: §e§o" + p.getStatistic(Statistic.ARMOR_CLEANED));
		      //p.sendMessage("§a§l[*] §2§lOggetti costruiti: §e§o" + p.getStatistic(Statistic.CRAFT_ITEM));
		      p.sendMessage("§a§l[*] §2§lTorte gustate: §e§o" + p.getStatistic(Statistic.CAKE_SLICES_EATEN));
		      p.sendMessage("§a§l[*] §2§lInterazioni alchemiche: §e§o" + p.getStatistic(Statistic.BREWINGSTAND_INTERACTION));
		      p.sendMessage("§a§l[*] §2§lInterazioni workbench: §e§o" + p.getStatistic(Statistic.CRAFTING_TABLE_INTERACTION));
		      p.sendMessage("§a§l[*] §2§lOggetti abbandonati: §e§o" + p.getStatistic(Statistic.DROP));
		      p.sendMessage("§a§l[*] §2§lDropper ispezionati: §e§o" + p.getStatistic(Statistic.DROPPER_INSPECTED));
		      p.sendMessage("§a§l[*] §2§lEnderchest aperte: §e§o" + p.getStatistic(Statistic.ENDERCHEST_OPENED));
		      //p.sendMessage("§a§l[*] §2§lEntit\u00E0 uccise: §e§o" + p.getStatistic(Statistic.ENTITY_KILLED_BY));
		      p.sendMessage("§a§l[*] §2§lPesciolini pescati: §e§o" + p.getStatistic(Statistic.FISH_CAUGHT));
		      p.sendMessage("§a§l[*] §2§lBanner puliti: §e§o" + p.getStatistic(Statistic.BANNER_CLEANED));
		      //p.sendMessage("§a§l[*] §2§lOggetti rotti: §e§o" + p.getStatistic(Statistic.BREAK_ITEM));
		      p.sendMessage("§a§l[*] §2§lBarche usate: §e§o" + p.getStatistic(Statistic.BOAT_ONE_CM));
		      p.sendMessage("§a§l[*] §2§lColpi inflitti: §e§o" + p.getStatistic(Statistic.DAMAGE_DEALT));
		      p.sendMessage("§a§l[*] §2§lColpi incassati: §e§o" + p.getStatistic(Statistic.DAMAGE_TAKEN));
		      p.sendMessage("§a§l[*] §2§lInterazioni fornace: §e§o" + p.getStatistic(Statistic.FURNACE_INTERACTION));
		      p.sendMessage("§a§l[*] §2§lHopper ispezionati: §e§o" + p.getStatistic(Statistic.HOPPER_INSPECTED));
		      p.sendMessage("§a§l[*] §2§lCavalli ingroppati: §e§o" + p.getStatistic(Statistic.HORSE_ONE_CM));
		      p.sendMessage("§a§l[*] §2§lArrampicate favolose: §e§o" + p.getStatistic(Statistic.CLIMB_ONE_CM));
		      p.sendMessage("§a§l[*] §2§lRagequits: §e§o" + p.getStatistic(Statistic.LEAVE_GAME));
		      p.sendMessage("§a§l[*] §2§lFiorellini potati: §e§o" + p.getStatistic(Statistic.FLOWER_POTTED));
		      //p.sendMessage("§a§l[*] §2§lBlocchi minati: §e§o" + p.getStatistic(Statistic.MINE_BLOCK));
		      p.sendMessage("§a§l[*] §2§lMinecart pilotati: §e§o" + p.getStatistic(Statistic.MINECART_ONE_CM));
		      p.sendMessage("§a§l[*] §2§lNoteblock suonati: §e§o" + p.getStatistic(Statistic.NOTEBLOCK_PLAYED));
		      p.sendMessage("§a§l[*] §2§lNoteblock armonizzati: §e§o" + p.getStatistic(Statistic.NOTEBLOCK_TUNED));
		      //p.sendMessage("§a§l[*] §2§lOggetti usati: §e§o" + p.getStatistic(Statistic.USE_ITEM));
		      p.sendMessage("§a§l[*] §2§lDischi girati: §e§o" + p.getStatistic(Statistic.RECORD_PLAYED));
		      p.sendMessage("§a§l[*] §2§lSprints: §e§o" + p.getStatistic(Statistic.SPRINT_ONE_CM));
		      p.sendMessage("§a§l[*] §2§lTrappole triggerate: §e§o" + p.getStatistic(Statistic.TRAPPED_CHEST_TRIGGERED));
		      p.sendMessage("§a§l[*] §2§lComunicate con villager: §e§o" + p.getStatistic(Statistic.TALKED_TO_VILLAGER));
		      p.sendMessage("§a§l[*] §2§lScambi con villager: §e§o" + p.getStatistic(Statistic.TRADED_WITH_VILLAGER));
		      p.sendMessage("§a§l[*] §2§lCamminate: §e§o" + p.getStatistic(Statistic.WALK_ONE_CM));
		      p.sendMessage("§a§l[*] §2§lArrampicate: §e§o" + p.getStatistic(Statistic.CLIMB_ONE_CM));
		    }
	   else if (command.getName().equalsIgnoreCase("statistiche")) {
		   p.sendMessage("/stats");
	   }
	  
		    return true;
	  }
}
		  
		

