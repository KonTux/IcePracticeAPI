# IcePracticeAPI
Official API for the IcePractice plugin.

**OVERVIEW**

This api enables you to customize IcePractice to your needs.
Its goal is to make it possible to modify the plugin's behaviour without needing to fork it.
It works similar to the Bukkit API where most API classes are interfaces, and the plugin itself
implements them.
That has multiple advantages:
- There is no need to publish the entire Plugin source code
- All code in the ```de.kontux.icepractice.api``` is API code. Many plugin APIs just let you add the plugin jar to the project
which is insecure and does not differ between API code and implementation code.
- You can clearly recognize what is intended to be an API and what is not because this repository
only contains API code.
- Changes to the original Plugin will not directly break addons because they will not use any of the Plugin's classes.

**Using The API**

Maven repository:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Maven dependency:
```xml
<dependency>
    <groupId>com.github.KonTux</groupId>
    <artifactId>IcePracticeAPI</artifactId>
    <version>0.5.1</version>
</dependency>
```

First of all, you need to tell Bukkit that you want to use the IcePractice plugin and that your plugin needs it in order to run properly.
To do so, simply go into your plugin's ```plugin.yml ``` and add the ```depend:``` key. This key holds values as a list. All list entries 
are Strings that indicate the *exact* name of the plugin your plugin depends on.

```yaml
name: IcePracticeAddon
version: 1.0
main: com.example.IcePracticeAddon
depend:
  - IcePractice
```

This will tell the Bukkit server that your plugin depends on IcePractice and that it needs to be loaded before your plugin.

Now you are ready to use the API! You will probably mostly use the ```IcePracticeAPI.java``` class. It contains several static methods
that let you do key things like retrieving a kit from the plugin. It is similar to the ```Bukkit.java``` class.

**Example: Changing an arena's first spawn point to another location**
Although this is a very simple example and doesn't make that much sense since it is already possible through the plugin itself,
it should give you a quick introduction of how most things in the IcePracticeAPI work

```java
public class ArenaPos1Command() {
    //....
    //Your code here
    //....
    private void changePos1(String name, Location location) {
        IcePracticeArena arena = IcePracticeAPI.getArena(name);
        
        if (arena == null) {
            //If null is returned, this arena/kit does not exist  
            return;
        }

        arena.setPos1(location);
    }
}
```

**NMS/CraftBukkit API**

IcePractice has a build in API that allows the plugin to do version-specific things that use non-api classes like NMS packet sending
or packet listening by Netty Injection. You don't need to worry about the version here - as long as the version you use
is supported by IcePractice.

__Example: Getting a player's ping__
For some reason the Bukkit API does not provide a method to get a player's ping. Luckily the ```EntityPlayer.java``` class
in ```net.minecraft.server``` has a public field ```ping``` which we can access. To make this cross-version, IcePractice has
and NMS Adapter.

```java
public class PingCommand() {
    //....
    //Your code here
    //....
    private void showPing(Player player) {
        NmsApi nmsApi = IcePracticeAPI.getNmsApi(); //Get IcePractice's NMS Adapter for the appropriate version
        int ping = nmsApi.getPing(player); //Get the player's ping through the adapter
        player.sendMessage("Your ping :" + ping + " ms");
    }
}
```

The NMS Adapter capabilities will be expanded update by update.

**Custom Fights**

This might be the thing you are interested in the most. Through the IcePracticeAPI you can create your own custom fights
by extending from ```CustomFight.java```.

**Before you start:**
There are some important aspects to keep in mind when coding your own fights. **If you don't stick to some of them, it is not likely to work.**

- IcePractice uses "player-states" to determine the current abilities a player has and to indicate
which Bukkit events need to be cancelled. By default, a player's state is ```IDLE```. In this state they can use join-items,
duel players, join events and so on. When you start a match, their state changes to ```STARTING_MATCH```. In this state they
can do most things like in vanilla Minecraft except damaging each other. So when the cooldown has expired, you must set their
state to ```MATCH``` so they can actually fight.

- There is one method you **must** call at the beginning at the fight and one you **must** call at the end of the fight.
These methods are ```registerFight()``` and ```unregisterFight()```. This is necessary and will be used to call ```killPlayer()```
when a player dies or quits. Also, things like adding hits to the ```FightStatistics``` instance of your fight will need to know
what fight the player is in.

- The ```MESSAGES``` constant is an instance of ```MatchMessages.java``` which let you access the messages set in
IcePractice's ```messages.yml``` and replace the placeholders with our content.

- Make sure to return when ```IcePracticeAPI.getArenaHandler().getRandomFreeArena(kit)``` returns null. This means that there
is no arena for this kit and without an arena you cannot start a fight in most cases.

- A few methods are ```final``` and there is a reason why.

- To start your fight, obviously call ```runMatch()``` on the fight instance.

- Do not pass the constructor null pointers and make sure there are no null pointers in the ```players``` list.
(Especially when using NPCs).

Here is an example of a possible Bot fight. This should give you an understanding of how to create your own fights.

**Example: BotFight**

