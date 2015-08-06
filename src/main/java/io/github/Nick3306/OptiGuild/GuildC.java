package io.github.Nick3306.OptiGuild;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
					UUID leader = ((Player) sender).getUniqueId();
					Guild newGuild = new Guild(args[1], 1, 0, 0, leader);
					plugin.guilds.add(newGuild);
					plugin.getConfig().set("guilds." + args[1] + ".Leader", leader);
					plugin.getConfig().set("guilds." + args[1] + ".Cod-Leaders", "");
					plugin.getConfig().set("guilds." + args[1] + ".Members", "");
					plugin.getConfig().set("guilds." + args[1] + ".rank", 1);
					plugin.getConfig().set("guilds." + args[1] + ".wins", 0);
					plugin.getConfig().set("guilds." + args[1] + ".losses", 0);
					return true;
				}
				else
				{
					sender.sendMessage("Incorrect usage! /g create (name)");
				}
			}
			if(args[0].equalsIgnoreCase("addmember"))
			{
				Boolean found = false;
				Guild target = null;
				if(args.length == 3)
				{
					for(int i = 0; i < plugin.guilds.size(); i++)
					{
						if(plugin.guilds.get(i).getName().equalsIgnoreCase(args[1]))
							{
								found = true;
								target = plugin.guilds.get(i);
							}
					}
					if(found = true)
					{
						UUID senderUUID = ((Player) sender).getUniqueId();
						if(senderUUID == target.getLeader() || target.coleaders.contains(senderUUID))
						{
							if (Bukkit.getOfflinePlayer(args[3]) != null)
							{
								UUID member = Bukkit.getOfflinePlayer(args[3]).getUniqueId();
								
								List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".members");
								list.add(member.toString());
								plugin.getConfig().set("guilds." + args[1] + ".Members", list);
								
								for(int i = 0; i < plugin.guilds.size(); i++)
								{
									if(target == plugin.guilds.get(i))
									{
										plugin.guilds.get(i).members.add(member);
									}
								}
							}
							else
							{
								sender.sendMessage("Player " + args[3] + " does not exist!!!" );
								return false;
							}
							
						}
						else
						{
							sender.sendMessage("You do not have permission to add members to this guild!");
							return false;
						}
						
					}
					else
					{
						sender.sendMessage("Guild does not exist!");
						return false;
					}
				}
				else
				{
					sender.sendMessage("Incorrect usage! /g addmemeber (guildname) (membername)");
					return false;
				}
			}
			if(args[0].equalsIgnoreCase("removemember"))
			{
				Boolean found = false;
				Guild target = null;
				if(args.length == 3)
				{
					for(int i = 0; i < plugin.guilds.size(); i++)
					{
						if(plugin.guilds.get(i).getName().equalsIgnoreCase(args[1]))
							{
								found = true;
								target = plugin.guilds.get(i);
							}
					}
					if(found = true)
					{
						UUID senderUUID = ((Player) sender).getUniqueId();
						if(senderUUID == target.getLeader() || target.coleaders.contains(senderUUID))
						{
							if (Bukkit.getOfflinePlayer(args[3]) != null)
							{
								UUID member = Bukkit.getOfflinePlayer(args[3]).getUniqueId();
								
								if(target.members.contains(member))
								{
									List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".members");
									list.remove(member.toString());
									plugin.getConfig().set("guilds." + args[1] + ".Members", list);
								
									for(int i = 0; i < plugin.guilds.size(); i++)
									{
										if(target == plugin.guilds.get(i))
										{
											plugin.guilds.get(i).members.remove(member);
										}
									}
								}
								else
								{
									sender.sendMessage("The player is not in the guild!");
								}
							}
							else
							{
								sender.sendMessage("Player " + args[3] + " does not exist!!!" );
								return false;
							}
							
						}
						else
						{
							sender.sendMessage("You do not have permission to remove members from this guild!");
							return false;
						}
						
					}
					else
					{
						sender.sendMessage("Guild does not exist!");
						return false;
					}
				}
				else
				{
					sender.sendMessage("Incorrect usage! /g addmemeber (guildname) (membername)");
					return false;
				}
			}
			if(args[0].equalsIgnoreCase("List"))
			{
				if(args.length == 1)
				{
					for(int i = 0; i < plugin.guilds.size(); i++)
					{
						sender.sendMessage(plugin.guilds.get(i).getName());
					}
					return true;
				}
				if(args.length == 2)
				{
					if(args[1].equals("rank"))
					{
						ArrayList<Guild>sorted = plugin.guilds;
						int low = 0;
						int high = sorted.size() -1;
						quickSort(sorted, low, high, "rank");
						for(int i = 0; i < sorted.size(); i++)
						{
							sender.sendMessage(sorted.get(i).getName());
						}
						return true;
					}
					if(args[1].equals("wins"))
					{
						ArrayList<Guild>sorted = plugin.guilds;
						int low = 0;
						int high = sorted.size() -1;
						quickSort(sorted, low, high, "wins");
						for(int i = 0; i < sorted.size(); i++)
						{
							sender.sendMessage(sorted.get(i).getName());
						}
						return true;
					}
					if(args[1].equals("losses"))
					{
						ArrayList<Guild>sorted = plugin.guilds;
						int low = 0;
						int high = sorted.size() -1;
						quickSort(sorted, low, high, "losses");
						for(int i = 0; i < sorted.size(); i++)
						{
							sender.sendMessage(sorted.get(i).getName());
						}
						return true;
					}
					
				}
			}
		}
		return false;
	}
	public void quickSort(ArrayList<Guild> sorted, int low, int high, String item) 
	{
		if(item.equalsIgnoreCase("rank"))
		{
			if (sorted == null || sorted.size() == 0)
				return;
			if (low >= high)
				return;
			// pick the pivot
			int middle = low + (high - low) / 2;
			int pivot = sorted.get(middle).getRank();
 
			// make left < pivot and right > pivot
			int i = low, j = high;
			while (i <= j) 
			{
				while (sorted.get(i).getRank() < pivot) 
				{
					i++;
				}
 
				while (sorted.get(j).getRank() > pivot) 
				{
					j--;
				}
 
				if (i <= j) 
				{
					Guild temp = sorted.get(i);
					sorted.set(i, sorted.get(j));
					sorted.set(j, temp);
					i++;
					j--;
				}
			}
			// recursively sort two sub parts
			if (low < j)
				quickSort(sorted, low, j, item);
			if (high > i)
				quickSort(sorted, i, high, item);
		}
		if(item.equalsIgnoreCase("wins"))
		{
			if (sorted == null || sorted.size() == 0)
				return;
			if (low >= high)
				return;
			// pick the pivot
			int middle = low + (high - low) / 2;
			int pivot = sorted.get(middle).getWins();
 
			// make left < pivot and right > pivot
			int i = low, j = high;
			while (i <= j) 
			{
				while (sorted.get(i).getWins() < pivot) 
				{
					i++;
				}
 
				while (sorted.get(j).getWins() > pivot) 
				{
					j--;
				}
 
				if (i <= j) 
				{
					Guild temp = sorted.get(i);
					sorted.set(i, sorted.get(j));
					sorted.set(j, temp);
					i++;
					j--;
				}
			}
			// recursively sort two sub parts
			if (low < j)
				quickSort(sorted, low, j, item);
			if (high > i)
				quickSort(sorted, i, high, item);
		}
		if(item.equalsIgnoreCase("losses"))
		{
			if (sorted == null || sorted.size() == 0)
				return;
			if (low >= high)
				return;
			// pick the pivot
			int middle = low + (high - low) / 2;
			int pivot = sorted.get(middle).getLosses();
 
			// make left < pivot and right > pivot
			int i = low, j = high;
			while (i <= j) 
			{
				while (sorted.get(i).getLosses() < pivot) 
				{
					i++;
				}
 
				while (sorted.get(j).getLosses() > pivot) 
				{
					j--;
				}
 
				if (i <= j) 
				{
					Guild temp = sorted.get(i);
					sorted.set(i, sorted.get(j));
					sorted.set(j, temp);
					i++;
					j--;
				}
			}
			// recursively sort two sub parts
			if (low < j)
				quickSort(sorted, low, j, item);
			if (high > i)
				quickSort(sorted, i, high, item);
		}
	}
}
