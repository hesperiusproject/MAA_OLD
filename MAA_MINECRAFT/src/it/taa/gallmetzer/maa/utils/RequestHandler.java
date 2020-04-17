package it.taa.gallmetzer.maa.utils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.taa.gallmetzer.maa.MAA;
import it.taa.gallmetzer.maa.Main;
import ly.adf.AdflyApiWrapper;

public class RequestHandler {

	public static String addPlayer(Player p) {
		try {
			String postUrl = "http://" + MAA.getDomain() + "/add";// put in your url
			Gson gson = new Gson();
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(postUrl);
			String url = generateUrl(p);
			StringEntity postingString = new StringEntity(gson.toJson(new Request(p, url)));

			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");

			httpClient.execute(post);
			return url;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	public static boolean checkPlayer(Player p) {
		try {
			String postUrl = "http://" + MAA.getDomain() + "/hasverified";
			Gson gson = new Gson();
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(postUrl);
			StringEntity postingString = new StringEntity(gson.toJson(new Request(p, "NONE")));

			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");

			HttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			JsonParser ps = new JsonParser();
			JsonObject obj = (JsonObject) ps.parse(json);
			boolean b = obj.getAsJsonPrimitive("completed").getAsBoolean();
			return b;
		} catch (Exception e) {
			System.out.println(e);
			return true;
		}
	}

	@SuppressWarnings("deprecation")
	public static void checker() {

		BukkitScheduler scheduler = Main.get().getServer().getScheduler();
		scheduler.scheduleAsyncRepeatingTask(Main.get(), new Runnable() {
			public void run() {
				if (ChallengeCompletionEvent.getHandlerList().getRegisteredListeners().length == 0
						|| MAA.activeChallenges()) {
					return;
				} else if (ChallengeCompletionEvent.getHandlerList().getRegisteredListeners().length > 0
						&& !MAA.activeChallenges()) {

					try {
						String postUrl = "http://" + MAA.getDomain() + "/getcomplete";
						Gson gson = new Gson();
						HttpClient httpClient = HttpClientBuilder.create().build();
						HttpPost post = new HttpPost(postUrl);
						StringEntity postingString = new StringEntity(gson.toJson(new GetCompleted()));

						post.setEntity(postingString);
						post.setHeader("Content-type", "application/json");

						HttpResponse response = httpClient.execute(post);
						HttpEntity entity = response.getEntity();
						String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

						JsonParser ps = new JsonParser();

						if (ps.parse(json) instanceof JsonNull) {
							return;
						}
						final String toGo = json;
						Bukkit.getServer().getScheduler().runTask(Main.get(), new Runnable() {

							public void run() {
								JsonParser s = new JsonParser();
								JsonObject obj = (JsonObject) s.parse(toGo);
								for (Object o : obj.getAsJsonArray("active")) {
									Bukkit.getConsoleSender()
											.sendMessage(o.toString().substring(1, o.toString().length() - 1));
									ChallengeCompletionEvent event = new ChallengeCompletionEvent(Bukkit.getPlayer(
											UUID.fromString(o.toString().substring(1, o.toString().length() - 1))));
									Bukkit.getPluginManager().callEvent(event);

								}
							}
						});

						return;
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}, 20L, 10 * 20L);
	}

	public static void deleteLink(final long id) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.get(), new Runnable() {
			public void run() {
				AdflyApiWrapper api = new AdflyApiWrapper();
				api.deleteUrl(id);
			}
		});

	}

	private static String generateUrl(Player p) {

		String random = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		String randomS = p.getUniqueId().toString() + "-";
		for (int i = 0; i < 4; i++) {
			randomS = randomS + random.charAt((int) (Math.random() * random.length()));
		}

		String s = p.getUniqueId().toString() + "-" + randomS;
		return s;
	}

	

}

class Request {
	String UUID;
	String link;

	public Request(Player p, String link) {
		this.UUID = p.getUniqueId().toString();
		this.link = link;
	}

}

class GetCompleted {
	String info = Bukkit.getName() + ": Requesting completed challenges";
}