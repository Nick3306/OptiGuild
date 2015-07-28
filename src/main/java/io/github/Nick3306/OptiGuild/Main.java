package io.github.Nick3306.OptiGuild;

import java.util.ArrayList;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin
{
	ArrayList<Guild> guilds = new ArrayList<Guild>();
    public void onEnable()
    {
    	PluginManager pm = getServer().getPluginManager();
    	this.getConfig().options().copyDefaults(true);
    	saveDefaultConfig();
    }
}
