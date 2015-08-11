package io.github.Nick3306.OptiGuild;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin
{
	ArrayList<Guild> guilds = new ArrayList<Guild>();
    public void onEnable()
    {
    	getCommand("Guild").setExecutor(new GuildC(this));
    	PluginManager pm = getServer().getPluginManager();
    	this.getConfig().options().copyDefaults(true);
    	saveDefaultConfig();
    	if(this.getConfig().getConfigurationSection("arenas") != null)
		{
    		getLogger().info("Getting Guilds");
    		String[] configGuilds = this.getConfig().getConfigurationSection("guilds").getKeys(false).toArray(new String[0]);
    		for(int i = 0; i < configGuilds.length; i++)
    		{
    			String name = configGuilds[i];
    			int rank = this.getConfig().getInt("guilds." + configGuilds[i]  + ".rank");
    			int wins =  this.getConfig().getInt("guilds." + configGuilds[i]  + ".wins");
    			int losses = this.getConfig().getInt("guilds." + configGuilds[i]  + ".losses");
    			String leader = this.getConfig().getString("guilds." + configGuilds[i]  + ".leader");
    			UUID leaderUUID = UUID.fromString(leader);
    			List<String> members = this.getConfig().getStringList("guilds." + name + ".members");
    			Guild addGuild = new Guild(name, rank, wins, losses, leaderUUID);
    			for(int j = 0; j < members.size(); j++)
    			{
    				UUID memberUUID = UUID.fromString(members.get(j));
    				addGuild.members.add(memberUUID);
    			}
    			List<String> coleaders = this.getConfig().getStringList("guilds." + name + ".coleaders");
    			for(int j = 0; j < coleaders.size(); j++)
    			{
    				UUID coleaderUUID = UUID.fromString(coleaders.get(j));
    				addGuild.members.add(coleaderUUID);
    			}
    			guilds.add(addGuild);
    		}
    	}
    	else
    	{
    		getLogger().info("No guilds to include");
    	}
    		
    	
    }
}
