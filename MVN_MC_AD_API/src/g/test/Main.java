package g.test;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import it.taa.gallmetzer.maa.MAA;
import it.taa.gallmetzer.maa.utils.RequestHandler;

public class Main extends JavaPlugin {

	public void onEnable() {

		MAA api = new MAA(this, "local.hes.re:2000", "67021652b5a41a84aba3ef847d24b93f", "afd0024b-f7a0-4c6e-a7f7-3d7a5816e5b2", 23160111);
		this.getCommand("create").setExecutor(new CreateCommand());
		Bukkit.getPluginManager().registerEvents(new EvntListener(), this);
		RequestHandler.start(this);
	}

	public void onDisable() {

	}

}
