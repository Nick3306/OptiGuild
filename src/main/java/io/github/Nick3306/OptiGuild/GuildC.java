package io.github.Nick3306.OptiGuild;



import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GuildC implements CommandExecutor
{
	private Main plugin;
	public GuildC(Main plugin)
	 {
	   this.plugin = plugin;
	 }
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(command.getName().equalsIgnoreCase("guild") || command.getName().equalsIgnoreCase("g"))
		{
			if (args[0].equalsIgnoreCase("create"))
			{
				if(args.length == 2)
				{
					for(int i = 0; i < this.plugin.guilds.size(); i++)
					{
						if(this.plugin.guilds.get(i).getName().equalsIgnoreCase(args[1]))
						{
							sender.sendMessage("There is already a guild with this name!");
							return false;
						}
					}
				}
				else
				{
					sender.sendMessage("Incorrect usage! /g create (name)");
				}
			}
		}
		return false;
	}
	
}
