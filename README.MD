
# Minecraft Advertisement API
## Setup:
Before you even start you will need to setup an Adf.ly account and enable API access. In your IDE add the .jar file to the Build-Path and then in the plugin.yml add `depend: [MAA]`. Place the MMA.jar file in you plugins folder.

> Attention: On the first load the plugin will automatically disable itself. You need to update the Adf.ly API access info in there and then specify on which domain the NodeJS is hosted.

Now go into the NodeJS server folder, open **index.js** and edit the array named: **allowed_ips**. There you will every IP adress of minecraft server using this API.

If you've done everything correclty, when you load the NodeJS server and then the minecraft one, everything should work fine.

## How to use:
Once everything is setup, we can get into coding! 
### Adding a Challenge
To create a "Challenge", or an adf.ly link, you can use following methods:

```java
MAA.add(Player player)
```
This method returns a string:
```java
Player player = Bukkit.getPlayerExact("Exampleplayer");
String link = MAA.add(player);
```

Alternatively you may use this method Asynchronously, adding a Callback() parameter
example usage of 
```java
final Player player = Bukkit.getPlayerExact("Exampleplayer");
MAA.add(player, new Callback(){
	public void asyncAddPlayer(String link) {
		//Your code goes in here
	}	
});
```
### Events:
MAA uses the Bukkit Event-API to fetch completed challenges.
To receive completed challenges, you will need to register a listener for the "ChallengeCompletionEvent":
```java
public class Example implements Listener{
	@EventHandler
	public void onChallengeComplete(ChallengeCompletionEvent e){
		e.getPlayer().sendMessage("You completed the challenge!");
	}
}
```
Then register this listener in you onEnable() method:
```java
onEnable(){
	...//your other code
	Bukkit.getPluginManager().registerEvents(new Example(), this);
	...//your other code
}
```
### Has the challenge been completed?
You of course may need to check if the player has completed a challenge, and for that there exists the MAA.hasCompleted() method, example:
```java
	Player player = Bukkit.getPlayerExact("Exampleplayer");
	boolean bool = MAA.hasCompleted(player);
```
This method returns *false* whener the challenge has not been completed (i.e. while the ChallengeCompletionEvent has not fired for a certain player after you created a challenge). 
Otherwise, wheter the challenge has been completed or there isn't one, the method will return always *true*.
## Other information
### Known issues:
I suggest not reloading the plugin once it is enabled on the server for the following reason: allthough the MAA will still work fine, it will start outputting an error on every HTTP request in the console. This bug  is not caused by the plugin itself, but by the libraries it uses.

# Minecraft Advertisement API
## Setup:
Before you even start you will need to setup an Adf.ly account and enable API access. In your IDE add the .jar file to the Build-Path and then in the plugin.yml add `depend: [MAA]`. Place the MMA.jar file in you plugins folder.

> Attention: On the first load the plugin will automatically disable itself. You need to update the Adf.ly API access info in there and then specify on which domain the NodeJS is hosted.

Now go into the NodeJS server folder, open **index.js** and edit the array named: **allowed_ips**. There you will every IP adress of minecraft server using this API.

If you've done everything correclty, when you load the NodeJS server and then the minecraft one, everything should work fine.

## How to use:
Once everything is setup, we can get into coding! 
### Adding a Challenge
To create a "Challenge", or an adf.ly link, you can use following methods:

```java
MAA.add(Player player)
```
This method returns a string:
```java
Player player = Bukkit.getPlayerExact("Exampleplayer");
String link = MAA.add(player);
```

Alternatively you may use this method Asynchronously, adding a Callback() parameter
example usage of 
```java
final Player player = Bukkit.getPlayerExact("Exampleplayer");
MAA.add(player, new Callback(){
	public void asyncAddPlayer(String link) {
		//Your code goes in here
	}	
});
```
### Events:
MAA uses the Bukkit Event-API to fetch completed challenges.
To receive completed challenges, you will need to register a listener for the "ChallengeCompletionEvent":
```java
public class Example implements Listener{
	@EventHandler
	public void onChallengeComplete(ChallengeCompletionEvent e){
		e.getPlayer().sendMessage("You completed the challenge!");
	}
}
```
Then register this listener in you onEnable() method:
```java
onEnable(){
	...//your other code
	Bukkit.getPluginManager().registerEvents(new Example(), this);
	...//your other code
}
```
### Has the challenge been completed?
You of course may need to check if the player has completed a challenge, and for that there exists the MAA.hasCompleted() method, example:
```java
	Player player = Bukkit.getPlayerExact("Exampleplayer");
	boolean bool = MAA.hasCompleted(player);
```
This method returns *false* whener the challenge has not been completed (i.e. while the ChallengeCompletionEvent has not fired for a certain player after you created a challenge). 
Otherwise, wheter the challenge has been completed or there isn't one, the method will return always *true*.
## Other information
### Known issues:
I suggest not reloading the plugin once it is enabled on the server for the following reason: allthough the MAA will still work fine, it will start outputting an error on every HTTP request in the console. This bug  is not caused by the plugin itself, but by the libraries it uses.


## License
![enter image description here](https://i.creativecommons.org/l/by/4.0/88x31.png)
This work is licensed under a [Creative Commons Attribution 4.0 International License](http://creativecommons.org/licenses/by/4.0/).
