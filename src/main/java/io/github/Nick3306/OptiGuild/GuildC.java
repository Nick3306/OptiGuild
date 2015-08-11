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
					plugin.getConfig().set("guilds." + args[1] + ".Leader", leader.toString());
					plugin.getConfig().set("guilds." + args[1] + ".Cod-Leaders", "");
					plugin.getConfig().set("guilds." + args[1] + ".Members", "");
					plugin.getConfig().set("guilds." + args[1] + ".rank", 1);
					plugin.getConfig().set("guilds." + args[1] + ".wins", 0);
					plugin.getConfig().set("guilds." + args[1] + ".losses", 0);
					plugin.saveConfig();
					return true;
				}
				else
				{
					sender.sendMessage("Incorrect usage! /g create (name)");
					return false;
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
					if(found == true)
					{
						UUID senderUUID = ((Player) sender).getUniqueId();
						if(senderUUID == target.getLeader() || target.coleaders.contains(senderUUID))
						{
							if (Bukkit.getOfflinePlayer(args[2]) != null)
							{
								UUID member = Bukkit.getOfflinePlayer(args[2]).getUniqueId();
								for(int i = 0; i < plugin.guilds.size(); i++)
								{
									if(plugin.guilds.get(i).members.contains(member))
									{
										sender.sendMessage("This person is already in a guild!!!");
										return false;
									}
								}
								List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".members");
								list.add(member.toString());
								plugin.getConfig().set("guilds." + args[1] + ".Members", list);
								plugin.saveConfig();
								for(int i = 0; i < plugin.guilds.size(); i++)
								{
									if(target == plugin.guilds.get(i))
									{
										plugin.guilds.get(i).members.add(member);
										plugin.saveConfig();
										return true;
									}
								}
							}
							else
							{
								sender.sendMessage("Player " + args[2] + " does not exist!!!" );
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
							if (Bukkit.getOfflinePlayer(args[2]) != null)
							{
								UUID member = Bukkit.getOfflinePlayer(args[2]).getUniqueId();
								
								if(target.members.contains(member))
								{
									List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".members");
									list.remove(member.toString());
									plugin.getConfig().set("guilds." + args[1] + ".Members", list);
									plugin.saveConfig();
								
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
								sender.sendMessage("Player " + args[2] + " does not exist!!!" );
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
					sender.sendMessage("Incorrect usage! /g removememeber (guildname) (membername)");
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
			if(args[0].equalsIgnoreCase("promote"))
			{
				if(args.length == 3)
				{
					Guild target = null;
					boolean found = false;
					UUID senderUUID = ((Player) sender).getUniqueId();
					for(int i = 0; i < plugin.guilds.size(); i++)
					{
						if(plugin.guilds.get(i).getName().equalsIgnoreCase(args[2]))
							{
								found = true;
								target = plugin.guilds.get(i);
								
								if(senderUUID == plugin.guilds.get(i).getLeader())
								{
									UUID memberUUID = Bukkit.getOfflinePlayer(args[3]).getUniqueId();
									if(plugin.guilds.get(i).members.contains(memberUUID))
									{
										plugin.guilds.get(i).members.remove(memberUUID);
										plugin.guilds.get(i).coleaders.add(memberUUID);
										List<String> list = this.plugin.getConfig().getStringList("guilds." + args[2] + ".members");
										list.remove(memberUUID.toString());
										List<String> list2 = this.plugin.getConfig().getStringList("guilds." + args[2] + ".coleaders");
										list2.add(memberUUID.toString());
										plugin.saveConfig();
										return true;	
									}
									else
									{
										sender.sendMessage("This player is not a memeber int he guild!!!");
										return false;
									}
								}
								else
								{
									sender.sendMessage("You do not have permission to promote in this guild");
									return false;
								}
							}
					}	
				}
				else
				{
					sender.sendMessage("Incorrect usage!! /g promote (playername) (guildname)");
					return false;
				}
			}
			if(args[0].equalsIgnoreCase("Leave"))
			{
				if(args.length == 2)
				{
					UUID senderUUID = ((Player) sender).getUniqueId();
					for(int i = 0; i< plugin.guilds.size(); i++)
					{
						if(plugin.guilds.get(i).getName().equalsIgnoreCase(args[1]))
						{
							if(plugin.guilds.get(i).members.contains(senderUUID))
							{
								List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".members");
								list.remove(senderUUID.toString());
								plugin.getConfig().set("guilds." + args[1] + ".Members", list);
								plugin.saveConfig();
								plugin.guilds.get(i).members.remove(senderUUID);
							}
							if(plugin.guilds.get(i).coleaders.contains(senderUUID))
							{
								List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".coleaders");
								list.remove(senderUUID.toString());
								plugin.getConfig().set("guilds." + args[1] + ".coleaders", list);
								plugin.saveConfig();
								plugin.guilds.get(i).coleaders.remove(senderUUID);
							}
							if(plugin.guilds.get(i).leader == senderUUID)
							{
								sender.sendMessage("You must promote a new leader before leaving!!");
								return false;
							}
						}
						else
						{
							sender.sendMessage("Tat guild does not exist!!!");
							return false;
						}
					}
				}
				else
				{
					sender.sendMessage("Incorrect usage!! /g leave (guildname)");
					return false;
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
