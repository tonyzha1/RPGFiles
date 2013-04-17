package entities;

import buffs.Buff.BuffStats;
import items.Weapon;

public class Player extends BattleEntity {

	private Weapon weapon;
	private int maxMP, manaPoints;
	private Stat manaStat;
	public Player(String name, 
			int str, int agi, int intel, 
			Stat mainStat) {
		super(name, str, agi, intel, mainStat);
		// TODO Auto-generated constructor stub
		manaStat=Stat.INTELLIGENCE;
		maxMP=getStatByName(manaStat);
		manaPoints=maxMP;
		
	}

	@Override
	public Stat getAttackingStat() {
		// TODO Auto-generated method stub
		return weapon.getType();
	}
	
	public Stat getMPStat()
	{
		return manaStat;
	}
	public int calcMP()
	{
		double m = buffs.getMultiplier(BuffStats.MP);
		int a = buffs.getAdditive(BuffStats.MP);
		int mpTemp= (int) (m * getStatByName(manaStat) + a);
		return Math.max(mpTemp, 1);
	}
	
}
