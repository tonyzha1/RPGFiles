package items;

import java.util.List;

import buffs.Buff;
import entities.BattleEntity.Stat;

public class Weapon extends Item {
	private Stat weaponType;
	private int weaponPower;
	public Weapon(String name, Stat type, int power)
	{
		super(name, Slot.WEAPON);
		weaponType=type;
		weaponPower = power;
		
		
	}
	public Weapon(String name, Stat type, int power, List<Buff> buffs)
	{
		super(name, Slot.WEAPON, buffs);
		weaponType = type;
		weaponPower = power;
		
	}
	public Stat getType()
	{
		return weaponType;
	}
	public int getPower()
	{
		return weaponPower;
	}
}
