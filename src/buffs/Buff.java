package buffs;

import battle.Battle;

public class Buff {
	public enum TriggerTime {
		BEFORE_BATTLE, AFTER_ADVENTURE, ROUND_END, BEFORE_PLAYER_ATTACK, AFTER_PLAYER_ATTACK, BEFORE_ENEMY_ATTACK, AFTER_ENEMY_ATTACK, AFTER_ENEMY_HIT, AFTER_ENEMY_MISS, AFTER_PLAYER_HIT, AFTER_PLAYER_MISS, ON_PLAYER_ATTACK_ALL, ON_ENEMY_ATTACK_ALL, SKILL_USE, PASSIVE
	}

	public enum BuffStats {
		INITIATIVE, AGILITY, STRENGTH, INTELLIGENCE, HP, MP, CHANGES_HPSTAT, CHANGES_MPSTAT
	}

	public final String name;
	public final String description;
	protected int duration;
	private TriggerTime time;
	private BuffStats stats;
	private boolean isMultiplier;
	private boolean isAdditive;
	private double multiplier;
	private boolean display;
	// multipliers stack additively; e.g. *2 and *2 = *3
	private int additive;
	private Proc proc;// pass in using anonymous class.
	private boolean isProc;

	public Buff(String name, int duration, BuffStats stats,
			double multiplierChange, int additive, String description,
			boolean visible) {
		// multiplier or additive buff: Assumed to be passive.
		this.name = name;
		this.duration = duration;
		this.time = TriggerTime.PASSIVE;
		this.stats = stats;
		multiplier = multiplierChange;
		this.additive = additive;
		isAdditive = (additive != 0);
		isMultiplier = (multiplier != 0);
		isProc = false;
		proc = null;
		display = visible;
		this.description = description;
	}

	public Buff(Buff other) {
		this.name = other.name;
		this.duration = other.duration;
		this.time = other.time;
		this.stats = other.stats;
		this.multiplier = other.multiplier;
		this.additive = other.additive;
		this.isAdditive = other.isAdditive;
		this.isMultiplier = other.isMultiplier;
		this.proc = other.proc;
		this.isProc = other.isProc;
		this.description = other.description;
	}

	public Buff(String name, TriggerTime time, Proc p, int duration,
			String description, boolean visible) {// procs should never be
													// passive.
		proc = p;
		isProc = true;
		this.name = name;
		this.time = time;
		this.duration = duration;
		this.isAdditive = false;
		this.isMultiplier = false;
		display = visible;
		this.description = description;
		// everything else can be null.
	}

	public boolean isVisible() {
		return display;
	}

	public void setVisible(boolean visible) {
		display = visible;
	}

	public boolean equals(Buff other) {
		return this.name.equals(other.name);
	}

	public boolean isMultiplier() {
		return isMultiplier;
	}

	public boolean isAdditive() {
		return isAdditive;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public boolean isAction() {
		return isProc;
	}

	public void procure(Battle b)// Should only be called on proc buffs.
	{
		proc.action(b);
	}

	public int getAdditive() {
		return additive;
	}

	public void tick() {
		duration--;
	}

	public int getDuration() {
		return duration;
	}

	public BuffStats getStatAffected() {
		return stats;
	}

	public String toString() {
		return name;

	}
	public TriggerTime getTriggerTime()
	{
		return time;
	}
	
}
