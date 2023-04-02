package mykerd;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import org.bukkit.plugin.java.JavaPlugin;
//import org.bukkit.potion.Potion;
//import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.entity.Firework;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.Sound;
import org.bukkit.World;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
//import com.comphenix.protocol.events.ListenerPriority;
//import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
//import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.collect.ImmutableList;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
//import mykerd.LogManager;
//import mykerd.PlayerMaliciousChatEvent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

//import java.util.logging.Level;





import mykerd.Main;

public class Main extends JavaPlugin implements Listener {
	//Main plugin;
	FileConfiguration config = getConfig();
	//public void saveDefaultConfig() {
	//}
	
	  private final Logger logger = getLogger();
	  
	  @SuppressWarnings("unused")
	private ProtocolManager protocolManager;
	  
	  private LogManager logManager;

	public HashMap<Entity, Entity> lastPlayerDamagePlayer = new HashMap<>();
	
	  public HashMap<Player, Float> cooldownTime = new HashMap<>();
	  
	  public HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<>();
	  
	  public HashMap<String, Long> used = new HashMap<String, Long>();
	  
	  public static final Pattern pattern = Pattern.compile(".*\\$\\{[^}]*\\}.*");
	  
	  private Map<Player, Location> playersToLastTime = new HashMap<>();
	  
	  private long duration;
	  
	 // private String sound;
	  
	  private String message;
	  
	  private Map<UUID, Long> cooldowns;
	  
	  List<String> playersCannotBuild = new ArrayList<>();
	  
	  List<String> playersCannotPlace = new ArrayList<>();
	  
	  List<String> playersCannotBreak = new ArrayList<>();
	  
	  ArrayList<String> cooldown = new ArrayList<String>();

	//public Main(Main plugin) {
		    //this.plugin = plugin;
		  //}

	List<String> loadConfigValue(String path) {
		    return loadConfigValue(path, new ArrayList<>());
		  }
		  
		  @SuppressWarnings("unchecked")
		List<String> loadConfigValue(String path, List<String> defaultValue) {
		    if (getConfig().get(path) == null) {
		      getConfig().set(path, defaultValue);
		      saveConfig();
		      return defaultValue;
		    } 
		    return (List<String>)getConfig().get(path);
		  }
	  
	public HashMap<Entity, Integer> damagedPlayerCounter = new HashMap<>();
	
    //public static Map<Player, Boolean> NoClip = new HashMap<>();
	
	//private String perfix = "§9§lPowaFixer §3§l| §b§o";
	
	public static Main pl;
	
	public static Main getPL() {
		return pl;
		
	}
	
	  public void onLoad() {
		    LConfig.load(this);
		    loadLogManager();
		    AppenderFilter.tryApply(this);
		    this.protocolManager = ProtocolLibrary.getProtocolManager();
		  }
	  
	  private void loadLogManager() {
		    File dir;
		    if (!LConfig.getLogging().isEnabled())
		      return; 
		    if (LConfig.getLogging().getCustomPath() != null) {
		      dir = new File(LConfig.getLogging().getCustomPath());
		    } else {
		      dir = new File(getDataFolder(), "logs");
		    } 
		    this.logManager = new LogManager(getLogger(), dir);
		    this.logManager.start();
		  }

	
	  public void onEnable() {
		    getServer().getPluginManager().registerEvents(this, (Plugin)this);
		    getConfig().options().copyDefaults(true);
		    saveConfig();
		    getCommand("cc").setExecutor(new CC(this));
		    getCommand("icc").setExecutor(new ICC(this));
		    getCommand("stats").setExecutor(new Stats(this));
		    getCommand("statistiche").setExecutor(new Stats(this));
		    getCommand("statistica").setExecutor(new Stats(this));
		    getCommand("progressi").setExecutor(new Stats(this));
		    getCommand("mklag").setExecutor(new MKLag(this));
		    getCommand("fix").setExecutor(new Fix(this));
		    getCommand("setspawn").setExecutor(new SimpleSpawn(this));
		    getCommand("spawn").setExecutor(new SimpleSpawn(this));
		    //getCommand("togglebreak").setExecutor(new Main(this));
		    //getCommand("toggleplace").setExecutor(new Main(this));
		    //getCommand("togglebuild").setExecutor(new Main(this));
		    String path1 = "Config.CC.message";
		    getConfig().addDefault(path1, "");
		    String path2 = "Config.ICC.message";
		    getConfig().addDefault(path2, "");
		    String path3 = "Config.permissions.message";
		    getConfig().addDefault(path3, "§8§oAccesso negato al comando!");
		    getConfig().options().copyDefaults(true);
		    //config.addDefault("MkConfig", true);
	        //config.options().copyDefaults(true);
	        saveConfig();
		    getLogger().info("Enabled Mykerd plugin!");
		    this.playersCannotBuild = loadConfigValue("playersCannotBuild");
		    //this.playersCannotPlace = loadConfigValue("playersCannotPlace");
		    //this.playersCannotBreak = loadConfigValue("playersCannotBreak");
		    this.duration = (config.getInt("duration") * 1000);
		   // this.sound = config.getString("sound");
		    this.message = ChatColor.translateAlternateColorCodes('&', config.getString("message"));
		    this.cooldowns = new HashMap<>();
		    pl = this;
		    //this.protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)this, ListenerPriority.LOWEST, new PacketType[] { PacketType.Play.Server.CHAT, PacketType.Play.Client.CHAT }));
	  }
	  
	  
	  
	  
	  //WARNING: THIS FIX LOG4J EXPLOIT CRITICAL BUG!
	  
