package g.test;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import it.taa.gallmetzer.maa.utils.ChallengeCompletionEvent;

public class EvntListener implements Listener{

	@EventHandler
	public void onEvent(ChallengeCompletionEvent e) {
		e.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND,1));
		e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCongratulations, you received a &bDiamond&4!"));
	}
	
	
}
