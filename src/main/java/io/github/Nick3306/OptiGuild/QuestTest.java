package io.github.Nick3306.OptiGuild;

public class QuestTest 
{
	String name;
	String details;
	boolean phase1;
	boolean phase2;
	boolean phase3;
	boolean john;
	int skeletonsKilled;
	QuestTest(int a, String name)
	{
		//this sets quest up for a player who has completed any phase, 0 being the start of the quest
		if(a == 0)
		{
			this.name = name;
		}
		if(a == 1)
		{
			phase1 = true;
		}
		if(a == 2)
		{
			phase1 = true;
			phase2 = true;
			john = true;
		}
		if(a == 3)
		{
			phase1 = true;
			phase2 = true;
			phase3 = true;
		}
	}
	String getInfo() //gives player info on quest and current objective in quest
	{
		if(phase1 != true)
		{
			return "John the blacksmith want bones from skeletons " + '\n' + "You have just started this quest, kill skeletons";
		}
		if(phase1 == true && phase2 != true)
		{
			return  "John the blacksmith want bones from skeletons " + '\n' + "you are in phase two of this quest, talk to John the blacksmith";
		}
		return "";
	}
	void setSKills(int a)
	{
		skeletonsKilled = skeletonsKilled + a;
	}
	int getSKills()
	{
		return skeletonsKilled;
	}
	
}
