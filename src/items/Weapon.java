package items;

import entities.BattleEntity.Stat;

public class Weapon extends Item {
	private Stat weaponType;
	public Weapon(Stat type)
	{
		slot=Slot.WEAPON;
		weaponType=type;
	}
	public Stat getType()
	{
		return weaponType;
	}
}