```java
import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.CustomFight;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.api.util.ItemBuilder;
import de.kontux.pvpbot.npc.Bot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BotFight extends CustomFight {

    private final Player player;
    private final Bot bot;

    public BotFight(Plugin plugin, Player player, Bot bot, IcePracticeKit kit) {
        super(plugin, new LinkedList<>(Arrays.asList(player, bot.spawn(player.getLocation()))), kit, false);
        this.player = player;
        this.bot = bot;
    }

    @Override
    public void runMatch() {
        arena = IcePracticeAPI.getArenaHandler().getRandomFreeArena(kit);

        //If there is no arena for this game, it is null and you should stop here
        if (arena == null) {
            player.sendMessage(MESSAGES.getNoArenaMessage());
            return;
        }

        //The bot needs some special things so we make our own spawn method for it
        spawnBot();

        //Spawns in the player to pos1 of the arena
        spawnPlayer(player, arena.getPos1());
        //Gives them the kit OR if they have custom kits for this kit they will get a book to choose one of them
        equipKit(player);

        //You MUST call this so everything works properly
        registerFight();

        //Start a five second cooldown which will set the players' match states to MATCH once finished
        //so they can start hitting each other
        startCooldown();
    }

    private void spawnBot() {

        if (bot.getNpc() == null || bot.getPlayerEntity() == null) {
            throw new IllegalStateException("The bot's player entity has not been spawned yet and is not operatable.");
        }

        spawnPlayer(bot.getPlayerEntity(), arena.getPos2());

        bot.equipKit(kit);
        bot.startFighting(this);
    }

    @Override
    protected void processDeath(Player dead, Player killer) {
        //If the bot dies, it should be removed
        if (dead.getUniqueId().equals(bot.getPlayerEntity().getUniqueId())) {
            bot.remove();
        }
    }

    @Override
    protected boolean isMatchOver() {
        //If there are 1 or less player(s) left, since this is a 1v1 fight
        return players.size() <= 1;
    }



    @Override
    public void endFight(Player lastDead) {
        IcePracticeAPI.broadcast(players, MESSAGES.getEndMessage());
        IcePracticeAPI.broadcast(spectators, MESSAGES.getEndMessage());

        //Send them the end messages defined in sendEndMessages();
        sendEndMessages(getWinnerTeam(lastDead), getLoserTeam(lastDead));

        for (Player winner : getWinnerTeam(lastDead)) {
            //Make sure every player who did not die is set into IDLE so they can't receive damage
            if (!winner.hasMetadata("NPC")) {
                IcePracticeAPI.getPlayerStateManager().setState(winner, PlayerState.IDLE);
            }

        }

        //Make the players remain in the arena for 4 seconds
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {

            //Teleport everyone back to spawn
            for (Player player : players) {
                if (!player.hasMetadata("NPC")) {
                    IcePracticeAPI.getSpawnpointManager().teleportToSpawn(player);
                }
            }

            for (Player player : spectators) {
                if (!player.hasMetadata("NPC")) {
                    IcePracticeAPI.getSpawnpointManager().teleportToSpawn(player);
                }
            }

            //You must call this to unregister the fight and make everything working properly
            unregisterFight();

            if (bot.getNpc() != null && bot.getNpc().isSpawned()) {
                //Removes the bot if necessary
                bot.remove();
            }
        }, 80L);
    }

    @Override
    public void addSpectator(Player player, boolean announce) {

        if (player.hasMetadata("NPC")) {
            return; //If it is a citizens npc, we want to stop
        }

        spectators.add(player); // Player should be added to spectators list
        IcePracticeAPI.getPlayerStateManager().setState(player, PlayerState.SPECTATING);//Handling player state

        //Giving them the leave item
        player.getInventory().clear();
        player.getInventory().setItem(8, ItemBuilder.create(Material.REDSTONE, "§cBack to spawn", null));

        player.setAllowFlight(true);
        player.setFlying(true);

        player.sendMessage(MESSAGES.getSoloSpectatorMessage(player, bot.getPlayerEntity(), kit.getName()));

        if (announce) {
            //If the other players should see that someone is now spectating, these messages are sent
            IcePracticeAPI.broadcast(players, MESSAGES.getNowSpectatingMessage(player));
            IcePracticeAPI.broadcast(spectators, MESSAGES.getNowSpectatingMessage(player));
        }

        player.teleport(arena.getCenter()); //Teleports the player to the arena's center

        //Makes sure the spectator can see the players of the match and their blocks etc. but the spectators are not seen by the playing players
        for (Player current : players) {
            IcePracticeAPI.getEntityHider().hideEntity(current, player);
            IcePracticeAPI.getEntityHider().showEntity(player, current);
        }
    }

    @Override
    public List<Player> getWinnerTeam(Player player) {
        if (!players.contains(player)) {
            return Collections.singletonList(bot.getPlayerEntity());
        } else {
            return Collections.singletonList(player);
        }
    }

    @Override
    public List<Player> getLoserTeam(Player player) {
        if (players.contains(player)) {
            return Collections.singletonList(bot.getPlayerEntity());
        } else {
            return Collections.singletonList(player);
        }
    }

    @Override
    public void sendEndMessages(List<Player> winners, List<Player> losers) {
        //You can be creative here
    }

    public final Bot getBot() {
        return bot;
    }
}
```

