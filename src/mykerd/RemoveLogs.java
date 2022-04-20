package mykerd;


import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

public class RemoveLogs extends JavaPlugin {
  public void onEnable() {
    File logdir = new File("logs");
    if (logdir.exists()) {
      String[] entries = logdir.list();
      byte b;
      int i;
      String[] arrayOfString1;
      for (i = (arrayOfString1 = entries).length, b = 0; b < i; ) {
        String s = arrayOfString1[b];
        File currentFile = new File(logdir.getPath(), s);
        currentFile.delete();
        b++;
      } 
      System.out.println("§4§o§nAttenzione: I files di logs sono stati rimossi da §c§4§oMKUnion");
    } 
  }
}

