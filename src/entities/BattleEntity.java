package entities;

import buffs.Buff;
import buffs.BuffResolver;
import buffs.Buff.BuffStats;

public abstract class BattleEntity {
	public enum Stat {
		STRENGTH, AGILITY, INTELLIGENCE
	}

	public final String name;
	private int strength, agility, intelligence;
	private int hitPoints, maxHitPoints;
	private Stat hpStat, initiativeStat;
	private Stat mainStat;
	private int damageBonus, damageReduction;
	public BuffResolver buffs;

	public BattleEntity(String name, int str, int agi, int intel, Stat mainStat) {
		this.name = name;
		strength = str;
		agility = agi;
		intelligence = intel;
		this.mainStat = mainStat;
		buffs = new BuffResolver();
		setHpStat(Stat.STRENGTH);
		maxHitPoints = calcHP();
		hitPoints = maxHitPoints;
		setDamageBonus(0);
		refresh();
	}

	protected int calcHP() {
		double m = buffs.getMultiplier(BuffStats.HP);
		int a = buffs.getAdditive(BuffStats.HP);
		int hpTemp = (int) (m * getStatByName(getHPStat()) + 3 + a);
		return Math.max(hpTemp, 1);
	}

	public boolean isDead() {
		return hitPoints <= 0;
	}

	public int getHP() {
		return hitPoints;
	}

	public int getMaxHP() {
		return maxHitPoints;
	}

	public Stat getAttackingStat() {
		return mainStat;
	}

	public Stat getDefendingStat() {
		return Stat.AGILITY;
	}

	public int getStatByName(Stat s) {
		if (s == Stat.AGILITY) {
			double m = buffs.getMultiplier(BuffStats.AGILITY);
			int a = buffs.getAdditive(BuffStats.AGILITY);
			return (int) (m * agility + a);
		}
		if (s == Stat.STRENGTH) {
			double m = buffs.getMultiplier(BuffStats.STRENGTH);
			int a = buffs.getAdditive(BuffStats.STRENGTH);
			return (int) (m * strength + a);
		}
		if (s == Stat.INTELLIGENCE) {
			double m = buffs.getMultiplier(BuffStats.INTELLIGENCE);
			int a = buffs.getAdditive(BuffStats.INTELLIGENCE);
			return (int) (m * intelligence + a);
		}
		return 0;
	}

	public int getUnbuffedStatByName(Stat s) {
		if (s == Stat.AGILITY)
			return agility;
		if (s == Stat.STRENGTH)
			return strength;
		if (s == Stat.INTELLIGENCE)
			return intelligence;
		return 0;
	}

	public Stat getHPStat() {
		return hpStat;
	}

	public int attack() {
		return getStatByName(getAttackingStat());
	}

	public int defense() {
		return getStatByName(getDefendingStat());
	}

	public void setHpStat(Stat hpStat) {
		this.hpStat = hpStat;
	}

	public Stat getInitiativeStat() {
		return initiativeStat;
	}

	public int getInitiative() {// buffed dextereity and unbuffed mainstat.
		double m = buffs.getMultiplier(BuffStats.INITIATIVE);
		int a = buffs.getAdditive(BuffStats.INITIATIVE);
		int unbuffedMain = getUnbuffedStatByName(mainStat);
		int initStat = getStatByName(getInitiativeStat());
		return (int) (m * initStat + a) + unbuffedMain;
	}

	public void setInitiativeStat(Stat initiativeStat) {
		this.initiativeStat = initiativeStat;
	}

	public void addBuff(Buff b) {
		buffs.addBuff(b);
		refresh();
	}

	public void tick() {
		buffs.tick();
		refresh();
	}

	public int takeDamage(int damage, boolean applyDR) {
		if (applyDR) {
			int newDamage = damage - damageReduction;
			if (newDamage < damage) {
				damage = newDamage;
				System.out.println(name + " ignores some of the damage!");
			}
		}
		hitPoints -= damage;
		if(hitPoints<0)
			hitPoints=0;
		return damage;
		// implement DR buffs here.
	}

	// public boolean isImmuneTo(), damage type
	protected void refresh() {
		maxHitPoints = calcHP();
		hitPoints = Math.min(maxHitPoints, hitPoints);
	}

	public int getDamageReduction() {
		return damageReduction;
	}

	public void setDamageReduction(int damageReduction) {
		this.damageReduction = damageReduction;
	}

	public int getDamageBonus() {
		return damageBonus;
	}

	public void setDamageBonus(int damageBonus) {
		this.damageBonus = damageBonus;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append(name + ": " + hitPoints + "/" + maxHitPoints + "\n");
		int strBefore = getUnbuffedStatByName(Stat.STRENGTH);
		int strAfter = getStatByName(Stat.STRENGTH);
		int agiBefore = getUnbuffedStatByName(Stat.AGILITY);
		int agiAfter = getStatByName(Stat.AGILITY);
		int intBefore = getUnbuffedStatByName(Stat.INTELLIGENCE);
		int intAfter = getStatByName(Stat.INTELLIGENCE);

		ret.append("STR: " + strBefore);
		if (strBefore != strAfter)
			ret.append("(" + strAfter + ")");
		ret.append("\nAGI: " + agiBefore);
		if (agiBefore != agiAfter)
			ret.append("(" + agiAfter+ ")");
		ret.append("\nINT: " + intBefore);
		if (intBefore != intAfter)
			ret.append("(" + intAfter + ")");
		String buffstring = buffs.listBuffs();
		if(buffstring.length()>0)
		{	
			ret.append("\nCurrent Buffs:\n");
			ret.append(buffs.listBuffs());
		}
		return ret.toString();

	}
}
