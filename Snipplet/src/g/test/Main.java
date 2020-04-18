package g.test;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import it.taa.gallmetzer.maa.utils.RequestHandler;

public class Main extends JavaPlugin{

	public void onEnable(){
		
		this.getCommand("create").setExecutor(new CreateCommand());
		Bukkit.getPluginManager().registerEvents(new EvntListener(), this);
		RequestHandler.start();
	}
	
	public void onDisable(){
		
	}
	
	
}
