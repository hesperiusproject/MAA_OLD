package it.taa.gallmetzer.maa.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import it.taa.gallmetzer.maa.MAA;

public class ChallengeCompletionEvent extends Event {

	private final Player playerName;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public <T extends JavaPlugin> ChallengeCompletionEvent(Player playerName,T plugin) {
		this.playerName = playerName; 
		MAA.getChallenge(playerName).setBool(true);
		RequestHandler.deleteLink(MAA.getChallenge(playerName).getID(), plugin);
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public Player getPlayer() {
		return this.playerName;
	}

}
