package io.github.Nick3306.OptiGuild;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Guild 
{
	ArrayList<Player> members = new ArrayList<Player>();
	ArrayList<Player> coleaders = new ArrayList<Player>();
	Player leader;
	int rank;
	int wins;
	int losses;
	String name;
	
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
	Player getLeader()
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