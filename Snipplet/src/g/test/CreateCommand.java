package g.test;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.taa.gallmetzer.maa.MAA;
import it.taa.gallmetzer.maa.callbacks.CreateCallback;

public class CreateCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		final Player p = Bukkit.getPlayer(arg3[0]);
		
		if(p != null){
			MAA.addChallenge(p, new CreateCallback() {
				
				public void asyncAddPlayer(String s) {
					
					p.sendMessage("Follow this link to receive a diamond: "+s);
					
				}
				
			});
		}
		
		return true;
	}

	
	
}
