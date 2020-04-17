package it.taa.gallmetzer.maa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main main;

	public void onEnable() {
		main = this;

		this.saveDefaultConfig();

		if (this.getConfig().getInt("API.user_id") == 001) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&c&lWARNING> Please update the config file with you authentication info!\n &fDisabling the plugin!"));
			this.onDisable();
		}
		MAA.setDomain(this.getConfig().getString("API.domain"));
		MAA.public_key = this.getConfig().getString("API.public_key");
		MAA.secret_key = this.getConfig().getString("API.secret_key");
		MAA.user_id = this.getConfig().getInt("API.user_id");
	}

	public static Main get() {
		return main;
	}

	public void onDisable() {
	}
}