	 @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	  public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
	    String content = event.getMessage();
	    //if (content.toLowerCase().contains("${jndi"))
	     if (content.toLowerCase().contains("${"))
	    	 //if (content.contains("${") && content.toLowerCase().contains("ldap"))
	      event.setCancelled(true);
	  }
	 
	 @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	 public void onPlayerCommandMonitor(PlayerCommandPreprocessEvent event) {
	     String message = event.getMessage();
	     @SuppressWarnings("unused")
		String arguments = "";
	     @SuppressWarnings("unused")
		String command = "";
	     if (!message.contains("${")) {
	         command = message.replace("/", "");
	     } else {
	         command = message.substring(0, message.indexOf("${")).replace("/", "");
	         arguments = message.substring(message.indexOf(" ") + 1, message.length());
	     }
	         }

	 
	  public void log(String message) {
		    if (this.logManager == null)
		      return; 
		    this.logManager.writeLog(message);
		  }
	  
      public void onPacketReceiving(PacketEvent event) {
          if (event.getPacketType() == PacketType.Play.Client.CHAT) {
            Player player = event.getPlayer();
            PacketContainer packetContainer = event.getPacket();
            String message = (String)packetContainer.getStrings().read(0);
            if (message.indexOf('$') == -1)
              return; 
            if (matches(message)) {
              event.setCancelled(true);
              packetContainer.getStrings().write(0, "");
              Main.this.logger.severe(player.getName() + " just tried something malicious");
              Bukkit.getScheduler().runTask((Plugin)Main.this, () -> Bukkit.getPluginManager().callEvent((Event)new PlayerMaliciousChatEvent(player, message)));
              if (LConfig.getLogging().isMessageReceive())
                Main.this.log("Blocked received message by player " + event.getPlayer().getName() + ": " + message); 
            } 
          } 
        }
      
      public void onPacketSending(PacketEvent event) {
          if (event.getPacketType() == PacketType.Play.Server.CHAT) {
            PacketContainer packetContainer = event.getPacket();
            WrappedChatComponent wrappedChatComponent = packetContainer.getChatComponents().getValues().get(0);
            if (wrappedChatComponent == null)
              return; 
            String jsonMessage = wrappedChatComponent.getJson();
            if (jsonMessage.indexOf('$') == -1)
              return; 
            if (matches(jsonMessage)) {
              event.setCancelled(true);
              packetContainer.getChatComponents().write(0, WrappedChatComponent.fromText(""));
              if (LConfig.getLogging().isMessageSend())
                Main.this.log("Prevented sending message to player " + event.getPlayer().getName() + ": " + jsonMessage); 
            } 
          } 
        }
      
      public boolean matches(String message) {
          Matcher matcher = Main.pattern.matcher(message.replaceAll("[^\\x00-\\x7F]", "").toLowerCase(Locale.ROOT));
          return matcher.find();
        }
	  
	 //WARNING: THIS FIX LOG4J EXPLOIT CRITICAL BUG!
	  
	  
      @EventHandler(ignoreCancelled = true)
      void onEnderPearlThrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.EnderPearl) || !(event.getEntity().getShooter() instanceof Player))
          return; 
        Player player = (Player)event.getEntity().getShooter();
        if (player.hasPermission("enderpearl.bypass"))
          return; 
        Long cooldown = this.cooldowns.get(player.getUniqueId());
        if (cooldown != null && cooldown.longValue() > System.currentTimeMillis()) {
          event.setCancelled(true);
          executeAction(player, cooldown.longValue());
        } else {
          this.cooldowns.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis() + this.duration));
        } 
      }
      
      private void executeAction(Player player, long cooldown) {
    	   // if (!this.sound.isEmpty())
    	     // player.playSound(player.getLocation(), this.sound, SoundCategory.MASTER, 1.0F, 1.0F); 
    	    if (!this.message.isEmpty())
    	      player.sendMessage(this.message.replace("%time%", String.valueOf(Math.floorDiv(cooldown - System.currentTimeMillis(), 1000L) + 1L))); 
    	  }
    	
      @EventHandler
      public void onLaunch(ProjectileLaunchEvent e) {
        Projectile pro = e.getEntity();
        if (!pro.getType().equals(EntityType.ENDER_PEARL))
          return; 
        if (pro.getType().equals(EntityType.ENDER_PEARL)) {
          Player p = (Player)pro.getShooter();
          Location loc = p.getLocation();
          this.playersToLastTime.remove(p);
          this.playersToLastTime.put(p, loc);
        } else {
          return;
        } 
      }
      
      public void trydebug(Player player) {}
      
      public void debug(Player player) {
    	    Block by = player.getLocation().getBlock();
    	    if (!by.getType().equals(Material.AIR)) {
    	      player.teleport(this.playersToLastTime.get(player));
    	      this.playersToLastTime.remove(player);
    	    } 
    	  }
      
      @EventHandler(priority = EventPriority.HIGHEST)
      public void onHit1(ProjectileHitEvent e) {
        Projectile pr = e.getEntity();
        if (!pr.getType().equals(EntityType.ENDER_PEARL))
          return; 
        if (pr.getType().equals(EntityType.ENDER_PEARL))
          if (pr.getShooter() instanceof Player) {
            Player player = (Player)pr.getShooter();
            if (!player.hasPermission("patchpearl.bypass")) {
              Block bxn = player.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock();
              Block bxn1 = player.getLocation().add(-0.5D, 0.0D, 0.0D).getBlock();
              Block bxp = player.getLocation().add(1.0D, 0.0D, 0.0D).getBlock();
              Block bxp1 = player.getLocation().add(0.5D, 0.0D, 0.0D).getBlock();
              Block bzn = player.getLocation().add(0.0D, 0.0D, -1.0D).getBlock();
              Block bzn1 = player.getLocation().add(0.0D, 0.0D, -0.5D).getBlock();
              Block bzp = player.getLocation().add(0.0D, 0.0D, 1.0D).getBlock();
              Block bzp1 = player.getLocation().add(0.0D, 0.0D, 0.5D).getBlock();
              Block by = player.getLocation().getBlock();
              Block by1 = player.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
              if (!by1.getType().equals(Material.AIR)) {
                boolean isOnLand = false;
                while (!isOnLand) {
                  player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(-1.0D)));
                  if (by1.getType() != Material.AIR) {
                    player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(-1.0D)));
                    debug(player);
                    isOnLand = true;
                  } 
                } 
              } 
              if (!by.getType().equals(Material.AIR))
                player.teleport(player.getLocation().add(0.0D, 0.5D, 0.0D)); 
              if (!bxn.getType().equals(Material.AIR) || !bxn1.getType().equals(Material.AIR))
                player.teleport(player.getLocation().add(0.5D, 0.0D, 0.0D)); 
              if (!bxp.getType().equals(Material.AIR) || !bxp1.getType().equals(Material.AIR))
                player.teleport(player.getLocation().add(-0.5D, 0.0D, 0.0D)); 
              if (!bzn.getType().equals(Material.AIR) || !bzn1.getType().equals(Material.AIR))
                player.teleport(player.getLocation().add(0.0D, 0.0D, 0.5D)); 
              if (!bzp.getType().equals(Material.AIR) || !bzp1.getType().equals(Material.AIR))
                player.teleport(player.getLocation().add(0.0D, 0.0D, -0.5D)); 
            } else {
              return;
            } 
          } else {
            return;
          }
          }  
	  
	  
	  @EventHandler
	  public void onPlayerJoin(final PlayerJoinEvent e) {
	    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable() {
	          public void run() {
	            Firework f = (Firework)e.getPlayer().getWorld().spawn(e.getPlayer().getLocation(), Firework.class);
	            FireworkMeta fm = f.getFireworkMeta();
	            fm.addEffect(FireworkEffect.builder()
	                .flicker(true)
	                .trail(true)
	                .with(FireworkEffect.Type.STAR)
	                .withColor(Color.SILVER)
	                .withFade(Color.BLUE)
	                .build());
	            fm.setPower(3);
	            f.setFireworkMeta(fm);
	          }
	        },20L);
	  }
	
	  
	  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	  public void onBlockPlace(BlockPlaceEvent event) {
		    if (this.playersCannotBuild.contains(event.getPlayer().getUniqueId().toString()) || this.playersCannotPlace.contains(event.getPlayer().getUniqueId().toString()))
			      event.setCancelled(true); 
	    HashSet<Player> players = new HashSet<>();
	    Player player = event.getPlayer();
	    Block block = player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
	    Location loc = player.getLocation().subtract(0.0D, 1.0D, 0.0D);
	    if (block.getType() == Material.AIR) {
	      if (event.isCancelled() || !event.canBuild()) {
	        if (player.getLocation().getPitch() <= 60.0F)
	          return; 
	        if (!player.isFlying()) {
	          players.add(event.getPlayer());
	        } else {
	          return;
	        } 
	      } else {
	        return;
	      } 
	    } else {
	      return;
	    } 
	    if (players.contains(event.getPlayer())) {
	      event.getPlayer().teleport(loc);
	      players.remove(player);
	    } 
	  }
	  
	  
	  @EventHandler
	  public void onProjHit(ProjectileHitEvent e) {
	    if (e.getEntity() instanceof Arrow) {
	      Arrow arrow = (Arrow)e.getEntity();
	      if (arrow.getShooter() instanceof org.bukkit.entity.Player) {
	        arrow.getShooter();
	        arrow.remove();
	      } 
	    } 
	  }
	  
	  
	  @EventHandler
	  public void onDeath(PlayerDeathEvent e) {
	    Player p = e.getEntity();
	    //if (p.hasPermission("event.thunder"))
	      p.getWorld().strikeLightning(p.getLocation());
	      p.playSound(p.getLocation(), Sound.GHAST_SCREAM, 2.0F, 1.0F);
	  }
	  
	  @EventHandler
	  public void onDeathOfPlayer(PlayerDeathEvent event) {
	    Player player = event.getEntity();
	    
	    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable() {
            public void run() {
              Firework f = (Firework)event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Firework.class);
              FireworkMeta fm = f.getFireworkMeta();
              fm.addEffect(FireworkEffect.builder()
                  .flicker(true)
                  .trail(true)
                  .with(FireworkEffect.Type.BALL_LARGE)
                  .withColor(Color.RED)
                  .withFade(Color.RED)
                  .build());
              fm.setPower(0);
              f.setFireworkMeta(fm);
            }
          },20L);
    //} else {
    	
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable() {
            public void run() {
              Firework f = (Firework)event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Firework.class);
              FireworkMeta fm = f.getFireworkMeta();
              fm.addEffect(FireworkEffect.builder()
                  .flicker(true)
                  .trail(true)
                  .with(FireworkEffect.Type.BALL_LARGE)
                  .withColor(Color.RED)
                  .withFade(Color.RED)
                  .build());
              fm.setPower(0);
              f.setFireworkMeta(fm);
              f.detonate();
            }
          },20L);

	    player.setHealth(20.0D);
	    player.setFoodLevel(35);
	    player.removePotionEffect(PotionEffectType.ABSORPTION);
	    player.removePotionEffect(PotionEffectType.BLINDNESS);
	    player.removePotionEffect(PotionEffectType.CONFUSION);
	    player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
	    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
	    player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
	    player.removePotionEffect(PotionEffectType.HARM);
	    player.removePotionEffect(PotionEffectType.HEAL);
	    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
	    player.removePotionEffect(PotionEffectType.HUNGER);
	    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
	    player.removePotionEffect(PotionEffectType.INVISIBILITY);
	    player.removePotionEffect(PotionEffectType.JUMP);
	    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
	    player.removePotionEffect(PotionEffectType.POISON);
	    player.removePotionEffect(PotionEffectType.REGENERATION);
	    player.removePotionEffect(PotionEffectType.SATURATION);
	    player.removePotionEffect(PotionEffectType.SLOW);
	    player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
	    player.removePotionEffect(PotionEffectType.SPEED);
	    player.removePotionEffect(PotionEffectType.WATER_BREATHING);
	    player.removePotionEffect(PotionEffectType.WEAKNESS);
	    player.removePotionEffect(PotionEffectType.WITHER);
	    player.setFoodLevel(35);
	    player.setExp(0);
	    player.setLevel(0);
	    player.setTotalExperience(0);
	    player.setMaxHealth(20);
	    player.teleport(player.getWorld().getSpawnLocation());
	    event.getDrops().clear();
	    event.setDroppedExp(0);
	  }
	
	  
	  
	  @EventHandler(priority = EventPriority.HIGHEST)
	  private void FallenIntoVoid(EntityDamageEvent event) {
	    if (event.getEntity() instanceof Player) {
	      Player p = (Player)event.getEntity();
	      if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
	    	  p.damage(2000.0D);
	      } 
	    } 
	  }
	  
	  @SuppressWarnings("unused")
	private boolean thisllfixit(Block block, Object bF) {
		    @SuppressWarnings("deprecation")
			byte data = block.getState().getData().getData();
		    if (data > 13 || block.getRelative((BlockFace)bF).getType() != Material.PISTON_BASE)
		      return false; 
		    block.getRelative((BlockFace)bF).setType(Material.AIR);
		    return true;
		  }
		  
		  @EventHandler
		  public void onPistonExplode(EntityExplodeEvent event) {
		    Set<Block> blockSet = (Set<Block>)event.blockList().stream().filter(b -> (b.getType() == Material.PISTON_MOVING_PIECE)).collect(Collectors.toSet());
		    for (@SuppressWarnings("unused") Block b2 : blockSet) {
		      BLOCK_FACES.forEach(bF -> {
		          
		          });
		    } 
		  }
		  
		  @SuppressWarnings("deprecation")
		@EventHandler
		  public void oncmd(PlayerCommandPreprocessEvent e) {
		    ArrayList<String> cmds = new ArrayList<>();
		    cmds.add("/pv");
		    cmds.add("/playervaults");
		    cmds.add("/chest");
		    cmds.add("/vault");
		    for (String s : cmds) {
		      if (e.getMessage().indexOf(s) != -1) {
		        Location loc = e.getPlayer().getLocation();
		        if (loc.getBlock().getType() != Material.WATER_LILY)
		          continue; 
		        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&oNon provarci neanche!"));
		        e.setCancelled(true);
		        e.getPlayer().closeInventory();
		        e.setMessage(null);
		        e.setFormat(null);
		      } 
		    } 
		  }
		  
		  private static final ImmutableList<Object> BLOCK_FACES = ImmutableList.of(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
		  
		  @SuppressWarnings("deprecation")
		@EventHandler(priority = EventPriority.MONITOR)
		  public void onInventoryClick(InventoryClickEvent event) {
		    for (int i = 0; i < event.getWhoClicked().getInventory().getSize(); i++) {
		      if (event.getWhoClicked().getInventory().getItem(i) != null && event.getWhoClicked().getInventory().getItem(i).getType() != Material.AIR && event.getWhoClicked().getInventory().getItem(i).getAmount() == 0) {
		        event.getWhoClicked().getInventory().setItem(i, null);
		        if (event.getCurrentItem() == event.getWhoClicked().getInventory().getItem(i) || event.getCursor() == event.getWhoClicked().getInventory().getItem(i)) {
		          event.setResult(Event.Result.DENY);
		          event.setCancelled(true);
		        } 
		      } 
		    } 
		    if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().getAmount() == 0) {
		      event.setCurrentItem(null);
		      event.setResult(Event.Result.DENY);
		      event.setCancelled(true);
		    } 
		    if (event.getCursor() != null && event.getCursor().getType() != Material.AIR && event.getCursor().getAmount() == 0) {
		      event.setCursor(null);
		      event.setResult(Event.Result.DENY);
		      event.setCancelled(true);
		    } 
		  }
		  
		  @EventHandler(priority = EventPriority.MONITOR)
		  public void onPlayerInteract(PlayerInteractEvent event) {
		    if (event.getPlayer().getItemInHand().getAmount() == 0)
		      event.getPlayer().setItemInHand(null); 
		  }
		  
		  @EventHandler(priority = EventPriority.MONITOR)
		  public void onItemSpawn(ItemSpawnEvent event) {
		    if (event.getEntity().getItemStack().getAmount() == 0)
		      event.setCancelled(true); 
		  }
		
	 // @EventHandler
	  //public void onVoid(PlayerMoveEvent event) {
	  //  Player p = event.getPlayer();
	    //    p.damage(2000.0D);
	      

	  
	  @EventHandler
	  public void PDP(EntityDamageByEntityEvent e) {
	    Entity Attacked = e.getEntity();
	    Entity Attacker = e.getDamager();
	    this.lastPlayerDamagePlayer.put(Attacked, Attacker);
	    this.damagedPlayerCounter.put(Attacked, Integer.valueOf(getConfig().getInt("cancelAttack")));
	  }
	  
	  @SuppressWarnings("deprecation")
	@EventHandler
	  public void onMove(PlayerMoveEvent e) {
	    Player p = e.getPlayer();
	    if (this.damagedPlayerCounter.containsKey(p)) {
	      if (p.isOnGround() && ((Integer)this.damagedPlayerCounter.get(p)).intValue() > 0)
	        this.damagedPlayerCounter.put(p, Integer.valueOf(((Integer)this.damagedPlayerCounter.get(p)).intValue() - 1)); 
	      if (((Integer)this.damagedPlayerCounter.get(p)).intValue() == 0)
	        this.lastPlayerDamagePlayer.remove(p); 
	    } 
	  }
	  
	  @EventHandler
	  public void onInteract(final PlayerInteractEvent e)
	  {
	  if(e.getAction() == Action.RIGHT_CLICK_AIR)
	  {
	  Player p = e.getPlayer();
	  ItemStack item = p.getItemInHand();
	  if(item.getType() == Material.ENDER_PEARL)
	  {
	  if (!cooldown.contains(e.getPlayer().getName()))
	  {
	  e.getPlayer().launchProjectile(EnderPearl.class);
	  //p.sendMessage(ChatColor.DARK_GRAY + "Devi attendere " + ChatColor.GRAY + "1.5 secondi" + ChatColor.DARK_GRAY + " per riutizzare le enderpearl.");
	  cooldown.add(e.getPlayer().getName());
	   
	  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
	  {
	  @Override
	  public void run()
	  {
	  cooldown.remove(e.getPlayer().getName());
	  }
	  },30L);
	  }else
	  {
	  }
	  }
	  }
	  }// 20L = 1 Second || 30L = 1.5 Seconds

	  
	  
	  @EventHandler
	  public void onDeath(EntityDeathEvent e) {
	    this.lastPlayerDamagePlayer.remove(e.getEntity());
	    }
	  
	  @EventHandler
	  public void onBlockBreak(BlockBreakEvent e) {
	    if (this.playersCannotBuild.contains(e.getPlayer().getUniqueId().toString()) || this.playersCannotBreak.contains(e.getPlayer().getUniqueId().toString()))
	      e.setCancelled(true); 
	  }
	  
	  
	  
	  //Fix BowShoot
	  
	  @EventHandler
	  public void onShoot(EntityShootBowEvent e) {
	    LivingEntity livingEntity = e.getEntity();
	    Vector dir = livingEntity.getLocation().getDirection();
	    Arrow a = (Arrow)e.getProjectile();
	    double speed = a.getVelocity().length();
	    Vector vel = dir.multiply(speed);
	    if (e.getBow().getItemMeta().getEnchantLevel(Enchantment.ARROW_KNOCKBACK) == 2)
	      a.setVelocity(vel); 
	  }
	  
	  @EventHandler
	  public void onVelo(PlayerVelocityEvent e) {
	    Player p = e.getPlayer();
	    Vector velo = e.getVelocity();
	    EntityDamageEvent event = p.getLastDamageCause();
	    if (event != null && !event.isCancelled() && event instanceof EntityDamageByEntityEvent) {
	      Entity damager = ((EntityDamageByEntityEvent)event).getDamager();
	      if (damager instanceof Arrow) {
	        Arrow a = (Arrow)damager;
	        if (a.getShooter().equals(p)) {
	          EnchantmentWrapper enchantmentWrapper = new EnchantmentWrapper(49);
	          if (e.getPlayer().getItemInHand().getItemMeta().getEnchantLevel((Enchantment)enchantmentWrapper) == 2) {
	            double speed = Math.sqrt(velo.getX() * velo.getX() + 3.25D + velo.getZ() * velo.getZ() + 1.0D);
	            Vector dir = a.getLocation().getDirection().normalize();
	            Vector newvelo = new Vector(dir.getX() * speed * -1.0D, velo.getY() + 0.15D, dir.getZ() * speed);
	            e.setVelocity(newvelo);
	          }
	        }
	      }
	    }
	      }
	  
	  public boolean isFalling(Player player) {
		    if (player == null) {
		      return false;
		    } 
		    Location location = player.getLocation();
		    Block block = location.getBlock();
		    Material blockType = block.getType();
		    if (blockType.isSolid() && !blockType.isOccluding()) {
		      return false;
		    } 
		    Block below = block.getRelative(BlockFace.DOWN);
		    Material belowType = below.getType();
		    boolean falling = (belowType == Material.AIR || belowType.name().endsWith("_AIR"));
		    return falling;
		  }
	  

	  //Make blood particles
	  
	  @EventHandler
	  public void bloodEffect(EntityDamageEvent event) {
	    if (event.getEntity() instanceof Player) {
	      Player player = (Player)event.getEntity();
	      Location loc = player.getLocation();
	      World world = loc.getWorld();
	      world.playEffect(loc, Effect.MAGIC_CRIT, -10);
	      world.playEffect(loc.add(0.0D, 0.8D, 0.0D), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
	    } 
	  }
	  
	  //Remove in air command execution
	  
	  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	  public void beforeCommand(PlayerCommandPreprocessEvent e) {
	    Player player = e.getPlayer();
	    if (isFalling(player) && (e.getMessage().startsWith("/"))) {
	    		   e.setCancelled(true);
	    		   player.damage(15.0D);
	    		   player.setFallDistance(9);
	    		   player.closeInventory();
	    		   player.setLastDamage(2);
	    		   player.sendMessage("§6§l (!) §0§l§o» §e§oNon puoi eseguire comandi in aria!");
	    }

	    	}
	  
	  //Organize potion glass bottle on consume
	  
//	  @EventHandler
//	  public void onPotionConsume(PlayerItemConsumeEvent event) {
//	    ItemStack consumedItem = event.getItem();
//	    Player player = event.getPlayer();
//	    if (consumedItem.getType() != Material.POTION)
//	      return; 
//	    ItemStack newItem = consumedItem.clone();
//	    newItem.setAmount(consumedItem.getAmount() - 1);
//	    player.getInventory().setItemInHand(newItem);
//	      //return; 
//	    //event.setCancelled(true);
//
//	  }
	  
//	  @EventHandler
//	  public void onConsume(PlayerItemConsumeEvent e) {
//	    if (!e.getItem().getType().equals(Material.POTION))
//	      return; 
//	    Player p = e.getPlayer();
//	    int heldSlot = p.getInventory().getHeldItemSlot();
//	    Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin)this, () -> {
//	          ItemStack held = p.getInventory().getItem(heldSlot);
//	          ItemStack off = p.getInventory().getItemInHand();
//	          if (held != null && held.getType() == Material.GLASS_BOTTLE)
//	            held.setAmount(0); 
//	          if (off.getType() == Material.GLASS_BOTTLE)
//	            off.setAmount(0); 
//		       else {
//		        e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
//		       }
//	    }
//	        ,1L);
//	  }
	
	  
//  @EventHandler
//	  public void onPlayerInteract1(PlayerInteractEvent e) {
//    if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem() != null && e.getItem().getType() == Material.POTION) {
//	      Potion p = Potion.fromItemStack(e.getItem());
//	      if (p.isSplash())
//	        return;
//	      for (PotionEffect effect : e.getPlayer().getActivePotionEffects())
//	        e.getPlayer().removePotionEffect(effect.getType()); 
//	      e.getPlayer().addPotionEffects(p.getEffects());
//	      
//	      
//	      if (Bukkit.getVersion().contains("1.10" + "1.11" + "1.12" + "1.13" + "1.14" + "1.15")) {
//	        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf("ENTITY_GENERIC_DRINK"), 1.0F, 1.0F);
//	      } else {
//	        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf("DRINK"), 1.0F, 1.0F);
//
//	        
//	        
//	      if (getConfig().getBoolean("bottle")) {
//	        e.getPlayer().setItemInHand(new ItemStack(Material.GLASS_BOTTLE));
//	      } else {
//	        e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
//	      } 
//	      
//	      e.setCancelled(true);
//	      
//	      }
//	    }
//	      
//	  }
	  
	  @EventHandler
	    public void onPotionDrink(PlayerItemConsumeEvent event){
	        Player player = event.getPlayer();
//	        if(event.getItem().getType() == Material.POTION){
//	            ItemStack air = new ItemStack(Material.AIR);
//	            player.setItemInHand(air);
	        if (player.getInventory().contains(Material.GLASS_BOTTLE)) {
	        	player.getInventory().remove(Material.GLASS_BOTTLE);
	        }
	    }
	  
//	   @SuppressWarnings("deprecation")
//	    @EventHandler
//	    public void onConsume(PlayerItemConsumeEvent e) {
//	        final Player player = e.getPlayer();
//	 
//	        if (e.getItem().getTypeId() == 373) {
//	            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(Class.inst, new Runnable() {
//	                public void run() {
//	                    player.setItemInHand(new ItemStack(Material.AIR));
//	                }
//	            }, 1L);
//	        }
//	    }
	  
 	  //Bottle potion modifier
	

	  ////public void onBlockPlace(BlockPlaceEvent e) {

	  //}
	  
	  
	  
	  //Creative enderpearl
	  
	  @EventHandler
	  public void onRightClick(PlayerInteractEvent event) {
	    Player player = event.getPlayer();
	    Action action = event.getAction();
	    if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))
	      if ((((player.getGameMode() == GameMode.CREATIVE) ? 1 : 0) & ((player.getItemInHand().getType() == Material.ENDER_PEARL) ? 1 : 0)) != 0) {
	        player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0F, 0.0F);
	        player.launchProjectile(EnderPearl.class);
	      }  
	  }
	
	  
	  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		    if (!(sender instanceof Player))
		      return true; 
		    Player p = (Player)sender;
		    if (command.getName().equalsIgnoreCase("togglebuild")) {
		      if (p.hasPermission("disablebuild.togglebuild")) {
		        String UUID = p.getUniqueId().toString();
		        if (this.playersCannotBuild.contains(UUID)) {
		          p.sendMessage("§2§l (!) §0§l§o» §a§oHai attivato il building per te stesso.");
		          this.playersCannotBuild.remove(UUID);
		          this.playersCannotPlace.remove(UUID);
		          this.playersCannotBreak.remove(UUID);
		        } else {
		          p.sendMessage("§4§l (!) §0§l§o» §c§oHai disattivato il building per te stesso.");
		          this.playersCannotBuild.add(UUID);
		        } 
		      } else {
		        p.sendMessage("§8§l(§0§l!§8§l) §1§l§o» §7§oAccesso negato al comando.");
		      } 
		      
		      
	 //     } else if (command.getName().equalsIgnoreCase("toggleplace") || p.hasPermission("disablebuild.togglebuild")) {
	//	      if (p.hasPermission("disablebuild.toggleplace")) {
	//	        String UUID = p.getUniqueId().toString();
	//	        if (this.playersCannotPlace.contains(UUID)) {
	//	          p.sendMessage("§a§oHai attivato il placing per te stesso.");
	//	          this.playersCannotPlace.remove(UUID);
	//	        } else {
	//	          p.sendMessage("§c§oHai disattivato il placing per te stesso.");
	//	          this.playersCannotPlace.add(UUID);
	//	        } 
		      //	      } else {
		      //	        p.sendMessage("§7§oNon hai il permesso per eseguire questa azione!");
		      //	      } 
		      
		      
		      
		      //		    } else if (command.getName().equalsIgnoreCase("togglebreak") || p.hasPermission("disablebuild.togglebuild")) {
		      //			      if (p.hasPermission("disablebuild.togglebreak")) {
		      //			        String UUID = p.getUniqueId().toString();
		      //			        if (this.playersCannotBreak.contains(UUID)) {
		      //			          p.sendMessage("§a§oHai attivato il breaking per te stesso.");
		      //			          this.playersCannotBreak.remove(UUID);
		      //			        } else {
			        	//		          p.sendMessage("§c§oHai disattivato il breaking per te stesso.");
		      //		          this.playersCannotBreak.add(UUID);
		      //		        } 
		    //		      } else {
		      //		        p.sendMessage("§7§oNon hai il permesso per eseguire questa azione!");
		      //		      } 
		      
		      
		    } 
		    return false;
		  }
	  

	  public void onDisable() {
		    getConfig().set("playersCannotBuild", this.playersCannotBuild);
		    getConfig().set("playersCannotPlace", this.playersCannotPlace);
		    getConfig().set("playersCannotBreak", this.playersCannotBreak);
		    if (this.logManager != null)
		        this.logManager.stop(); 
		    saveConfig();
		  getLogger().info("Disabling Mykerd plugin...");
		  }

}