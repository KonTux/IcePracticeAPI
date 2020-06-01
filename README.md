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
