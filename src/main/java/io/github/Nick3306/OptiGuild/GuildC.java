package io.github.Nick3306.OptiGuild;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildC implements CommandExecutor
{
	ArrayList<String> delete = new ArrayList<String>();
	private Main plugin;
	public GuildC(Main plugin)
	 {
	   this.plugin = plugin;
	 }
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(command.getName().equalsIgnoreCase("guild") || command.getName().equalsIgnoreCase("g"))
		{
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.DARK_RED + "______OptiGuild______");
				sender.sendMessage(ChatColor.GREEN+ "/g create (guildname)");
				sender.sendMessage(ChatColor.YELLOW + "Creates a guild");
				sender.sendMessage(ChatColor.GREEN+"/g addmember (guildname) (playername)");
				sender.sendMessage(ChatColor.YELLOW + "Add a player to the specified guild");
				sender.sendMessage(ChatColor.GREEN+"/g removemember (guildname) (playername)");
				sender.sendMessage(ChatColor.YELLOW + "Removes a player from specified guild");
				sender.sendMessage(ChatColor.GREEN+"/g leave");
				sender.sendMessage(ChatColor.YELLOW + "Leave whatever guild you are in");
				sender.sendMessage(ChatColor.GREEN+"/g promote (guildname) (playername) (coleader/leader)");
				sender.sendMessage(ChatColor.YELLOW + "Promotes a member to the specified position");
				sender.sendMessage(ChatColor.GREEN+"/g list (rank/wins/losses)");
				sender.sendMessage(ChatColor.YELLOW + "Lists all guilds sorted in the specified manner or by creation date with no param");
				sender.sendMessage(ChatColor.GREEN+"/g delete (guildname)");
				sender.sendMessage(ChatColor.YELLOW + "Deletes the specified guild");
				sender.sendMessage(ChatColor.GREEN+"/g me");
				sender.sendMessage(ChatColor.YELLOW + "Gives info on the guild you are in");
				sender.sendMessage(ChatColor.GREEN+"/g info (guildname)");
				sender.sendMessage(ChatColor.YELLOW + "Gives info on the guild you specified");
			}
		else{
			if (args[0].equalsIgnoreCase("create"))
			{
				UUID senderUUID = ((Player) sender).getUniqueId();
				if(args.length == 2)
				{
					for(int i = 0; i < plugin.guilds.size(); i++)
					{
						if(plugin.guilds.get(i).leader.equals(senderUUID) || plugin.guilds.get(i).coleaders.contains(senderUUID) || plugin.guilds.get(i).members.contains(senderUUID))
						{
							sender.sendMessage("You are already in a guild!!!");
							return false;
						}
					}
					for(int i = 0; i < this.plugin.guilds.size(); i++)
					{
						if(this.plugin.guilds.get(i).getName().equalsIgnoreCase(args[1]))
						{
							sender.sendMessage("There is already a guild with this name!");
							return false;
						}
					}
					Guild newGuild = new Guild(args[1], 1, 0, 0, senderUUID);
					plugin.guilds.add(newGuild);
					plugin.getConfig().set("guilds." + args[1] + ".leader", senderUUID.toString());
					plugin.getConfig().set("guilds." + args[1] + ".coleaders", "");
					plugin.getConfig().set("guilds." + args[1] + ".members", "");
					plugin.getConfig().set("guilds." + args[1] + ".rank", 1);
					plugin.getConfig().set("guilds." + args[1] + ".wins", 0);
					plugin.getConfig().set("guilds." + args[1] + ".losses", 0);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GREEN + "Guild Created!");
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
						if(senderUUID.equals(target.getLeader()) || target.coleaders.contains(senderUUID))
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
								plugin.getConfig().set("guilds." + args[1] + ".members", list);
								plugin.saveConfig();
								for(int i = 0; i < plugin.guilds.size(); i++)
								{
									if(target == plugin.guilds.get(i))
									{
										plugin.guilds.get(i).members.add(member);
										plugin.saveConfig();
										sender.sendMessage(ChatColor.GREEN + "Member added!!");
										return true;
									}
								}
							}
							else
							{
								sender.sendMessage(ChatColor.GREEN + "Player " + args[2] + " does not exist!!!" );
								return false;
							}
							
						}
						else
						{
							sender.sendMessage(ChatColor.GREEN + "You do not have permission to add members to this guild!");
							return false;
						}
						
					}
					else
					{
						sender.sendMessage(ChatColor.GREEN + "Guild does not exist!");
						return false;
					}
				}
				else
				{
					sender.sendMessage(ChatColor.GREEN + "Incorrect usage! /g addmemeber (guildname) (membername)");
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
						if(senderUUID.equals(target.getLeader()) || target.coleaders.contains(senderUUID))
						{
							if (Bukkit.getOfflinePlayer(args[2]) != null)
							{
								UUID member = Bukkit.getOfflinePlayer(args[2]).getUniqueId();
								if(target.getLeader().equals(member))
								{
									sender.sendMessage(ChatColor.GREEN + "You can not remove the leader from the guild!");
									return false;
								}
								if(target.coleaders.contains(senderUUID) && target.coleaders.contains(member))
								{
									sender.sendMessage(ChatColor.GREEN + "Coleaders can not remove other coleaders!");
								}
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
											sender.sendMessage(ChatColor.GREEN + "Player removed from guild");
											return true;
										}
									}
								}
								else
								{
									sender.sendMessage(ChatColor.GREEN + "The player is not in the guild!");
								}
							}
							else
							{
								sender.sendMessage(ChatColor.GREEN + "Player " + args[2] + " does not exist!!!" );
								return false;
							}
							
						}
						else
						{
							sender.sendMessage(ChatColor.GREEN + "You do not have permission to remove members from this guild!");
							return false;
						}
						
					}
					else
					{
						sender.sendMessage(ChatColor.GREEN + "Guild does not exist!");
						return false;
					}
				}
				else
				{
					sender.sendMessage(ChatColor.GREEN + "Incorrect usage! /g removememeber (guildname) (membername)");
					return false;
				}
			}
			if(args[0].equalsIgnoreCase("List"))
			{
				if(args.length == 1)
				{
					sender.sendMessage(ChatColor.GREEN + "______Guilds______");
					for(int i = 0; i < plugin.guilds.size(); i++)
					{
						sender.sendMessage(ChatColor.YELLOW +plugin.guilds.get(i).getName());
					}
					return true;
				}
				if(args.length == 2)
				{
					sender.sendMessage(ChatColor.GREEN + "______Guilds______");
					if(args[1].equals("rank"))
					{
						ArrayList<Guild>sorted = plugin.guilds;
						int low = 0;
						int high = sorted.size() -1;
						quickSort(sorted, low, high, "rank");
						for(int i = 0; i < sorted.size(); i++)
						{
							sender.sendMessage(ChatColor.YELLOW + sorted.get(i).getName());
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
							sender.sendMessage(ChatColor.YELLOW + sorted.get(i).getName());
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
							sender.sendMessage(ChatColor.YELLOW + sorted.get(i).getName());
						}
						return true;
					}
					
				}
			}
			if(args[0].equalsIgnoreCase("promote"))
			{
				if(args.length == 3)
				{
					UUID senderUUID = ((Player) sender).getUniqueId();
					if(args[3].equalsIgnoreCase("leader"))
					{
						for(int i = 0; i < plugin.guilds.size(); i++)
						{
							if(plugin.guilds.get(i).getName().equalsIgnoreCase(args[1]))
								{
									if(senderUUID.equals(plugin.guilds.get(i).getLeader()))
									{
										UUID memberUUID = Bukkit.getOfflinePlayer(args[2]).getUniqueId();
										if(plugin.guilds.get(i).members.contains(memberUUID))
										{
											plugin.guilds.get(i).members.remove(memberUUID);
											plugin.guilds.get(i).leader = memberUUID;
											List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".members");
											list.remove(memberUUID.toString());
											list.add(senderUUID.toString());
											plugin.getConfig().set("guilds." + args[2] + ".members", list);
											this.plugin.getConfig().set("guilds." + args[1] + ".leader", memberUUID.toString());
											plugin.saveConfig();
											sender.sendMessage(ChatColor.GREEN + "User has been promoted!");
											return true;	
										}
										if(plugin.guilds.get(i).coleaders.contains(memberUUID))
										{
											plugin.guilds.get(i).coleaders.remove(memberUUID);
											plugin.guilds.get(i).leader = memberUUID;
											List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".coleaders");
											list.remove(memberUUID.toString());
											plugin.getConfig().set("guilds." + args[2] + ".coleaders", list);
											List<String> list2 = this.plugin.getConfig().getStringList("guilds." + args[1] + ".members");
											list2.add(senderUUID.toString());
											plugin.getConfig().set("guilds." + args[2] + ".members", list2);	
											this.plugin.getConfig().set("guilds." + args[1] + ".leader", memberUUID.toString());
											plugin.saveConfig();
											sender.sendMessage(ChatColor.GREEN + "User has been promoted!");
											return true;	
										}
										else
										{
											sender.sendMessage(ChatColor.GREEN + "This player is not a memeber in the guild");
											return false;
										}
									}
									else
									{
										sender.sendMessage(ChatColor.GREEN + "You do not have permission to promote in this guild");
										return false;
									}
								}
						}
					}
					if(args[3].equalsIgnoreCase("coleader"))
					{
						for(int i = 0; i < plugin.guilds.size(); i++)
						{
							if(plugin.guilds.get(i).getName().equalsIgnoreCase(args[1]))
								{
									if(senderUUID == plugin.guilds.get(i).getLeader())
									{
										UUID memberUUID = Bukkit.getOfflinePlayer(args[2]).getUniqueId();
										if(plugin.guilds.get(i).members.contains(memberUUID))
										{
											plugin.guilds.get(i).members.remove(memberUUID);
											plugin.guilds.get(i).coleaders.add(memberUUID);
											List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".members");
											list.remove(memberUUID.toString());
											plugin.getConfig().set("guilds." + args[2] + ".members", list);
											List<String> list2 = this.plugin.getConfig().getStringList("guilds." + args[1] + ".coleaders");
											list2.add(memberUUID.toString());
											plugin.getConfig().set("guilds." + args[1] + ".coleaders", list);
											plugin.saveConfig();
											sender.sendMessage(ChatColor.GREEN + "User has been promoted!");
											return true;	
										}
										else
										{
											sender.sendMessage(ChatColor.GREEN + "This player is not a memeber in the guild!!!");
											return false;
										}
									}
									else
									{
										sender.sendMessage(ChatColor.GREEN + "You do not have permission to promote in this guild");
										return false;
									}
								}
						}	
					}
				}
				else
				{
					sender.sendMessage(ChatColor.GREEN + "Incorrect usage!! /g promote (guildname) (playername) (position)");
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
								sender.sendMessage(ChatColor.GREEN + "You have left the guild " + args[1]);
								return true;
							}
							if(plugin.guilds.get(i).coleaders.contains(senderUUID))
							{
								List<String> list = this.plugin.getConfig().getStringList("guilds." + args[1] + ".coleaders");
								list.remove(senderUUID.toString());
								plugin.getConfig().set("guilds." + args[1] + ".coleaders", list);
								plugin.saveConfig();
								plugin.guilds.get(i).coleaders.remove(senderUUID);
								sender.sendMessage(ChatColor.GREEN + "You have left the guild " + args[1]);
								return true;
							}
							if(plugin.guilds.get(i).leader.equals(senderUUID))
							{
								sender.sendMessage(ChatColor.GREEN + "You must promote a new leader before leaving!!");
								return false;
							}
						}
						else
						{
							sender.sendMessage(ChatColor.GREEN + "That guild does not exist!!!");
							return false;
						}
					}
				}
				else
				{
					sender.sendMessage(ChatColor.GREEN + "Incorrect usage!! /g leave (guildname)");
					return false;
				}
			}
			if(args[0].equalsIgnoreCase("me"))
			{
				UUID senderUUID = ((Player) sender).getUniqueId();
				for(int i = 0; i < plugin.guilds.size(); i++)
				{
					if(plugin.guilds.get(i).getLeader().equals(senderUUID) || plugin.guilds.get(i).members.contains(senderUUID) || plugin.guilds.get(i).coleaders.contains(senderUUID))
					{
						sender.sendMessage(ChatColor.RED + "Name:" + ChatColor.GRAY +plugin.guilds.get(i).getName());
						sender.sendMessage(ChatColor.RED + "Leader:" + ChatColor.GRAY + Bukkit.getPlayer(plugin.guilds.get(i).getLeader()).getName());
						sender.sendMessage(ChatColor.RED + "Coleaders:");
						for(int j = 0; j < plugin.guilds.get(i).coleaders.size(); j++)
						{
							sender.sendMessage(ChatColor.GRAY + "" + Bukkit.getPlayer(plugin.guilds.get(i).coleaders.get(i)).getName());
						}
						sender.sendMessage(ChatColor.RED + "Rank:" + ChatColor.GRAY + plugin.guilds.get(i).getRank());
						sender.sendMessage(ChatColor.RED + "Wins:" + ChatColor.GRAY +plugin.guilds.get(i).getWins());
						sender.sendMessage(ChatColor.RED + "Losses:" + ChatColor.GRAY +plugin.guilds.get(i).getLosses());
						return true;
					}
				}
				sender.sendMessage(ChatColor.GREEN + "You are not in a guild!");
				return false;
			}
			if(args[0].equalsIgnoreCase("info"))
			{
				if(args.length == 2)
				{
					for(int i = 0; i < plugin.guilds.size(); i++)
					{
						if(plugin.guilds.get(i).getName().equalsIgnoreCase(args[1]))
						{
							sender.sendMessage(ChatColor.RED + "Name:" + ChatColor.GRAY +plugin.guilds.get(i).getName());
							sender.sendMessage(ChatColor.RED + "Leader:" + ChatColor.GRAY + Bukkit.getPlayer(plugin.guilds.get(i).getLeader()).getName());
							sender.sendMessage(ChatColor.RED + "Coleaders:");
							for(int j = 0; j < plugin.guilds.get(i).coleaders.size(); j++)
							{
								sender.sendMessage(ChatColor.GRAY + "" + Bukkit.getPlayer(plugin.guilds.get(i).coleaders.get(i)).getName());
							}
							sender.sendMessage(ChatColor.RED + "Rank:" + ChatColor.GRAY + plugin.guilds.get(i).getRank());
							sender.sendMessage(ChatColor.RED + "Wins:" + ChatColor.GRAY +plugin.guilds.get(i).getWins());
							sender.sendMessage(ChatColor.RED + "Losses:" + ChatColor.GRAY +plugin.guilds.get(i).getLosses());
							return true;
						}
					}
				}
				else
				{
					sender.sendMessage(ChatColor.GREEN + "Incorrect usage! /g info (guildname)");
					return false;
				}
			}
			if(args[0].equalsIgnoreCase("delete"))
			{
				if(args.length == 2)
				{
					String senderName = sender.getName();
					sender.sendMessage(ChatColor.RED + "WARNING this will delete your guild permanently. If you wish to continue, type /g yes (guildname)");
					delete.add(senderName);
					return true;
				}
				else
				{
					sender.sendMessage(ChatColor.GREEN + "Incorrect usage! /g delete (guildname)");
					return false;
				}
			}
			if(args[0].equalsIgnoreCase("yes"))
			{
				UUID senderUUID = ((Player) sender).getUniqueId();
				String senderName = sender.getName();			
				if(args.length == 2)
				{
					if(delete.contains(senderName))
					{
						for(int i = 0; i < plugin.guilds.size(); i++)
						{
							if(plugin.guilds.get(i).getName().equalsIgnoreCase(args[1]))
							{	
								if(plugin.guilds.get(i).getLeader().equals(senderUUID))
								{
									plugin.guilds.remove(i);
									plugin.getConfig().getConfigurationSection("guilds").set(args[1], null);
									plugin.saveConfig();
									sender.sendMessage(ChatColor.GREEN + "Guild Deleted");
									delete.remove(senderName);
									return true;
								}
								else
								{
									sender.sendMessage(ChatColor.GREEN + "You are not the leader of that guild!");
									return false;
								}	
					
							}
						
						}
						sender.sendMessage(ChatColor.GREEN + "That guild does not exist!!");
						delete.remove(senderName);
						return false;
					}
				}
				else
				{
					sender.sendMessage(ChatColor.GREEN + "Incorrect Usage! /g yes (guildname)");
					return false;
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
