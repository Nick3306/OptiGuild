package io.github.Nick3306.OptiGuild;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Guild 
{
	ArrayList<UUID> members = new ArrayList<UUID>();
	ArrayList<UUID> coleaders = new ArrayList<UUID>();
	UUID leader;
	int rank;
	int wins;
	int losses;
	String name;
	
	Guild(String name, int rank, int wins, int losses, UUID leader)
	{
		this.name = name;
		this.rank = rank;
		this.wins = wins;
		this.losses = losses;
		this.leader = leader;
	}
	
	int getRank()
	{
		return rank;
	}
	int getWins()
	{
		return wins;
	}
	int getLosses()
	{
		return losses;
	}
	UUID getLeader()
	{
		return leader;
	}
	String getName()
	{
		return name;
	}
	void setRank(int rank)
	{
		this.rank = rank;
	}
	void setWins(int wins)
	{
		this.wins = wins;
	}
	void setLosses(int losses)
	{
		this.losses = losses;
	}
}