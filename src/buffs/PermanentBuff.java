package buffs;

public class PermanentBuff extends Buff {
	public PermanentBuff(Buff other) {
		super(other);

	}

	public PermanentBuff(String name, BuffStats stats, double multiplierChange,
			int additive, String description, boolean visible) {
		super(name, 1, stats, multiplierChange, additive, description, visible);

	}

	public PermanentBuff(String name, TriggerTime time, Proc p,
			String description, boolean visible) {
		super(name, time, p, 1, description, visible);
	}
	public void tick()//don't count down.
	{
		;
	}
	public int getDuration()
	{
		return 1;
	}

}
