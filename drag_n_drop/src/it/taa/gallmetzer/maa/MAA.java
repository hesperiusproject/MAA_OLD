package it.taa.gallmetzer.maa;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.taa.gallmetzer.maa.callbacks.CheckCallback;
import it.taa.gallmetzer.maa.callbacks.CreateCallback;
import it.taa.gallmetzer.maa.utils.Challenge;
import it.taa.gallmetzer.maa.utils.RequestHandler;
import ly.adf.AdflyApiWrapper;

public class MAA<T extends JavaPlugin> {

	private static HashMap<Player, Challenge> currentChallenges = new HashMap<Player, Challenge>();
	public static String domain;
	public static String public_key;
	public static String secret_key;
	public static int user_id;
	private T plugin;
	
	@SuppressWarnings("rawtypes")
	public static MAA maa;
	
	public MAA(T plugin, String domain_, String public_key_, String secret_key_, int user_id_) {
		
		this.plugin = plugin;
		domain = domain_;
		public_key = public_key_;
		secret_key = secret_key_;
		user_id = user_id_;
		maa = this;
	}
	
	@SuppressWarnings("rawtypes")
	public static MAA get() {
		return maa;
	}
	
	
	public static  void addChallenge(final Player p, final CreateCallback b) {
		Bukkit.getScheduler().runTaskAsynchronously(MAA.get().plugin, new Runnable() {
			public void run() {
				if (MAA.getChallenge(p) != null) {
					b.asyncAddPlayer("Error 3");
					return;
				}

				String unshort = RequestHandler.addPlayer(p);
				if (unshort != null) {
					String link = getApiRes("http://" + domain + "/verify/" + unshort);
					if (link != null) {
						currentChallenges.put(p, new Challenge(getID(link)));
						b.asyncAddPlayer(justUrl(link));
					} else {
						b.asyncAddPlayer("Error 2");
					}
				} else {
					b.asyncAddPlayer("Error 1");
				}
			}

		});
	}

	@Deprecated
	protected void deleteIfExists(final Player p) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			public void run() {
				if (MAA.addChallenge(p) != null) {
					AdflyApiWrapper api = new AdflyApiWrapper();
					api.deleteUrl(MAA.getChallenge(p).getID());
					return;
				}
			}
		});
	}

	public static String addChallenge(Player player) {
		if (MAA.getChallenge(player) != null) {
			return "Error 3";
		}
		String unshort = RequestHandler.addPlayer(player);
		if (unshort != null) {
			String link = getApiRes("http://" + domain + "/verify/" + unshort);
			if (link != null) {
				currentChallenges.put(player, new Challenge(getID(link)));
				return justUrl(link);
			} else {
				return "Error 2";
			}
		} else {
			return "Error 1";
		}

	}

	public static boolean checkPlayer(Player p) {
		boolean complete = RequestHandler.checkPlayer(p);
		if (complete) {
			MAA.getChallenge(p).setBool(true);
			RequestHandler.deleteLink(MAA.getChallenge(p).getID(), MAA.get().plugin);
			MAA.hasCompleted(p);

		} else if (!complete && MAA.getChallenge(p) == null) {
			complete = true;
		}
		return complete;
	}

	public static void checkPlayer(final Player p, final CheckCallback c) {
		
		Bukkit.getScheduler().runTaskAsynchronously(MAA.get().plugin, new Runnable() {

			public void run() {
				boolean complete = RequestHandler.checkPlayer(p);
				if (complete) {
					MAA.getChallenge(p).setBool(true);
					RequestHandler.deleteLink(MAA.getChallenge(p).getID(), MAA.get().plugin);
					MAA.hasCompleted(p);

				} else if (!complete && MAA.getChallenge(p) == null) {
					complete = true;
				}

				c.asyncCheckPlayer(complete);
			}

		});

	}

	public static boolean hasCompleted(Player p) {
		try {
			if (!currentChallenges.get(p).hasPassed()) {
				return false;
			} else if (currentChallenges.get(p).hasPassed()) {
				currentChallenges.remove(p);
				return true;
			}
		} catch (NullPointerException e) {
		}
		return true;

	}

	public static void clean() {
		AdflyApiWrapper api = new AdflyApiWrapper();
		for (Player p : getCurrentChallenges().keySet()) {
			api.deleteUrl(MAA.getChallenge(p).getID());
		}
		return;
	}

	public static Challenge getChallenge(Player p) {
		return currentChallenges.get(p);
	}

	private static String getApiRes(String url) {
		AdflyApiWrapper api = new AdflyApiWrapper();
		String a = api.shorten(url);
		JsonParser prs = new JsonParser();
		JsonObject obj = (JsonObject) prs.parse(a);
		return obj.getAsJsonArray("data").get(0).getAsJsonObject().getAsJsonPrimitive("short_url").toString()
				+ obj.getAsJsonArray("data").get(0).getAsJsonObject().getAsJsonPrimitive("id").toString();
	}

	private static String justUrl(String dirt) {
		String newa = dirt.substring(1);
		String ret = "";
		for (int i = 0; i < newa.length(); i++) {
			if (newa.charAt(i) != '"') {
				ret = ret + newa.charAt(i);
			} else {
				return ret;
			}
		}
		return dirt;
	}

	private static long getID(String ds) {
		String dirt = ds.substring(1);
		for (int i = 0; i < dirt.length(); i++) {
			if (dirt.charAt(i) != '"') {
			} else {
				String finl = dirt.substring(i + 1);

				return Long.parseLong(finl.substring(1, finl.length() - 1));
			}
		}
		return 0;
	}

	// SETTERS & GETTERS

	public static String getDomain() {
		return domain;
	}

	public static HashMap<Player, Challenge> getCurrentChallenges() {
		return currentChallenges;
	}

	public static boolean activeChallenges() {
		return currentChallenges.isEmpty();
	}

	public static void setDomain(String s) {
		domain = s;
	}

}
