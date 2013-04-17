package buffs;

import java.util.ArrayList;
import java.util.List;

import buffs.Buff.BuffStats;

public class BuffResolver {
	private List<Buff> buffList;
	public BuffResolver()
	{
		buffList = new ArrayList<Buff>();
	}
	public double getMultiplier(BuffStats stat)
	{
		double sum = 0;
		for(Buff b : buffList)
		{
			if(b.getStatAffected() == stat && b.isMultiplier())
				sum+=b.getMultiplier();
		}
		return 1+sum;
	}
	public int getAdditive(BuffStats stat)
	{
		int sum = 0;
		for(Buff b : buffList)
		{
			if(b.getStatAffected() == stat && b.isAdditive())
				sum+=b.getAdditive();
		}
		return 1+sum;
	}
	public void addBuff(Buff b)
	{
		buffList.add(new Buff(b));
	}
	public void tick()
	{
		for(Buff b : buffList)
		{
			b.tick();
			if(b.getDuration()<=0)
			{
				buffList.remove(b);
			}
		}
	}


}
