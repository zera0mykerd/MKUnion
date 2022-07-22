package mykerd;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;


public class SimpleReport implements CommandExecutor {
	File reports = new File("plugins/MKUnion/reports.yml");
	YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(this.reports);
  Main plugin;
  
  public SimpleReport(Main plugin) {
    this.plugin = plugin;
  }
  
  @SuppressWarnings("rawtypes")
public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
      if (cmd.getName().equalsIgnoreCase("report")) {
          if (sender.hasPermission("simplereport.report")) {
              if (args.length < 2) {
                  sender.sendMessage("§1§lPowaReports §0§l§o> §cSintassi errata, usa:  /report <player> <motivo>");
              }
              else {
                  final StringBuilder str = new StringBuilder();
                  for (int i = 1; i < args.length; ++i) {
                      str.append(String.valueOf(args[i]) + " ");
                  }
                  Bukkit.broadcast("§1§lPowaReports §0§l§o>>> §2§l[§a§lReport§2§l] §e " + sender.getName() + " §9Ha reportato §2" + args[0] + "§9 per §b" + (Object)str, "simplereport.see");
                  final String report = "§2 " + args[0] + " risulta reportato da §2" + sender.getName() + "§9 per §b" + (Object)str;
                  final List<String> reports = (List<String>)this.yamlFile.getStringList("reports");
                  reports.add(report);
                  this.yamlFile.set("reports", (Object)reports);
                  try {
                      this.yamlFile.save("plugins/SimpleReport/reports.yml");
                  }
                  catch (IOException e) {
                      e.printStackTrace();
                  }
                  sender.sendMessage("§1§lPowaReports §0§l§o> §aGiocatore reportato con successo.");
              }
          }
          else {
              sender.sendMessage("§1§lPowaReports §0§l§o> §4Accesso negato al comando.");
          }
      }
      if (cmd.getName().equalsIgnoreCase("reports")) {
          if (sender.hasPermission("simplereport.list")) {
              if (args.length == 0) {
                  sender.sendMessage(StringUtils.join((Collection)this.yamlFile.getList("reports"), "\n"));
                  sender.sendMessage("§1§lPowaReports §0§l§o> §9Per rimuovere tutti i reports: /reports removeall");
              }
              else if (args[0].equalsIgnoreCase("removeall")) {
                  this.yamlFile.set("reports", (Object)"");
                  sender.sendMessage("§1§lPowaReports §0§l§o> §aTutti i reports sono stati rimossi con successo.");
              }
              else {
                  sender.sendMessage("§1§lPowaReports §0§l§o> §cUso incorretto del comando.");
              }
          }
          else {
              sender.sendMessage("§1§lPowaReports §0§l§o> §4Accesso negato al comando.");
          }
      }
      return true;
  }
}

