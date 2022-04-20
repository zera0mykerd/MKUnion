package mykerd;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerMaliciousChatEvent extends Event {
  private static final HandlerList HANDLERS = new HandlerList();
  
  private final Player player;
  
  private final String message;
  
  @NotNull
  public static HandlerList getHandlerList() {
    return HANDLERS;
  }
  
  public PlayerMaliciousChatEvent(Player player, String message) {
    this.player = player;
    this.message = message;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  @NotNull
  public HandlerList getHandlers() {
    return HANDLERS;
  }
}
