package entities;

import items.Weapon;
import buffs.Buff;
import buffs.Buff.BuffStats;

public class Player extends BattleEntity {

	private Weapon weapon;

	private int maxMP, manaPoints;
	private Stat manaStat;

	public Player(String name, int str, int agi, int intel, Stat mainStat) {
		super(name, str, agi, intel, mainStat);
		// TODO Auto-generated constructor stub
		manaStat = Stat.INTELLIGENCE;
		maxMP = getStatByName(manaStat);
		manaPoints = maxMP;

	}



	@Override
	public Stat getAttackingStat() {
		// TODO Auto-generated method stub
		if (weapon != null)
			return weapon.getType();
		return Stat.STRENGTH;
	}

	public int getWeaponPower() {
		if (weapon != null)
			return weapon.getPower();
		return 10;
	}

	public Stat getMPStat() {
		return manaStat;
	}

	public int calcMP() {
		double m = buffs.getMultiplier(BuffStats.MP);
		int a = buffs.getAdditive(BuffStats.MP);
		int mpTemp = (int) (m * getStatByName(manaStat) + a);
		return Math.max(mpTemp, 1);
	}

	public void equip(Weapon w) {
		if (weapon != null) {
			for (Buff b : weapon.buffs)
				buffs.removeBuff(b);
		}
		weapon = w;
		if (weapon != null) {
			for (Buff b : weapon.buffs) {
				buffs.addBuff(b);
			}
		}
		refresh();
	}

	protected void refresh() {
		super.refresh();
		maxMP = calcMP();
		manaPoints = Math.max(maxMP, manaPoints);

	}
}
