package buffs;

public class Buff {
	public enum TriggerTime{
		BEFORE_BATTLE, AFTER_ADVENTURE, ROUND_END, ALLY_ATTACK, 
		ENEMY_ATTACK, SKILL_USE, PASSIVE
	}
	public enum BuffStats{
		INITIATIVE, AGILITY, STRENGTH, INTELLIGENCE, HP, MP,
		CHANGES_HPSTAT, CHANGES_MPSTAT
	}
	public final String name;
	private int duration;
	private TriggerTime time;
	private BuffStats stats;
	private boolean isMultiplier;
	private boolean isAdditive;
	private double multiplier;
	//multipliers stack additively; e.g. *2 and *2 = *3
	private int additive;
	public Buff(String name, int duration, TriggerTime time, BuffStats stats, 
			double multiplierChange, int additive)
	{
		this.name=name;
		this.duration=duration;
		this.time=time;
		this.stats=stats;
		multiplier=multiplierChange;
		this.additive=additive;
		isAdditive = (additive==0);
		isMultiplier=(multiplier==0);
	}
	public Buff(Buff other)
	{
		this.name=other.name;
		this.duration=other.duration;
		this.time=other.time;
		this.stats=other.stats;
		this.multiplier=other.multiplier;
		this.additive=other.additive;
		this.isAdditive=other.isAdditive;
		this.isMultiplier=other.isMultiplier;
	}
	public boolean equals(Buff other)
	{
		return this.name.equals(other.name);
	}
	public boolean isMultiplier()
	{
		return isMultiplier;
	}
	public boolean isAdditive()
	{
		return isAdditive;
	}
	public double getMultiplier()
	{
		return multiplier;
	}
	public int getAdditive()
	{
		return additive;
	}
	public void tick()
	{
		duration--;
	}
	public int getDuration()
	{
		return duration;
	}
	public BuffStats getStatAffected()
	{
		return stats;
	}
}
