package entities;

public class Enemy extends BattleEntity {

	public Enemy(String name,
			int str, int agi, int intel, Stat mainStat) {
		super(name, str, agi, intel, mainStat);
		// TODO Auto-generated constructor stub
	}
	public Enemy(String name, int level)
	{
		super(name, level, level, level, Stat.STRENGTH);
	}

}
