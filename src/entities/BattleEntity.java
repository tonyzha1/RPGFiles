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
	public BuffResolver buffs;

	public BattleEntity(String name, int str, int agi, int intel, Stat mainStat) {
		this.name = name;
		setHpStat(Stat.STRENGTH);
		maxHitPoints = calcHP();
		hitPoints = maxHitPoints;
		strength = str;
		agility = agi;
		intelligence = intel;
		this.mainStat = mainStat;
		buffs = new BuffResolver();
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

	public Stat getHPStat() {
		return hpStat;
	}

	public void setHpStat(Stat hpStat) {
		this.hpStat = hpStat;
	}

	public Stat getInitiativeStat() {
		return initiativeStat;
	}

	public int getInitiative() {
		double m = buffs.getMultiplier(BuffStats.INITIATIVE);
		int a = buffs.getAdditive(BuffStats.INITIATIVE);
		return (int) (m * getStatByName(getInitiativeStat()) + a);
	}

	public void setInitiativeStat(Stat initiativeStat) {
		this.initiativeStat = initiativeStat;
	}

	public void addBuff(Buff b) {
		buffs.addBuff(b);
	}

}
