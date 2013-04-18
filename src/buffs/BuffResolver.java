package buffs;

import java.util.ArrayList;
import java.util.List;

import battle.Battle;
import buffs.Buff.BuffStats;
import buffs.Buff.TriggerTime;

public class BuffResolver {
	private List<Buff> buffList;

	public BuffResolver() {
		buffList = new ArrayList<Buff>();
	}

	public double getMultiplier(BuffStats stat) {
		double sum = 0;
		for (Buff b : buffList) {
			if (b.getStatAffected() == stat && b.isMultiplier())
				sum += b.getMultiplier();
		}
		return 1 + sum;
	}

	public int getAdditive(BuffStats stat) {
		int sum = 0;

		for (Buff b : buffList) {

			if (stat.equals(b.getStatAffected()) && b.isAdditive()) {

				sum += b.getAdditive();
			}

		}
		// System.out.println(sum);
		return sum;
	}

	public void addBuff(Buff b) {
		buffList.add(b);
	}

	public boolean removeBuff(Buff b) {
		return buffList.remove(b);
	}

	public int buffCount() {
		return buffList.size();
	}

	public void tick() {
		for (Buff b : buffList) {
			b.tick();
			if (b.getDuration() <= 0) {
				buffList.remove(b);
			}
		}
	}

	public String listBuffs() {
		StringBuilder ret = new StringBuilder();
		for (Buff b : buffList) {
			if (b.isVisible())
				ret.append(b.name + "\n");

		}
		return ret.toString();
	}

	public void procAtTime(TriggerTime time, Battle battle) {
		for (Buff b : buffList) {
			if (b.isAction() && b.getTriggerTime() == time) {
				b.procure(battle);
			}
		}
	}

}
